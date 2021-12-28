package com.jboard.controller;

import com.jboard.model.myJDBC.SqlMapper;
import com.jboard.model.vo.URIHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleController2 {

    private Logger logger = LoggerFactory.getLogger(ArticleController2.class);
    private SqlMapper mapper = new SqlMapper();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ArticleController2(HttpServletRequest req, HttpServletResponse res) {
       this.request = req;
       this.response = res;

    }
    public String doMethod(URIHandler uriStruct) {
        String result = "";
        logger.info(uriStruct.getUriType().toString());
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
                Map<String, Object> articleForAdd = getArticleMapFromJsonParam();
                result = getPostTypeResult(articleForAdd);
                break;
            case PUT:
                Map<String, Object> articleForUpdate = getArticleMapFromJsonParam();
                result = getPutTypeResult(articleForUpdate);
                break;
            case DELETE :
                logger.info("DELETE ENTER");
                Map<String, Object> articleForDelete = getArticleMapFromJsonParam();
                result = getDeleteTypeResult(articleForDelete);
                break;
            default:
                result = "page not found";
                break;
        }
        return result;
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

    private String getSingleTypeResult(Map<String, Object> map) {
       JSONObject jobj = new JSONObject();
       if(map == null) {
          return "no data";
       }
       jobj.putAll(map);
       return jobj.toJSONString();
    }

    private String getPostTypeResult(Map<String, Object> article) {
        int result = addArticle(article);
        if(result == 1) {
            return "article add success : [ " + article.toString() + " ]";
        }
        return "add failed";
    }

    private String getPutTypeResult(Map<String, Object> articleForUpdate) {
        int result = updateArticle(articleForUpdate);
        if(result == 1) {
            return "article update success : [ " + articleForUpdate.toString() + " ]";
        }
        return "update failed";
    }

    private String getDeleteTypeResult(Map<String, Object> articleForUpdate) {
        int result = deleteArticle(articleForUpdate);
        if(result == 1) {
            return "article delete success : [ " + articleForUpdate.toString() + " ]";
        }
        return "delete failed";
    }

    private Map<String, Object> getArticleMapFromJsonParam() {
        Map<String, Object> article = new HashMap<>();
        try {
            InputStream inputStream = request.getInputStream(); //Read from a file, or a HttpRequest, or whatever.
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(
                    new InputStreamReader(inputStream, "UTF-8"));

            article = putToMapArticleDataIfNotNull(article, "idx", jsonObject.get("idx"));
            article = putToMapArticleDataIfNotNull(article, "memberIdx", jsonObject.get("memberIdx"));
            article = putToMapArticleDataIfNotNull(article, "title", jsonObject.get("title"));
            article = putToMapArticleDataIfNotNull(article, "body", jsonObject.get("body"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return article;
    }

    private Map<String, Object> putToMapArticleDataIfNotNull(Map<String, Object> map, String key, Object value) {
        if( value != null) {
            map.put(key, value);
        }
        return map;
    }
    private List<Map<String, Object>> getArticles() {
        return mapper.getAllArticles();
    }

    private Map<String, Object> getArticleById(String idx) {
        if(idx == null) {
            idx = "0";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("idx", Integer.parseInt(idx));
        return mapper.getArticleById(params);
    }

    private int addArticle(Map<String, Object> articleForAdd) {
        return mapper.insertArticle(articleForAdd);
    }

    private int updateArticle(Map<String, Object> articleForAdd) {
        return mapper.updateArticle(articleForAdd);
    }

    private int deleteArticle(Map<String, Object> articleForDelete) {
        return mapper.deleteArticle(articleForDelete);
    }
}

