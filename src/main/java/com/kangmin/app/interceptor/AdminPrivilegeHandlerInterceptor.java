package com.kangmin.app.interceptor;

import com.kangmin.app.model.Account;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.kangmin.app.util.AttributeName.SESSION_ACCOUNT;

public class AdminPrivilegeHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws IOException {
        final HttpSession session = request.getSession();
        final Account account = (Account) session.getAttribute(SESSION_ACCOUNT);

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/error/invalid-session");
            return false;
        }

        if (!account.getRole().equals("ADMIN")
                && !account.getRole().equals("SUPER_ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/error/access-denied");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler,
                           final ModelAndView modelAndView) {
        // can do logging & utf-8 here
    }

    @Override
    public void afterCompletion(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final Object handler,
                                final Exception ex) {
        // can also do logging & utf-8 here
    }
}
