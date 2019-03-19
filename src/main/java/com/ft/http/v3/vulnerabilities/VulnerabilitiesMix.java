package com.ft.http.v3.vulnerabilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.assets.AssetsScanVulnerabilities;

import java.util.List;

public class VulnerabilitiesMix {

    public VulnerabilitiesMix(){}

    @JsonCreator
    public VulnerabilitiesMix(@JsonProperty("assetsScanVulnerabilities") AssetsScanVulnerabilities assetsScanVulnerabilities,
                              @JsonProperty("vulnerabilitiesDetail") VulnerabilitiesDetail vulnerabilitiesDetail,
                              @JsonProperty("singleSolutions") List<SingleSolutions> singleSolutions) {
        this.assetsScanVulnerabilities = assetsScanVulnerabilities;
        this.vulnerabilitiesDetail = vulnerabilitiesDetail;
        this.singleSolutions = singleSolutions;
    }

    public void setAssetsScanVulnerabilities(AssetsScanVulnerabilities assetsScanVulnerabilities) {
        this.assetsScanVulnerabilities = assetsScanVulnerabilities;
    }

    public void setVulnerabilitiesDetail(VulnerabilitiesDetail vulnerabilitiesDetail) {
        this.vulnerabilitiesDetail = vulnerabilitiesDetail;
    }

    public void setSingleSolutions(List<SingleSolutions> singleSolutions) {
        this.singleSolutions = singleSolutions;
    }

    private AssetsScanVulnerabilities   assetsScanVulnerabilities;
    private VulnerabilitiesDetail       vulnerabilitiesDetail;
    private List<SingleSolutions>       singleSolutions;
}
