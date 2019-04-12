package com.ft.http.v3.vulnerabilities;

//{
//    "added": "2004-11-01",
//    "categories": [
//        "Network"
//    ],
//    "cves": [
//        "CVE-1999-0524"
//    ],
//    "cvss": {
//        "v2": {
//            "score": 0,
//            "vector": "AV:L/AC:L/Au:N/C:N/I:N/A:N"
//        }
//    },
//    "denialOfService": false,
//    "description": {
//        "html": "<p>远程主机响应ICMP时间戳的一个请求。在ICMP时间戳回应包含远程主机的日期和时间。此信息理论上可能用来对付一些系统，去利用在其他设备中的微弱的期限随机数发生器。</p>\n<p>此外，某些操作系统的版本可以通过分析他们对无效的ICMP时间戳请求的反应，精确地采取指纹。</p>"
//    },
//    "exploits": 0,
//    "id": "generic-icmp-timestamp",
//    "malwareKits": 0,
//    "modified": "2018-03-21",
//    "riskScore": 0,
//    "severity": "Moderate",
//    "severityScore": 1,
//    "title": "ICMP时间戳回应"
//}

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VulnerabilitiesDetail {
    public static class VulnerabilitiesDescription {

        @JsonCreator
        public VulnerabilitiesDescription(@JsonProperty("html") String html) {
            this.html = html;
        }

        private String html;
    }

    public static class V2 {
        private double score;
        private String vector;

        @JsonCreator
        public V2(@JsonProperty("score") double score,
                  @JsonProperty("vector") String vector) {
            this.score = score;
            this.vector = vector;
        }
    }
    public static class Cvss {
        private V2 v2;

        @JsonCreator
        public Cvss(@JsonProperty("v2") V2 v2) {
            this.v2 = v2;
        }
    }

    @JsonCreator
    public VulnerabilitiesDetail(@JsonProperty("added") String added,
                                 @JsonProperty("categories") List<String> categories,
                                 @JsonProperty("cves") List<String> cves,
                                 @JsonProperty("cvss") Cvss cvss,
                                 @JsonProperty("denialOfService") boolean denialOfService,
                                 @JsonProperty("description") VulnerabilitiesDescription description,
                                 @JsonProperty("exploits") int exploits,
                                 @JsonProperty("id") String id,
                                 @JsonProperty("malwareKits") int malwareKits,
                                 @JsonProperty("modified") String modified,
                                 @JsonProperty("riskScore") double riskScore,
                                 @JsonProperty("severity") String severity,
                                 @JsonProperty("severityScore") int severityScore,
                                 @JsonProperty("solution") String solution,
                                 @JsonProperty("title") String title) {
        this.added = added;
        this.categories = categories;
        this.cves = cves;
        this.cvss = cvss;
        this.denialOfService = denialOfService;
        this.description = description;
        this.exploits = exploits;
        this.id = id;
        this.malwareKits = malwareKits;
        this.modified = modified;
        this.riskScore = riskScore;
        this.severity = severity;
        this.severityScore = severityScore;
        this.solution = solution;
        this.title = title;
    }

    private String added;
    private List<String> categories;
    private List<String> cves;
    private Cvss cvss;
    private boolean denialOfService;
    private VulnerabilitiesDescription description;
    private int exploits;
    private String id;
    private int malwareKits;
    private String modified;
    private double riskScore;
    private String severity;
    private int severityScore;
    private String solution;
    private String title;
}
