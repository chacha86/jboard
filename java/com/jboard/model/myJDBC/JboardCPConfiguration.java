package com.jboard.model.myJDBC;

public class JboardCPConfiguration {

    private String driverClassName;
    private String url;
    private String username;
    private String userpass;

    public JboardCPConfiguration() {
    }

    public JboardCPConfiguration(String driverClassName, String url, String username, String userpass) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.userpass = userpass;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }
}
