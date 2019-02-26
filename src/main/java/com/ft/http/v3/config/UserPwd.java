package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPwd {
    @JsonCreator
    public UserPwd(@JsonProperty("service") String service,
                         @JsonProperty("username") String username,
                         @JsonProperty("password") String password) {
        this.service = service;
        this.username = username;
        this.password = password;
    }

    private String service;
    private String username;
    private String password;
}
