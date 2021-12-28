package com.jboard.test;

import com.jboard.model.myJDBC.BindingMaterials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
enum MappingURI {
    ARTICLES("/articles"),
    ARTICLES_ID("articles/#{aritlceId}");

    private final String name;

    MappingURI(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
public class paramQueryTest {

    public static void main(String[] args) {
        String sql = "insert into article set id=#{id}, title=#{title}, body=#{body}";
        getBindingMaterials(sql);
        String uri = "/articles/1/replies";
        String result = getMappingURI(uri);
        System.out.println(result);
    }
    private static String getBindingMaterials(String paramQuery) {
        char deli = '#';
        String queryForPrepareStatement = "";
        List<String> orderedParamKeyList = new ArrayList<>();
        int isNext = paramQuery.indexOf(deli);
        while(isNext != -1) {
            int startIdx = paramQuery.indexOf('{');
            int endIdx = paramQuery.indexOf('}'); String paramKey = paramQuery.substring(startIdx + 1, endIdx);
            orderedParamKeyList.add(paramKey);
            paramQuery = paramQuery.replace(String.format("#{%s}", paramKey),"?");
            isNext = paramQuery.indexOf(deli);
        }
        System.out.println("queryForPrepareStetment : " + paramQuery);
        return paramQuery;
    }

    private static String getMappingURI(String uri) {
        String[] uriPeices = uri.split("/");
        Map<String, String> pathParams = new HashMap<>();
        String mappingURI = "";
        for(int i = 1; i < uriPeices.length; i+=2) {
            String group = uriPeices[i];
            String groupId = group + "Id";
            mappingURI = addURIPath(mappingURI, group);
            if(i + 1 >= uriPeices.length) {
                break;
            }
            pathParams.put(groupId, uriPeices[i+1]);
            mappingURI = addURIPath(mappingURI, "#{" + groupId +"}");
        }

        return mappingURI;
    }
    private static String addURIPath(String path, String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("/");
        sb.append(name);
        return sb.toString();
    }
    private static void splitUriStructure(String uri) {
        List<String> gourps = new ArrayList<>();
        List<String> resources = new ArrayList<>();

        String[] uriPieces = uri.split("/");

    }

}
