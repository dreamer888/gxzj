package com.dlc.common.exception;

import com.dlc.common.utils.CodeAndMsg;
import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.RedisUtils;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.vo.AgentVo;
import com.dlc.modules.api.vo.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OnLineInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(OnLineInterceptor.class);
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws RRException {
        if(null== request.getSession().getAttribute(ConfigConstant.ACCOUNT)){
            throw new RRException(CodeAndMsg.ERROR_USER_NOT_LOGIN);
        }
        AgentVo vo = (AgentVo) request.getSession().getAttribute(ConfigConstant.ACCOUNT);
//        logger.info("OnLineInterceptor---userVo->"+vo);
        if(vo != null){
            String sessionID = redisUtils.get(ConfigConstant.AGENT+vo.getId());
            if(!sessionID.equalsIgnoreCase(request.getSession().getId())){
                throw new RRException(CodeAndMsg.ERROR_TAPE_OUT);
            }
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
