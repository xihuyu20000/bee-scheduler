package cn.crxy.scheduler.consolenode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crxy.scheduler.consolenode.dao.StandardDao;
import cn.crxy.scheduler.consolenode.entity.User;
import cn.crxy.scheduler.consolenode.service.UserService;

/**
 * @author  吴超
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private StandardDao standardDao;

    @Override
    public User getByAccount$Pwd(String account, String pwd) {
        return standardDao.getUserByAccount$Pwd(account, pwd);
    }

    @Override
    public void updatePwdByAccount(String account, String pwd) {
        User user = new User();
        user.setAccount(account);
        user.setPwd(pwd);
        standardDao.updateUserByAccount(user);
    }
}