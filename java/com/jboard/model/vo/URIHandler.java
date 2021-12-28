package com.jboard.model.vo;

import com.jboard.util.MyUtil;

import java.util.HashMap;
import java.util.Map;

public class URIHandler {

    private String methodType;
    private String group;
    private Map<String, Object> pathParams;
    private String mappingURI;
    private boolean isCollectionType;

    public URIHandler(String methodType, String uri) {

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
        URIDataInit(uri);
    }

    private void URIDataInit(String uri) {
        String[] uriPeices = uri.split("/");
        int realStartIndexInPeices = 1;
        int stepForNextResourceId = 2;
        int uriPeicesLength = uriPeices.length;
        Map<String, Object> pathParams = new HashMap<>();
        String mappingURI = "";
        for(int i = realStartIndexInPeices; i < uriPeicesLength; i+= stepForNextResourceId) {
            int resourceIdIndex = i + 1;
            String group = uriPeices[i];
            String groupId = group + "Id";
            mappingURI = addURIPath(mappingURI, group);

            if(resourceIdIndex > uriPeicesLength - 1) {
                this.isCollectionType = true;
                break;
            }
            pathParams.put(groupId, uriPeices[resourceIdIndex]);
            mappingURI = addURIPath(mappingURI, "#{" + groupId +"}");
        }
        this.pathParams = pathParams;
        this.mappingURI = mappingURI;
    }

    private static String addURIPath(String path, String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("/");
        sb.append(name);
        return sb.toString();
    }

    public String getMappingURI() {
        return mappingURI;
    }

    public Map<String, Object> getParams() {
        return this.pathParams;
    }

    public URIType getUriType() {
        if(methodType.equals("GET")) {
            return getUriTypeInGET();
        } else if(methodType.equals("POST")){
            return URIType.POST;
        } else if(methodType.equals("DELETE")) {
            return URIType.DELETE;
        } else if(methodType.equals("PUT")) {
            return URIType.PUT;
        }

        return URIType.ERROR;
    }

    public URIType getUriTypeInGET() {
        if(this.isCollectionType) {
            return URIType.GET_COLLECTION;
        }
        return  URIType.GET_SINGLE;
    }

    public String getId() {
        return "1";
    }

    public String getFullURI() {
        String url = MyUtil.nullToBlank(this.group) + "/" + MyUtil.nullToBlank(this.getId()) + "/";
        return url.substring(0, url.length() - 2);
    }
}
