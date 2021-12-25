package com.kangmin.app.interceptor;

import com.kangmin.app.model.Account;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.kangmin.app.util.AttributeName.SESSION_ACCOUNT;

public class LoginSessionHandlerInterceptor implements HandlerInterceptor {

    private static final Set<String> OPEN_PATHS = new HashSet<>(
        Arrays.asList(
            "/funds",
            "/help",
            "/favicon.ico"
        )
    );

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws IOException {
        if (OPEN_PATHS.contains(request.getRequestURI().toLowerCase())) {
            if (request.getMethod().equalsIgnoreCase("GET")) {
                return true;
            }
        }
        final HttpSession session = request.getSession();
        final Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/error/invalid-session");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler,
                           final ModelAndView modelAndView) {
        // System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final Object handler,
                                final Exception ex) {
        // System.out.println("afterCompletion");
    }
}
