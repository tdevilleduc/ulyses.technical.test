package com.septeo.ulyses.technical.test.interceptor;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class LoggingInterceptorTest {

    private LoggingInterceptor interceptor;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        interceptor = new LoggingInterceptor();
        Logger logger = (Logger) LoggerFactory.getLogger(LoggingInterceptor.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        Logger logger = (Logger) LoggerFactory.getLogger(LoggingInterceptor.class);
        logger.detachAppender(listAppender);
    }

    @Test
    void preHandle_returnsTrue_andLogsRequestInfo() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/brands");

        boolean result = interceptor.preHandle(request, new MockHttpServletResponse(), new Object());

        assertThat(result).isTrue();
        assertThat(listAppender.list).hasSize(1);
        ILoggingEvent event = listAppender.list.get(0);
        assertThat(event.getLevel()).isEqualTo(Level.INFO);
        assertThat(event.getFormattedMessage())
            .contains("incoming request")
            .contains("/api/brands")
            .contains("GET")
            .contains("DateTime:");
    }

    @Test
    void afterCompletion_logsAllRequiredResponseFields() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/brands");
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(201);

        interceptor.preHandle(request, response, new Object());
        interceptor.afterCompletion(request, response, new Object(), null);

        // index 0 = preHandle log, index 1 = afterCompletion log
        String log = listAppender.list.get(1).getFormattedMessage();
        assertThat(log)
            .contains("outgoing response")
            .contains("/api/brands")
            .contains("POST")
            .contains("201")
            .contains("Duration:");
    }

    @Test
    void afterCompletion_computesDuration_fromStartTimeStoredByPreHandle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/brands");
        MockHttpServletResponse response = new MockHttpServletResponse();

        interceptor.preHandle(request, response, new Object());
        interceptor.afterCompletion(request, response, new Object(), null);

        String log = listAppender.list.get(1).getFormattedMessage();
        assertThat(log).matches(".*Duration: \\d+ms.*");
    }
}
