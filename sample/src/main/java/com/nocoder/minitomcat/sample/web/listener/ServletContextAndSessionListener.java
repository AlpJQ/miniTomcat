package com.nocoder.minitomcat.sample.web.listener;

import com.nocoder.minitomcat.listener.HttpSessionListener;
import com.nocoder.minitomcat.listener.ServletContextListener;
import com.nocoder.minitomcat.listener.event.HttpSessionEvent;
import com.nocoder.minitomcat.listener.event.ServletContextEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ServletContextAndSessionListener implements ServletContextListener, HttpSessionListener {
    private static final Logger logger = LoggerFactory.getLogger(ServletContextAndSessionListener.class);

    private AtomicInteger sessionCount = new AtomicInteger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("servlet context init...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("servlet context destroy...");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("session created, count = {}", this.sessionCount.incrementAndGet());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("session destroyed, count = {}", sessionCount.decrementAndGet());
    }
}
