package com.jboard.model.myJDBC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SqlMapper {

    private DBManager dbManager;
    private Logger logger = LoggerFactory.getLogger(SqlMapper.class);
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
//        String sql = "select * from article";
        String sql = """
                select *
                from 
                article
                """;
        DBWorker dbWorker = dbManager.createSession();
        List<Map<String, Object>> articles = dbWorker.getList(sql, null);
        dbWorker.destroySession();
        return articles;
    }

    public Map<String, Object> getArticleById(Map<String, Object> params) {
        String sql = "select * from article where idx = #{idx}";
        DBWorker worker = dbManager.createSession();
        Map<String, Object> article = worker.getSingle(sql, params);
        worker.destroySession();
        return article;
    }

    public int insertArticle(Map<String, Object> params) {
        String sql = "insert into article set title = #{title}, body = #{body}, memberIdx = #{memberIdx}, regDate = NOW(), updateDate = NOW()";
        DBWorker worker = dbManager.createSession();
        logger.debug(params.toString());
        int result = worker.update(sql, params);
        worker.destroySession();
        return result;
    }

    public int updateArticle(Map<String, Object> params) {
        String sql = "update article set title = #{title}, body = #{body}, updateDate = NOW() where idx = #{idx}";
        logger.debug(params.toString());
        DBWorker worker = dbManager.createSession();
        int result = worker.update(sql, params);
        worker.destroySession();
        return result;
    }

    public int deleteArticle(Map<String, Object> params) {
        String sql = "delete from article where idx = #{idx}";
        DBWorker worker = dbManager.createSession();
        int result = worker.update(sql, params);
        worker.destroySession();
        return result;
    }

    public List<Map<String, Object>> getRepliesByArticleId(Map<String, Object> params) {
        String sql = "select * from articleReply where articleIdx = #{articleIdx}";
        DBWorker dbWorker = dbManager.createSession();
        List<Map<String, Object>> replies = dbWorker.getList(sql, params);
        dbWorker.destroySession();

        return replies;
    }
}
