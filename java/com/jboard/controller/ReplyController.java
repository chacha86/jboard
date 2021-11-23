package com.jboard.controller;

import com.jboard.model.myJDBC.SqlMapper;
import com.jboard.model.vo.MyURI;
import com.jboard.model.vo.ReplyMapper;
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

public class ReplyController {
    HttpServletRequest req;
    HttpServletResponse res;
    Logger logger = LoggerFactory.getLogger(ReplyController.class);
    SqlMapper mapper = new SqlMapper();

    public ReplyController(HttpServletRequest req, HttpServletResponse res) {
        this.req = req;
        this.res = res;
    }

    public String doMethod(MyURI uriStruct) {
        String result = "";
        logger.info(uriStruct.getUriType().toString());
        switch (uriStruct.getUriType()) {
            case GET_COLLECTION:
                List<Map<String, Object>> repliesForList = getRepliesByArticleIdx(uriStruct.getId());
                result = getCollectionTypeResult(repliesForList);
                break;
//            case POST:
//                Map<String, Object> articleForAdd = getArticleMapFromJsonParam();
//                result = getPostTypeResult(articleForAdd);
//                break;
//            case PUT:
//                Map<String, Object> articleForUpdate = getArticleMapFromJsonParam();
//                result = getPutTypeResult(articleForUpdate);
//                break;
//            case DELETE :
//                logger.info("DELETE ENTER");
//                Map<String, Object> articleForDelete = getArticleMapFromJsonParam();
//                result = getDeleteTypeResult(articleForDelete);
//                break;
            default:
                result = "page not found";
                break;
        }
        return result;
    }
    public String getCollectionTypeResult(List<Map<String, Object>> datas) {
        JSONArray jarr = new JSONArray();
        for(int i = 0; i < datas.size(); i++) {
            JSONObject jobj = new JSONObject();
            jarr.add(datas.get(i));
        }
        return jarr.toJSONString();
    }

    private Map<String, Object> getReplyMapFromJsonParam() {
        Map<String, Object> reply = new HashMap<>();
        try {
            InputStream inputStream = req.getInputStream(); //Read from a file, or a HttpRequest, or whatever.
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(
                    new InputStreamReader(inputStream, "UTF-8"));

            reply = putToMapArticleDataIfNotNull(reply, "idx", jsonObject.get("idx"));
            reply = putToMapArticleDataIfNotNull(reply, "memberIdx", jsonObject.get("memberIdx"));
            reply = putToMapArticleDataIfNotNull(reply, "articleIdx", jsonObject.get("articleIdx"));
            reply = putToMapArticleDataIfNotNull(reply, "body", jsonObject.get("body"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reply;
    }

    private Map<String, Object> putToMapArticleDataIfNotNull(Map<String, Object> map, String key, Object value) {
        if( value != null) {
            map.put(key, value);
        }
        return map;
    }

    public List<Map<String, Object>> getRepliesByArticleIdx(String articleIdx) {
        if(articleIdx == null) {
            articleIdx = "0";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("idx", Integer.parseInt(articleIdx));
        return mapper.getRepliesByArticleId(params);
    }
}
