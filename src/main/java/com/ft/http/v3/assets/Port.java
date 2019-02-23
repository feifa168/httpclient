package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Port {

    @JsonCreator
    public Port(@JsonProperty("address") String address,
                @JsonProperty("port") int port,
                @JsonProperty("process") String process,
                @JsonProperty("protocol") String protocol) {
        this.address = address;
        this.port = port;
        this.process = process;
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getProcess() {
        return process;
    }

    public String getProtocol() {
        return protocol;
    }

    private String address;
    private int    port;
    private String process;
    private String protocol;
}
