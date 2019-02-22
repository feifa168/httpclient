package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// "osFingerprint": {
//      "architecture": "PowerPC",
//      "configurations": [
//      {
//      "name": "<name>",
//      "value": "<value>"
//      }
//      ],
//      "description": "Red Hat Fedora Core Linux 23",
//      "id": 2,
//      "product": "Fedora Core Linux",
//      "systemName": "Red Hat Linux",
//      "vendor": "Red Hat",
//      "version": "23"
//  },
public class OsFingerprint {

    @JsonCreator
    public OsFingerprint(@JsonProperty("architecture") String architecture,
                         @JsonProperty("configurations") List<Configuration> configurations,
                         @JsonProperty("description") String description,
                         @JsonProperty("id") int id,
                         @JsonProperty("product") String product,
                         @JsonProperty("systemName") String systemName,
                         @JsonProperty("vendor") String vendor,
                         @JsonProperty("version") String version) {
        this.architecture = architecture;
        this.configurations = configurations;
        this.description = description;
        this.id = id;
        this.product = product;
        this.systemName = systemName;
        this.vendor = vendor;
        this.version = version;
    }

    public String getArchitecture() {
        return architecture;
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

    public String getSystemName() {
        return systemName;
    }

    public String getVendor() {
        return vendor;
    }

    public String getVersion() {
        return version;
    }

    private String architecture;
    private List<Configuration> configurations;
    private String description;
    private int    id;
    private String product;
    private String systemName;
    private String vendor;
    private String version;
}
