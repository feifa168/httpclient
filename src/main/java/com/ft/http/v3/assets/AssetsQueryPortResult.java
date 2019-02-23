package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AssetsQueryPortResult {
    @JsonCreator
    public AssetsQueryPortResult(@JsonProperty("resources") List<Port> resources) {
        this.resources = resources;
    }

    public List<Port> getResources() {
        return resources;
    }

    private List<Port> resources;
}
