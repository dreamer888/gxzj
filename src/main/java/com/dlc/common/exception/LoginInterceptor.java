package com.dlc.common.exception;

import com.dlc.common.utils.CodeAndMsg;
import com.dlc.common.utils.ConfigConstant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws RRException {
        if(null== request.getSession().getAttribute(ConfigConstant.ACCOUNT)){
            throw new RRException(CodeAndMsg.ERROR_USER_NOT_LOGIN);
        }
       return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
