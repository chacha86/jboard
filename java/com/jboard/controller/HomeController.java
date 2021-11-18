package com.jboard.controller;

import com.jboard.model.vo.MyURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class HomeController extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String methodType = req.getMethod();
        String uri = req.getRequestURI();
        MyURI myUri = new MyURI(methodType, uri);
        ArticleController controller = new ArticleController(req, resp);
        String result = controller.doMethod(myUri);
        resp.getWriter().append(result);

    }

}
