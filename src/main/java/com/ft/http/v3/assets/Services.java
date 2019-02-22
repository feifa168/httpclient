package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//  {
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
//      "databases": [
//      {
//          "description": "Microsoft SQL Server",
//          "id": 13,
//          "name": "MSSQL"
//          }
//      ],
//      "name": "SSH",
//      "port": 22,
//      "product": "OpenSSH",
//      "protocol": "TCP",
//      "vendor": "OpenBSD",
//      "version": "7.2"
//  }
public class Services {

    @JsonCreator
    public Services(@JsonProperty("configurations") List<Configuration> configurations,
                    @JsonProperty("databases") List<Database> databases,
                    @JsonProperty("name") String name,
                    @JsonProperty("port") int port,
                    @JsonProperty("product") String product,
                    @JsonProperty("protocol") String protocol,
                    @JsonProperty("vendor") String vendor,
                    @JsonProperty("version") String version) {
        this.configurations = configurations;
        this.databases = databases;
        this.name = name;
        this.port = port;
        this.product = product;
        this.protocol = protocol;
        this.vendor = vendor;
        this.version = version;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public List<Database> getDatabases() {
        return databases;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public String getProduct() {
        return product;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVendor() {
        return vendor;
    }

    public String getVersion() {
        return version;
    }

    private List<Configuration> configurations;
    private List<Database>      databases;
    private String  name;
    private int     port;
    private String  product;
    private String  protocol;
    private String  vendor;
    private String  version;
}
