package com.jboard.filter;

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
        System.out.println(uri);
        if(uri.equals("/")) {
            ((HttpServletResponse)response).sendRedirect("http://localhost:8082/index.html");
            return;
        }
        chain.doFilter(request, response);
    }
}
