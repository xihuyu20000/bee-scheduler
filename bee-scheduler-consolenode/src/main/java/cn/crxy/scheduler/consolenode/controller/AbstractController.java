package cn.crxy.scheduler.consolenode.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.crxy.scheduler.consolenode.model.UserPassport;
import cn.crxy.scheduler.consolenode.util.Constants;

import javax.servlet.http.HttpServletRequest;

public class AbstractController {
    protected Log logger = LogFactory.getLog(getClass());

    public UserPassport getUserPassport(HttpServletRequest request) {
        return (UserPassport) request.getAttribute(Constants.REQUEST_ATTR_KEY_USER_PASSPORT);
    }
}
