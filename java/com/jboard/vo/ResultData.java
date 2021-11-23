package com.jboard.vo;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultData {
    private Map<String, Object> resultMap = new HashMap<>();

    public void addData(String key, Object data) {
        resultMap.put(key, data);
    }
    public String toJSONString() {
        JSONObject jboj = new JSONObject();
        jboj.putAll(resultMap);
        return jboj.toJSONString();
    }
}