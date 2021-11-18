package com.jboard.model.myJDBC;

import com.mysql.cj.jdbc.JdbcPreparedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class DBWorker {
    private Connection conn;
    private Logger logger = LoggerFactory.getLogger("sql");

    public DBWorker(Connection conn) {
        this.conn = conn;
    }

    public List<Map<String, Object>> getList(String sql, Map<String, Object> params) {
        List<Map<String, Object>> result = new ArrayList<>();
        if(conn != null) {
            try {
                PreparedStatement ps = getPrepareStatementBindedParams(sql, params);
                ResultSet rs = ps.executeQuery();
                result = convertResultSetToMapList(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public Map<String, Object> getSingle(String sql, Map<String, Object> params) {
        List<Map<String, Object>> list = getList(sql, params);
        if(list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
//    public PreparedStatement getPrepareStatementBindedParams(String sql, Map<String, Object> params) throws SQLException {
//        bindParamsToStatemnt(sql, params);
//        PreparedStatement ps = conn.prepareStatement(sql);
//        if(params != null) {
//            Iterator<String> iter;
//            iter = params.keySet().iterator();
//            int i = 1;
//            while(iter.hasNext()) {
//                Object param = params.get(iter.next());
//                if(param instanceof Integer) {
//                    ps.setInt(i, (int)param);
//                } else if(param instanceof String) {
//                    ps.setString(i, (String)param);
//                }
//                i++;
//            }
//        }
//        return ps;
//    }

    private PreparedStatement getPrepareStatementBindedParams(String paramQuery, Map<String, Object> params) throws SQLException {
        BindingMaterials bindingMaterials = getBindingMaterials(paramQuery, params);
        String bindedQuery = bindingMaterials.getBindingQeury();
        List<Object> orderedParamList = bindingMaterials.getOrderedParamList();

        PreparedStatement ps = conn.prepareStatement(bindedQuery);
        if(orderedParamList != null || orderedParamList.size() != 0 ) {
            for(int i = 0; i < orderedParamList.size(); i++) {
                Object param = orderedParamList.get(i);
                if(param instanceof Integer) {
                    ps.setInt(i + 1, (int)param);
                } else if(param instanceof String) {
                    ps.setString(i + 1, (String)param);
                }
            }
        }
        return ps;
    }

    private BindingMaterials getBindingMaterials(String paramQuery, Map<String, Object> params) {
        char deli = '#';
        List<Object> orderedParamValueList = new ArrayList<>();
        int isNext = paramQuery.indexOf(deli);
        while(isNext != -1) {
            int startIdx = paramQuery.indexOf('{');
            int endIdx = paramQuery.indexOf('}');
            String paramKey = paramQuery.substring(startIdx + 1, endIdx);
            orderedParamValueList.add(params.get(paramKey));
            paramQuery = paramQuery.replace(String.format("#{%s}", paramKey),"?");
            isNext = paramQuery.indexOf(deli);
        }
        logger.debug("queryForPrepareStetment : " + paramQuery);
        BindingMaterials bindingMaterials = new BindingMaterials(orderedParamValueList, paramQuery);
        return bindingMaterials;
    }

//    public ResultSet getResultByState(String sql, List<Object> params) throws SQLException {
//
//        PreparedStatement ps = conn.prepareStatement(sql);
//        if(params != null) {
//            for(int i = 0; i < params.size(); i++) {
//                Object param = params.get(i);
//                if(param instanceof Integer) {
//                    ps.setInt(i + 1, (int)param);
//                } else if(param instanceof String) {
//                    ps.setString(i + 1, (String)param);
//                }
//            }
//        }
//
//        logger.debug("sql : " + ((JdbcPreparedStatement)ps).getPreparedSql());
//        logger.debug("params : " + params.toString());
//        return ps.executeQuery();
//    }

    public List<Map<String, Object>> convertResultSetToMapList(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();
        Map<String, Object> row;
        List<Map<String, Object>> rows = new ArrayList<>();

        while(rs.next()) {
            row = new HashMap<>();
            for(int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                if(rsMetaData.getColumnTypeName(i).startsWith("INT")) {
                    row.put(rsMetaData.getColumnName(i), rs.getInt(i));
                } else {
                    row.put(rsMetaData.getColumnName(i), rs.getString(i));
                }
            }

            rows.add(row);
        }
        return rows;
    }

    public void destroySession() {
        DBManager.returnConnectionToPool(this.conn);
    }

    public int update(String sql, Map<String, Object> params) {
        int result = 0;
        try {
            PreparedStatement ps = getPrepareStatementBindedParams(sql, params);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}

