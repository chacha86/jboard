package com.jboard.controller;

import com.jboard.exception.NotImplementsResourceMethodException;
import com.jboard.model.ParamMapper;
import com.jboard.model.myJDBC.SqlMapper;
import com.jboard.model.vo.MyURI;
import com.jboard.vo.ResultData;
import com.jboard.vo.ResultDataBuilder;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Controller {

    private Logger logger = LoggerFactory.getLogger(Controller.class);
    private SqlMapper mapper = new SqlMapper();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public Controller(HttpServletRequest req, HttpServletResponse res) {
        this.request = req;
        this.response = res;
    }

    public String doMethod(MyURI myURI) {
        String result = "";
        logger.info(myURI.getUriType().toString());
        try {
            switch (myURI.getUriType()) {
                case GET_COLLECTION:
                    result = getResultMsgForCollectionType();
                    break;
                case GET_SINGLE:
                    result = getResultMsgForSingleType();
                    break;
                case POST:
                    result = getResultMsgForPostType();
                    break;
                case PUT:
                    result = getResultMsgForPutType();
                    break;
                case DELETE :
                    result = getResultMsgForDeleteType();
                    break;
                default:
                    result = "page not found";
                    break;
            }
        } catch(NotImplementsResourceMethodException e) {
            e.printStackTrace();
            e.getMessage();
            result = "server is confronted with some problems";
        }
        return result;
    }


    public String getResultMsgForCollectionType() throws NotImplementsResourceMethodException {
        throw new NotImplementsResourceMethodException();
    }

    public String getResultMsgForSingleType() throws NotImplementsResourceMethodException {
        throw new NotImplementsResourceMethodException();
    }

    public String getResultMsgForPostType() throws NotImplementsResourceMethodException {
        throw new NotImplementsResourceMethodException();
    }

    public String getResultMsgForPutType() throws NotImplementsResourceMethodException {
        throw new NotImplementsResourceMethodException();
    }

    private String getResultMsgForDeleteType() throws NotImplementsResourceMethodException {
        throw new NotImplementsResourceMethodException();
    }

    protected Map<String, Object> getParamMapFromJsonParam() {

        Map<String, Object> params = new HashMap<>();
        try {
            InputStream inputStream = request.getInputStream();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(
                    new InputStreamReader(inputStream, "UTF-8"));
            Iterator<String> jsonKeySet = jsonObject.keySet().iterator();
            while(jsonKeySet.hasNext()) {
                String key = jsonKeySet.next();
                params.put(key, jsonObject.get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return params;
    }

    protected ResultData getResultData() {
        return new ResultData();
    }

    private Map<String, Object> putToMapArticleDataIfNotNull(Map<String, Object> map, String key, Object value) {
        if( value != null) {
            map.put(key, value);
        }
        return map;
    }
//    public List<Map<String, Object>> getResourceCollection() throws NotImplementsResourceMethodException {
//        throw new NotImplementsResourceMethodException();
//    }
//
//    public Map<String, Object> getSingleResource(String idx) throws NotImplementsResourceMethodException {
//        throw new NotImplementsResourceMethodException();
//    }
//
//    public int addResource(Map<String, Object> paramsForAdd) throws NotImplementsResourceMethodException {
//        throw new NotImplementsResourceMethodException();
//    }
//
//    public int updateResource(Map<String, Object> paramsForUpdate) throws NotImplementsResourceMethodException {
//        throw new NotImplementsResourceMethodException();
//    }
//
//    public int deleteResource(Map<String, Object> paramsForDelete) throws NotImplementsResourceMethodException {
//        throw new NotImplementsResourceMethodException();
//    }
}

