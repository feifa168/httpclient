package com.ft.http.client;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ft.http.v3.App;
import com.ft.http.v3.HttpClient;
import com.ft.http.v3.assets.*;
import com.ft.http.v3.config.*;
import com.ft.http.v3.credential.Credential;
import com.ft.http.v3.credential.CredentialReturn;
import com.ft.http.v3.credential.SharedCredential;
import com.ft.http.v3.scan.NewScan;
import com.ft.http.v3.scan.NewScanReturn;
import com.ft.http.v3.scan.ScanResult;
import com.ft.http.v3.task.*;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.util.AsciiString;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestHttpClient {

    enum HttpRequestType {
        HTTP_UNKNOWN,
        HTTP_GET,
        HTTP_POST,
        HTTP_PUT,
    }
    public static class AssetsScanResult {
        public List<AssetsQueryVulnerabilitiesResult> assetsVulnerabilities = new ArrayList<>();
        public List<AssetsQueryPortResult> assetsPorts = new ArrayList<>();

        public void clear() {
            assetsPorts.clear();
            assetsVulnerabilities.clear();
        }
    }

    private ObjectMapper mapper = new ObjectMapper();
    private XmlMapper xmlMapper = new XmlMapper();
    private RunConfig runConfig;
    private String host;
    private int port;
    private String authName;
    private String authPassword;

    public TestHttpClient() throws IOException {
        //字段为null，自动忽略，不再序列化
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)               // 允许不序列化值为null的属性
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)    // 设置getter,setter,field等所有都不不见
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)   // 可以不配置setter而使用直接访问类属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)    // 忽略pojo中不存在的字段
        ;

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

        buildTaskConfig();
    }

    public <T> T buildResultObject(byte[] result, Class<?> className) throws IOException {
        T t = (T)mapper.readValue(result, className);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t));
        return t;
    }
    public byte[] testNormal(HttpClient client, String url, HttpRequestType type, byte[] body) throws Exception {
        return testNormal(client, url, type, null, body);
    }

    public byte[] testNormal(HttpClient client, String url, HttpRequestType type, Map<String, Object> mapParams, byte[] body) throws Exception {
        client.configSSL(true);
        client.configAuth(true, authName, authPassword);
        client.start();
        ResultCallBackImpl call = new ResultCallBackImpl();

        Map<String, Object> mapHeader = new HashMap<>();
        mapHeader.put(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json");

        switch (type) {
            case HTTP_GET: {
                client.getMessage(url, mapParams, mapHeader, call);
                break;
            }
            case HTTP_POST: {
                client.postMessage(url, mapHeader, body, call);
                break;
            }
            case HTTP_PUT: {
                client.putMessage(url, mapHeader, body, call);
                break;
            }
        }

        byte[] result = call.getResult();
        String jsonString = new String(result);
        System.out.println("============================\n"+jsonString);

        return result;
    }

    @Test
    public void testCreateTask() throws Exception {
        HttpClient client = new HttpClient(host, port);
        byte[] result = testNormal(client,"/api/v3/tasks", HttpRequestType.HTTP_POST, buildNewTask(buildNewTask2()));

        NewTaskReturn taskReturn = buildResultObject(result, NewTaskReturn.class);
        client.stop();
    }

    @Test
    public void testQueryScanResult() throws Exception {
        HttpClient client = new HttpClient(host, port);
        byte[] result = testNormal(client,"/api/v3/scans/49", HttpRequestType.HTTP_GET, null);

        ScanResult scanResult = buildResultObject(result, ScanResult.class);
        client.stop();
    }

    @Test
    public void testQueryTaskScanResult() throws Exception {
        HttpClient client = new HttpClient(host, port);
        byte[] result = testNormal(client,"/api/v3/tasks/67/scans", HttpRequestType.HTTP_GET, null);

        TaskResult scanResult = buildResultObject(result, TaskResult.class);
        client.stop();
    }

    @Test
    public void testQueryAssetsVulnerabilitiesResult() throws Exception {

        host = "10.0.92.95";
        port = 443;
        authName = "admin";
        authPassword = "admin@123";
        AssetsQueryVulnerabilitiesResult assetsVulnerabilities = null;
        try {
            assetsVulnerabilities = queryAssetsVulnerabilities(3);
            System.out.println("============================"+assetsVulnerabilities.getResources().size()+"============================");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(assetsVulnerabilities));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AssetsQueryResult queryAssets() throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询资产
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("page", 0);
        mapParams.put("size", 10);

        String url = "/api/v3/assets";
        byte[] result = testNormal(client,url, App.HttpRequestType.HTTP_GET, mapParams,null, null);
        AssetsQueryResult queryResult = buildResultObject(result, AssetsQueryResult.class);
        pageResource(queryResult, client, url, mapParams, null, null);
        client.stop();
        return queryResult;
    }

    public byte[] testNormal(HttpClient client, String url, App.HttpRequestType type, Map<String, Object> mapParams, Map<String, Object> mapHeader, byte[] body) throws Exception {
        client.configSSL(true);
        client.configAuth(true, authName, authPassword);
        client.start();
        ResultCallBackImpl call = new ResultCallBackImpl();

        //Map<AsciiString, String> mapHeader = new HashMap<>();
        //mapHeader.put(HttpHeaderNames.CONTENT_TYPE, "application/json");
        if (null == mapHeader) {
            mapHeader = new HashMap<>();
            mapHeader.put(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json");
        }

        switch (type) {
            case HTTP_GET: {
                client.getMessage(url, mapParams, mapHeader, call);
                break;
            }
            case HTTP_POST: {
                client.postMessage(url, mapHeader, body, call);
                break;
            }
            case HTTP_PUT: {
                client.putMessage(url, mapHeader, body, call);
                break;
            }
        }

        byte[] result = call.getResult();
        String jsonString = new String(result);
        System.out.println("============================\n"+jsonString);

        return result;
    }
    public byte[] testNormal(HttpClient client, String url, App.HttpRequestType type, Map<String, Object> mapHeader, byte[] body) throws Exception {
        return testNormal(client, url, type, null, mapHeader, body);
    }
    public byte[] testNormal(HttpClient client, String url, App.HttpRequestType type, byte[] body) throws Exception {
        Map<String, Object> mapHeader = new HashMap<>();
        mapHeader.put(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json");
        return testNormal(client, url, type, null, mapHeader, body);
    }

    public boolean parseRunConfig() throws IOException {

        RunConfig runConfig =  mapper.readValue(new File("config/runConfig.json"), RunConfig.class);
        this.host = runConfig.getHost();
        this.port = runConfig.getPort();
        this.authName = runConfig.getAuthName();
        this.authPassword = runConfig.getAuthPassword();
        if (((null == host) || host.isEmpty())
                || ((null == authName) || authName.isEmpty())
                || ((null == authPassword) || authPassword.isEmpty())
                || (0 == port)
        ) {
            return false;
        }
        return true;
    }

    @Test
    public void testAll() throws Exception {
        NewTask       task = null;
        NewScan       scan;
        NewTaskReturn newTaskReturn;
        NewScanReturn newScanReturn;

        List<AssetsScanResultMix> scanReslutMix = new ArrayList<>();
        AssetsScanResultMixWithError scanReslutMixWithError = new AssetsScanResultMixWithError();
        //task = buildNewTask2();
        TaskScanConfig taskScanConfig = null;

        do {
            // 读取配置文件
            try {
                taskScanConfig = buildTaskConfig();
            } catch (IOException e) {
                scanReslutMixWithError.setMessage("读取配置文件失败，原因是"+e.getMessage());
                break;
            }

            // 设置工具类型
            scanReslutMixWithError.setToolcategory(taskScanConfig.getToolcategory());
            // 设置下发的taskcode
            String code = taskScanConfig.getTaskcode();
            if (code == null) {
                scanReslutMixWithError.setMessage("没有配置taskcode");
                break;
            }
            scanReslutMixWithError.setTaskcode(code);

            // 构造任务
            try {
                task = buildNewTask(taskScanConfig);
            } catch (JsonProcessingException e) {
                scanReslutMixWithError.setMessage("创建任务失败，原因是"+e.getMessage());
                break;
            }

            if (0 == task.getEngineId()) {
                scanReslutMixWithError.setMessage("没有配置引擎ID");
                break;
            }
            if ((null==task.getScanTemplateId()) || ("".equals(task.getScanTemplateId()))) {
                scanReslutMixWithError.setMessage("没有配置模板ID");
                break;
            }
            NewTask.Scan sc = task.getScan();
            if ((null == sc)
                    || (null == sc.getAssets())
                    || (null == sc.getAssets().getIncludedTargets())
                    || (null == sc.getAssets().getIncludedTargets().getAddresses())
                    || (0 == sc.getAssets().getIncludedTargets().getAddresses().size())
            ) {
                scanReslutMixWithError.setMessage("没有配置扫描地址");
                break;
            }

            // 创建任务
            newTaskReturn = startTask(task);

            int taskId = newTaskReturn.getId();
            if (0 == taskId) {
                scanReslutMixWithError.setMessage("创建任务失败,"+newTaskReturn.getError());
                break;
            }

            // 设置任务ID
            scanReslutMixWithError.setTaskid(String.valueOf(taskId));

            // 登录检查
            for (TaskConfig taskConfig : taskScanConfig.getTaskconfigs()) {
                if (taskConfig.getCredentials() == null) {
                    continue;
                }

                CredentialReturn credentialReturn;
                // /api/v3/tasks/taskId/task_credentials
                for (Credential credential : taskConfig.getCredentials()) {
                    String url = "/api/v3/tasks/"+taskId+"/task_credentials";
                    credentialReturn = createCredential(newTaskReturn, credential);
                    if (0 == credentialReturn.getId()) {
                        System.out.println("创建认证失败,"+credentialReturn.getError());
                        //scanReslutMixWithError.setMessage("创建认证失败,"+newTaskReturn.getError());
                    }
                }
            }

            // 创建扫描
            scan = new NewScan(task.getEngineId(), task.getScan().getAssets().getIncludedTargets().getAddresses(), task.getScanTemplateId());
            // /v3/tasks/11/scans;
            String url = "/api/v3/tasks/"+taskId+"/scans";
            newScanReturn = startScan(newTaskReturn, scan);
            if (0 == newScanReturn.getId()) {
                scanReslutMixWithError.setMessage("创建扫描失败,"+newScanReturn.getError());
                break;
            }

            // 设置扫描ID
            scanReslutMixWithError.setScanid(String.valueOf(newScanReturn.getId()));

            // 查询扫描状态
            ScanResult scanResult = queryScanResult(newScanReturn);
            while (!scanResult.finished()) {
                scanResult = queryScanResult(newScanReturn);
                Thread.sleep(2000);
            }

            // 查询任务状态
            TaskResult taskResult = queryTaskScanResult(newTaskReturn);

            // 查询资产
            AssetsQueryResult assetsAll = queryAssets();
            if (null == assetsAll.getResources() || assetsAll.getResources().size() == 0) {
                scanReslutMixWithError.setMessage("资产为空");
                break;
            }

            AssetsScanResult assetsScanResult = new AssetsScanResult();
            // 获取哪些资产的扫描结果
            for (Assets assets : assetsAll.getResources()) {
                String assetsAddr = assets.getIp();
                for (String addr : task.getScan().getAssets().getIncludedTargets().getAddresses()) {
                    if ((assetsAddr != null) && (addr != null)) {
                        if (assetsAddr.equals(addr)) {

                            AssetsQueryVulnerabilitiesResult assetsVulnerabilities = queryAssetsVulnerabilities(assets.getId());
                            assetsScanResult.assetsVulnerabilities.add(assetsVulnerabilities);

                            AssetsQueryPortResult assetsPort = queryAssetsPorts(assets.getId());

                            AssetsScanResultMix mix = new AssetsScanResultMix(assets.getId(), addr, assetsVulnerabilities.getResources(), assetsPort.getResources());
                            scanReslutMix.add(mix);

                            break;
                        }
                    }
                }
            }

            if (scanReslutMix.size() == 0) {
                scanReslutMixWithError.setMessage("没有找到匹配的资产扫描结果");
                break;
            }

            scanReslutMixWithError.setSuccess(true);
            scanReslutMixWithError.setResult(scanReslutMix);

        } while (false);

        // 处理检查结果
        System.out.println("===================== ok ========================");
        String output = null;
        String taskName = (task != null) ? task.getName() : "";
        if (taskName==null) {
            taskName = "";
        }
        String toolcategory = null;
        String taskcode = null;
        String vulnerabilitiesFile = null;
        if (taskScanConfig != null) {
            output = taskScanConfig.getOutput();
            toolcategory = taskScanConfig.getToolcategory();
            taskcode = taskScanConfig.getTaskcode();
        }

        if ((output == null)) {
            output = "results";
        }
        if ( (!output.endsWith("\\")) && (!output.endsWith("/"))) {
            output += File.separator;
        }
        if (toolcategory == null) {
            toolcategory = "";
        }
        if (taskcode == null) {
            taskcode = "";
        }

        vulnerabilitiesFile = output+toolcategory+"_"+taskcode+"_"+taskName+".json";

        vulnerabilitiesFile = output+taskScanConfig.getToolcategory()+"_"+taskScanConfig.getTaskcode()+"_"+taskName+".json";
        vulnerabilitiesFile = vulnerabilitiesFile.replaceAll(":", "-");
        RandomAccessFile f1 = new RandomAccessFile(vulnerabilitiesFile, "rw");

        String mixResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scanReslutMixWithError);
        System.out.println(mixResult);
        f1.write(mixResult.getBytes());
        f1.close();
    }

    private NewTaskReturn startTask(NewTask task) throws Exception {
        HttpClient client = new HttpClient(host, port);

        // 创建任务
        byte[] postBody = mapper.writeValueAsBytes(task);
        byte[] result = testNormal(client,"/api/v3/tasks", HttpRequestType.HTTP_POST, postBody);
        NewTaskReturn taskReturn = buildResultObject(result, NewTaskReturn.class);
        client.stop();
        return taskReturn;
    }
    private CredentialReturn createCredential(NewTaskReturn taskReturn, Credential credential) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 创建凭证
        byte[] postBody = mapper.writeValueAsBytes(credential);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(credential));
        // /api/v3/tasks/taskid/task_credentials
        byte[] result = testNormal(client,"/api/v3/tasks/"+taskReturn.getId()+"/task_credentials", HttpRequestType.HTTP_POST, postBody);
        CredentialReturn credentialReturn = buildResultObject(result, CredentialReturn.class);
        client.stop();
        return credentialReturn;
    }
    private SharedCredential createSharedCredential(NewTaskReturn taskReturn, Credential credential) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 创建共享凭证
        byte[] postBody = mapper.writeValueAsBytes(credential);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(credential));
        // /api/v3/shared_credentials
        byte[] result = testNormal(client,"/api/v3/shared_credentials", HttpRequestType.HTTP_POST, postBody);
        SharedCredential credentialReturn = buildResultObject(result, SharedCredential.class);
        client.stop();
        return credentialReturn;
    }
    private NewScanReturn startScan(NewTaskReturn taskReturn, NewScan scan) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 创建扫描
        byte[] postBody = mapper.writeValueAsBytes(scan);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scan));
        byte[] result = testNormal(client,"/api/v3/tasks/"+taskReturn.getId()+"/scans", HttpRequestType.HTTP_POST, postBody);
        NewScanReturn scanReturn = buildResultObject(result, NewScanReturn.class);
        client.stop();
        return scanReturn;
    }
    private ScanResult queryScanResult(NewScanReturn scanReturn) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询扫描状态
        byte[] result = testNormal(client,"/api/v3/scans/"+scanReturn.getId(), HttpRequestType.HTTP_GET, null);
        ScanResult scanResult = buildResultObject(result, ScanResult.class);
        client.stop();
        return scanResult;
    }
    private TaskResult queryTaskScanResult(NewTaskReturn taskReturn) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询扫描状态
        byte[] result = testNormal(client,"/api/v3/tasks/"+taskReturn.getId()+"/scans", HttpRequestType.HTTP_GET, null);
        TaskResult taskResult = buildResultObject(result, TaskResult.class);
        client.stop();
        return taskResult;
    }
    private AssetsQueryVulnerabilitiesResult queryAssetsVulnerabilities(int assetId) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询资产漏洞
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("page", 0);
        mapParams.put("size", 10);

        String url = "/api/v3/assets/"+assetId+"/vulnerabilities";
        byte[] result = testNormal(client,url, App.HttpRequestType.HTTP_GET, mapParams, null, null);
        AssetsQueryVulnerabilitiesResult queryResult = buildResultObject(result, AssetsQueryVulnerabilitiesResult.class);
        pageResource(queryResult, client, url, mapParams, null, null);
        client.stop();
        return queryResult;
    }
    private <T extends PageAndResources> T pageResource(T t, HttpClient client, String url, Map<String, Object> mapHeader, byte[] body) throws Exception {
        return pageResource(t, client, url, null, mapHeader, body);
    }
    private <T extends PageAndResources> T pageResource(T t, HttpClient client, String url, Map<String, Object> mapParams, Map<String, Object> mapHeader, byte[] body) throws Exception {
        Page page = t.getPage();
        if (page != null) {
            int pageNum = page.getTotalPages();
            int curPage = page.getNumber();
            int perPageSize = page.getSize();
            int totalResources = page.getTotalResources();
            for (int num=1; num<pageNum;num++) {
                mapParams.put("page", curPage+num);
                byte[] result = testNormal(client,url, App.HttpRequestType.HTTP_GET, mapParams, mapHeader, null);
                T tmpTaskResult = buildResultObject(result, t.getClass());
                t.getResources().addAll(tmpTaskResult.getResources());
                t.getPage().setSize(perPageSize*(num+1));
                t.getPage().setTotalResources(t.getResources().size());
            }
            t.getPage().setTotalPages(1);
        }
        return t;
    }
    private AssetsQueryPortResult queryAssetsPorts(int assetId) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询资产端口
        byte[] result = testNormal(client,"/api/v3/assets/"+assetId+"/ports", HttpRequestType.HTTP_GET, null);
        AssetsQueryPortResult queryResult = buildResultObject(result, AssetsQueryPortResult.class);
        client.stop();
        return queryResult;
    }

    private byte[] buildNewTask(NewTask nt) throws JsonProcessingException {

        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nt);
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

    private TaskScanConfig buildTaskConfig() throws IOException {
        String xmlString = createTaskConfig();
        TaskScanConfig taskScanConfig = xmlMapper.readValue(xmlString, TaskScanConfig.class);
        return taskScanConfig;
    }
    private String createTaskConfig() throws IOException {

        List<TaskConfig> taskConfigs = new ArrayList<>();

        List<Credential> credentials = new ArrayList<>();
        Credential credential;

        credential = new Credential(new DomainUserPwd("as400", "domain", "user", "password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DomainUserPwd("cifs", "domain", "user", "password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Cifshash("cifshash", "domain", "user", "ntlmHash"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DomainUserPwd("cvs", "domain", "user", "password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DbUserPwd("db2", "databasename", "user", "password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new UserPwd("ftp","user","password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Http("http","realm","user","password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DbDomainUserPwd("ms-sql","databasename", false, "domain","user","password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DbUserPwd("mysql","databasename", "user","password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Notes("notes","notesIDPassword"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Oracle("oracle", "sid","user","password", false, "oracleListenerPassword"),
                "description", true, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new UserPwd("pop","user","password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DbUserPwd("postgresql","databasename", "user","password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new UserPwd("remote-exec","user","password"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Snmp("snmp","communityName"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Snmpv3("snmpv3","md5", "user", "pwd", "aes-128", "privacyPassword"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new Ssh("ssh","user", "pwd", "sudo", "permissionElevationUsername", "permissionElevationPassword"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new SshKey("ssh-key","user", "privateKeyPassword", "pemKey", "sudo", "permissionElevationUsername", "permissionElevationPassword"),
                "description", null, "127.0.0.1", "name", 22);
        credentials.add(credential);

        credential = new Credential(new DbDomainUserPwd("sybase","databasename", false, "domain","user","password"),
                "description", null, "127.0.0.1", "name", 22);
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
                name,"full-audit-without-web-spider", "toolcategory", ".\\results", "taskcode");
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskScanConfig);
        System.out.println(xmlString);
        taskScanConfig = xmlMapper.readValue(xmlString, TaskScanConfig.class);

        xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskScanConfig);
        System.out.println(xmlString);

        return xmlString;
    }

    private NewTask buildNewTask(TaskScanConfig taskScanConfig) throws JsonProcessingException {
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
        return mewTask;
    }
}
