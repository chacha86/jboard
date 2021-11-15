package com.jboard.model.myJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class JboardCP {

    private static JboardCP jboardCPInstance = null;
    private Stack<Connection> connectionPool;
    private int maxConn = 5;

    private JboardCP(JboardCPConfiguration configuration) {
        createPool(configuration);
    }

    public static JboardCP getInstance(JboardCPConfiguration configuration) {
        if(jboardCPInstance == null) {
            jboardCPInstance = new JboardCP(configuration);
        }
        return jboardCPInstance;
    }

    public Connection getConnection() {
        if(!connectionPool.empty()) {
            Connection conn = connectionPool.pop();
            System.out.println(conn + "이 인출");
            return conn;
        }

        return null;
    }

    public void returnConnection(Connection conn) {
        if(connectionPool.size() < maxConn) {
            connectionPool.push(conn);
            System.out.println(conn + "이 반환");
        }
    }

    private void createPool(JboardCPConfiguration configuration) {
        try {

            Class.forName(configuration.getDriverClassName());
            connectionPool = new Stack<>();

            for(int i = 0; i < maxConn; i++) {
                
                Connection conn = DriverManager.getConnection(configuration.getUrl(), configuration.getUsername(), configuration.getUserpass());
                System.out.println(conn + "생성되어 풀에 저장");
                connectionPool.push(conn);
            }

            System.out.println("pool create completion : " + connectionPool.size());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            destroyPool();
        }
    }

    public void destroyPool() {
        try {
            while(!connectionPool.empty()) {
                connectionPool.pop().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
