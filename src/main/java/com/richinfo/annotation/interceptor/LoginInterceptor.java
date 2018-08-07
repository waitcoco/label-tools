package com.richinfo.annotation.interceptor;

import com.richinfo.util.Variable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
               HttpSession session = request.getSession();

        if (session.getAttribute(Variable.SESSION_SYSUSER) != null) {
            return true;
        }
        String url = "../admin/login";
        //System.out.println(url);
        response.sendRedirect(url);
        return false;
    }

}
