package com.jboard.controller;

import com.jboard.model.vo.URIHandler;
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
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "*");
        logger.info("method : " + methodType);
        if(methodType.equals("OPTIONS")) {
            return;
        }
        //String uri = req.getRequestURI();
        //URIHandler uriHandler = new URIHandler(methodType, uri);
        //req.setAttribute("URIHandler", uriHandler);
        ArticleController controller = new ArticleController(req, resp);
        String result = controller.doMethod();
        resp.getWriter().append(result);

    }

}
