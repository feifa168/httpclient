package com.ft.xml;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ft.http.v3.config.*;
import com.ft.http.v3.credential.Credential;
import com.ft.http.v3.task.NewTask;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestConfig {
    private XmlMapper xmlMapper = new XmlMapper();
    private ObjectMapper mapper = new ObjectMapper();

    public TestConfig() {
        xmlMapper.setDefaultUseWrapper(false);
        //字段为null，自动忽略，不再序列化
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)               // 允许不序列化值为null的属性
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)    // 设置getter,setter,field等所有都不不见
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)   // 可以不配置setter而使用直接访问类属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)    // 忽略pojo中不存在的字段
        ;
        //XML标签名:使用骆驼命名的属性名，
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        //设置转换模式
        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);

        //字段为null，自动忽略，不再序列化
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)               // 允许不序列化值为null的属性
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)    // 设置getter,setter,field等所有都不不见
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)   // 可以不配置setter而使用直接访问类属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)    // 忽略pojo中不存在的字段
        ;
        //XML标签名:使用骆驼命名的属性名，
        //mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        //设置转换模式
        mapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
    }

    @Test
    public void testTaskConfig() throws IOException {
        com.ft.http.v3.config.TaskScanConfig config = buildTaskConfig();
        String xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        System.out.println(xmlString);

        config = xmlMapper.readValue(xmlString, com.ft.http.v3.config.TaskScanConfig.class);
        xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        System.out.println(xmlString);
    }
    @Test
    public void testTaskConfigJson() throws IOException {
        com.ft.http.v3.config.TaskScanConfig config = buildTaskConfig();
        String xmlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        System.out.println(xmlString);

        config = mapper.readValue(xmlString, com.ft.http.v3.config.TaskScanConfig.class);
        xmlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        System.out.println(xmlString);
    }

    private com.ft.http.v3.config.TaskScanConfig buildTaskConfig() throws JsonProcessingException {
        Object as400 = new DomainUserPwd("as400", "domain", "user", "password");

        NewTask task = buildNewTask2();
        List<Credential> credentials = new ArrayList<>();
        Credential credential;

        credential = new Credential(new DomainUserPwd("as400", "domain", "user", "password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DomainUserPwd("cifs", "domain", "user", "password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Cifshash("cifshash", "domain", "user", "ntlmHash"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DomainUserPwd("cvs", "domain", "user", "password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DbUserPwd("db2", "databasename", "user", "password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new UserPwd("ftp","user","password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Http("http","realm","user","password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DbDomainUserPwd("ms-sql","databasename", false, "domain","user","password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DbUserPwd("mysql","databasename", "user","password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Notes("notes","notesIDPassword"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Oracle("oracle", "sid","user","password", false, "oracleListenerPassword"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new UserPwd("pop","user","password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DbUserPwd("postgresql","databasename", "user","password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new UserPwd("remote-exec","user","password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Snmp("snmp","communityName"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Snmpv3("snmpv3","md5", "user", "pwd", "aes-128", "privacyPassword"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Ssh("ssh","user", "pwd", "sudo", "permissionElevationUsername", "permissionElevationPassword"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new SshKey("ssh-key","user", "privateKeyPassword", "pemKey", "sudo", "permissionElevationUsername", "permissionElevationPassword"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DbDomainUserPwd("sybase","databasename", false, "domain","user","password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new UserPwd("telnet","user","password"),
                "description", false, "127.0.0.1", "name", 22);
        credentials.add(credential);

        com.ft.http.v3.config.TaskScanConfig config = new com.ft.http.v3.config.TaskScanConfig(task, credentials);
        return config;
    }

    private byte[] buildNewTask(NewTask nt) throws JsonProcessingException {

        String jsonString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nt);
        System.out.println(jsonString);

        return jsonString.getBytes();
    }

    private NewTask buildNewTask2() throws JsonProcessingException {
        List<String> incAddrs = new ArrayList<>();
        incAddrs.add("172.16.1.152");
        incAddrs.add("192.168.0.121");
        List<String> excAddrs = new ArrayList<>();
        excAddrs.add("172.16.39.254");

        NewTask.Scan.Assets.Targets includedTargets = new NewTask.Scan.Assets.Targets(incAddrs);
        NewTask.Scan.Assets.Targets excludedTargets = new NewTask.Scan.Assets.Targets(excAddrs);
        NewTask.Scan.Assets assets = new NewTask.Scan.Assets(includedTargets, null);
        NewTask.Scan scan = new NewTask.Scan(assets);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String name = "SCAN_56_"+formatter.format(new Date());

        NewTask nt = new NewTask("description", 3, "normal", name,
                scan, null, "full-audit-without-web-spider");

        return nt;
    }
}
