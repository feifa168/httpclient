package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Snmpv3 {

    @JsonCreator
    public Snmpv3(@JsonProperty("service") String service,
                  @JsonProperty("authenticationType") String authenticationType,
                  @JsonProperty("username") String username,
                  @JsonProperty("password") String password,
                  @JsonProperty("privacyType") String privacyType,
                  @JsonProperty("privacyPassword") String privacyPassword) {
        this.service = service;
        this.authenticationType = authenticationType;
        this.username = username;
        this.password = password;
        this.privacyType = privacyType;
        this.privacyPassword = privacyPassword;
    }
    private String service;
    private String authenticationType;
    private String username;
    private String password;
    private String privacyType;
    private String privacyPassword;
}
