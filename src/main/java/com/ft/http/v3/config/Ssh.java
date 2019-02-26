package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ssh {

    @JsonCreator
    public Ssh(@JsonProperty("service") String service,
               @JsonProperty("username") String username,
               @JsonProperty("password") String password,
               @JsonProperty("permissionElevation") String permissionElevation,
               @JsonProperty("permissionElevationUsername") String permissionElevationUsername,
               @JsonProperty("permissionElevationPassword") String permissionElevationPassword) {
        this.service = service;
        this.username = username;
        this.password = password;
        this.permissionElevation = permissionElevation;
        this.permissionElevationUsername = permissionElevationUsername;
        this.permissionElevationPassword = permissionElevationPassword;
    }

    private String service;
    private String username;
    private String password;
    private String permissionElevation;
    private String permissionElevationUsername;
    private String permissionElevationPassword;
}
