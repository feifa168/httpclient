package com.ft.http.v3.credential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//  {
//  "account": {
//      "database": "string",
//      "domain": "string",
//      "ntlmHash": "string",
//      "password": "****",
//      "pemKey": "string",
//      "permissionElevation": "sudo",
//      "permissionElevationPassword": "*****",
//      "permissionElevationUsername": "root",
//      "privateKeyPassword": "string",
//      "realm": "string",
//      "service": "ssh",
//      "sid": "string",
//      "username": "${credential.class.SharedCredential.username}"
//  },
//  "description": "描述",
//  "enabled": false,
//  "hostRestriction": "10.1.40.16",
//  "name": "名称",
//  "portRestriction": 22
//  }

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credential {
    @JsonCreator
    public Credential(@JsonProperty("account") Object account,
                      @JsonProperty("description") String description,
                      @JsonProperty("enabled") boolean enabled,
                      @JsonProperty("hostRestriction") String hostRestriction,
                      @JsonProperty("name") String name,
                      @JsonProperty("portRestriction") int portRestriction) {
        this.account = account;
        this.description = description;
        this.enabled = enabled;
        this.hostRestriction = hostRestriction;
        this.name = name;
        this.portRestriction = portRestriction;
    }

    public Object getAccount() {
        return account;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getHostRestriction() {
        return hostRestriction;
    }

    public String getName() {
        return name;
    }

    public int getPortRestriction() {
        return portRestriction;
    }

    private Object account;
    private String description;
    private boolean enabled;
    private String hostRestriction;
    private String name;
    private int    portRestriction;
}
