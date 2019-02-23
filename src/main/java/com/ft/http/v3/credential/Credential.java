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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Account {

        @JsonCreator
        public Account(@JsonProperty("database") String database,
                       @JsonProperty("domain") String domain,
                       @JsonProperty("ntlmHash") String ntlmHash,
                       @JsonProperty("password") String password,
                       @JsonProperty("pemKey") String pemKey,
                       @JsonProperty("permissionElevation") String permissionElevation,
                       @JsonProperty("permissionElevationUsername") String permissionElevationUsername,
                       @JsonProperty("privateKeyPassword") String privateKeyPassword,
                       @JsonProperty("realm") String realm,
                       @JsonProperty("service") String service,
                       @JsonProperty("sid") String sid,
                       @JsonProperty("username") String username) {
            this.database = database;
            this.domain = domain;
            this.ntlmHash = ntlmHash;
            this.password = password;
            this.pemKey = pemKey;
            this.permissionElevation = permissionElevation;
            this.permissionElevationUsername = permissionElevationUsername;
            this.privateKeyPassword = privateKeyPassword;
            this.realm = realm;
            this.service = service;
            this.sid = sid;
            this.username = username;
        }

        public String getDatabase() {
            return database;
        }

        public String getDomain() {
            return domain;
        }

        public String getNtlmHash() {
            return ntlmHash;
        }

        public String getPassword() {
            return password;
        }

        public String getPemKey() {
            return pemKey;
        }

        public String getPermissionElevation() {
            return permissionElevation;
        }

        public String getPermissionElevationUsername() {
            return permissionElevationUsername;
        }

        public String getPrivateKeyPassword() {
            return privateKeyPassword;
        }

        public String getRealm() {
            return realm;
        }

        public String getService() {
            return service;
        }

        public String getSid() {
            return sid;
        }

        public String getUsername() {
            return username;
        }

        private String database;
        private String domain;
        private String ntlmHash;
        private String password;
        private String pemKey;
        private String permissionElevation;
        private String permissionElevationUsername;
        private String privateKeyPassword;
        private String realm;
        private String service;
        private String sid;
        private String username;
    }

    @JsonCreator
    public Credential(@JsonProperty("account") Account account,
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

    public Account getAccount() {
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

    private Account account;
    private String description;
    private boolean enabled;
    private String hostRestriction;
    private String name;
    private int    portRestriction;
}
