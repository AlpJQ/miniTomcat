package com.nocoder.minitomcat.sample.web.filter;

import com.nocoder.minitomcat.filter.Filter;
import com.nocoder.minitomcat.filter.FilterChain;
import com.nocoder.minitomcat.request.Request;
import com.nocoder.minitomcat.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class LogFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init() {
        logger.info("LogFilter init...");
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        logger.info("{} before accessed, method is {}", request.getUrl(), request.getMethod());
        filterChain.doFilter(request, response);
        logger.info("{} after accessed, method is {}", request.getUrl(), request.getMethod());
    }

    @Override
    public void destroy() {
        logger.info("LogFilter destroy...");
    }
}
