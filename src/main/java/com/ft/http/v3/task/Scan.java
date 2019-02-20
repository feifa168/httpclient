package com.ft.http.v3.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// {
//     "assets": 0,
//     "duration": null,
//     "endTime": null,
//     "engineId": null,
//     "engineName": "Local scan engine",
//     "id": 17,
//     "scanType": "Manual",
//     "startTime": "2019-02-20T09:14:20.437Z",
//     "startedBy": null,
//     "status": "RUNNING",
//     "vulnerabilities": {
//         "critical": 0,
//         "moderate": 0,
//         "severe": 0,
//         "total": 0
//     }
// }
// {
//     "assets": 1,
//     "duration": "PT6M34.322S",
//     "endTime": "2019-02-19T03:32:32.593Z",
//     "engineId": null,
//     "engineName": "Local scan engine",
//     "id": 16,
//     "scanType": "Manual",
//     "startTime": "2019-02-19T03:25:58.271Z",
//     "startedBy": null,
//     "status": "FINISHED",
//     "vulnerabilities": {
//         "critical": 9,
//         "moderate": 8,
//         "severe": 57,
//         "total": 74
//     }
// }
public class Scan {

    public static class Vulnerabilities {
        @JsonCreator
        public Vulnerabilities(@JsonProperty("critical") int critical,
                               @JsonProperty("moderate") int moderate,
                               @JsonProperty("severe") int severe,
                               @JsonProperty("total") int total) {
            this.critical = critical;
            this.moderate = moderate;
            this.severe = severe;
            this.total = total;
        }

        private int critical;
        private int moderate;
        private int severe;
        private int total;
    }

    @JsonCreator
    public Scan(@JsonProperty("assets") int assets,
                @JsonProperty("duration") String duration,
                @JsonProperty("endTime") String endTime,
                @JsonProperty("engineId") String engineId,
                @JsonProperty("engineName") String engineName,
                @JsonProperty("id") int id,
                @JsonProperty("scanType") String scanType,
                @JsonProperty("startTime") String startTime,
                @JsonProperty("startedBy") String startedBy,
                @JsonProperty("status") String status,
                @JsonProperty("vulnerabilities") Vulnerabilities vulnerabilities) {
        this.assets = assets;
        this.duration = duration;
        this.endTime = endTime;
        this.engineId = engineId;
        this.engineName = engineName;
        this.id = id;
        this.scanType = scanType;
        this.startTime = startTime;
        this.startedBy = startedBy;
        this.status = status;
        this.vulnerabilities = vulnerabilities;
    }

    private int     assets;     // 资产数
    private String  duration;   // 持续时间
    private String  endTime;
    private String  engineId;
    private String  engineName;
    private int     id;
    private String  scanType;
    private String  startTime;
    private String  startedBy;
    private String  status;     // 扫描状态，RUNNING|FINISHED|PAUSED|STOPPED
    private Vulnerabilities vulnerabilities;
}
