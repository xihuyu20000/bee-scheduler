package cn.crxy.scheduler.consolenode.service;


import cn.crxy.scheduler.consolenode.entity.User;

/**
 * @author  吴超
 */
public interface UserService {
    User getByAccount$Pwd(String account, String pwd);

    void updatePwdByAccount(String account, String pwd);
}
