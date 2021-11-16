package com.jboard.controller;

import com.jboard.model.vo.UriStruct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class HomeController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String methodType = req.getMethod();
        System.out.println(methodType);
        String uri = req.getRequestURI();
        UriStruct uriStruct = new UriStruct(methodType, uri);
        ArticleController controller = new ArticleController(req, resp);
        String result = controller.doMethod(uriStruct);
        resp.getWriter().append(result);

    }

}
