package com.ft.http.v3.scantemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScanTemplateDiscovery {

    private ScanTemplateAssetDiscovery          asset;
    private ScanTemplateDiscoveryPerformance    performance;
    private ScanTemplateServiceDiscovery        service;

    @JsonCreator
    public ScanTemplateDiscovery(@JsonProperty("asset") ScanTemplateAssetDiscovery asset,
                                 @JsonProperty("performance") ScanTemplateDiscoveryPerformance performance,
                                 @JsonProperty("service") ScanTemplateServiceDiscovery service) {
        this.asset = asset;
        this.performance = performance;
        this.service = service;
    }

    public ScanTemplateAssetDiscovery getAsset() {
        return asset;
    }

    public ScanTemplateDiscoveryPerformance getPerformance() {
        return performance;
    }

    public ScanTemplateServiceDiscovery getService() {
        return service;
    }
}
