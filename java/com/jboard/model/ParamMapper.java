package com.jboard.model;

import org.json.simple.JSONObject;

import java.util.Map;

public interface ParamMapper {
    Map<String, Object> getParam(JSONObject jobj);
}
