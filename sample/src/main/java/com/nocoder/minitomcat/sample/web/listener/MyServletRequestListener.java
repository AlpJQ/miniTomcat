package com.nocoder.minitomcat.sample.web.listener;

import com.nocoder.minitomcat.listener.ServletRequestListener;
import com.nocoder.minitomcat.listener.event.ServletRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class MyServletRequestListener implements ServletRequestListener {
    private static final Logger logger = LoggerFactory.getLogger(MyServletRequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        logger.info("request destroy...");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        logger.info("request init...");
    }
}
