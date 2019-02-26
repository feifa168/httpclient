package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DbUserPwd {
    @JsonCreator
    public DbUserPwd(@JsonProperty("service") String service,
                           @JsonProperty("database") String database,
                           @JsonProperty("username") String username,
                           @JsonProperty("password") String password) {
        this.service = service;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    private String service;
    private String database;
    private String username;
    private String password;
}
