package com.nocoder.minitomcat.sample.web.filter;


import com.nocoder.minitomcat.filter.Filter;
import com.nocoder.minitomcat.filter.FilterChain;
import com.nocoder.minitomcat.request.Request;
import com.nocoder.minitomcat.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init() {
        logger.info("LoginFilter init...");
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        logger.info("当前访问的servletPath:{}", request.getServletPath());
        // login直接放行，其他页面访问均需要登录
        if (request.getServletPath().equals("/login") || request.getServletPath().startsWith("/views/errors")) {
            logger.info("直接放行");
            filterChain.doFilter(request, response);
        } else {
            logger.info("检查是否登录...");
            if (request.getSession(false) != null && request.getSession().getAttribute("username") != null) {
                logger.info("已登录，通过检查...");
                filterChain.doFilter(request, response);
            } else {
                logger.info("未登录,401");
                // 未登录。重定向至登录页面
                response.sendRedirect("/login");
            }
        }
    }

    @Override
    public void destroy() {
        logger.info("LoginFilter destroy...");
    }
}
