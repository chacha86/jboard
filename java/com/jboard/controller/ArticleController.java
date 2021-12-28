package com.jboard.controller;

import com.jboard.exception.NotImplementsResourceMethodException;
import com.jboard.model.myJDBC.SqlMapper;
import com.jboard.model.vo.ResultData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ArticleController extends Controller {

    SqlMapper mapper = new SqlMapper();

    public ArticleController(HttpServletRequest req, HttpServletResponse res) {
        super(req, res);
    }

    @Override
    public String getResultMsgForCollectionType() {
        String mappingURI = getURIHander().getMappingURI();
        System.out.println(mappingURI);
        if(mappingURI.equals("/articles")) {
            return getArticles();
        }
        else if(mappingURI.equals("articles/#{idx}/replies")) {
            return null;
        }
        return "";
    }

    @Override
    public String getResultMsgForSingleType() {
        if(getURIHander().getMappingURI().equals("/articles/#{articlesId}")) {
            return getArticleById(getURIHander().getParams());
        }
        return "";
    }

    private String getArticles() {
        List<Map<String, Object>> articles = mapper.getAllArticles();
        ResultData resultData = getResultData();
        resultData.addData("articles", articles);
        resultData.addData("resultCode", 200);

        return resultData.toJSONString();
    }

    @Override
    public String getResultMsgForPostType() throws NotImplementsResourceMethodException {
        return super.getResultMsgForPostType();
    }

    private String getArticleById(Map<String, Object> pathParams) {
        Map<String, Object> params = getParamMapFromJsonParam();
        params.put("idx", Integer.parseInt((String)pathParams.get("articlesId")));
        Map<String, Object> article = mapper.getArticleById(params);
        ResultData resultData = getResultData();
        resultData.addData("article", article);
        resultData.addData("resultCode", 200);

        return resultData.toJSONString();
    }
}
