package com.ft.http.v3.assets;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Software {

    @JsonCreator
    public Software(@JsonProperty("configurations") List<Configuration> configurations,
                    @JsonProperty("description") String description,
                    @JsonProperty("id") int id,
                    @JsonProperty("product") String product,
                    @JsonProperty("vendor") String vendor,
                    @JsonProperty("version") String version) {
        this.configurations = configurations;
        this.description = description;
        this.id = id;
        this.product = product;
        this.vendor = vendor;
        this.version = version;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public String getVendor() {
        return vendor;
    }

    public String getVersion() {
        return version;
    }

    private List<Configuration> configurations;
    private String description;
    private int id;
    private String product;
    private String vendor;
    private String version;
}
