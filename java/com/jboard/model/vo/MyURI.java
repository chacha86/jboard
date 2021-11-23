package com.jboard.model.vo;

import com.jboard.util.MyUtil;

public class MyURI {

    private final int URI_MAX_LENGTH = 3;
    private String basePattern;
    private String methodType;
    private String group;
    private String id;

    public MyURI(String methodType, String uri) {

        this.methodType = methodType;
        if(uri == null || uri.length() <= 1) {
           group = "/index.html";
           return;
        }
        String[] uriBits = uri.split("/");

        if(uriBits.length < 2) {
            this.group = "error";
            return;
        }

        this.group = uriBits[1];
        this.basePattern = getFullURI();

        if(uriBits.length == URI_MAX_LENGTH) {
            this.id = uriBits[URI_MAX_LENGTH - 1];
        }
    }

    public URIType getUriType() {
        if(methodType.equals("GET")) {
            if(getFullURI().equals(basePattern)) {
                return URIType.GET_COLLECTION;
            } else if(getFullURI().startsWith(basePattern + "/")) {
                return URIType.GET_SINGLE;
            }
        } else if(methodType.equals("POST")){
            return URIType.POST;
        } else if(methodType.equals("DELETE")) {
            return URIType.DELETE;
        } else if(methodType.equals("PUT")) {
            return URIType.PUT;
        }

        return URIType.ERROR;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullURI() {
        String url = MyUtil.nullToBlank(this.group) + "/" + MyUtil.nullToBlank(this.id) + "/";
        return url.substring(0, url.length() - 2);
    }
}
