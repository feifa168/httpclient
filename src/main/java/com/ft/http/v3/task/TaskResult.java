package com.ft.http.v3.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.scan.ScanResult;

import java.util.List;

public class TaskResult {
    @JsonCreator
    public TaskResult(@JsonProperty("page") Page page,
                      @JsonProperty("scanResult") List<ScanResult> resources) {
        this.page = page;
        this.resources = resources;
    }

    private Page page;
    private List<ScanResult> resources;
}
