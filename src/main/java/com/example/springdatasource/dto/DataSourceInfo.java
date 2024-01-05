package com.example.springdatasource.dto;

/**
 * 数据源对象类
 */
public class DataSourceInfo {

    private String userName;
    private String passWord;
    private String url;
    private String dataSourceKey;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDataSourceKey() {
        return dataSourceKey;
    }

    public void setDataSourceKey(String dataSourceKey) {
        this.dataSourceKey = dataSourceKey;
    }

    @Override
    public String toString() {
        return "DataSourceInfo{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", url='" + url + '\'' +
                ", dataSourceKey='" + dataSourceKey + '\'' +
                '}';
    }
}
