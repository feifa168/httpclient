package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.assets.Assets;
import com.ft.http.v3.assets.AssetsScanVulnerabilities;
import com.ft.http.v3.assets.Port;

import java.util.List;

public class AssetsScanResultMix {

    @JsonCreator
    public AssetsScanResultMix(@JsonProperty("assetId") int assetId,
                               @JsonProperty("assets") Assets assets,
                               @JsonProperty("address") String address,
                               @JsonProperty("vulnerabilitiesResult") List<AssetsScanVulnerabilities> vulnerabilitiesResult,
                               @JsonProperty("portResult") List<Port> portResult) {
        this.assetId = assetId;
        this.assets = assets;
        this.address = address;
        this.vulnerabilitiesResult = vulnerabilitiesResult;
        this.portResult = portResult;
    }

    private int assetId;
    private Assets assets;
    private String address;
    private List<AssetsScanVulnerabilities>  vulnerabilitiesResult;
    private List<Port> portResult;
}
