package com.jboard.test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PstmtTest {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3333/t1?serverTimezone=UTC";
        String user = "chacha";
        String pass = "ckxowls1!";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);

//            PreparedStatement pstmt = conn.prepareStatement("select * from article");
//            ResultSet rs = pstmt.executeQuery();
//            ResultSetMetaData rsmd = rs.getMetaData();
//            Map<String, Object> row = null;
//            List<Map<String, Object>> rows = new ArrayList<>();
//
//            while(rs.next()) {
//                row = new HashMap<String, Object>();
//                for(int i = 1; i <= rsmd.getColumnCount(); i++) {
//                    if(rsmd.getColumnTypeName(i).startsWith("INT")) {
//                        row.put(rsmd.getColumnName(i), rs.getInt(i));
//                    } else {
//                        row.put(rsmd.getColumnName(i), rs.getString(i));
//                    }
//                }
//
//                rows.add(row);
//            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
