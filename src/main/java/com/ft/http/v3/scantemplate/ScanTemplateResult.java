package com.ft.http.v3.scantemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScanTemplateResult {
    private int total;
    private List<ScanTemplate> resources;

    @JsonCreator
    public ScanTemplateResult(@JsonProperty("total") int total,
                              @JsonProperty("resources") List<ScanTemplate> resources) {
        this.total = total;
        this.resources = resources;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ScanTemplate> getResources() {
        return resources;
    }

    public void setResources(List<ScanTemplate> resources) {
        this.resources = resources;
    }
}
