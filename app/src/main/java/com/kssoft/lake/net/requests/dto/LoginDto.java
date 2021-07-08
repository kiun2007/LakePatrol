package com.kssoft.lake.net.requests.dto;

import kiun.com.bvroutine.interfaces.JSON;

public class LoginDto implements JSON {

    public static final String TAG = "LoginDto";

    /**
     * 用户名称.
     */
    private String username;

    /**
     * 密码.
     */
    private String password;

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
}
