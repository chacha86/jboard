package com.jboard.controller;

import com.jboard.model.myJDBC.SqlMapper;
import com.jboard.model.vo.MyURI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleController {

    private SqlMapper mapper = new SqlMapper();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ArticleController(HttpServletRequest req, HttpServletResponse res) {
       this.request = req;
       this.response = res;

    } public String doMethod(MyURI uriStruct) {
        String result = "";
        switch (uriStruct.getUriType()) {
            case GET_COLLECTION:
                List<Map<String, Object>> articlesForList = getArticles();
                result = getCollectionTypeResult(articlesForList);
                break;
            case GET_SINGLE:
                Map<String, Object> articleForSingle = getArticleById(uriStruct.getId());
                result = getSingleTypeResult(articleForSingle);
                break;
            case POST:
                Map<String, Object> articleForAdd = getArticleMapFromParam();
                result = getPostTypeResult(articleForAdd);
                break;
            default:
                result = "page not found";
                break;
        }
        return result;
    }

    private void addArticle(Map<String, Object> articleForAdd) {
        mapper.insertArticle(articleForAdd);
    }

    private Map<String, Object> getArticleMapFromParam() {
        Map<String, Object> article = new HashMap<>();
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        article.put("title", title);
        article.put("body", body);
        article.put("memberIdx", 1);
        return article;
    }

//    private Map<String, Object> getArticleMapFromJsonParam() {
//
//        String title = request.getParameter("title");
//        String body = request.getParameter("body");
//        int result = mapper.insertArticle();
//
//
//        JSONObject jsonObject = (JSONObject) JSONValue.parse(param);
//        System.out.println(jsonObject.toString());
//        return null;
//    }

    private Map<String, Object> getArticleById(String idx) {
        if(idx == null) {
            idx = "0";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("idx", Integer.parseInt(idx));
        return mapper.getArticleById(params);
    }

    private List<Map<String, Object>> getArticles() {
        return mapper.getAllArticles();
    }

    private String getPostTypeResult(Map<String, Object> article) {
        int result = mapper.insertArticle(article);
        if(result == 1) {
            return "article add success : [ " + article.toString() + " ]";
        }
        return "add failed";
    }

    private String getSingleTypeResult(Map<String, Object> map) {
       JSONObject jobj = new JSONObject();
       if(map == null) {
          return "no data";
       }
       jobj.putAll(map);
       return jobj.toJSONString();
    }

    private String getCollectionTypeResult(List<Map<String, Object>> datas) {
        JSONArray jarr = new JSONArray();
        //jarr.add(datas.get(0));
        for(int i = 0; i < datas.size(); i++) {
            JSONObject jobj = new JSONObject();
            jarr.add(datas.get(i));
        }
        return jarr.toJSONString();
    }
}

