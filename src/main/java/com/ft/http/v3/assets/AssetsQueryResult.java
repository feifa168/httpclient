package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.task.Page;
import com.ft.http.v3.task.PageAndResources;

import java.util.List;

public class AssetsQueryResult implements PageAndResources<Assets> {

    @JsonCreator
    public AssetsQueryResult(@JsonProperty("page") Page page,
                             @JsonProperty("resources") List<Assets> resources) {
        this.page = page;
        this.resources = resources;
    }

    public Page getPage() {
        return page;
    }

    public List<Assets> getResources() {
        return resources;
    }
    private Page page;
    private List<Assets> resources;
}
