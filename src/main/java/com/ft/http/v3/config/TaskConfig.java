package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.credential.Credential;

import java.util.List;

public class TaskConfig {

    @JsonCreator
    public TaskConfig(@JsonProperty("ip") String ip,
                      @JsonProperty("credential") List<Credential> credentials) {
        this.ip = ip;
        this.credentials = credentials;
    }

    public String getIp() {
        return ip;
    }

    public List<Credential> getCredentials() {
        return credentials;
    }

    private String ip;
    private List<Credential> credentials;
}
