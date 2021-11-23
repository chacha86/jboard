package com.jboard.controller;

import com.jboard.exception.NotImplementsResourceMethodException;
import com.jboard.model.myJDBC.SqlMapper;
import com.jboard.vo.ResultData;

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
    public String getResultMsgForCollectionType() throws NotImplementsResourceMethodException {
        List<Map<String, Object>> articles = mapper.getAllArticles();
        ResultData resultData = getResultData();
        resultData.addData("articles", articles);
        resultData.addData("resultCode", 200);

        return resultData.toJSONString();
    }

}
