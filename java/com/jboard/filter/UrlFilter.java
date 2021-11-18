package com.jboard.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "UrlFilter", urlPatterns = "/*")
public class UrlFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String uri = ((HttpServletRequest)request).getRequestURI();
        Logger logger = LoggerFactory.getLogger(UrlFilter.class);
        logger.debug("debug: " + uri);
        logger.info("info: " + uri);
        logger.error("error : " + uri);
        if(uri.equals("/")) {
            ((HttpServletResponse)response).sendRedirect("http://localhost/index.html");
            return;
        }
        chain.doFilter(request, response);
    }
}
