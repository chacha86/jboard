package com.jboard.filter;

import com.jboard.model.vo.URIHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "UrlFilter", urlPatterns = "/*")
public class UrlFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(UrlFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String uri = ((HttpServletRequest)request).getRequestURI();

        if(uri.equals("/")) {
            ((HttpServletResponse)response).sendRedirect("http://localhost/index.html");
            return;
        }

        String methodType = ((HttpServletRequest) request).getMethod();
        logger.debug(methodType);
        URIHandler uriHandler = new URIHandler(methodType, uri);
        request.setAttribute("URIHandler", uriHandler);

        logger.debug(uri);
        logger.debug(String.valueOf(uriHandler.getUriType()));
        logger.debug(uriHandler.getMappingURI());
        chain.doFilter(request, response);

    }
}
