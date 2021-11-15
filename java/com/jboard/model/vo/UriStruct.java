package com.jboard.model.vo;

import com.jboard.util.MyUtil;

public class UriStruct {

    private final int URI_MAX_LENGTH = 4;
    private String basePattern;
    private String module;
    private String group;
    private String identity;

    public UriStruct(String uri) {
        if(uri == null) {
            uri = "";
        }
        String[] uriBits = uri.split("/");

        if(uriBits.length < 3) {
            this.module = "Error";
            return;
        }

        this.module = uriBits[1];
        this.group = uriBits[2];
        this.basePattern = getFullURI();

        if(uriBits.length == URI_MAX_LENGTH) {
            this.identity = uriBits[URI_MAX_LENGTH - 1];
        }
    }

    public UriType getUriType() {
        if(getFullURI().equals(basePattern)) {
            return UriType.CollectionType;
        } else if(getFullURI().startsWith(basePattern + "/")) {
            return UriType.ResourceType;
        }
        return UriType.ErrorType;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getFullURI() {
        String url = MyUtil.nullToBlank(this.module) + "/" + MyUtil.nullToBlank(this.group) + "/" + MyUtil.nullToBlank(this.identity) + "/";
        return url.substring(0, url.length() - 2);
    }
}
