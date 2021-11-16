package com.jboard.controller;

import com.jboard.model.myJDBC.SqlMapper;
import com.jboard.model.vo.UriStruct;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ArticleController {

    private SqlMapper mapper = new SqlMapper();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ArticleController(HttpServletRequest req, HttpServletResponse res) {
       this.request = req;
       this.response = res;
    } public String doMethod(UriStruct uriStruct) {
        String result = "";
        switch (uriStruct.getUriType()) {
            case GET_COLLECTION:
                List<Map<String, Object>> datas = getArticles();
                result = getCollectionTypeResult(datas);
                break;
            case GET_SINGLE:
                Map<String, Object> article = getArticleById(uriStruct.getIdentity());
                result = getSingleTypeResult(article);
                break;
            case POST:
                //Map<String, Object> article = getArticleMapFromJsonParam();
                //addArticle(article);
            default:
                result = "page not found";
                break;
        }
        return result;
    }

    private Map<String, Object> getArticleMapFromJsonParam() {
        String param = request.getParameter("jsonParam");
        JSONObject jsonObject = (JSONObject) JSONValue.parse(param);
        System.out.println(jsonObject.toString());
        return null;
    }

    private Map<String, Object> getArticleById(String id) {
        return mapper.getArticleById(Integer.parseInt(id));
    }

    private List<Map<String, Object>> getArticles() {
        return mapper.getAllArticles();
    }

    private String getUpdateTypeResult() {
        String resultCode = "200";
        return resultCode;
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

