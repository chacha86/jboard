package com.jboard.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "UrlFilter")
public class UrlFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String uri = ((HttpServletRequest)request).getRequestURI();
        System.out.println(uri);
        if(uri.startsWith("/resources/")) {
            RequestDispatcher requestDispatcher = ((HttpServletRequest)request).getRequestDispatcher("/index.html");
            requestDispatcher.forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}
