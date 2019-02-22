package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AssetsScanVulnerabilities {
    @JsonCreator
    public AssetsScanVulnerabilities(@JsonProperty("id") String id,
                           @JsonProperty("title") String title,
                           @JsonProperty("cvss") int cvss,
                           @JsonProperty("cve") String cve,
                           @JsonProperty("publishDate") String publishDate,
                           @JsonProperty("riskScore") int riskScore,
                           @JsonProperty("severity") int severity,
                           @JsonProperty("exploit") int exploit,
                           @JsonProperty("malware") int malware,
                           @JsonProperty("instances") int instances) {
        this.id = id;
        this.title = title;
        this.cvss = cvss;
        this.cve = cve;
        this.publishDate = publishDate;
        this.riskScore = riskScore;
        this.severity = severity;
        this.exploit = exploit;
        this.malware = malware;
        this.instances = instances;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCvss() {
        return cvss;
    }

    public String getCve() {
        return cve;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public int getSeverity() {
        return severity;
    }

    public int getExploit() {
        return exploit;
    }

    public int getMalware() {
        return malware;
    }

    public int getInstances() {
        return instances;
    }

    private String id;
    private String title;
    private int    cvss;
    private String cve;
    private String publishDate;
    private int    riskScore;
    private int    severity;
    private int    exploit;
    private int    malware;
    private int    instances;
}