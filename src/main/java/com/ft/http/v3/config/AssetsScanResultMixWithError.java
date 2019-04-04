package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.scan.ScanResult;
import com.ft.http.v3.scanengine.ScanEngineResult;
import com.ft.http.v3.scantemplate.ScanTemplate;
import com.ft.http.v3.scantemplate.ScanTemplateResult;
import com.ft.http.v3.update.OfflineUpdateResult;
import com.ft.http.v3.vulnerabilities.ScanVulnerabilityResult;
import com.ft.http.v3.vulnerabilities.VulnerabilityResult;
import com.ft.http.v3.weakpassword.CrackScanResult;

import java.util.List;

public class AssetsScanResultMixWithError {

    public AssetsScanResultMixWithError() {
        this.success = false;
    }

    public static class MixResult {
        private int total;
        private List<AssetsScanResultMix> resources;

        @JsonCreator
        public MixResult(@JsonProperty("total") int total,
                         @JsonProperty("resources") List<AssetsScanResultMix> resources) {
            this.total = total;
            this.resources = resources;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<AssetsScanResultMix> getResources() {
            return resources;
        }

        public void setResources(List<AssetsScanResultMix> resources) {
            this.resources = resources;
        }
    }

    @JsonCreator
    public AssetsScanResultMixWithError(@JsonProperty("toolcategory") String toolcategory,
                                        @JsonProperty("taskcode") String taskcode,
                                        @JsonProperty("scanType") TaskScanConfig.ScanType scanType,
                                        @JsonProperty("success") boolean success,
                                        @JsonProperty("message") String message,
                                        @JsonProperty("taskid") String taskid,
                                        @JsonProperty("scanid") String scanid,
                                        @JsonProperty("mixScanResult") MixResult mixScanResult,
                                        @JsonProperty("scanResult") ScanResult scanResult,
                                        @JsonProperty("scanTemplateResult") ScanTemplateResult scanTemplateResult,
                                        @JsonProperty("scanEnginResult") ScanEngineResult scanEnginResult,
                                        @JsonProperty("scanVulnerabilityResult") ScanVulnerabilityResult scanVulnerabilityResult,
                                        @JsonProperty("offlineUpdateResult") OfflineUpdateResult offlineUpdateResult) {
        this.toolcategory = toolcategory;
        this.taskcode = taskcode;
        this.scanType = scanType;
        this.success = success;
        this.message = message;
        this.taskid = taskid;
        this.scanid = scanid;
        this.mixScanResult = mixScanResult;
        this.scanResult = scanResult;
        this.scanTemplateResult = scanTemplateResult;
        this.scanEnginResult = scanEnginResult;
        this.scanVulnerabilityResult = scanVulnerabilityResult;
        this.offlineUpdateResult = offlineUpdateResult;
    }
//    public AssetsScanResultMixWithError(@JsonProperty("toolcategory") String toolcategory,
//                                        @JsonProperty("taskcode") String taskcode,
//                                        @JsonProperty("scanType") TaskScanConfig.ScanType scanType,
//                                        @JsonProperty("success") boolean success,
//                                        @JsonProperty("message") String message,
//                                        @JsonProperty("taskid") String taskid,
//                                        @JsonProperty("scanid") String scanid,
//                                        @JsonProperty("result") List<AssetsScanResultMix> result) {
//        this.toolcategory = toolcategory;
//        this.taskcode = taskcode;
//        this.scanType = scanType;
//        this.success = success;
//        this.message = message;
//        this.taskid = taskid;
//        this.scanid = scanid;
//        this.result = result;
//    }

    public void setToolcategory(String toolcategory) {
        this.toolcategory = toolcategory;
    }

    public void setTaskcode(String taskcode) {
        this.taskcode = taskcode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }
    public void setScanid(String scanid) {
        this.scanid = scanid;
    }

//    public void setResult(List<AssetsScanResultMix> result) {
//        this.result = result;
//    }

    public void setMixScanResult(MixResult mixScanResult) {
        this.mixScanResult = mixScanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setScanType(TaskScanConfig.ScanType scanType) {
        this.scanType = scanType;
    }

    public void setScanTemplateResult(ScanTemplateResult scanTemplateResult) {
        this.scanTemplateResult = scanTemplateResult;
    }

    public void setScanEnginResult(ScanEngineResult scanEnginResult) {
        this.scanEnginResult = scanEnginResult;
    }

    public void setScanVulnerabilityResult(ScanVulnerabilityResult scanVulnerabilityResult) {
        this.scanVulnerabilityResult = scanVulnerabilityResult;
    }

    public void setOfflineUpdateResult(OfflineUpdateResult offlineUpdateResult) {
        this.offlineUpdateResult = offlineUpdateResult;
    }

    private String toolcategory;
    private String taskcode;
    private TaskScanConfig.ScanType scanType;
    private boolean success;
    private String  message;
    private String taskid;
    private String scanid;
    //private List<AssetsScanResultMix> result;
    private MixResult mixScanResult;
    private ScanResult scanResult;
    private ScanTemplateResult scanTemplateResult;
    private ScanEngineResult scanEnginResult;
    private ScanVulnerabilityResult scanVulnerabilityResult;
    private OfflineUpdateResult offlineUpdateResult;
}
