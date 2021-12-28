package com.jboard.controller;

import com.jboard.exception.NotImplementsResourceMethodException;
import com.jboard.model.vo.URIHandler;
import com.jboard.model.vo.ResultData;
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
import java.util.Map;

public class Controller {

    private Logger logger = LoggerFactory.getLogger(Controller.class);
    private URIHandler uriHandler;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public Controller(HttpServletRequest req, HttpServletResponse res) {
        this.request = req;
        this.response = res;
        this.uriHandler = (URIHandler) req.getAttribute("URIHandler");
    }

    public String doMethod() {
        String result = "";
        logger.info(uriHandler.getUriType().toString());
        try {
            switch (uriHandler.getUriType()) {
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
            //result = getResultMsgForErrorType(500, e.getMessage());
        } catch(NullPointerException e) {
            result = "server is confronted with null pointer exception";
        }
        return result;
    }

    public void sendStatusCode(int sc) {
        try {
            response.setContentType("text/json");
            response.sendError(sc, "error!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getResultMsgForCollectionType() throws NotImplementsResourceMethodException {
        String msg = "not implements Collection Method";
        throw new NotImplementsResourceMethodException(msg);
    }

    public String getResultMsgForSingleType() throws NotImplementsResourceMethodException {
        String msg = "not implements single Method";
        throw new NotImplementsResourceMethodException(msg);
    }

    public String getResultMsgForPostType() throws NotImplementsResourceMethodException {
        String msg = "not implements Post Method";
        throw new NotImplementsResourceMethodException(msg);
    }

    public String getResultMsgForPutType() throws NotImplementsResourceMethodException {
        String msg = "not implements Put Method";
        throw new NotImplementsResourceMethodException(msg);
    }

    private String getResultMsgForDeleteType() throws NotImplementsResourceMethodException {
        String msg = "not implements Delete Method";
        throw new NotImplementsResourceMethodException(msg);
    }

    protected Map<String, Object> getParamMapFromJsonParam() {

        Map<String, Object> params = new HashMap<>();
        try {
            InputStream inputStream = request.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            if(inputStream.readAllBytes().length <= 0) {

            }
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
    protected URIHandler getURIHander() {
        return uriHandler;
    }
}

