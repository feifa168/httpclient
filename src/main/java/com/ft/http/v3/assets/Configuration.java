package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//      "configurations": [
//          {
//              "name": "ssh.banner",
//              "value": "SSH-2.0-OpenSSH_7.2"
//          },
//          {
//              "name": "ssh.protocol.version",
//              "value": "2.0"
//          }
//      ],
public class Configuration {
    @JsonCreator
    public Configuration(@JsonProperty("name") String name,
                         @JsonProperty("value") String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    private String name;
    private String value;
}