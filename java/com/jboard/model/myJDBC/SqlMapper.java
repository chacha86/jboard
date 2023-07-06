package com.jboard.model.myJDBC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlMapper {

    private DBManager dbManager;
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://101.101.218.17:3306/t1?serverTimezone=UTC";
    private String username = "testUser";
    private String userpass = "1234";

    public SqlMapper() {
        JboardCPConfiguration configuration = new JboardCPConfiguration();
        configuration.setDriverClassName(driverClassName);
        configuration.setUrl(url);
        configuration.setUsername(username);
        configuration.setUserpass(userpass);

        dbManager = new DBManager(configuration);
    }

    public List<Map<String, Object>> getAllArticles() {
        String sql = "select * from article";
        DBWorker dbWorker = dbManager.createSession();
        List<Map<String, Object>> articles = dbWorker.getList(sql, null);
        dbWorker.destroySession();

        return articles;
    }

    public Map<String, Object> getArticleById(int id) {
        String sql = "select * from article where idx = ?";
        DBWorker worker = dbManager.createSession();
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        Map<String, Object> article = worker.getSingle(sql, params);
        worker.destroySession();
        return article;
    }
}
