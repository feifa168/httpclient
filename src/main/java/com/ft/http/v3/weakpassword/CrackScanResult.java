package com.ft.http.v3.weakpassword;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

//{
//    "id": 5,
//    "startTime": "2019-03-08 10:30:59",
//    "status": 4, // Enum:0 1 3 4 5 扫描状态,0 Not:running 1:Running 3:stopped 4:finished 5:exception
//    "progress": 0,    // 扫描进度,0到100之间的整数，50表示进度百分之五十
//    "items": [
//        {
//            "target": "172.16.39.2",
//            "service": "ssh",
//            "port": 22,
//            "username": "test",
//            "password": "123456"
//        }
//    ]
//}
public class CrackScanResult {

    private static class Result {

        @JsonCreator
        public Result(@JsonProperty("target") String target,
                      @JsonProperty("service") String service,
                      @JsonProperty("username") String username,
                      @JsonProperty("password") String password,
                      @JsonProperty("port") int port) {
            this.target = target;
            this.service = service;
            this.username = username;
            this.password = password;
            this.port = port;
        }

        private String target;
        private String service;
        private String username;
        private String password;
        private int port;
    }

    @JsonCreator
    public CrackScanResult(@JsonProperty("id") int id,
                           @JsonProperty("startTime") String startTime,
                           @JsonProperty("status") int status,
                           @JsonProperty("progress") int progress,
                           @JsonProperty("items") List<Result> items) {
        this.id = id;
        this.startTime = startTime;
        this.status = status;
        this.progress = progress;
        this.items = items;
    }

    public boolean finished() {
        // 0 Not:running 1:Running 3:stopped 4:finished 5:exception
        return status!=1;
    }

    private int id;
    private String startTime;
    private int status;
    private int progress;
    @JacksonXmlElementWrapper(localName = "scanitems")
    @JacksonXmlProperty(localName = "item")
    private List<Result> items;
}
