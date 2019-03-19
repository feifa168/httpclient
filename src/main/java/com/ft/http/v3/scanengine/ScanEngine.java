package com.ft.http.v3.scanengine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//{
//    "resources": [
//        {
//            "address": "127.0.0.1",
//            "id": 3,
//            "lastRefreshedDate": null,
//            "lastUpdatedDate": "2019-02-13T09:26:20.958Z",
//            "name": "Local scan engine",
//            "port": 40814,
//            "tasks": [
//                32,
//                30,
//                31
//            ]
//        }
//    ]
//}
public class ScanEngine {

    @JsonCreator
    public ScanEngine(@JsonProperty("id") int id,
                      @JsonProperty("port") int port,
                      @JsonProperty("tasks") List<Integer> tasks,
                      @JsonProperty("address") String address,
                      @JsonProperty("lastRefreshedDate") String lastRefreshedDate,
                      @JsonProperty("lastUpdatedDate") String lastUpdatedDate,
                      @JsonProperty("name") String name) {
        this.id = id;
        this.port = port;
        this.tasks = tasks;
        this.address = address;
        this.lastRefreshedDate = lastRefreshedDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.name = name;
    }

    private int id;
    private int port;
    private List<Integer> tasks;
    private String address;
    private String lastRefreshedDate;
    private String lastUpdatedDate;
    private String name;
}
