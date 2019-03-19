package com.ft.http.v3.scantemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//{
//    "checks": {
//        "categories": {
//            "disabled": [],
//            "enabled": []
//        },
//        "correlate": true,
//        "individual": {
//            "disabled": [],
//            "enabled": []
//        },
//        "potential": false,
//        "types": {
//            "disabled": [
//                "Local",
//                "Patch",
//                "Policy"
//            ],
//            "enabled": []
//        },
//        "unsafe": true
//    },
//    "database": {
//        "db2": null,
//        "oracle": [
//            "SAY",
//            "SAZ"
//        ],
//        "postgres": "template1"
//    },
//    "description": "使用安全和不安全（拒绝服务）检查对所有系统执行基本的网络审计。不会执行深入补丁/修复程序检查、策略合规性检查和应用程序层审计等。",
//    "discovery": {
//        "asset": {
//            "collectWhoisInformation": false,
//            "fingerprintMinimumCertainty": 0.16,
//            "fingerprintRetries": 0,
//            "ipFingerprintingEnabled": true,
//            "sendArpPings": true,
//            "sendIcmpPings": true,
//            "tcpPorts": [
//                21,
//                8080
//            ],
//            "treatTcpResetAsAsset": true,
//            "udpPorts": [
//                53,
//                49152
//            ]
//        },
//        "performance": {
//            "packetRate": {
//                "defeatRateLimit": true,
//                "maximum": 15000,
//                "minimum": 450
//            },
//            "parallelism": {
//                "maximum": 0,
//                "minimum": 0
//            },
//            "retryLimit": 3,
//            "scanDelay": {
//                "maximum": "PT0S",
//                "minimum": "PT0S"
//            },
//            "timeout": {
//                "initial": "PT0.5S",
//                "maximum": "PT3S",
//                "minimum": "PT0.5S"
//            }
//        },
//        "service": {
//            "serviceNameFile": "default-services.properties",
//            "tcp": {
//                "additionalPorts": [
//                    "1-1040"
//                ],
//                "excludedPorts": null,
//                "method": "SYN",
//                "ports": "WELL_KNOWN"
//            },
//            "udp": {
//                "additionalPorts": null,
//                "excludedPorts": null,
//                "ports": "WELL_KNOWN"
//            }
//        }
//    },
//    "discoveryOnly": false,
//    "id": "dos-audit",
//    "maxParallelAssets": 10,
//    "maxScanProcesses": 10,
//    "name": "拒绝服务",
//    "vulnerabilityEnabled": true,
//    "web": {
//        "dontScanMultiUseDevices": true,
//        "includeQueryStrings": false,
//        "paths": {
//            "boostrap": null,
//            "excluded": null,
//            "honorRobotDirectives": false
//        },
//        "patterns": {
//            "sensitiveContent": null,
//            "sensitiveField": null
//        },
//        "performance": {
//            "httpDaemonsToSkip": [
//                "RMC Webserver",
//                "Virata-EmWeb"
//            ],
//            "maximumDirectoryLevels": 6,
//            "maximumForeignHosts": 100,
//            "maximumLinkDepth": 6,
//            "maximumPages": 3000,
//            "maximumRetries": 2,
//            "maximumTime": "PT0S",
//            "responseTimeout": "PT2M",
//            "threadsPerServer": 3
//        },
//        "testCommonUsernamesAndPasswords": false,
//        "testXssInSingleScan": false,
//        "userAgent": null
//    },
//    "webEnabled": true
//}
public class ScanTemplate {

    @JsonCreator
    public ScanTemplate(@JsonProperty("id") String id,
                        @JsonProperty("checks") ScanTemplateVulnerabilityChecks checks,
                        @JsonProperty("database") ScanTemplateDatabase database,
                        @JsonProperty("description") String description,
                        @JsonProperty("discovery") ScanTemplateDiscovery discovery,
                        @JsonProperty("discoveryOnly") boolean discoveryOnly,
                        @JsonProperty("maxParallelAssets") int maxParallelAssets,
                        @JsonProperty("maxScanProcesses") int maxScanProcesses,
                        @JsonProperty("name") String name,
                        @JsonProperty("vulnerabilityEnabled") boolean vulnerabilityEnabled,
                        @JsonProperty("web") ScanTemplateWebSpider web,
                        @JsonProperty("webEnabled") boolean webEnabled) {
        this.id = id;
        this.checks = checks;
        this.database = database;
        this.description = description;
        this.discovery = discovery;
        this.discoveryOnly = discoveryOnly;
        this.maxParallelAssets = maxParallelAssets;
        this.maxScanProcesses = maxScanProcesses;
        this.name = name;
        this.vulnerabilityEnabled = vulnerabilityEnabled;
        this.web = web;
        this.webEnabled = webEnabled;
    }

    public ScanTemplateVulnerabilityChecks getChecks() {
        return checks;
    }

    public ScanTemplateDatabase getDatabase() {
        return database;
    }

    public String getDescription() {
        return description;
    }

    public ScanTemplateDiscovery getDiscovery() {
        return discovery;
    }

    public boolean isDiscoveryOnly() {
        return discoveryOnly;
    }

    public int getMaxParallelAssets() {
        return maxParallelAssets;
    }

    public int getMaxScanProcesses() {
        return maxScanProcesses;
    }

    public String getName() {
        return name;
    }

    public boolean isVulnerabilityEnabled() {
        return vulnerabilityEnabled;
    }

    public ScanTemplateWebSpider getWeb() {
        return web;
    }

    public boolean isWebEnabled() {
        return webEnabled;
    }

    public void setChecks(ScanTemplateVulnerabilityChecks checks) {
        this.checks = checks;
    }

    public void setDatabase(ScanTemplateDatabase database) {
        this.database = database;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscovery(ScanTemplateDiscovery discovery) {
        this.discovery = discovery;
    }

    public void setDiscoveryOnly(boolean discoveryOnly) {
        this.discoveryOnly = discoveryOnly;
    }

    public void setMaxParallelAssets(int maxParallelAssets) {
        this.maxParallelAssets = maxParallelAssets;
    }

    public void setMaxScanProcesses(int maxScanProcesses) {
        this.maxScanProcesses = maxScanProcesses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVulnerabilityEnabled(boolean vulnerabilityEnabled) {
        this.vulnerabilityEnabled = vulnerabilityEnabled;
    }

    public void setWeb(ScanTemplateWebSpider web) {
        this.web = web;
    }

    public void setWebEnabled(boolean webEnabled) {
        this.webEnabled = webEnabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private ScanTemplateVulnerabilityChecks checks;
    private ScanTemplateDatabase database;
    private String description;
    private ScanTemplateDiscovery discovery;
    private boolean discoveryOnly;
    private int maxParallelAssets;
    private int maxScanProcesses;
    private String name;
    private boolean vulnerabilityEnabled;
    private ScanTemplateWebSpider web;
    private boolean webEnabled;
}
