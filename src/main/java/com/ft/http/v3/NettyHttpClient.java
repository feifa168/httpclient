package com.ft.http.v3;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ft.http.v3.assets.*;
import com.ft.http.v3.scan.NewScan;
import com.ft.http.v3.scan.NewScanReturn;
import com.ft.http.v3.scan.ScanResult;
import com.ft.http.v3.task.NewTask;
import com.ft.http.v3.task.NewTaskReturn;
import com.ft.http.v3.task.TaskResult;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.AsciiString;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class NettyHttpClient {
    public enum TaskType {
        TASK_TYPE_UNKNOWN,
        TASK_TYPE_CREATE_TASK,          // 创建任务
        TASK_TYPE_CREATE_TASK_RETURN,   // 创建任务返回结果
        TASK_TYPE_QUERY_TASK_RESULT,       // 查询任务结果
        TASK_TYPE_CREATE_SCAN,  // 创建扫描
        TASK_TYPE_CREATE_SCAN_RETURN,  // 创建扫描返回结果
        TASK_TYPE_QUERY_SCAN_RESULT,  // 查询扫描结果
        TASK_TYPE_QUERY_ASSETS_SOFTWARE,        // 查询资产软件
        TASK_TYPE_QUERY_ASSETS_ALL,             // 查询所有资产
        TASK_TYPE_QUERY_ASSETS_SINGLE,             // 查询单个资产
        TASK_TYPE_QUERY_ASSETS_VULNERABILITIES, // 查询资产漏洞
        TASK_TYPE_QUERY_ASSETS_PORTS,           // 查询资产端口
    }
    public static class AssetsScanResult {
        public List<AssetsQueryVulnerabilitiesResult> assetsVulnerabilities = new ArrayList<>();
        public List<AssetsQueryPortResult> assetsPorts = new ArrayList<>();

        public void clear() {
            assetsPorts.clear();
            assetsVulnerabilities.clear();
        }
    }

    private String  host;
    private int     port;
    private boolean usessl = false;
    private boolean useauth = false;
    private String  user;
    private String pwd;

    private EventLoopGroup workerGroup;
    private ChannelFuture futtureChannel;

    private byte[] result;
    private TaskType        taskType    = TaskType.TASK_TYPE_UNKNOWN;
    private NewScan         scan;
    private NewScanReturn   scanReturn;
    private ScanResult      scanResult;
    private NewTask         task;
    private NewTaskReturn   taskReturn;
    private TaskResult      taskResult;
    private AssetsScanResult assetScanResult = new AssetsScanResult();
    ObjectMapper mapper = new ObjectMapper();

    private boolean finish = true;
    private CountDownLatch  latch;

    public NettyHttpClient(String host, int port) {
        this.host = host;
        this.port = port;

        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        //.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        ;
    }
    public NettyHttpClient configSSL(boolean usessl) {
        this.usessl = usessl;
        return this;
    }
    public NettyHttpClient configAuth(boolean useauth, String user, String pwd) {
        this.useauth = useauth;
        this.user = user;
        this.pwd  = pwd;
        return this;
    }

    public void connect() throws Exception {
        workerGroup = new NioEventLoopGroup();
        try {
            NettyHttpClientHandler clientHandler = new NettyHttpClientHandler(this);
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(host, port)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipe = ch.pipeline();
                        //添加sslhandler
                        if (usessl) {
                            final SslContext sslCtx = SslContextBuilder.forClient()
                                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                            //pipeline.addLast(sslCtx.newHandler(ch.alloc(), "172.16.39.251.1", 8443));
                            pipe.addLast(sslCtx.newHandler(ch.alloc()));
                        }

                        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                        pipe.addLast(new HttpResponseDecoder());
                        pipe.addLast(new HttpObjectAggregator(655336));
                        // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                        pipe.addLast(new HttpRequestEncoder());
                        //pipe.addLast(new ChunkedWriteHandler());
                        //pipe.addLast(new JsonObjectDecoder());
                        pipe.addLast(clientHandler);
                    }
                });
            futtureChannel = b.connect().sync();
        }
        finally {
//            workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        try {
            if (futtureChannel.channel().isActive()) {
                futtureChannel.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        System.out.println("future.isDone() is " + future.isDone());
                    }
                });
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    private <T> T buildResultObject(Class<?> className) throws IOException {
        T t = (T)mapper.readValue(result, className);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t));
        return t;
    }

    public void setResult(byte[] result) {
        this.result = result;

        String resultStr = null;
        try {
            resultStr = new String(result, "utf8");
            System.out.println("receive data is:" + resultStr);

            switch (taskType) {
                case TASK_TYPE_CREATE_TASK:{
                    taskReturn = buildResultObject(NewTaskReturn.class);

                    NewScan newScan = new NewScan(task.getEngineId(), task.getScan().getAssets().getIncludedTargets().getAddresses(), task.getScanTemplateId());
                    // /v3/tasks/11/scans;
                    String url = "/api/v3/tasks/"+taskReturn.getId()+"/scans";
                    createScan(newScan, url);
                    break;
                }
                case TASK_TYPE_CREATE_SCAN:{
                    scanReturn = buildResultObject(NewScanReturn.class);

                    // /api/v3/scans/18
                    String url = "/api/v3/scans/"+scanReturn.getId();
                    getScanResult(url);
                    break;
                }
                case TASK_TYPE_QUERY_SCAN_RESULT:{
                    scanResult = buildResultObject(ScanResult.class);
                    if (!scanResult.finished()) {
                        Thread.sleep(2000);
                        String url = "/api/v3/scans/" + scanReturn.getId();
                        getScanResult(url);
                    } else {
                        // /api/v3/tasks/17/scans
                        String url = "/api/v3/tasks/" + taskReturn.getId() + "/scans";
                        getTaskScanResult(url);
                    }
                    break;
                }
                case TASK_TYPE_QUERY_TASK_RESULT:{
                    taskResult = buildResultObject(TaskResult.class);
                    String url = "/api/v3/assets";
                    getAllAssets(url);
                    break;
                }
                case TASK_TYPE_QUERY_ASSETS_SOFTWARE:{
                    AssetsSoftware softs = buildResultObject(AssetsSoftware.class);
                    break;
                }
                case TASK_TYPE_QUERY_ASSETS_SINGLE: {
                    Assets item = buildResultObject(Assets.class);
                    break;
                }
                case TASK_TYPE_QUERY_ASSETS_ALL: {
                    AssetsQueryResult allAssets = buildResultObject(AssetsQueryResult.class);
                    // todo: 资产多了有可能一次查询得不到想要的资产
                    List<Assets> resources = allAssets.getResources();
                    List<String> addrs = task.getScan().getAssets().getIncludedTargets().getAddresses();
                    if (resources != null) {
                        latch = new CountDownLatch(resources.size()*2);
                    } else {
                        finish = true;
                    }
                    for (Assets item : resources) {
                        // /api/v3/assets/{assetsid}/vulnerabilities
                        // todo 可能有多个IP
                        for (String addr : addrs) {
                            if (item.getIp().equals(addr)) {
                                int assetId = item.getId();
                                // 漏洞
                                String url = "/api/v3/assets/"+assetId+"/vulnerabilities";
                                getAssetsVulnerabilities(url);

                                // 端口
                                // /api/v3/assets/{assetid}/ports
                                url = "/api/v3/assets/"+assetId+"/ports";
                                getAssetsPorts(url);
                                break;
                            }
                        }
                    }

                    break;
                }
                case TASK_TYPE_QUERY_ASSETS_VULNERABILITIES: {
                    AssetsQueryVulnerabilitiesResult assetsScanResult = buildResultObject(AssetsQueryVulnerabilitiesResult.class);
                    assetScanResult.assetsVulnerabilities.add(assetsScanResult);
                    downLatch();
                    break;
                }
                case TASK_TYPE_QUERY_ASSETS_PORTS: {
                    AssetsQueryPortResult assetsPorts = buildResultObject(AssetsQueryPortResult.class);
                    assetScanResult.assetsPorts.add(assetsPorts);
                    downLatch();
                    break;
                }
                default:{
                    break;
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void getMessage(String url, Map<String, String> mapHeader) throws URISyntaxException {
//        URI uri;
//        //uri = new URI("http://"+host+":"+port);
//        //uri = new URI("/api/compile.php");
//        uri = new URI(url);
//        DefaultFullHttpRequest request = new DefaultFullHttpRequest(
//                HttpVersion.HTTP_1_1
//                , HttpMethod.GET
//                , uri.toASCIIString()
//        );
//        // 构建http请求
//        HttpHeaders headers = request.headers();
//        headers.set(HttpHeaderNames.HOST, host)
////                .set(HttpHeaderNames.ACCEPT, "*/*")
////                .set(HttpHeaderNames.CONNECTION, "keep-alive")
////                .set(HttpHeaderNames.ACCEPT_ENCODING, "gzip, deflate")
//        ;
//
//        // 外部配置的请求头
//        if (mapHeader != null) {
//            for (Map.Entry<String, String> head : mapHeader.entrySet()) {
//                headers.set(head.getKey(), head.getValue());
//            }
//        }
//
//        if (useauth) {
//            String auth = "Basic "+ new String(Base64.getEncoder().encode((user+":"+pwd).getBytes()));
//            //headers.set(HttpHeaderNames.AUTHORIZATION, "Basic YWRtaW46YWRtaW5AMTIz");
//            headers.set(HttpHeaderNames.AUTHORIZATION, auth);
//        }
//
////            request.headers().set(HttpHeaderNames.CONNECTION,
////                    HttpHeaderNames.CONNECTION);
////            request.headers().set(HttpHeaderNames.CONTENT_LENGTH,
////                    request.content().readableBytes());
////            request.headers().set("messageType", "normal");
////            request.headers().set("businessType", "testServerState");
//        // 发送http请求
//        futtureChannel.channel().writeAndFlush(request);
////            futtureChannel.channel().write(request);
////            futtureChannel.channel().flush();
//        //futtureChannel.channel().closeFuture().sync();
//    }

    public void setTaskType(TaskType type) {
        this.taskType = type;
    }

    public void createTask(NewTask newTask, String url) throws Exception {
        this.task = newTask;
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newTask));

        assetScanResult.clear();
        finish = false;

        byte[] postBody = mapper.writeValueAsBytes(newTask);

//        Map<AsciiString, String> mapHead = new HashMap<>();
//        mapHead.put(HttpHeaderNames.CONTENT_TYPE, "application/json");

//        if (usessl) {
//            configSSL(true);
//        }
//
//        if (useauth) {
//            configAuth(true, "admin", "admin@123");
//        }

        //connect();
        //setTaskType(TaskType.TASK_TYPE_CREATE_TASK);
        //postMessage(url, mapHead, postBody);
        postMessage(url, postBody, TaskType.TASK_TYPE_CREATE_TASK);
        //stop();
    }
    public void createScan(NewScan newScan, String url) throws Exception {
        byte[] postBody = mapper.writeValueAsBytes(newScan);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newScan));

//        Map<AsciiString, String> mapHead = new HashMap<>();
//        mapHead.put(HttpHeaderNames.CONTENT_TYPE, "application/json");

        //connect();
//        setTaskType(TaskType.TASK_TYPE_CREATE_SCAN);
//        postMessage(url, mapHead, postBody);
        postMessage(url, postBody, TaskType.TASK_TYPE_CREATE_SCAN);
        //stop();
    }
    public void getScanResult(String url) throws Exception {
//        Map<AsciiString, String> mapHead = new HashMap<>();
//        mapHead.put(HttpHeaderNames.CONTENT_TYPE, "application/json");
//
//        //connect();
//        setTaskType(TaskType.TASK_TYPE_QUERY_SCAN_RESULT);
//        getMessage(url, mapHead);
        //stop();

        getMessage(url, TaskType.TASK_TYPE_QUERY_SCAN_RESULT);
    }
    public void getTaskScanResult(String url) throws Exception {
//        Map<AsciiString, String> mapHead = new HashMap<>();
//        mapHead.put(HttpHeaderNames.CONTENT_TYPE, "application/json");
//
//        //connect();
//        setTaskType(TaskType.TASK_TYPE_QUERY_TASK_RESULT);
//        getMessage(url, mapHead);
        //stop();
        //latch.countDown();

        getMessage(url, TaskType.TASK_TYPE_QUERY_TASK_RESULT);
    }
    public void getAllAssets(String url) throws Exception {
        getMessage(url, TaskType.TASK_TYPE_QUERY_ASSETS_ALL);
    }
    public void getAssetsVulnerabilities(String url) throws Exception {
//        Map<AsciiString, String> mapHead = new HashMap<>();
//        mapHead.put(HttpHeaderNames.CONTENT_TYPE, "application/json");
//
//        //connect();
//        setTaskType(TaskType.TASK_TYPE_QUERY_ASSETS_VULNERABILITIES);
//        getMessage(url, mapHead);
        //stop();
        getMessage(url, TaskType.TASK_TYPE_QUERY_ASSETS_VULNERABILITIES);
    }
    public void getAssetsPorts(String url) throws Exception {
//        Map<AsciiString, String> mapHead = new HashMap<>();
//        mapHead.put(HttpHeaderNames.CONTENT_TYPE, "application/json");
//
//        //connect();
//        setTaskType(TaskType.TASK_TYPE_QUERY_ASSETS_VULNERABILITIES);
//        getMessage(url, mapHead);
        //stop();
        getMessage(url, TaskType.TASK_TYPE_QUERY_ASSETS_VULNERABILITIES);
    }

    public void getMessage(String url, TaskType type) throws Exception {
        Map<AsciiString, String> mapHeader = new HashMap<>();
        mapHeader.put(HttpHeaderNames.CONTENT_TYPE, "application/json");

        //connect();
        setTaskType(type);
        queryMessage(HttpMethod.GET, url, mapHeader, null);
        //stop();
    }
    public void postMessage(String url, byte[] body, TaskType type) throws Exception {
        Map<AsciiString, String> mapHeader = new HashMap<>();
        mapHeader.put(HttpHeaderNames.CONTENT_TYPE, "application/json");

        //connect();
        setTaskType(type);
        queryMessage(HttpMethod.POST, url, mapHeader, body);
        //stop();
    }

    public void getMessage(String url, Map<AsciiString, String> mapHeader) throws URISyntaxException {
        queryMessage(HttpMethod.GET, url, mapHeader, null);
    }
    public void postMessage(String url, Map<AsciiString, String> mapHeader, byte[] body) throws URISyntaxException {
        queryMessage(HttpMethod.POST, url, mapHeader, body);
    }

    private void queryMessage(HttpMethod method, String url, Map<AsciiString, String> mapHeader, byte[] body) throws URISyntaxException {
        URI uri;
        uri = new URI(url);
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1
                , method
                , uri.toASCIIString()
        );
        System.out.println(method + ", url " + url);
        // 构建http请求
        HttpHeaders headers = request.headers();
        headers.set(HttpHeaderNames.HOST, host)
                .set(HttpHeaderNames.ACCEPT, "*/*")
                .set(HttpHeaderNames.CONNECTION, "keep-alive")
                .set(HttpHeaderNames.ACCEPT_ENCODING, "gzip, deflate")
                //.set(HttpHeaderNames.CONTENT_TYPE, "application/json")
        ;
        if (body != null) {
            headers.set(HttpHeaderNames.CONTENT_LENGTH, body.length);
        }

        // 外部配置的请求头
        if (mapHeader != null) {
            for (Map.Entry<AsciiString, String> head : mapHeader.entrySet()) {
                headers.set(head.getKey(), head.getValue());
            }
        }

        if (useauth) {
            String auth = "Basic "+ new String(Base64.getEncoder().encode((user+":"+pwd).getBytes()));
            //headers.set(HttpHeaderNames.AUTHORIZATION, "Basic YWRtaW46YWRtaW5AMTIz");
            headers.set(HttpHeaderNames.AUTHORIZATION, auth);
        }

        //添加请求正文
        //request.content().writeBytes(Unpooled.wrappedBuffer(requestContent.getBytes(CharsetUtil.UTF_8)));
        if ((body != null) && body.length>0) {
            request.content().writeBytes(Unpooled.wrappedBuffer(body));
        }

        // 发送http请求
        futtureChannel.channel().writeAndFlush(request);
    }

    private void downLatch() {
        latch.countDown();
        if (latch.getCount() == 0) {
            finish = true;
        }
    }
    public void waitCheckResult() throws InterruptedException {
        if (!finish) {
            //latch.await();
        }
    }
}
