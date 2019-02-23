package com.ft.http.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ft.http.v3.HttpClient;
import com.ft.http.v3.assets.Assets;
import com.ft.http.v3.assets.AssetsQueryPortResult;
import com.ft.http.v3.assets.AssetsQueryResult;
import com.ft.http.v3.assets.AssetsQueryVulnerabilitiesResult;
import com.ft.http.v3.scan.NewScan;
import com.ft.http.v3.scan.NewScanReturn;
import com.ft.http.v3.scan.ScanResult;
import com.ft.http.v3.task.NewTask;
import com.ft.http.v3.task.NewTaskReturn;
import com.ft.http.v3.task.ResultCallBackImpl;
import com.ft.http.v3.task.TaskResult;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.util.AsciiString;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestHttpClient {

    public static class AssetsScanResult {
        public List<AssetsQueryVulnerabilitiesResult> assetsVulnerabilities = new ArrayList<>();
        public List<AssetsQueryPortResult> assetsPorts = new ArrayList<>();

        public void clear() {
            assetsPorts.clear();
            assetsVulnerabilities.clear();
        }
    }

    private ObjectMapper mapper = new ObjectMapper();

    public TestHttpClient() {
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        //.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        ;
    }

    public <T> T buildResultObject(byte[] result, Class<?> className) throws IOException {
        T t = (T)mapper.readValue(result, className);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t));
        return t;
    }

    public byte[] testNormal(HttpClient client, String url, boolean isget, byte[] body) throws Exception {
        client.configSSL(true);
        client.configAuth(true, "admin", "admin@123");
        client.start();
        ResultCallBackImpl call = new ResultCallBackImpl();

        Map<AsciiString, String> mapHeader = new HashMap<>();
        mapHeader.put(HttpHeaderNames.CONTENT_TYPE, "application/json");

        if (isget) {
            client.getMessage(url, mapHeader, call);
        } else {
            client.postMessage(url, mapHeader, body, call);
        }
        byte[] result = call.getResult();
        String jsonString = new String(result);
        System.out.println("============================\n"+jsonString);

        return result;
    }

    @Test
    public void testCreateTask() throws Exception {
        HttpClient client = new HttpClient("10.0.92.95", 443);
        byte[] result = testNormal(client,"/api/v3/tasks", false, buildNewTask(buildNewTask2()));

        NewTaskReturn taskReturn = buildResultObject(result, NewTaskReturn.class);
        client.stop();
    }

    @Test
    public void testQueryScanResult() throws Exception {
        HttpClient client = new HttpClient("10.0.92.95", 443);
        byte[] result = testNormal(client,"/api/v3/scans/49", true, null);

        ScanResult scanResult = buildResultObject(result, ScanResult.class);
        client.stop();
    }

    @Test
    public void testQueryTaskScanResult() throws Exception {
        HttpClient client = new HttpClient("10.0.92.95", 443);
        byte[] result = testNormal(client,"/api/v3/tasks/67/scans", true, null);

        TaskResult scanResult = buildResultObject(result, TaskResult.class);
        client.stop();
    }

    @Test
    public void testAll() throws Exception {
        NewTask       task;
        NewScan       scan;
        NewTaskReturn newTaskReturn;
        NewScanReturn newScanReturn;

        task = buildNewTask2();
        // 创建任务
        newTaskReturn = startTask(task);

        // 登录检查

        // 创建扫描
        scan = new NewScan(task.getEngineId(), task.getScan().getAssets().getIncludedTargets().getAddresses(), task.getScanTemplateId());
        // /v3/tasks/11/scans;
        String url = "/api/v3/tasks/"+newTaskReturn.getId()+"/scans";
        newScanReturn = startScan(newTaskReturn, scan);

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
                        assetsScanResult.assetsPorts.add(assetsPort);
                        break;
                    }
                }
            }
        }

        // 处理检查结果
        System.out.println("===================== ok ========================");
        String vulnerabilitiesFile = task.getName()+".json";
        vulnerabilitiesFile = vulnerabilitiesFile.replaceAll(":", "-");
        RandomAccessFile f1 = new RandomAccessFile(vulnerabilitiesFile, "rw");
        f1.write("[".getBytes());
        int num = assetsScanResult.assetsVulnerabilities.size();
        int i=0;
        byte[] jsonByte;
        for (AssetsQueryVulnerabilitiesResult v: assetsScanResult.assetsVulnerabilities) {
            jsonByte = mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(v);
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(v);
            System.out.println(s);
            f1.write(jsonByte);

            if (i>0 && i<num-1) {
                f1.write(",".getBytes());
            }
        }
        f1.write("]".getBytes());

        if (num > 0) {
            f1.write(",".getBytes());
        }

        f1.write("[".getBytes());
        num = assetsScanResult.assetsPorts.size();
        i = 0;
        for (AssetsQueryPortResult p : assetsScanResult.assetsPorts) {
            jsonByte = mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(p);
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(p);
            System.out.println(s);
            f1.write(jsonByte);

            if (i>0 && i<num-1) {
                f1.write(",".getBytes());
            }
        }
        f1.write("]".getBytes());
        f1.close();
    }

    private NewTaskReturn startTask(NewTask task) throws Exception {
        HttpClient client = new HttpClient("10.0.92.95", 443);

        // 创建任务
        byte[] postBody = mapper.writeValueAsBytes(task);
        byte[] result = testNormal(client,"/api/v3/tasks", false, postBody);
        NewTaskReturn taskReturn = buildResultObject(result, NewTaskReturn.class);
        client.stop();
        return taskReturn;
    }
    private NewScanReturn startScan(NewTaskReturn taskReturn, NewScan scan) throws Exception {
        HttpClient client = new HttpClient("10.0.92.95", 443);
        // 创建扫描
        byte[] postBody = mapper.writeValueAsBytes(scan);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scan));
        byte[] result = testNormal(client,"/api/v3/tasks/"+taskReturn.getId()+"/scans", false, postBody);
        NewScanReturn scanReturn = buildResultObject(result, NewScanReturn.class);
        client.stop();
        return scanReturn;
    }
    private ScanResult queryScanResult(NewScanReturn scanReturn) throws Exception {
        HttpClient client = new HttpClient("10.0.92.95", 443);
        // 查询扫描状态
        byte[] result = testNormal(client,"/api/v3/scans/"+scanReturn.getId(), true, null);
        ScanResult scanResult = buildResultObject(result, ScanResult.class);
        client.stop();
        return scanResult;
    }
    private TaskResult queryTaskScanResult(NewTaskReturn taskReturn) throws Exception {
        HttpClient client = new HttpClient("10.0.92.95", 443);
        // 查询扫描状态
        byte[] result = testNormal(client,"/api/v3/tasks/"+taskReturn.getId()+"/scans", true, null);
        TaskResult taskResult = buildResultObject(result, TaskResult.class);
        client.stop();
        return taskResult;
    }
    private AssetsQueryResult queryAssets() throws Exception {
        HttpClient client = new HttpClient("10.0.92.95", 443);
        // 查询资产
        byte[] result = testNormal(client,"/api/v3/assets", true, null);
        AssetsQueryResult queryResult = buildResultObject(result, AssetsQueryResult.class);
        client.stop();
        return queryResult;
    }
    private AssetsQueryVulnerabilitiesResult queryAssetsVulnerabilities(int assetId) throws Exception {
        HttpClient client = new HttpClient("10.0.92.95", 443);
        // 查询资产漏洞
        byte[] result = testNormal(client,"/api/v3/assets/"+assetId+"/vulnerabilities", true, null);
        AssetsQueryVulnerabilitiesResult queryResult = buildResultObject(result, AssetsQueryVulnerabilitiesResult.class);
        client.stop();
        return queryResult;
    }
    private AssetsQueryPortResult queryAssetsPorts(int assetId) throws Exception {
        HttpClient client = new HttpClient("10.0.92.95", 443);
        // 查询资产端口
        byte[] result = testNormal(client,"/api/v3/assets/"+assetId+"/ports", true, null);
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
}
