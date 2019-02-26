package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DbDomainUserPwd {
    @JsonCreator
    public DbDomainUserPwd(@JsonProperty("service") String service,
                 @JsonProperty("domain") String database,
                 @JsonProperty("useWindowsAuthentication") boolean useWindowsAuthentication,
                 @JsonProperty("domain") String domain,
                 @JsonProperty("username") String username,
                 @JsonProperty("password") String password) {
        this.service = service;
        this.database = database;
        this.useWindowsAuthentication = useWindowsAuthentication;
        this.domain = domain;
        this.username = username;
        this.password = password;
    }

    private String service;
    private String database;
    private boolean useWindowsAuthentication;
    private String domain;
    private String username;
    private String password;
}
