package com.jboard.model.myJDBC;

import java.sql.Connection;

public class DBManager {

    static private JboardCP jboardCp;

    public DBManager(JboardCPConfiguration jboardCPConfiguration) {
        jboardCp = JboardCP.getInstance(jboardCPConfiguration);
    }

    public DBWorker createSession() {
        Connection conn = jboardCp.getConnection();
        if(conn == null) {
            System.out.println("pool이 비었음");
        }
        return new DBWorker(conn);
    }

    public static void returnConnectionToPool(Connection conn) {
        jboardCp.returnConnection(conn);
    }
}
