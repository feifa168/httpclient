package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SshKey {

    @JsonCreator
    public SshKey(@JsonProperty("service") String service,
                  @JsonProperty("username") String username,
                  @JsonProperty("privateKeyPassword") String privateKeyPassword,
                  @JsonProperty("pemKey") String pemKey,
                  @JsonProperty("permissionElevation") String permissionElevation,
                  @JsonProperty("permissionElevationUsername") String permissionElevationUsername,
                  @JsonProperty("permissionElevationPassword") String permissionElevationPassword) {
        this.service = service;
        this.username = username;
        this.privateKeyPassword = privateKeyPassword;
        this.pemKey = pemKey;
        this.permissionElevation = permissionElevation;
        this.permissionElevationUsername = permissionElevationUsername;
        this.permissionElevationPassword = permissionElevationPassword;
    }

    private String service;
    private String username;
    private String privateKeyPassword;
    private String pemKey;
    private String permissionElevation;
    private String permissionElevationUsername;
    private String permissionElevationPassword;
}
