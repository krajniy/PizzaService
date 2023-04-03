package com.telran.pizzaservice.config;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Configuration class for a servlet filter that adds a unique trace ID to the logging context using MDC.
 *
 * @author Elena Ivanishcheva
 */
@Component
@Order(1)
public class LogMdcFilter implements Filter {

    /**
     * Adds a unique trace ID to the logging context using MDC and executes the next filter in the chain.
     * The trace ID is generated using UUID.randomUUID().
     * After the request has been processed, MDC is cleared to avoid memory leaks.
     *
     * @param servletRequest  the servlet request
     * @param servletResponse the servlet response
     * @param filterChain     the filter chain
     * @throws IOException      if an I/O error occurs during the processing of the request
     * @throws ServletException if the request could not be processed
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            MDC.put("traceId", UUID.randomUUID().toString());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }
}