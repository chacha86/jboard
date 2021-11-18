package com.jboard.test;

import com.jboard.model.myJDBC.BindingMaterials;

import java.util.ArrayList;
import java.util.List;

public class paramQueryTest {

   public static void main(String[] args) {
       String sql = "insert into article set id=#{id}, title=#{title}, body=#{body}";
       getBindingMaterials(sql);
   }
    private static String getBindingMaterials(String paramQuery) {
        char deli = '#';
        String queryForPrepareStatement = "";
        List<String> orderedParamKeyList = new ArrayList<>();
        int isNext = paramQuery.indexOf(deli);
        while(isNext != -1) {
            int startIdx = paramQuery.indexOf('{');
            int endIdx = paramQuery.indexOf('}');
            String paramKey = paramQuery.substring(startIdx + 1, endIdx);
            orderedParamKeyList.add(paramKey);
            paramQuery = paramQuery.replace(String.format("#{%s}", paramKey),"?");
            isNext = paramQuery.indexOf(deli);
        }
        System.out.println("queryForPrepareStetment : " + paramQuery);
        return paramQuery;
    }

}
