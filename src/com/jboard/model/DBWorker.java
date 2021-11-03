package com.jboard.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBWorker {
    private Connection conn;

    public DBWorker(Connection conn) {
        this.conn = conn;
    }

    public List<Map<String, Object>> getList(String sql, List<Object> params) {
        List<Map<String, Object>> result = new ArrayList<>();
        if(conn != null) {
            try {
                result = convertResultSetToMapList(getResultByState(sql, params));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public Map<String, Object> getSingle(String sql, List<Object> params) {
        List<Map<String, Object>> list = getList(sql, params);
        if(list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public ResultSet getResultByState(String sql, List<Object> params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        if(params != null) {
            for(int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if(param instanceof Integer) {
                    ps.setInt(i, (int)param);
                } else if(param instanceof String) {
                    ps.setString(i, (String)param);
                }
            }
        }

        return ps.executeQuery();
    }

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
}

