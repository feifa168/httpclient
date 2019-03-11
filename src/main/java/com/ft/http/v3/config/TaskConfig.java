package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.credential.Credential;
import com.ft.http.v3.weakpassword.NewCracks;

import java.util.List;

public class TaskConfig {

    @JsonCreator
    public TaskConfig(@JsonProperty("id") String id,
                      @JsonProperty("ip") String ip,
                      @JsonProperty("cracks") NewCracks cracks,
                      @JsonProperty("credential") List<Credential> credentials) {
        this.id = id;
        this.ip = ip;
        this.cracks = cracks;
        this.credentials = credentials;
    }

    public String getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public NewCracks getCracks() {
        return cracks;
    }

    public List<Credential> getCredentials() {
        return credentials;
    }

    private String id;
    private String ip;
    private NewCracks cracks;
    private List<Credential> credentials;
}
