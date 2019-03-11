package com.ft.http.v3.weakpassword;

//{
//  "bfTimeOut": 3,
//  "connectTimeOut": 5,
//  "models": [
//    {
//      "port": 22,
//      "service": "ssh"
//    }
//  ],
//  "name": "TaskName",
//  "targets": "192.168.0.1",
//  "threads": 15
//}

// bfTimeOut
//integer <int32>
//破解超时时间,单位为分钟，默认值是3
//
//connectTimeOut
//integer <int32>
//连接超时时间,单位为秒，默认值是5
//
//models
//required
//Array of object (Model)
//每个弱口令猜测子任务的配置
//
//Array
//port
//required
//integer <int32> ( 0 .. 65535 )
//服务端口
//
//service
//required
//string
//Enum:"ftp" "ftps" "imap" "imaps" "mysql" "mssql" "pop3" "pop3s" "rdp" "snmp" "ssh" "postgres" "telnet" "smb"
//服务名称
//
//name
//required
//string
//任务名称
//
//targets
//required
//string
//扫描目标,多个扫描目标用换行符分割。IP段用192.168.0.1/24或者192.168.0.1-192.168.0.254表示
//
//threads
//integer <int32>
//任务并发进程数，默认值是15,在性能有限的情况下不建议超过20

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

public class NewCracks {

    public static class Model {

        @JsonCreator
        public Model(@JsonProperty("port") int port,
                     @JsonProperty("service") String service) {
            this.port = port;
            this.service = service;
        }

        private int port;       // ( 0 .. 65535 )
        private String service; // Enum:"ftp" "ftps" "imap" "imaps" "mysql" "mssql" "pop3" "pop3s" "rdp" "snmp" "ssh" "postgres" "telnet" "smb"
    }

    @JsonCreator
    public NewCracks(@JsonProperty("bfTimeOut") int bfTimeOut,
                     @JsonProperty("connectTimeOut") int connectTimeOut,
                     @JsonProperty("models") List<Model> models,
                     @JsonProperty("name") String name,
                     @JsonProperty("targets") String targets,
                     @JsonProperty("threads") int threads) {
        this.bfTimeOut = bfTimeOut;
        this.connectTimeOut = connectTimeOut;
        this.models = models;
        this.name = name;
        this.targets = targets;
        this.threads = threads;
    }

    public void setName(String name) { this.name = name; }

    private int bfTimeOut;
    private int connectTimeOut;
    @JacksonXmlElementWrapper(localName = "modelItems")
    @JacksonXmlProperty(localName = "model")
    private List<Model> models;
    private String name;
    private String targets;
    private int threads;
}
