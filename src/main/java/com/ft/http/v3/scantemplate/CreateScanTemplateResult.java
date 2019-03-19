package com.ft.http.v3.scantemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateScanTemplateResult {
    private String id;
    private String error;

    @JsonCreator
    public CreateScanTemplateResult(@JsonProperty("id") String id,
                                    @JsonProperty("error") String error) {
        this.id = id;
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public String getError() {
        return error;
    }
}
