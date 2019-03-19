package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ft.http.v3.credential.Credential;
import com.ft.http.v3.task.NewTask;
import com.ft.http.v3.weakpassword.NewCracks;

import java.util.List;

@JacksonXmlRootElement(localName = "taskscan")
public class TaskScanConfig {

    public static enum ScanType{
        SCAN_TYPE_SCAN(1, "scan"),
        SCAN_TYPE_TEMPLATE(2, "template"),
        SCAN_TYPE_ENGINE(3, "engine"),
        SCAN_TYPE_VULNERABILITIES(4, "vulnerabilities"),
        SCAN_TYPE_OFFLINE_UPDATE(5, "offlineupdate");

        private ScanType(int index, String type) {
            this.index = index;
            this.type = type;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        @JsonValue
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private int index;
        private String type;
    }

    public static class CustomScanTemplate {
        @JsonCreator
        public CustomScanTemplate(@JsonProperty("sendIcmpPings") boolean sendIcmpPings,
                                  @JsonProperty("tcpPorts") String tcpPorts,
                                  @JsonProperty("udpPorts") String udpPorts,
                                  @JsonProperty("ipFingerprintingEnabled") boolean ipFingerprintingEnabled,
                                  @JsonProperty("tcpAdditionalPorts") String tcpAdditionalPorts,
                                  @JsonProperty("method") String method,
                                  @JsonProperty("udpAdditionalPorts") String udpAdditionalPorts,
                                  @JsonProperty("parallelismMininum") int parallelismMininum,
                                  @JsonProperty("parallelismMaxinum") int parallelismMaxinum,
                                  @JsonProperty("packetRateMininum") int packetRateMininum,
                                  @JsonProperty("packetRateMaxinum") int packetRateMaxinum,
                                  @JsonProperty("maxParallelAssets") int maxParallelAssets,
                                  @JsonProperty("maxScanProcesses") int maxScanProcesses) {
            this.sendIcmpPings = sendIcmpPings;
            this.tcpPorts = tcpPorts;
            this.udpPorts = udpPorts;
            this.ipFingerprintingEnabled = ipFingerprintingEnabled;
            this.tcpAdditionalPorts = tcpAdditionalPorts;
            this.method = method;
            this.udpAdditionalPorts = udpAdditionalPorts;
            this.parallelismMininum = parallelismMininum;
            this.parallelismMaxinum = parallelismMaxinum;
            this.packetRateMininum = packetRateMininum;
            this.packetRateMaxinum = packetRateMaxinum;
            this.maxParallelAssets = maxParallelAssets;
            this.maxScanProcesses = maxScanProcesses;
        }

        public boolean isSendIcmpPings() {
            return sendIcmpPings;
        }

        public String getTcpPorts() {
            return tcpPorts;
        }

        public String getUdpPorts() {
            return udpPorts;
        }

        public boolean isIpFingerprintingEnabled() {
            return ipFingerprintingEnabled;
        }

        public String getTcpAdditionalPorts() {
            return tcpAdditionalPorts;
        }

        public String getUdpAdditionalPorts() {
            return udpAdditionalPorts;
        }

        public int getParallelismMininum() {
            return parallelismMininum;
        }

        public int getParallelismMaxinum() {
            return parallelismMaxinum;
        }

        public int getPacketRateMininum() {
            return packetRateMininum;
        }

        public int getPacketRateMaxinum() {
            return packetRateMaxinum;
        }

        public int getMaxParallelAssets() {
            return maxParallelAssets;
        }

        public int getMaxScanProcesses() {
            return maxScanProcesses;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        private boolean sendIcmpPings;
        private String tcpPorts;    // 以逗号做分割
        private String udpPorts;
        private boolean ipFingerprintingEnabled;    // 是否启用指纹识别
        private String tcpAdditionalPorts;  // 以逗号做分割，有两种，一种是 1-1024这种区间的，另一种是整形字符串
        private String method;              // 扫描方式，"SYN" "SYN+RST" "SYN+FIN" "SYN+ECE" "Full"
        private String udpAdditionalPorts;
        private int parallelismMininum;     // 最小并发
        private int parallelismMaxinum;     // 最大并发
        private int packetRateMininum;      // 最小发包速率
        private int packetRateMaxinum;      // 最大发包速率
        private int maxParallelAssets;      // 最大并发扫描主机数
        private int maxScanProcesses;       // 每个资产分配的最大扫描进程数量
    }

    @JsonCreator
    public TaskScanConfig(@JsonProperty("taskconfigs") List<TaskConfig> taskconfigs,
                          @JsonProperty("description") String description,
                          @JsonProperty("engineId") int engineId,
                          @JsonProperty("importance") String importance,
                          @JsonProperty("name") String name,
                          @JsonProperty("scanTemplateId") String scanTemplateId,
                          @JsonProperty("toolcategory") String toolcategory,
                          @JsonProperty("output") String output,
                          @JsonProperty("taskcode") String taskcode,
                          @JsonProperty("scanType") ScanType scanType,
                          @JsonProperty("customConfig") CustomScanTemplate customConfig,
                          @JsonProperty("update") boolean update,
                          @JsonProperty("updateFile") String updateFile,
                          @JsonProperty("acquireVulnerabilities") boolean acquireVulnerabilities,
                          @JsonProperty("acquireTemplate") boolean acquireTemplate,
                          @JsonProperty("accquireEngine") boolean acquireEngine) {
        this.taskconfigs = taskconfigs;
        this.description = description;
        this.engineId = engineId;
        this.importance = importance;
        this.name = name;
        this.scanTemplateId = scanTemplateId;
        this.toolcategory = toolcategory;
        this.output = output;
        this.taskcode = taskcode;
        this.scanType = scanType;
        this.customConfig = customConfig;
        this.update = update;
        this.updateFile = updateFile;
        this.acquireVulnerabilities = acquireVulnerabilities;
        this.acquireTemplate = acquireTemplate;
        this.acquireEngine = acquireEngine;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskConfig> getTaskconfigs() {
        return taskconfigs;
    }

    public String getDescription() {
        return description;
    }

    public int getEngineId() {
        return engineId;
    }

    public String getImportance() {
        return importance;
    }

    public String getName() {
        return name;
    }

    public String getScanTemplateId() {
        return scanTemplateId;
    }

    public String getToolcategory() {
        return toolcategory;
    }

    public String getOutput() {
        return output;
    }

    public String getTaskcode() {
        return taskcode;
    }

    public CustomScanTemplate getCustomConfig() {
        return customConfig;
    }

    public void setCustomConfig(CustomScanTemplate customConfig) {
        this.customConfig = customConfig;
    }

    public void setScanTemplateId(String scanTemplateId) {
        this.scanTemplateId = scanTemplateId;
    }

    public boolean isUpdate() {
        return update;
    }

    public boolean isAcquireVulnerabilities() {
        return acquireVulnerabilities;
    }

    public boolean isAcquireTemplate() {
        return acquireTemplate;
    }

    public boolean isAcquireEngine() {
        return acquireEngine;
    }

    public String getUpdateFile() {
        return updateFile;
    }

    public ScanType getScanType() {
        return scanType;
    }

    public void setScanType(ScanType scanType) {
        this.scanType = scanType;
    }

    @JacksonXmlElementWrapper(localName = "taskconfigItems")
    @JacksonXmlProperty(localName = "taskconfig")
    private List<TaskConfig> taskconfigs;

    private String description;
    private int    engineId;
    private String importance;
    private String name;
    private String scanTemplateId;
    private String toolcategory;
    private String output;
    private String taskcode;
    private ScanType scanType;
    private CustomScanTemplate customConfig;
    private boolean update;
    private String updateFile;
    private boolean acquireVulnerabilities;
    private boolean acquireTemplate;
    private boolean acquireEngine;
}
