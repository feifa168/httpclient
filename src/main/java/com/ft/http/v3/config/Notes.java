package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Notes {
    private String service;
    private String notesIDPassword;

    @JsonCreator
    public Notes(@JsonProperty("service") String service,
                 @JsonProperty("notesIDPassword") String notesIDPassword) {
        this.service = service;
        this.notesIDPassword = notesIDPassword;
    }
}
