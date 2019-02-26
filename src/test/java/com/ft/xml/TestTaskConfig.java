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

class TaskConfig {

    @JsonCreator
    public TaskConfig(@JsonProperty("ip") String ip,
                      @JsonProperty("credential") List<Credential> credentials) {
        this.ip = ip;
        this.credentials = credentials;
    }

    public String getIp() {
        return ip;
    }

    public List<Credential> getCredentials() {
        return credentials;
    }

    private String ip;
    private List<Credential> credentials;
}

@JacksonXmlRootElement(localName = "taskscan")
class TaskScanConfig {
    @JsonCreator
    public TaskScanConfig(@JsonProperty("taskconfigs") List<TaskConfig> taskconfigs,
                          @JsonProperty("description") String description,
                          @JsonProperty("engineId") int engineId,
                          @JsonProperty("importance") String importance,
                          @JsonProperty("name") String name,
                          @JsonProperty("scanTemplateId") String scanTemplateId) {
        this.taskconfigs = taskconfigs;
        this.description = description;
        this.engineId = engineId;
        this.importance = importance;
        this.name = name;
        this.scanTemplateId = scanTemplateId;
    }

    public List<TaskConfig> getTaskconfigs() {
        return taskconfigs;
    }

    public String getDescription() {
        return description;
    }

    public int getEngineId() {
        return engineId;
    }

    public String getImportance() {
        return importance;
    }

    public String getName() {
        return name;
    }

    public String getScanTemplateId() {
        return scanTemplateId;
    }

    @JacksonXmlElementWrapper(localName = "taskconfigItems")
    @JacksonXmlProperty(localName = "taskconfig")
    private List<TaskConfig> taskconfigs;

    private String description;
    private int    engineId;
    private String importance;
    private String name;
    private String scanTemplateId;
}

public class TestTaskConfig {

    private XmlMapper xmlMapper = new XmlMapper();
    private ObjectMapper mapper = new ObjectMapper();

    public TestTaskConfig() {
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
    public void test() throws IOException {
        List<TaskConfig> taskConfigs = new ArrayList<>();

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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String name = "SCAN_56_"+formatter.format(new Date());
        TaskConfig config = new TaskConfig("172.16.39.22", null);

        taskConfigs.add(config);

        name = "SCAN_56_"+formatter.format(new Date());
        config = new TaskConfig("172.16.39.251", credentials);
        taskConfigs.add(config);

        TaskScanConfig taskScanConfig = new TaskScanConfig(taskConfigs, "description", 3, "normal",
                name,"full-audit-without-web-spider");
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskScanConfig);
        System.out.println(xmlString);
        taskScanConfig = xmlMapper.readValue(xmlString, TaskScanConfig.class);

        xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskScanConfig);
        System.out.println(xmlString);

        buildNewTask(taskScanConfig);
    }
    public String createTaskConfig() {
        return null;
    }

    private String buildNewTask(TaskScanConfig taskScanConfig) throws JsonProcessingException {
        List<String> address = new ArrayList<>();
        for (TaskConfig tmpconfig : taskScanConfig.getTaskconfigs()) {
            address.add(tmpconfig.getIp());
        }
        NewTask.Scan.Assets.Targets includeTargets = new NewTask.Scan.Assets.Targets(address);
        NewTask.Scan.Assets assets = new NewTask.Scan.Assets(includeTargets, null);
        NewTask.Scan scan = new NewTask.Scan(assets);
        NewTask mewTask = new NewTask(taskScanConfig.getDescription(), taskScanConfig.getEngineId(), taskScanConfig.getImportance(),
                taskScanConfig.getName(), scan, null, taskScanConfig.getScanTemplateId());

        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mewTask);
        System.out.println(jsonString);
        return jsonString;
    }
}
