package com.ft.http.v3.scan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// {
//  "engineId": 3,
//  "hosts": [
//    "172.16.39.152"
//  ],
//  "templateId": "full-audit-without-web-spider"
// }
public class NewScan {
    @JsonCreator
    public NewScan(@JsonProperty("engineId") int engineId,
                   @JsonProperty("hosts") List<String> hosts,
                   @JsonProperty("templateId") String templateId) {
        this.engineId = engineId;
        this.hosts = hosts;
        this.templateId = templateId;
    }

    private int engineId;
    private List<String> hosts;
    private String templateId;
}
