package com.jboard.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlMapper {

    private DBManager dbManager;

    public SqlMapper() {
        JboardCPConfiguration configuration = new JboardCPConfiguration();
        configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");
        configuration.setUrl("jdbc:mysql://localhost:3333/t1?serverTimezone=UTC");
        configuration.setUsername("chacha");
        configuration.setUserpass("ckxowls1!");

        dbManager = new DBManager(configuration);
    }

    public List<Map<String, Object>> getAllArticles() {
        String sql = "select * from article";
        DBWorker dbWorker = dbManager.createSession();
        List<Map<String, Object>> articles = dbWorker.getList(sql, null);
        dbWorker.destroySession();
        return articles;
    }


}
