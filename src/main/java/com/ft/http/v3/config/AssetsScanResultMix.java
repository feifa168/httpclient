package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.assets.Assets;
import com.ft.http.v3.assets.AssetsScanVulnerabilities;
import com.ft.http.v3.assets.Port;
import com.ft.http.v3.weakpassword.CrackScanResult;

import java.util.List;

public class AssetsScanResultMix {

    @JsonCreator
    public AssetsScanResultMix(@JsonProperty() String subTaskid,
                               @JsonProperty("assetId") int assetId,
                               @JsonProperty("assets") Assets assets,
                               @JsonProperty("address") String address,
                               @JsonProperty("crackScanResult") CrackScanResult crackScanResult,
                               @JsonProperty("vulnerabilitiesResult") List<AssetsScanVulnerabilities> vulnerabilitiesResult,
                               @JsonProperty("portResult") List<Port> portResult) {
        this.subTaskid = subTaskid;
        this.assetId = assetId;
        this.assets = assets;
        this.address = address;
        this.crackScanResult = crackScanResult;
        this.vulnerabilitiesResult = vulnerabilitiesResult;
        this.portResult = portResult;
    }

    private String subTaskid;
    private int assetId;
    private Assets assets;
    private String address;
    private CrackScanResult crackScanResult;
    private List<AssetsScanVulnerabilities>  vulnerabilitiesResult;
    private List<Port> portResult;

    public Assets getAssets() {
        return assets;
    }

    public void setCrackScanResult(CrackScanResult crackScanResult) {
        this.crackScanResult = crackScanResult;
    }

    public void setSubTaskid(String subTaskid) {
        this.subTaskid = subTaskid;
    }
}
