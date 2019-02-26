package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Http {
    @JsonCreator
    public Http(@JsonProperty("service") String service,
                     @JsonProperty("realm") String realm,
                     @JsonProperty("username") String username,
                     @JsonProperty("password") String password) {
        this.service = service;
        this.realm = realm;
        this.username = username;
        this.password = password;
    }

    private String service;
    private String realm;
    private String username;
    private String password;
}
