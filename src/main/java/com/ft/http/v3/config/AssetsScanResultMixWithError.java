package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AssetsScanResultMixWithError {
    public AssetsScanResultMixWithError() {
        this.success = false;
    }

    @JsonCreator
    public AssetsScanResultMixWithError(@JsonProperty("toolcategory") String toolcategory,
                                        @JsonProperty("taskcode") String taskcode,
                                        @JsonProperty("success") boolean success,
                                        @JsonProperty("message") String message,
                                        @JsonProperty("taskid") String taskid,
                                        @JsonProperty("scanid") String scanid,
                                        @JsonProperty("result") List<AssetsScanResultMix> result) {
        this.toolcategory = toolcategory;
        this.taskcode = taskcode;
        this.success = success;
        this.message = message;
        this.taskid = taskid;
        this.scanid = scanid;
        this.result = result;
    }

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

    public void setResult(List<AssetsScanResultMix> result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String toolcategory;
    private String taskcode;
    private boolean success;
    private String  message;
    private String taskid;
    private String scanid;
    List<AssetsScanResultMix> result;
}
