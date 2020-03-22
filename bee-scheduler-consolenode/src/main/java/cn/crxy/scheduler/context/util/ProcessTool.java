package cn.crxy.scheduler.context.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class ProcessTool {
	private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	private ExecutorService executorService = Executors.newCachedThreadPool();
	final CountDownLatch countDownLatch = new CountDownLatch(3);

	public String run(String cmd) throws Exception {
		doRun(cmd);
		countDownLatch.await();
		executorService.shutdown();
		List<String> lines = Lists.newArrayList();
		String line = null;
		while ((line = queue.poll()) != null) {
			lines.add(line);
		}

		return Joiner.on("<p>").join(lines);
	}

	private void doRun(String cmd) throws Exception {
		executorService.execute(() -> {
			try {
				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec(cmd);
				InputStream err = process.getErrorStream();
				InputStream input = process.getInputStream();
				readStreamInfo(err, input);
				int i = process.waitFor();
				process.destroy();
				if (i == 0) {
					queue.add("正常退出");
				} else {
					queue.add("异常结束");
				}
			} catch (Exception e) {

			} finally {
				countDownLatch.countDown();
			}
		});
	}

	private void readStreamInfo(InputStream... inputStreams) {
		for (InputStream inputStream : inputStreams) {
			executorService.execute(() -> {
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
					String line = null;
					while ((line = br.readLine()) != null) {
						queue.add(line);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						inputStream.close();
					} catch (IOException e) {

					}
					countDownLatch.countDown();
				}
			});
		}
	}

	public static boolean isLinux() {
		return OS.indexOf("linux") >= 0;
	}

	public static boolean isWindows() {
		return OS.indexOf("windows") >= 0;
	}

	private static String OS = System.getProperty("os.name").toLowerCase();

	public static void main(String[] args) throws Exception {
		new ProcessTool().run("python C:\\Users\\Administrator\\AppData\\Local\\Temp\\a.py");
	}
}