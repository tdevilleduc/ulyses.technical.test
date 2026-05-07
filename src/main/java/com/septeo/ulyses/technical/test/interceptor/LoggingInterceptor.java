package com.septeo.ulyses.technical.test.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    private static final String START_TIME_ATTRIBUTE = "logging.startTime";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTRIBUTE, startTime);

        String requestDateTime = 
            LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault())
                .format(DATE_FORMATTER);

        logger.info(
            "incoming request  - DateTime: {}, URI: {}, Method: {}", 
            requestDateTime, 
            request.getRequestURI(), 
            request.getMethod()
        );

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
        long elapsed = startTime != null ? System.currentTimeMillis() - startTime : -1;

        String requestDateTime = startTime != null 
            ? LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault())
                .format(DATE_FORMATTER)
            : "unknown";

        logger.info(
            "outgoing response - DateTime: {}, URI: {}, Method: {}, Status: {}, Duration: {}ms", 
            requestDateTime, 
            request.getRequestURI(), 
            request.getMethod(), 
            response.getStatus(), 
            elapsed
        );
    }
}   