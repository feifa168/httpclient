package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Snmp {

    @JsonCreator
    public Snmp(@JsonProperty("service") String service,
                @JsonProperty("communityName") String communityName) {
        this.service = service;
        this.communityName = communityName;
    }

    private String service;
    private String communityName;
}
