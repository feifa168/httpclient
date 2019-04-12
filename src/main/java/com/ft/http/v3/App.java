package com.ft.http.v3;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ft.http.v3.assets.*;
import com.ft.http.v3.config.*;
import com.ft.http.v3.credential.Credential;
import com.ft.http.v3.credential.CredentialReturn;
import com.ft.http.v3.credential.SharedCredential;
import com.ft.http.v3.scan.NewScan;
import com.ft.http.v3.scan.NewScanReturn;
import com.ft.http.v3.scan.ScanResult;
import com.ft.http.v3.scanengine.ScanEngine;
import com.ft.http.v3.scanengine.ScanEngineResult;
import com.ft.http.v3.scantemplate.*;
import com.ft.http.v3.task.*;
import com.ft.http.v3.update.OfflineUpdateResult;
import com.ft.http.v3.vulnerabilities.*;
import com.ft.http.v3.weakpassword.CrackScanResult;
import com.ft.http.v3.weakpassword.CrackScanReturn;
import com.ft.http.v3.weakpassword.NewCracks;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.util.AsciiString;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class App {

    public enum HttpRequestType {
        HTTP_UNKNOWN,
        HTTP_GET,
        HTTP_POST,
        HTTP_PUT,
    }

    public App() {
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

        startTime = System.currentTimeMillis();
    }

    public boolean isTimeout() {
        return (System.currentTimeMillis()-startTime) > timeout*60*1000;
    }

    public boolean isSignalTimeout() {
        return (System.currentTimeMillis()-startTime) > signalTimeout*60*1000;
    }

    /**
     * 转换byte数组内容为指定类型对象
     *
     * @param  result     待转换的字节
     *
     * @param  className     字节要转换的类型
     *
     * @return  字节转换的类型对象
     *
     * @throws  IOException  对象转换失败
     *
     */
    public <T> T buildResultObject(byte[] result, Class<?> className) throws IOException {
        if (null == result) {
            return null;
        }

        T t = (T)mapper.readValue(result, className);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t));
        return t;
    }

    /**
     * 获取HTTP请求结果
     *
     * @param  client  HTTP客户端
     *
     * @param  url     HTTP URL路径
     *
     * @param  type    HTTP请求类型 GET，POST
     *
     * @param  mapParams     URL后面带的参数
     *
     * @param  mapHeader     HTTP头信息
     *
     * @param  body     HTTP发送的body
     *
     * @return  返回结果的数组
     *
     * @throws  IOException  抛出的异常
     *
     */
    public byte[] testNormal(HttpClient client, String url, HttpRequestType type, Map<String, Object> mapParams, Map<String, Object> mapHeader, byte[] body) throws Exception {
        client.configSSL(true);
        client.configAuth(true, authName, authPassword);
        client.start();
        ResultCallBackImpl call = new ResultCallBackImpl();
        call.setSignalTimeout(signalTimeout);

        //Map<AsciiString, String> mapHeader = new HashMap<>();
        //mapHeader.put(HttpHeaderNames.CONTENT_TYPE, "application/json");

        if (null == mapHeader) {
            mapHeader = new HashMap<>();
            mapHeader.put(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json");
        }

        byte[] result = null;
        try {
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
        } finally {
//            if (client.isActive()) {
//                result = call.getResult();
//            }
            result = call.getResult();
            if (result != null) {
                String jsonString = new String(result);
                System.out.println("============================\n" + jsonString);
            }
        }

        return result;
    }

    public byte[] testNormal(HttpClient client, String url, HttpRequestType type, Map<String, Object> mapHeader, byte[] body) throws Exception {
        return testNormal(client, url, type, null, mapHeader, body);
    }
    public byte[] testNormal(HttpClient client, String url, HttpRequestType type, byte[] body) throws Exception {
        Map<String, Object> mapHeader = new HashMap<>();
        mapHeader.put(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json");
        return testNormal(client, url, type, mapHeader, body);
    }

    /**
     * 处理弱口令扫描
     *
     * @param  taskconfigs  弱口令扫描的配置信息
     *
     * @param  crackResultInfos     弱口令扫描结果的集合，IP与结果的对应
     *
     * @param  mapIp2TaskConfig    IP与扫描资产信息的对应集合
     *
     * @return  无
     *
     */
    public void handleCracks(List<TaskConfig> taskconfigs, NewTaskReturn newTaskReturn, Map<String, CrackScanResultInfo> crackResultInfos, Map<String, TaskConfig> mapIp2TaskConfig) {

        if (null==taskconfigs || null==crackResultInfos || null==mapIp2TaskConfig) {
            return;
        }

        int taskSize = taskconfigs.size();
        if (taskSize < 1) {
            return;
        }

        int corePoolSize = Runtime.getRuntime().availableProcessors();

        threadPool = new ThreadPoolExecutor(corePoolSize, corePoolSize*2, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024));

        for (TaskConfig taskConfig : taskconfigs) {

            String taskIp = taskConfig.getIp();
            if (null == taskIp) {
                continue;
            }
            mapIp2TaskConfig.put(taskIp, taskConfig);

            do {
                // 登录检查
                if (taskConfig.getCredentials() == null) {
                    break;
                }

                int taskId = newTaskReturn.getId();
                CredentialReturn credentialReturn;
                // /api/v3/tasks/taskId/task_credentials
                for (Credential credential : taskConfig.getCredentials()) {
                    String url = "/api/v3/tasks/"+taskId+"/task_credentials";
                    try {
                        credentialReturn = createCredential(newTaskReturn, credential);
                        if (0 == credentialReturn.getId()) {
                            System.out.println("创建认证失败,"+credentialReturn.getError());
                            //scanReslutMixWithError.setMessage("创建认证失败,"+newTaskReturn.getError());
                        }
                    } catch (Exception e) {
                        System.out.println("创建认证失败");
                    }
                }
            } while (false);

            do {
                // 弱口令扫描
                NewCracks cracks = taskConfig.getCracks();
                if (null == cracks) {
                    continue;
                }
                //CrackScanResultInfo resultInfo = new CrackScanResultInfo();

                if ( null == cracks.getModels() || 0 == cracks.getModels().size()) {
                    continue;
                }

                // todo 测试使用，实际使用要去掉
                cracks.setName(new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS").format(new Date()).toString());

                CountDownLatch latch = new CountDownLatch(1);
                CrackScanResultInfo crackScanResultInfo = new CrackScanResultInfo();
                crackScanResultInfo.setLatch(latch);

                crackResultInfos.put(taskIp, crackScanResultInfo);

                threadPool.execute(new CrackThread(cracks, this, crackScanResultInfo/*resultInfo*/, latch));
//            threadPool.execute(()->{
//                CrackScanReturn crackReturn = null;
//                try {
//                    crackReturn = startCracks(cracks);
//                    if (null != crackReturn) {
//                        if (0 != crackReturn.getId()) {
//                            CrackScanReturn crackScanReturn = startCracksScan(crackReturn);
//                            if (null != crackScanReturn) {
//                                CrackScanResult result = null;
//                                do {
//                                    Thread.sleep(1000);
//                                    result = queryCracksScanResult(crackScanReturn);
//                                } while((result != null) && (!result.finished()));
//                                resultInfo.setCrackScanResult(result);
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    latch.countDown();
//                }
//            });
            } while (false);
        }

        threadPool.shutdown();
    }

    /**
     * 处理扫描结果文件
     *
     * @param  task  任务信息
     *
     * @param  scanReslutMixWithError     任务扫描结果
     *
     * @param  taskScanConfig    扫描配置信息
     *
     * @return  无
     *
     */
    public void handleResult(NewTask task, AssetsScanResultMixWithError scanReslutMixWithError, TaskScanConfig taskScanConfig) {
        // 处理检查结果
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
        File directory = new File(output);
        if (!directory.exists()) {
            directory.mkdirs();
        }

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//        formatter.format(new Date());
        String newName = output+toolcategory+"_"+taskcode+"_"+taskName+".json";
        newName = newName.replaceAll(":", "-");
        //String nname = toolcategory+"_"+taskcode+"_"+taskName+".json";
        vulnerabilitiesFile = newName + ".tmp";
        //vulnerabilitiesFile = vulnerabilitiesFile.replaceAll(":", "-");

        // 删除之前的文件
        File fl = new File(vulnerabilitiesFile);
        if (fl.exists() && fl.isFile()) {
            fl.delete();
        }
        File fl2 = new File(newName);
        if (fl2.exists() && fl2.isFile()) {
            fl2.delete();
        }

        String mixResult = null;
        try {
            mixResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scanReslutMixWithError);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(vulnerabilitiesFile, "rw");

            //String mixResultUtf8 = new String(mixResult.getBytes("utf-8"), "utf-8");
            //System.out.println(mixResult);
            //f1.write(mixResultUtf8.getBytes("utf-8"));
            if (null != mixResult) {
                raf.write(mixResult.getBytes("utf-8"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != raf) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        File fold = new File(vulnerabilitiesFile);
        if (fold.exists()) {
//            String nnm = fold.getAbsolutePath();
//            nnm = nnm.substring(0, nnm.lastIndexOf(File.separator)+1) + nname;

            File fnew = new File(newName);
            System.out.println("new exists is " + fnew.exists());
            //System.out.println("delete result is " + fold.delete());
            System.out.println("rename from " + vulnerabilitiesFile + " to " + newName + " result is " + fold.renameTo(fnew));
        } else {
            System.out.println("rename from " + vulnerabilitiesFile + " is not exist");
        }
    }

    /**
     * 创建任务
     *
     * @param  task     所要创建的任务信息
     *
     * @return  任务创建返回结果
     *
     * @throws  Exception  异常情况
     *
     */
    public NewTaskReturn startTask(NewTask task) throws Exception {
        HttpClient client = new HttpClient(host, port);

        // 创建任务
        byte[] postBody = mapper.writeValueAsBytes(task);
        byte[] result = null;
        try {
            result = testNormal(client, "/api/v3/tasks", HttpRequestType.HTTP_POST, postBody);
        } finally {
            client.stop();
        }
        return buildResultObject(result, NewTaskReturn.class);
    }

    /**
     * 创建弱口令任务
     *
     * @param  cracks     所要创建的弱口令信息
     *
     * @return  任务创建返回结果
     *
     * @throws  Exception  异常情况
     *
     */
    public CrackScanReturn startCracks(NewCracks cracks) throws Exception {
        if (null == cracks) {
            return null;
        }
        HttpClient client = new HttpClient(host, port);
//        for (NewCracks.Model model : cracks.getModels()) {
//            model.service2Lower();
//        }

        // 创建弱口令任务
        byte[] postBody = mapper.writeValueAsBytes(cracks);
        byte[] result = null;
        try {
            result = testNormal(client, "/api/v3/cracks", HttpRequestType.HTTP_POST, postBody);
        } finally {
            client.stop();
        }

        int resultId = 0;
        CrackScanReturn scanReturn = null;
        if (result != null && (-1 == new String(result).indexOf("error"))) {
            try {
                resultId = Integer.parseInt(new String(result));
                System.out.println(resultId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (resultId != 0) {
                scanReturn = new CrackScanReturn(resultId, null);
            } else {
                scanReturn = buildResultObject(result, CrackScanReturn.class);
            }
        }
        return scanReturn;
    }

    /**
     * 启动弱口令扫描
     *
     * @param  ret     创建的弱口令返回结果
     *
     * @return  任务创建返回结果
     *
     * @throws  Exception  异常情况
     *
     */
    private CrackScanReturn startCracksScan(CrackScanReturn ret) throws Exception {
        if (ret == null || ret.getId() == 0) {
            return null;
        }

        HttpClient client = new HttpClient(host, port);

        // 创建弱口令扫描任务
        byte[] result = null;
        try {
            result = testNormal(client, "/api/v3/cracks/scan/"+ret.getId(), HttpRequestType.HTTP_POST, null);
        } finally {
            client.stop();
        }

        int resultId = 0;
        CrackScanReturn scanReturn = null;
        if (result != null) {
            try {
                resultId = Integer.parseInt(new String(result));
                System.out.println(resultId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (resultId != 0) {
                scanReturn = new CrackScanReturn(resultId, null);
            } else {
                scanReturn = buildResultObject(result, CrackScanReturn.class);
            }
        }
        return scanReturn;
    }

    /**
     * 查询弱口令结果
     *
     * @param  ret     启动弱口令扫描的结果
     *
     * @return  查询结果
     *
     * @throws  Exception  异常情况
     *
     */
    private CrackScanResult queryCracksScanResult(CrackScanReturn ret) throws Exception {
        if (ret == null || ret.getId() == 0) {
            return null;
        }

        HttpClient client = new HttpClient(host, port);

        // 查询弱口令扫描任务
        byte[] result = null;
        try {
            result = testNormal(client, "/api/v3/cracks/scan/"+ret.getId(), HttpRequestType.HTTP_GET, null);
        } finally {
            client.stop();
        }

        int resultId = 0;
        CrackScanResult scanResult = null;
        if (result != null) {
            scanResult = buildResultObject(result, CrackScanResult.class);
        }
        return scanResult;
    }

    /**
     * 创建扫描凭证
     *
     * @param  taskReturn     任务扫描结果
     *
     * @param  credential     认证信息
     *
     * @return  认证返回结果
     *
     * @throws  Exception  异常情况
     *
     */
    private CredentialReturn createCredential(NewTaskReturn taskReturn, Credential credential) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 创建凭证
        byte[] postBody = mapper.writeValueAsBytes(credential);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(credential));
        // /api/v3/tasks/taskid/task_credentials
        byte[] result = null;
        try {
            result = testNormal(client, "/api/v3/tasks/" + taskReturn.getId() + "/task_credentials", HttpRequestType.HTTP_POST, postBody);
        } finally {
            client.stop();
        }
        return buildResultObject(result, CredentialReturn.class);
    }
    /**
     * 创建共享凭证
     *
     * @param  taskReturn     任务扫描结果
     *
     * @param  credential     认证信息
     *
     * @return  认证返回结果
     *
     * @throws  Exception  异常情况
     *
     */
    private SharedCredential createSharedCredential(NewTaskReturn taskReturn, Credential credential) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 创建共享凭证
        byte[] postBody = mapper.writeValueAsBytes(credential);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(credential));
        // /api/v3/shared_credentials
        byte[] result = null;
        try {
            result = testNormal(client, "/api/v3/shared_credentials", HttpRequestType.HTTP_POST, postBody);
        } finally {
            client.stop();
        }
        return buildResultObject(result, SharedCredential.class);
    }

    /**
     * 启动任务
     *
     * @param  taskReturn     创建任务返回的结果
     *
     * @param  scan     扫描所需的信息
     *
     * @return  任务启动返回结果
     *
     * @throws  Exception  异常情况
     *
     */
    private NewScanReturn startScan(NewTaskReturn taskReturn, NewScan scan) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 创建扫描
        byte[] postBody = mapper.writeValueAsBytes(scan);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scan));
        byte[] result = null;
        try {
            result = testNormal(client,"/api/v3/tasks/"+taskReturn.getId()+"/scans", HttpRequestType.HTTP_POST, postBody);
        } finally {
            client.stop();
        }
        return buildResultObject(result, NewScanReturn.class);
    }

    /**
     * 查询扫描结果
     *
     * @param  scanReturn     启动扫描任务返回的结果
     *
     * @return  扫描的返回结果
     *
     * @throws  Exception  异常情况
     *
     */
    private ScanResult queryScanResult(NewScanReturn scanReturn) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询扫描状态
        byte[] result = null;
        try {
            result = testNormal(client,"/api/v3/scans/"+scanReturn.getId(), HttpRequestType.HTTP_GET, null);
        } finally {
            client.stop();
        }
        return buildResultObject(result, ScanResult.class);
    }
    /**
     * 查询任务扫描结果
     *
     * @param  taskReturn     创建任务返回的结果
     *
     * @return  扫描的返回结果
     *
     * @throws  Exception  异常情况
     *
     */
    private TaskResult queryTaskScanResult(NewTaskReturn taskReturn) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询任务扫描状态

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("active", "true");
        mapParams.put("page", 0);
        mapParams.put("size", 500);

        String url = "/api/v3/tasks/"+taskReturn.getId()+"/scans";
        byte[] result = null;
        try {
            result = testNormal(client,url, HttpRequestType.HTTP_GET, mapParams, null, null);
        } finally {
            client.stop();
        }
        TaskResult taskResult = buildResultObject(result, TaskResult.class);
        pageResource(taskResult, client, url, mapParams, null, null);
        return taskResult;
    }
    private <T extends PageAndResources> T pageResource(T t, HttpClient client, String url, Map<String, Object> mapParams, Map<String, Object> mapHeader, byte[] body) {
        return pageResource(t, client, url, mapParams, mapHeader, body, null);
    }
    /**
     * 处理翻页的结果
     *
     * @param  t     创建任务返回的结果
     *
     * @param  client  HTTP客户端
     *
     * @param  url     HTTP URL路径
     *
     * @param  mapParams     URL后面带的参数
     *
     * @param  mapHeader     HTTP头信息
     *
     * @param  body     HTTP发送的body
     *
     * @param  fc     对于翻页的，可以不用等到返回所有结果再处理，可以直接写入文件
     *
     * @return  翻页结果集
     *
     * @throws  Exception  异常情况
     *
     */
    private <T extends PageAndResources> T pageResource(T t, HttpClient client, String url, Map<String, Object> mapParams, Map<String, Object> mapHeader, byte[] body, FileChannel fc) {

        //HttpClient client = new HttpClient(host, port);

//        if (t != null) {
//            return t;
//        }
        if (null == t) {
            return null;
        }

        Page page = t.getPage();
        if (page != null) {
            int pageNum = page.getTotalPages();
            int curPage = page.getNumber();
            int perPageSize = page.getSize();
            int totalResources = page.getTotalResources();
            for (int num=1; num<pageNum;num++) {
//                if (num > 5) {
//                    break;
//                }
                mapParams.put("page", String.valueOf(curPage+num));
                client = new HttpClient(host, port);
                byte[] result = null;
                try {
                    result = testNormal(client,url, HttpRequestType.HTTP_GET, mapParams, mapHeader, null);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    client.stop();
                }
                T tmpTaskResult = null;
                try {
                    tmpTaskResult = buildResultObject(result, t.getClass());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<T> resultResources = tmpTaskResult.getResources();
                if (null != resultResources) {
                    t.getPage().setSize(perPageSize * (num + 1));
                    t.getPage().setTotalResources(perPageSize*num + resultResources.size());
                }

                if (null == fc) {
                    t.getResources().addAll((null != tmpTaskResult.getResources()) ? tmpTaskResult.getResources() : null);
//                    t.getPage().setSize(perPageSize * (num + 1));
//                    t.getPage().setTotalResources(t.getResources().size());
                } else {
                    try {
                        for (Object tmpt : resultResources) {
                            fc.write(ByteBuffer.wrap(",".getBytes()));
                            write2File(fc, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tmpt), true);
                        }
                        //fc.write(ByteBuffer.wrap(mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(resultResources)));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return t;
    }
    private <T extends PageAndResources> T pageResource(T t, HttpClient client, String url, Map<String, Object> mapHeader, byte[] body) throws Exception {
        return pageResource(t, client, url, null, mapHeader, body);
    }

    /**
     * 查询资产信息
     *
     * @return  资产信息
     *
     * @throws  Exception  异常情况
     *
     */
    private AssetsQueryResult queryAssets(int taskId) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询资产
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("page", 0);
        mapParams.put("size", 500);

        // /api/v3/tasks/{id}/assets
        //String url = "/api/v3/assets";
        String url = "/api/v3/tasks/"+taskId+"/assets";
        byte[] result = null;
        try {
            result = testNormal(client,url, HttpRequestType.HTTP_GET, mapParams, null, null);
        } finally {
            client.stop();
        }
        AssetsQueryResult queryResult = buildResultObject(result, AssetsQueryResult.class);
        pageResource(queryResult, client, url, mapParams, null, null);
        return queryResult;
    }
    /**
     * 查询资产漏洞
     *
     * @param  assetId     资产ID
     *
     * @return  资产漏洞集合
     *
     * @throws  Exception  异常情况
     *
     */
    private AssetsQueryVulnerabilitiesResult queryAssetsVulnerabilities(int assetId) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询资产漏洞
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("page", 0);
        mapParams.put("size", 500);

        String url = "/api/v3/assets/"+assetId+"/vulnerabilities";
        byte[] result = null;
        try {
            result = testNormal(client,url, HttpRequestType.HTTP_GET, mapParams, null, null);
        } finally {
            client.stop();
        }
        AssetsQueryVulnerabilitiesResult queryResult = buildResultObject(result, AssetsQueryVulnerabilitiesResult.class);
        pageResource(queryResult, client, url, mapParams, null, null);
        return queryResult;
    }
    /**
     * 查询资产端口
     *
     * @param  assetId     资产ID
     *
     * @return  资产端口集合
     *
     * @throws  Exception  异常情况
     *
     */
    private AssetsQueryPortResult queryAssetsPorts(int assetId) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询资产端口
        //byte[] result = testNormal(client,"/api/v3/assets/"+assetId+"/ports", HttpRequestType.HTTP_GET, null);
        byte[] result = null;
        try {
            result = testNormal(client,"/api/v3/assets/"+assetId+"/services", HttpRequestType.HTTP_GET, null);
        } finally {
            client.stop();
        }
        AssetsQueryPortResult queryResult = buildResultObject(result, AssetsQueryPortResult.class);
        return queryResult;
    }

    /**
     * 查询所有漏洞
     *
     * @return  资产漏洞集合
     *
     * @throws  Exception  异常情况
     *
     */
    public VulnerabilityResult  queryAllVulnerabilities() throws Exception {
        return queryAllVulnerabilities(null);
    }

    /**
     * 处理翻页的结果
     *
     * @param  url     HTTP URL路径
     *
     * @param  mapParams     URL后面带的参数
     *
     * @param  clazz     翻页资源类型
     *
     * @param  fc     对于翻页的，可以不用等到返回所有结果再处理，可以直接写入文件
     *
     * @return  翻页结果集
     *
     * @throws  Exception  异常情况
     *
     */
    public <T extends PageAndResources> T  queryPageAndResources(String url, Map<String, Object> mapParams, Class clazz, FileChannel fc) throws Exception {
        HttpClient client = new HttpClient(host, port);

        byte[] result = null;
        try {
            result = testNormal(client,url, HttpRequestType.HTTP_GET, mapParams, null, null);
        } finally {
            client.stop();
        }
        T queryResult = buildResultObject(result, clazz);
        if (null != fc && null != queryResult && null != queryResult.getResources()) {
            write2File(fc, "{\n\"total\":"+queryResult.getPage().getTotalResources()+",\n\"resource\":[", true);
            List<VulnerabilityInfo> resultResources = queryResult.getResources();
            for (int i=0; i<resultResources.size(); i++) {
                if (i > 0) {
                    fc.write(ByteBuffer.wrap(",".getBytes()));
                }
                write2File(fc, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultResources.get(i)), true);
            }
            //fc.write(ByteBuffer.wrap(mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(queryResult.getResources())));
        }
        T tmpt = pageResource(queryResult, client, url, mapParams, null, null, fc);
        if (null != fc) {
            fc.write(ByteBuffer.wrap("]\n}".getBytes()));
        }
        return queryResult;
    }

    /**
     * 查询扫描引擎
     *
     * @param  fc     查询结果可以直接写入文件
     *
     * @return  翻页结果集
     *
     * @throws  Exception  异常情况
     *
     */
    public ScanEngineResult queryScanEngine(FileChannel fc) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询资产端口
        //byte[] result = testNormal(client,"/api/v3/assets/"+assetId+"/ports", HttpRequestType.HTTP_GET, null);
        byte[] result = null;
        try {
            result = testNormal(client,"/api/v3/scan_engiens", HttpRequestType.HTTP_GET, null);
        } finally {
            client.stop();
        }
        ScanEngineResult queryResult = buildResultObject(result, ScanEngineResult.class);
        if (null!=result && null!=queryResult && null!=queryResult.getResources()) {
            int size = queryResult.getResources().size();
            queryResult.setTotal(size);
            if (null != fc) {
                write2File(fc, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryResult), true);
            }
        }
        return queryResult;
    }

    /**
     * 查询所有漏洞
     *
     * @param  fc     对于翻页的，可以不用等到返回所有结果再处理，可以直接写入文件
     *
     * @return  翻页结果集
     *
     * @throws  Exception  异常情况
     *
     */
    public VulnerabilityResult  queryAllVulnerabilities(FileChannel fc) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询所有漏洞
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("page", 0);
        mapParams.put("size", 5000);

        String url = "/api/v3/vulnerabilities/all";
        return queryPageAndResources(url, mapParams,VulnerabilityResult.class, fc);

//        byte[] result = null;
//        try {
//            result = testNormal(client,url, HttpRequestType.HTTP_GET, mapParams, null, null);
//        } finally {
//            client.stop();
//        }
//        VulnerabilityResult queryResult = buildResultObject(result, VulnerabilityResult.class);
//        if (null != fc && null != queryResult && null != queryResult.getResources()) {
//            write2File(fc, "{\n\"total\":"+queryResult.getPage().getTotalResources()+",\n\"resource\":[", true);
//            List<VulnerabilityInfo> resultResources = queryResult.getResources();
//            for (int i=0; i<resultResources.size(); i++) {
//                if (i > 0) {
//                    fc.write(ByteBuffer.wrap(",".getBytes()));
//                }
//                write2File(fc, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultResources.get(i)), true);
//            }
//            //fc.write(ByteBuffer.wrap(mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(queryResult.getResources())));
//        }
//        VulnerabilityResult tmpQueryResult = pageResource(queryResult, client, url, mapParams, null, null, fc);
//        if (null != fc) {
//            fc.write(ByteBuffer.wrap("]\n}".getBytes()));
//        }
//        return queryResult;
    }

    /**
     * 查询漏洞详情
     *
     * @param  vulnerabId     漏洞ID
     *
     * @return  漏洞详情
     *
     * @throws  Exception  异常情况
     *
     */
    public VulnerabilitiesDetail  queryVulnerabilitiesDetail(String vulnerabId) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询漏洞详情
        String url = "/api/v3/vulnerabilities/"+vulnerabId;
        byte[] result = null;
        try {
            result = testNormal(client,url, HttpRequestType.HTTP_GET, null);
        } finally {
            client.stop();
        }
        VulnerabilitiesDetail queryResult = buildResultObject(result, VulnerabilitiesDetail.class);
        return queryResult;
    }

    /**
     * 将字符串以默认或utf8格式写入文件
     *
     * @param  fc     待写入的文件通道
     *
     * @param  buf     待写入的字符串
     *
     * @param  conv2Utf8     true则为utf8格式，false则为系统默认格式
     *
     * @return  无
     *
     */
    public void write2File(FileChannel fc, String buf, boolean conv2Utf8) {
        if (conv2Utf8) {
            try {
                fc.write(ByteBuffer.wrap(new String(buf.getBytes("utf-8"), "utf-8").getBytes()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                fc.write(ByteBuffer.wrap(buf.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 查询漏洞解决方案
     *
     * @param  vulnerabId     漏洞ID
     *
     * @return  漏洞解决方案
     *
     * @throws  Exception  异常情况
     *
     */
    public VulnerabilitiesSolutions queryVulnerabilitiesSolutions(String vulnerabId) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询漏洞解决方案
        String url = "/api/v3/vulnerabilities/"+vulnerabId+"/solutions";
        byte[] result = null;
        try {
            result = testNormal(client,url, HttpRequestType.HTTP_GET, null);
        } finally {
            client.stop();
        }
        VulnerabilitiesSolutions queryResult = buildResultObject(result, VulnerabilitiesSolutions.class);
        return queryResult;
    }

    /**
     * 查询漏洞单个解决方案
     *
     * @param  vulnerabId     漏洞ID
     *
     * @return  漏洞解决方案
     *
     * @throws  Exception  异常情况
     *
     */
    public SingleSolutions querySingleSolutions(String vulnerabId) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询单个解决方案
        String url = "/api/v3/solutions/" + vulnerabId;
        byte[] result = null;
        try {
            result = testNormal(client,url, HttpRequestType.HTTP_GET, null);
        } finally {
            client.stop();
        }
        SingleSolutions queryResult = buildResultObject(result, SingleSolutions.class);
        return queryResult;
    }

    /**
     * 创建扫描模板
     *
     * @param  template     扫描模板信息
     *
     * @return  创建模板返回结果
     *
     * @throws  Exception  异常情况
     *
     */
    public CreateScanTemplateResult createScanTemplate(ScanTemplate template) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 创建扫描模板
        byte[] postBody = mapper.writeValueAsBytes(template);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(template));
        byte[] result = null;
        try {
            result = testNormal(client,"/api/v3/scan_templates", HttpRequestType.HTTP_POST, postBody);
        } finally {
            client.stop();
        }
        return buildResultObject(result, CreateScanTemplateResult.class);
    }

    /**
     * 离线更新
     *
     * @param  updateFile     更新文件名
     *
     * @return  更新结果
     *
     * @throws  Exception  异常情况
     *
     */
    public OfflineUpdateResult offlineUpdate(String updateFile) throws Exception {
//        host = "120.55.40.41";
//        port = 80;
        HttpClient client = new HttpClient(host, port);
//        client.configSSL(false);
        // 离线升级
        Map<String, Object> mapParams = new HashMap<>();
        String boundary = String.valueOf(new Date().getTime());//"ZH76Tqas4FOGv4OmMaLEjevT1kLsVPiQE";//"-----------------"+new Date().getTime();//"--------------------------395956373148364292469995";
        String boundaryBegin = "--" + boundary;
        String boundaryEnd = boundaryBegin+"--";
        String splitTag = "\r\n";
        mapParams.put("Content-Type", "multipart/form-data; boundary="+boundary);
        mapParams.put("cache-control", "no-cache");
        byte[] postBody = null;
        byte[] result = null;
        try {
            FileChannel fc = FileChannel.open(Paths.get(updateFile), StandardOpenOption.READ);
            ByteBuffer buf = ByteBuffer.allocate((int) fc.size());
            int readLen = fc.read(buf);
            fc.close();
            StringBuilder sbd = new StringBuilder(64);
            sbd.append(boundaryBegin).append(splitTag)
                    .append("Content-Disposition: form-data; name=\"file\"; filename=\""
                            +updateFile+"\""+splitTag+ "Content-Type: application/octet-stream").append(splitTag).append(splitTag);
            String afterTag = splitTag+boundaryEnd+splitTag;
            ByteBuffer postBuf = ByteBuffer.allocate(sbd.length()+buf.limit()+afterTag.length());
            postBuf.put(sbd.toString().getBytes()).put(buf.array()).put(afterTag.getBytes());

            postBody = postBuf.array();//tiy/t.asp  // /api/v3/admin/updates
            result = testNormal(client,"/api/v3/admin/updates", HttpRequestType.HTTP_POST, mapParams, postBody);
        } finally {
            client.stop();
        }
        return buildResultObject(result, OfflineUpdateResult.class);
    }

    /**
     * 查询单个模板
     *
     * @param  templateId     模板ID
     *
     * @return  模板信息
     *
     * @throws  Exception  异常情况
     *
     */
    public ScanTemplate queryScanTemplate(String templateId) throws Exception {
        HttpClient client = new HttpClient(host, port);
        // 查询单个模板
        String url = "/api/v3/scan_templates/" + templateId;
        byte[] result = null;
        try {
            result = testNormal(client,url, HttpRequestType.HTTP_GET, null);
        } finally {
            client.stop();
        }
        ScanTemplate queryResult = buildResultObject(result, ScanTemplate.class);
        return queryResult;
    }

    /**
     * 构造自定义模板
     *
     * @param  template     模板信息
     *
     * @param  customConfig     模板自定义信息
     *
     * @return  无
     *
     */
    public void buildCustomTemplate(ScanTemplate template, TaskScanConfig.CustomScanTemplate customConfig) {
        if (null == template || null == customConfig) {
            return;
        }
        ScanTemplateDiscovery discovery = template.getDiscovery();
        if (null == discovery) {
            return;
        }

        ScanTemplateAssetDiscovery       asset = discovery.getAsset();
        ScanTemplateServiceDiscovery     service = discovery.getService();
        ScanTemplateDiscoveryPerformance performance = discovery.getPerformance();
        if (null == asset || null == performance || null == service) {
            return;
        }

        // 资产发现配置
        asset.setSendIcmpPings(customConfig.isSendIcmpPings());
        String sPorts = customConfig.getTcpPorts();
        if (null != sPorts) {
            String[] splitS = sPorts.split("\\s*,\\s*");
            if (null != splitS) {
                List<String> listPorts = Arrays.asList(splitS);
                if (null != listPorts) {
                    List<Integer> intPorts = new ArrayList<>();
                    for (String port : listPorts) {
                        if (port.length() > 0) {
                            intPorts.add(Integer.valueOf(port));
                        }
                    }
                    asset.setTcpPorts(intPorts);
                }
            }
        }

        sPorts = customConfig.getUdpPorts();
        if (null != sPorts) {
            String[] splitS = sPorts.split("\\s*,\\s*");
            if (null != splitS) {
                List<String> listPorts = Arrays.asList(splitS);
                if (null != listPorts) {
                    List<Integer> intPorts = new ArrayList<>();
                    for (String port : listPorts) {
                        if (port.length() > 0) {
                            intPorts.add(Integer.valueOf(port));
                        }
                    }
                    asset.setUdpPorts(intPorts);
                }
            }
        }
        asset.setIpFingerprintingEnabled(customConfig.isIpFingerprintingEnabled());

        // 服务发现配置
        sPorts = customConfig.getTcpAdditionalPorts();
        if (null != sPorts) {
            String[] splitS = sPorts.split("\\s*,\\s*");
            if (null != splitS) {
                List<String> listPorts = Arrays.asList(splitS);
                service.getTcp().setAdditionalPorts(listPorts);
            }
        }
        sPorts = customConfig.getUdpAdditionalPorts();
        if (null != sPorts) {
            String[] splitS = sPorts.split("\\s*,\\s*");
            if (null != splitS) {
                List<String> listPorts = Arrays.asList(splitS);
                service.getUdp().setAdditionalPorts(listPorts);
            }
        }

        // 性能配置
        performance.getParallelism().setMinimum(customConfig.getParallelismMininum());
        performance.getParallelism().setMaximum(customConfig.getParallelismMaxinum());
        performance.getPacketRate().setMinimum(customConfig.getPacketRateMininum());
        performance.getPacketRate().setMaximum(customConfig.getPacketRateMaxinum());

        template.setMaxParallelAssets(customConfig.getMaxParallelAssets());
        template.setMaxScanProcesses(customConfig.getMaxScanProcesses());
    }

    /**
     * 任务转数组
     *
     * @param  nt     任务信息
     *
     * @return  任务对象转数组的结果
     *
     * @throws  JsonProcessingException  转换异常
     *
     */
    private byte[] buildNewTask(NewTask nt) throws JsonProcessingException {

        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nt);
        System.out.println(jsonString);

        return jsonString.getBytes();
    }

    /**
     * 构造任务，用于测试
     *
     * @return  创建的任务信息
     *
     * @throws  JsonProcessingException  转换异常
     *
     */
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

    /**
     * 配置文件转对象
     *
     * @param  configFile     配置文件
     *
     * @return  配置文件转对象
     *
     * @throws  IOException  转换异常
     *
     */
    public TaskScanConfig buildTaskConfig(String configFile) throws IOException {
        TaskScanConfig taskScanConfig = xmlMapper.readValue(new File(configFile), TaskScanConfig.class);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String name = "SCAN_56_"+formatter.format(new Date());
        taskScanConfig.setName(name);
        TaskScanConfig.CustomScanTemplate customConfig = taskScanConfig.getCustomConfig();
        if (null != customConfig) {
        }

        return taskScanConfig;
    }

    /**
     * 根据配置信息生成新任务
     *
     * @param  taskScanConfig     配置文件
     *
     * @return  新任务
     *
     * @throws  JsonProcessingException  转换异常
     *
     */
    public NewTask buildNewTask(TaskScanConfig taskScanConfig) throws JsonProcessingException {
        if (null == taskScanConfig.getTaskconfigs()) {
            return null;
        }

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

    /**
     * 解析连接的服务器配置信息
     *
     * @return  true为有效，false为失败
     *
     * @throws  IOException  转换异常
     *
     */
    public boolean parseRunConfig() throws IOException {

        runConfig =  mapper.readValue(new File("config/runConfig.json"), RunConfig.class);
        this.host = runConfig.getHost();
        this.port = runConfig.getPort();
        this.authName = runConfig.getAuthName();
        this.authPassword = runConfig.getAuthPassword();
        this.timeout = runConfig.getTimeout();
        this.signalTimeout = runConfig.getSignalTimeout();
        if (((null == host) || host.isEmpty())
                || ((null == authName) || authName.isEmpty())
                || ((null == authPassword) || authPassword.isEmpty())
                || (0 == port)
        ) {
            return false;
        }
        return true;
    }

    /**
     * 弱口令扫描结果信息
     */
    public static class CrackScanResultInfo {
        private CrackScanResult crackScanResult;
        private CountDownLatch latch;
        private ThreadPoolExecutor pool;

        public CountDownLatch getLatch() {
            return latch;
        }

        public void setLatch(CountDownLatch latch) {
            this.latch = latch;
        }

        public ThreadPoolExecutor getPool() {
            return pool;
        }

        public void setPool(ThreadPoolExecutor pool) {
            this.pool = pool;
        }

        public CrackScanResult getCrackScanResult() {
            return crackScanResult;
        }

        public void setCrackScanResult(CrackScanResult crackScanResult) {
            this.crackScanResult = crackScanResult;
        }
    }

    /**
     * 弱口令扫描线程
     */
    public static class CrackThread implements Runnable {
        private NewCracks cracks;
        private App app;
        private CrackScanResultInfo resultInfo;
        private CountDownLatch latch;

        public CrackThread(NewCracks cracks, App app, CrackScanResultInfo resultInfo, CountDownLatch latch) {
            this.cracks = cracks;
            this.app = app;
            this.resultInfo = resultInfo;
            this.latch = latch;
        }

        @Override
        public void run() {
            CrackScanReturn crackReturn = null;
            try {
                crackReturn = app.startCracks(cracks);
                if (null != crackReturn) {
                    if (0 != crackReturn.getId()) {
                        CrackScanReturn crackScanReturn = app.startCracksScan(crackReturn);
                        if (null != crackScanReturn) {
                            CrackScanResult result = null;
                            do {
                                if (app.isTimeout()) {
                                    break;
                                }
                                Thread.sleep(1000);
                                result = app.queryCracksScanResult(crackScanReturn);
                            } while((result != null) && (!result.finished()));
                            resultInfo.setCrackScanResult(result);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    /**
     * 提交给线程池执行的弱口令扫描任务
     *
     * @param  cracks     弱口令信息
     *
     * @param  resultInfo     若口令返回结果信息
     *
     * @return  返回线程池信息，异步的，后面要根据线程池状态判断是否结束
     *
     */
    private ThreadPoolExecutor executeWeakPasswordPool(NewCracks cracks, CrackScanResultInfo resultInfo) {
        if (null == cracks) {
            return null;
        }

        int cracksNum = cracks.getModels().size();
        int processNum = Runtime.getRuntime().availableProcessors();
        int poolNum = processNum > cracksNum ? cracksNum : processNum;

        ExecutorService pool = Executors.newFixedThreadPool(poolNum);

        pool.execute(()->{
            CrackScanReturn crackReturn = null;
            try {
                // todo 测试使用，要去掉
                cracks.setName(new Date().toString());
                crackReturn = startCracks(cracks);
                if (null != crackReturn) {
                    if (0 != crackReturn.getId()) {
                        CrackScanReturn crackScanReturn = startCracksScan(crackReturn);
                        if (null != crackScanReturn) {
                            CrackScanResult result = null;
                            do {
                                Thread.sleep(1000);
                                result = queryCracksScanResult(crackScanReturn);
                            } while((result != null) && (!result.finished()));
                            resultInfo.setCrackScanResult(result);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        });
        pool.shutdown();
//        ThreadPoolExecutor tpool = (ThreadPoolExecutor)pool;
//        tpool.getCompletedTaskCount()

        return (ThreadPoolExecutor)pool;
    }

    /**
     * 提交给线程池执行的弱口令扫描任务
     *
     * @param  cracks     弱口令信息
     *
     * @param  resultInfo     若口令返回结果信息
     *
     * @return  异步的，后面要等待CountDownLatch表示执行结束
     *
     */
    private CountDownLatch executeWeakPassword(NewCracks cracks, CrackScanResultInfo resultInfo) {
        if (null == cracks || null == cracks.getModels()) {
            return null;
        }

        int cracksNum = cracks.getModels().size();
        if (cracksNum == 0) {
            return null;
        }

        CountDownLatch latch = new CountDownLatch(cracksNum);
        int processNum = Runtime.getRuntime().availableProcessors();
        int poolNum = processNum > cracksNum ? cracksNum : processNum;

        ExecutorService pool = Executors.newFixedThreadPool(poolNum);

        pool.execute(()->{
            CrackScanReturn crackReturn = null;
            try {
                // todo 测试使用，要去掉
                cracks.setName(new Date().toString());
                crackReturn = startCracks(cracks);
                if (null != crackReturn) {
                    if (0 != crackReturn.getId()) {
                        CrackScanReturn crackScanReturn = startCracksScan(crackReturn);
                        if (null != crackScanReturn) {
                            CrackScanResult result = null;
                            do {
                                Thread.sleep(1000);
                                result = queryCracksScanResult(crackScanReturn);
                            } while((result != null) && (!result.finished()));
                            resultInfo.setCrackScanResult(result);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });
        pool.shutdown();
//        ThreadPoolExecutor tpool = (ThreadPoolExecutor)pool;
//        tpool.getCompletedTaskCount()

        return latch;
    }

    /**
     * 获取处理json格式的mapper
     *
     * @return  用于处理json数据与对象转换
     *
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * 获取处理xml格式的mapper
     *
     * @return  用于处理xml数据与对象转换
     *
     */
    public XmlMapper getXmlMapper() {
        return xmlMapper;
    }

    private ObjectMapper mapper = new ObjectMapper();
    private XmlMapper xmlMapper = new XmlMapper();
    private RunConfig runConfig;
    private String host;
    private int port;
    private String authName;
    private String authPassword;
    private int     timeout;     // 超时时间，单位分钟
    private int     signalTimeout;  // 单次超时时间，单位分钟
    private long    startTime;   // 启动时间，单位毫秒
    private ThreadPoolExecutor threadPool;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("please enter param config file");
            return;
        }

        String configFile = args[0];
        App app = new App();

        NewTask task = null;
        NewScan scan;
        NewTaskReturn newTaskReturn = null;
        NewScanReturn newScanReturn = null;

        List<AssetsScanResultMix> scanReslutMix = new ArrayList<>();
        AssetsScanResultMixWithError scanReslutMixWithError = new AssetsScanResultMixWithError();
        Map<String, CrackScanResultInfo> crackResultInfos = new HashMap<>();
        //task = buildNewTask2();
        TaskScanConfig taskScanConfig = null;

        do {
            try {
                if (!app.parseRunConfig()) {
                    scanReslutMixWithError.setMessage("运行配置无效");
                    break;
                }
            } catch (IOException e) {
                scanReslutMixWithError.setMessage("读取运行配置文件失败，原因是"+e.getMessage());
                break;
            }

            // 读取配置文件
            try {
                taskScanConfig = app.buildTaskConfig(configFile);
            } catch (IOException e) {
                scanReslutMixWithError.setMessage("读取配置文件失败，原因是"+e.getMessage());
                break;
            }

            // 设置工具类型
            String toolcategory = taskScanConfig.getToolcategory();
            if (null == toolcategory) {
                scanReslutMixWithError.setMessage("没有配置toolcategory");
                break;
            }
            scanReslutMixWithError.setToolcategory(toolcategory);

            // 设置下发的taskcode
            String code = taskScanConfig.getTaskcode();
            if (code == null) {
                scanReslutMixWithError.setMessage("没有配置taskcode");
                break;
            }
            scanReslutMixWithError.setTaskcode(code);

            // 设置扫描类型
            TaskScanConfig.ScanType scanType = taskScanConfig.getScanType();
            if (scanType == null) {
                scanReslutMixWithError.setMessage("没有配置scanType");
                break;
            }
            scanReslutMixWithError.setScanType(scanType);

            switch (scanType) {
                case SCAN_TYPE_SCAN: {
                    break;
                }
                case SCAN_TYPE_TEMPLATE: {
                    break;
                }
                case SCAN_TYPE_ENGINE: {
                    break;
                }
                case SCAN_TYPE_VULNERABILITIES: {
                    break;
                }
            }
            // 获取模板
            if(taskScanConfig.isAcquireTemplate()) {
                String scanTemplate[] = {"discovery", "full-audit-without-web-spider"};
                List<ScanTemplate> scanTemplates = new ArrayList<>();
                for (int i=0; i<scanTemplate.length; i++) {
                    try {
                        ScanTemplate writeScanTemplate = app.queryScanTemplate(scanTemplate[i]);
                        scanTemplates.add(writeScanTemplate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ScanTemplateResult scanTemplateResult = new ScanTemplateResult(scanTemplates.size(), scanTemplates);
                scanReslutMixWithError.setScanTemplateResult(scanTemplateResult);
                if (scanTemplates.size() == 0) {
                    scanReslutMixWithError.setMessage("获取模板个数为0");
                    break;
                }
            }

            // 获取引擎
            if (taskScanConfig.isAcquireEngine()) {
                try {
                    scanReslutMixWithError.setScanEnginResult(app.queryScanEngine(null));
                } catch (Exception e) {
                    //e.printStackTrace();
                    scanReslutMixWithError.setMessage("获取扫描引擎失败，原因是"+e.getMessage());
                    break;
                }
            }

            // 获取所有漏洞
            if (taskScanConfig.isAcquireVulnerabilities()) {
                try {
                    VulnerabilityResult vulnerabilityResult = app.queryAllVulnerabilities(null);
                    if (null != vulnerabilityResult) {
                        ScanVulnerabilityResult scanVulnerabilityResult = new ScanVulnerabilityResult(vulnerabilityResult.getPage().getTotalResources(), vulnerabilityResult.getResources());
                        scanReslutMixWithError.setScanVulnerabilityResult(scanVulnerabilityResult);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    scanReslutMixWithError.setMessage("获取漏洞信息失败，原因是"+e.getMessage());
                    break;
                }
            }

            // 判断是否启用自定义模板
            TaskScanConfig.CustomScanTemplate customScanTemplate = taskScanConfig.getCustomConfig();
            if (null != customScanTemplate) {
                // 获取模板
                try {
                    ScanTemplate template = app.queryScanTemplate(taskScanConfig.getScanTemplateId());
                    if (null == template) {
                        scanReslutMixWithError.setMessage("查询模板"+taskScanConfig.getScanTemplateId()+"失败");
                        break;
                    }

                    // 修改模板配置内容
                    app.buildCustomTemplate(template, customScanTemplate);
                    template.setId(null);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                    String templateName = "template_"+formatter.format(new Date());
                    template.setName(templateName);

                    // 创建模板
                    CreateScanTemplateResult templateResult = app.createScanTemplate(template);
                    if ((null == templateResult) || (null != templateResult.getError())) {
                        scanReslutMixWithError.setMessage("自定义扫描模板无效" + (templateResult!=null ? templateResult.getError() : " return null"));
                        break;
                    }
                    taskScanConfig.setScanTemplateId(templateResult.getId());
                } catch (Exception e) {
                    //e.printStackTrace();
                    scanReslutMixWithError.setMessage("创建自定义扫描模板失败，原因是"+e.getMessage());
                    break;
                }
            }

            if (taskScanConfig.getScanType() == TaskScanConfig.ScanType.SCAN_TYPE_SCAN) {
                // 构造任务
                try {
                    task = app.buildNewTask(taskScanConfig);
                    if (null == task) {
                        scanReslutMixWithError.setMessage("创建任务失败，原因是任务为空");
                        break;
                    }
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
                try {
                    newTaskReturn = app.startTask(task);

                    if (null == newTaskReturn) {
                        scanReslutMixWithError.setMessage("创建任务失败,返回无效任务");
                        break;
                    }
                } catch (Exception e) {
                    scanReslutMixWithError.setMessage("创建任务失败,"+e.getMessage());
                    break;
                }

                int taskId = newTaskReturn.getId();
                if (0 == taskId) {
                    scanReslutMixWithError.setMessage("创建任务失败,"+newTaskReturn.getError());
                    break;
                }

                // 设置任务ID
                scanReslutMixWithError.setTaskid(String.valueOf(taskId));

                Map<String, TaskConfig> mapIp2TaskConfig = new HashMap<>();
                app.handleCracks(taskScanConfig.getTaskconfigs(), newTaskReturn, crackResultInfos, mapIp2TaskConfig);

//                for (TaskConfig taskConfig : taskScanConfig.getTaskconfigs()) {
//                    mapIp2TaskConfig.put(taskConfig.getIp(), taskConfig);
//
//                    // 弱口令扫描
//                    CrackScanResultInfo resultInfo = new CrackScanResultInfo();
//                    resultInfo.setLatch(app.executeWeakPassword(taskConfig.getCracks(), resultInfo));
//                    //resultInfo.setPool(app.executeWeakPasswordPool(taskConfig.getCracks(), resultInfo));
//                    crackResultInfos.put(taskConfig.getIp(), resultInfo);
//
//                    /////////////////////////////////////////////
////                    NewCracks cracks = taskConfig.getCracks();
////                    // todo 测试使用，要去掉
////                    cracks.setName(new Date().toString());
////                    try {
////                        CrackScanReturn crackReturn = app.startCracks(cracks);
////                        if (null != crackReturn) {
////                            if (0 != crackReturn.getId()) {
////                                CrackScanReturn crackScanReturn = app.startCracksScan(crackReturn);
////                                if (null != crackScanReturn) {
////                                    CrackScanResult result = null;
////                                    do {
////                                        Thread.sleep(1000);
////                                        result = app.queryCracksScanResult(crackScanReturn);
////                                    } while ((result != null) && (!result.finished()));
////                                    resultInfo.setCrackScanResult(result);
////                                }
////                            }
////                        }
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//                    ///////////////////////////////////////////////
//
//                    // 登录检查
//                    if (taskConfig.getCredentials() == null) {
//                        continue;
//                    }
//
//                    CredentialReturn credentialReturn;
//                    // /api/v3/tasks/taskId/task_credentials
//                    for (Credential credential : taskConfig.getCredentials()) {
//                        String url = "/api/v3/tasks/"+taskId+"/task_credentials";
//                        try {
//                            credentialReturn = app.createCredential(newTaskReturn, credential);
//                            if (0 == credentialReturn.getId()) {
//                                System.out.println("创建认证失败,"+credentialReturn.getError());
//                                //scanReslutMixWithError.setMessage("创建认证失败,"+newTaskReturn.getError());
//                            }
//                        } catch (Exception e) {
//                            System.out.println("创建认证失败");
//                        }
//                    }
//                }

                // 创建扫描
                scan = new NewScan(task.getEngineId(), task.getScan().getAssets().getIncludedTargets().getAddresses(), task.getScanTemplateId());
                // /v3/tasks/11/scans;
                String url = "/api/v3/tasks/"+taskId+"/scans";
                try {
                    newScanReturn = app.startScan(newTaskReturn, scan);
                } catch (Exception e) {
                    scanReslutMixWithError.setMessage("创建扫描失败");
                    break;
                }
                if (0 == newScanReturn.getId()) {
                    scanReslutMixWithError.setMessage("创建扫描失败,"+newScanReturn.getError());
                    break;
                }

                // 设置扫描ID
                scanReslutMixWithError.setScanid(String.valueOf(newScanReturn.getId()));

                // 查询扫描状态
                ScanResult scanResult = null;
                try {
                    scanResult = app.queryScanResult(newScanReturn);
                } catch (Exception e) {
                    scanReslutMixWithError.setMessage("查询扫描状态失败");
                }
                while (!scanResult.finished()) {
                    if (app.isTimeout()) {
                        break;
                    }

                    try {
                        scanResult = app.queryScanResult(newScanReturn);
                    } catch (Exception e) {
                        scanReslutMixWithError.setMessage("查询扫描状态失败");
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                scanReslutMixWithError.setScanResult(scanResult);

                // 查询任务状态
                try {
                    TaskResult taskResult = app.queryTaskScanResult(newTaskReturn);
                } catch (Exception e) {
                    scanReslutMixWithError.setMessage("查询扫描状态失败");
                }

                // 查询资产
                AssetsQueryResult assetsAll = null;
                try {
                    assetsAll = app.queryAssets(taskId);
                } catch (Exception e) {
                    scanReslutMixWithError.setMessage("查询资产失败");
                    break;
                }
                if (null == assetsAll.getResources() || assetsAll.getResources().size() == 0) {
                    scanReslutMixWithError.setMessage("资产为空");
                    break;
                }

                // 获取哪些资产的扫描结果
                for (Assets assets : assetsAll.getResources()) {
                    String assetsAddr = assets.getIp();
                    for (String addr : task.getScan().getAssets().getIncludedTargets().getAddresses()) {
                        if ((assetsAddr != null) && (addr != null)) {
                            if (assetsAddr.equals(addr)) {

                                List<VulnerabilitiesMix> vulnerabilitiesMixs = new ArrayList<>();
                                AssetsQueryVulnerabilitiesResult assetsVulnerabilities = null;
                                try {
                                    assetsVulnerabilities = app.queryAssetsVulnerabilities(assets.getId());
                                    if (null != assetsVulnerabilities) {
                                        List<AssetsScanVulnerabilities> asvs = assetsVulnerabilities.getResources();
                                        if (null != asvs) {
                                            for (AssetsScanVulnerabilities asv : asvs) {
                                                if (asv != null) {
                                                    VulnerabilitiesDetail detail = null;
                                                    try {
                                                        detail = app.queryVulnerabilitiesDetail(asv.getId());
                                                    } catch (Exception e) {
                                                        // todo
                                                    }

                                                    // 漏洞详情中包含了解决方案，所以不需要再单独获取
//                                                    List<SingleSolutions> singleSolutionss = new ArrayList<>();
//                                                    try {
//                                                        VulnerabilitiesSolutions solutions = app.queryVulnerabilitiesSolutions(asv.getId());
//                                                        if (null != solutions) {
//                                                            List<String> solutionIds = solutions.getResources();
//                                                            if (null != solutionIds) {
//                                                                for (String id : solutionIds) {
//                                                                    try {
//                                                                        singleSolutionss.add(app.querySingleSolutions(id));
//                                                                    } catch (Exception e) {
//                                                                        // todo
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    } catch (Exception e) {
//                                                        // todo
//                                                    }
                                                    vulnerabilitiesMixs.add(new VulnerabilitiesMix(asv, detail, null/*singleSolutionss*/));
                                                }
                                            }
                                        }
                                    }

                                } catch (Exception e) {
                                    scanReslutMixWithError.setMessage("查询资产漏洞失败");
                                }

                                AssetsQueryPortResult assetsPort = null;
                                try {
                                    assetsPort = app.queryAssetsPorts(assets.getId());
                                } catch (Exception e) {
                                    scanReslutMixWithError.setMessage("查询资产端口失败");
                                }
                                TaskConfig tc = mapIp2TaskConfig.get(addr);
                                String subTaskId = (null != tc) ? tc.getId() : "";
                                AssetsScanResultMix mix = new AssetsScanResultMix(subTaskId, assets.getId(), assets, addr, null,
                                        //(null!=assetsVulnerabilities) ? assetsVulnerabilities.getResources() : null,
                                        (null!=vulnerabilitiesMixs) ? vulnerabilitiesMixs : null,
                                        (null!=assetsPort) ? assetsPort.getResources() : null);
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

                for (AssetsScanResultMix mix : scanReslutMix) {
                    if (mix.getAssets() != null) {
                        CrackScanResultInfo resultInfo = crackResultInfos.get(mix.getAssets().getIp());
                        if (null != resultInfo) {
                            if (resultInfo.getLatch() != null) {
                                try {
                                    if (null != resultInfo.getLatch()) {
                                        resultInfo.getLatch().await();
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
//                                ThreadPoolExecutor pool = resultInfo.getPool();
//                                while (null != pool && (!pool.isTerminated()) ) {
//                                    try {
//                                        Thread.sleep(100);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
                                mix.setCrackScanResult(resultInfo.getCrackScanResult());

                            } else {
                                System.out.println("crackscan result get latch is null");
                            }
                        } else {
                            System.out.println("crackscan result is null");
                        }
                    }
                }

                AssetsScanResultMixWithError.MixResult mixResult = new AssetsScanResultMixWithError.MixResult(scanReslutMix.size(), scanReslutMix);
                scanReslutMixWithError.setMixScanResult(mixResult);
            }

            // 更新
            if (taskScanConfig.isUpdate()) {
                try {
                    OfflineUpdateResult updateResult = app.offlineUpdate(taskScanConfig.getUpdateFile());
                    if (null==updateResult) {
                        scanReslutMixWithError.setMessage("更新文件"+taskScanConfig.getUpdateFile()+"返回空");
                    } else if (!updateResult.isSuccess()) {
                        scanReslutMixWithError.setMessage("更新文件"+taskScanConfig.getUpdateFile()+"失败，错误原因"+updateResult.getMsg()+" | " + updateResult.getError());
                    }
                    scanReslutMixWithError.setOfflineUpdateResult(updateResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            scanReslutMixWithError.setSuccess(true);

        } while (false);

        // 处理检查结果
        System.out.println("===================== ok ========================");
        app.handleResult(task, scanReslutMixWithError, taskScanConfig);
    }
}
