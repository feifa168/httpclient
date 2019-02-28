package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RunConfig {

    @JsonCreator
    public RunConfig(
            @JsonProperty("usessl") boolean usessl,
            @JsonProperty("useauth") boolean useauth,
            @JsonProperty("authName") String authName,
            @JsonProperty("authPassword") String authPassword,
            @JsonProperty("host") String host,
            @JsonProperty("port") int port) {
        this.usessl = usessl;
        this.useauth = useauth;
        this.authName = authName;
        this.authPassword = authPassword;
        this.host = host;
        this.port = port;
    }

    public boolean isUsessl() {
        return usessl;
    }

    public boolean isUseauth() {
        return useauth;
    }

    public String getAuthName() {
        return authName;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    private boolean usessl;
    private boolean useauth;
    private String authName;
    private String authPassword;
    private String host;
    private int port;
}
