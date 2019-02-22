package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AssetsSoftware {

    @JsonCreator
    public AssetsSoftware(@JsonProperty("resources") List<Software> resources) {
        this.resources = resources;
    }

    public List<Software> getResources() {
        return resources;
    }

    private List<Software> resources;
}
