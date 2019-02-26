package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DomainUserPwd{
    @JsonCreator
    public DomainUserPwd(@JsonProperty("service") String service,
                         @JsonProperty("domain") String domain,
                         @JsonProperty("username") String username,
                         @JsonProperty("password") String password) {
        this.service = service;
        this.domain = domain;
        this.username = username;
        this.password = password;
    }

    private String service;
    private String domain;
    private String username;
    private String password;
}
