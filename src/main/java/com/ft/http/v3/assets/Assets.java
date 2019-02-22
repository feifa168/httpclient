package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.task.Page;

import java.util.List;

public class Assets {

    @JsonCreator
    public Assets(@JsonProperty("address") List<Address> addresses,
                  @JsonProperty("databases") Database databases,
                  @JsonProperty("hostName") String hostName,
                  @JsonProperty("id") int id,
                  @JsonProperty("ip") String ip,
                  @JsonProperty("mac") String mac,
                  @JsonProperty("os") String os,
                  @JsonProperty("osFingerprint") OsFingerprint osFingerprint,
                  @JsonProperty("riskScore") String riskScore,
                  @JsonProperty("services") List<Services> services,
                  @JsonProperty("software") List<Software> software,
                  @JsonProperty("type") String type,
                  @JsonProperty("vulnerabilities") Vulnerabilities vulnerabilities) {
        this.addresses = addresses;
        this.databases = databases;
        this.hostName = hostName;
        this.id = id;
        this.ip = ip;
        this.mac = mac;
        this.os = os;
        this.osFingerprint = osFingerprint;
        this.riskScore = riskScore;
        this.services = services;
        this.software = software;
        this.type = type;
        this.vulnerabilities = vulnerabilities;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Database getDatabases() {
        return databases;
    }

    public String getHostName() {
        return hostName;
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

    public String getOs() {
        return os;
    }

    public OsFingerprint getOsFingerprint() {
        return osFingerprint;
    }

    public String getRiskScore() {
        return riskScore;
    }

    public List<Services> getServices() {
        return services;
    }

    public List<Software> getSoftware() {
        return software;
    }

    public String getType() {
        return type;
    }

    public Vulnerabilities getVulnerabilities() {
        return vulnerabilities;
    }

    private List<Address> addresses;
    private Database databases;
    private String hostName;
    private int id;
    private String ip;
    private String mac;
    private String os;
    private OsFingerprint osFingerprint;
    private String riskScore;
    private List<Services> services;
    private List<Software> software;
    private String type;
    private Vulnerabilities vulnerabilities;
}
