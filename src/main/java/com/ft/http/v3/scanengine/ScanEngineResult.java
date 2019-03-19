package com.ft.http.v3.scanengine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScanEngineResult {
    private int total;
    private List<ScanEngine> resources;

    @JsonCreator
    public ScanEngineResult(@JsonProperty("resources") List<ScanEngine> resources) {
        this.resources = resources;
    }

    public List<ScanEngine> getResources() {
        return resources;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
