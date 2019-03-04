package com.ft.http.v3.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.scan.ScanResult;

import java.util.List;

public class TaskResult implements PageAndResources<ScanResult> {
    @JsonCreator
    public TaskResult(@JsonProperty("page") Page page,
                      @JsonProperty("scanResult") List<ScanResult> resources) {
        this.page = page;
        this.resources = resources;
    }

    public Page getPage() {
        return page;
    }

    public List<ScanResult> getResources() {
        return resources;
    }

    private Page page;
    private List<ScanResult> resources;
}
