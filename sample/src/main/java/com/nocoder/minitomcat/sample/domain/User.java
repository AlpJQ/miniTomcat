package com.nocoder.minitomcat.sample.domain;

public class User {
    private String username;
    private String password;
    private String realName;
    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User() {
    }

    public User(String username, String password, String realName, Integer age) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.age = age;
    }
}
