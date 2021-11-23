package com.jboard.controller;

import com.jboard.exception.NotImplementsResourceMethodException;
import com.jboard.model.myJDBC.SqlMapper;
import com.jboard.vo.ResultData;
import com.jboard.vo.ResultDataBuilder;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplyController extends Controller {

    private enum ReplyUris {
        REPLIES_ALL("replies"),
        REPLIES_IN_ARTICLE("replies/");

        private final String name;

       ReplyUris(String name) {
           this.name = name;
       }

        @Override
        public String toString() {
           return this.name;
        }
    }

    HttpServletRequest req;
    HttpServletResponse res;
    SqlMapper mapper = new SqlMapper();
    public ReplyController(HttpServletRequest req, HttpServletResponse res) {
        super(req, res);
    }

    @Override
    public String getResultMsgForCollectionType() throws NotImplementsResourceMethodException {
        Map<String, Object> params = getParamMapFromJsonParam();
        List<Map<String, Object>> replies = mapper.getRepliesByArticleId(params);
        ResultData resultData = getResultData();
        resultData.addData("replies", replies);

        return resultData.toJSONString();
    }


}
