package com.kssoft.lake.net.responses.vo;

import com.kssoft.lake.net.responses.NetWrapper;

public class LoginVO extends NetWrapper<String> {

    private String token;

    @Override
    public String getData() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
