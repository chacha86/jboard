package com.jboard.controller;

import com.jboard.model.DBManager;
import com.jboard.model.DBWorker;
import com.jboard.model.SqlMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ArticleController", value = "/article")
public class ArticleController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SqlMapper mapper = new SqlMapper();
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        List<Map<String, Object>> articles = mapper.getAllArticles();

        for(int i = 0; i < articles.size(); i++) {
            Map<String, Object> article = articles.get(i);
            out.println("<div>");
            out.println("   <span> 번호 : " + article.get("id") + "</span><br />");
            out.println("   <span> 제목 : " + article.get("title") + "</span><br />");
            out.println("   <span> 내용 : " + article.get("body") + "</span><br />");
            out.println("</div>");
            out.println("<hr>");
        }

//        List<Article> articles = dbUtil.getAllArticles();
//
//        for(Article a : articles) {
//            System.out.println(a.getTitle());
//            System.out.println(a.getBody());
//        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

