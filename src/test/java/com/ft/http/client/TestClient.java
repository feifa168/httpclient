package com.ft.http.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ft.http.v3.NettyHttpClient;
import com.ft.http.v3.task.NewTask;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.util.AsciiString;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

public class TestClient {
    public void test(String host, int port, String url) {
        test(host, port, url, false, false);
    }
    public void test(String host, int port, String url, boolean usessl, boolean useauth) {
        testGet(host, port, url, usessl, usessl, null);
    }

    public void testGet(String host, int port, String url, boolean usessl, boolean useauth, Map<AsciiString, String> mapHead) {
        try {
            NettyHttpClient client = new NettyHttpClient(host, port);

            if (usessl) {
                client.configSSL(true);
            }

            if (useauth) {
                client.configAuth(true, "admin", "admin@123");
            }

            client.connect();
            client.getMessage(url, mapHead);
            client.stop();
            //Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testPost(String host, int port, String url, boolean usessl, boolean useauth, Map<AsciiString, String> mapHead, byte[] body) {
        try {
            NettyHttpClient client = new NettyHttpClient(host, port);

            if (usessl) {
                client.configSSL(true);
            }

            if (useauth) {
                client.configAuth(true, "admin", "admin@123");
            }

            client.connect();
            client.postMessage(url, mapHead, body);
            Thread.sleep(5000);
            client.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testImportNew() {
        test("www.importnew.com", 80, "/31318.html");
    }
    @Test
    public void testRunoob() {
        test("www.runoob.com", 80, "/try/runcode.php?filename=HelloWorld2&type=java");
    }
    @Test
    public void testRunoobJson() {
        test("www.runoob.com", 80, "/api/compile.php");
    }
    @Test
    public void testLocal() {
        test("127.0.0.1", 8000, "/");
    }
    @Test
    public void testdianli() {
        testGet("10.0.92.95", 443, "/api/v3/tasks", true, true, null);
    }

    @Test
    public void testRunoobJsonPost() throws JsonProcessingException {
        byte[] body = buildPost();
        Map<AsciiString, String> mapHead = new HashMap<>();
        mapHead.put(HttpHeaderNames.ACCEPT, "*/*");
        mapHead.put(HttpHeaderNames.CONNECTION, "keep-alive");
        mapHead.put(HttpHeaderNames.ACCEPT_ENCODING, "gzip, deflate");
        mapHead.put(HttpHeaderNames.CONTENT_TYPE, "application/json");
        mapHead.put(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(body.length));
        testPost("www.runoob.com", 80, "/api/compile.php", false, false, mapHead, body);
    }

    @Test
    public void testPost() throws JsonProcessingException {
        byte[] body = buildPost();
        Map<AsciiString, String> mapHead = new HashMap<>();
        mapHead.put(HttpHeaderNames.ACCEPT, "*/*");
        mapHead.put(HttpHeaderNames.CONNECTION, "keep-alive");
        mapHead.put(HttpHeaderNames.ACCEPT_ENCODING, "gzip, deflate");
        mapHead.put(HttpHeaderNames.CONTENT_TYPE, "application/json");
        mapHead.put(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(body.length));

        testPost("10.0.92.95", 443, "/api/v3/tasks", true, true, mapHead, body);
    }
    @Test
    public void testCreateScan() throws JsonProcessingException {
        byte[] body = ("{\n" +
                "  \"engineId\": 3,\n" +
                "  \"hosts\": [\n" +
                "    \"172.16.39.152\"\n" +
                "  ],\n" +
                "  \"templateId\": \"full-audit-without-web-spider\"\n" +
                "}").getBytes();
        Map<AsciiString, String> mapHead = new HashMap<>();
        mapHead.put(HttpHeaderNames.ACCEPT, "*/*");
        mapHead.put(HttpHeaderNames.CONNECTION, "keep-alive");
        mapHead.put(HttpHeaderNames.ACCEPT_ENCODING, "gzip, deflate");
        mapHead.put(HttpHeaderNames.CONTENT_TYPE, "application/json");
        mapHead.put(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(body.length));

        testPost("10.0.92.95", 443, "/api/v3/tasks/11/scans", true, true, mapHead, body);
    }

    @Test
    public void testScanStatus() {
        Map<AsciiString, String> mapHead = new HashMap<>();
        mapHead.put(HttpHeaderNames.ACCEPT, "*/*");
        mapHead.put(HttpHeaderNames.CONNECTION, "keep-alive");
        mapHead.put(HttpHeaderNames.ACCEPT_ENCODING, "gzip, deflate");
        mapHead.put(HttpHeaderNames.CONTENT_TYPE, "application/json");
        testGet("10.0.92.95", 443, "/api/v3/scans/17", true, true, mapHead);
    }

    private byte[] buildPost() throws JsonProcessingException {
        List<String> incAddrs = new ArrayList<>();
        incAddrs.add("172.16.39.152");
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

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        //.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        ;
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nt);
        System.out.println(jsonString);

        return jsonString.getBytes();
    }
}
