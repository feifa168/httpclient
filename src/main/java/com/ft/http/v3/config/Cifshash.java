package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cifshash {
    @JsonCreator
    public Cifshash(@JsonProperty("service") String service,
                         @JsonProperty("domain") String domain,
                         @JsonProperty("username") String username,
                         @JsonProperty("ntlmHash") String ntlmHash) {
        this.service = service;
        this.domain = domain;
        this.username = username;
        this.ntlmHash = ntlmHash;
    }

    private String service;
    private String domain;
    private String username;
    private String ntlmHash;
}
