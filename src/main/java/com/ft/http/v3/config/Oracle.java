package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Oracle {

    @JsonCreator
    public Oracle(@JsonProperty("service") String service,
                  @JsonProperty("sid") String sid,
                  @JsonProperty("username") String username,
                  @JsonProperty("password") String password,
                  @JsonProperty("enumerateSids") boolean enumerateSids,
                  @JsonProperty("oracleListenerPassword") String oracleListenerPassword) {
        this.service = service;
        this.sid = sid;
        this.username = username;
        this.password = password;
        this.enumerateSids = enumerateSids;
        this.oracleListenerPassword = oracleListenerPassword;
    }

    private String service;
    private String sid;
    private String username;
    private String password;
    private boolean enumerateSids;
    private String oracleListenerPassword;
}
