package com.ft.http.v3;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ft.http.v3.task.ResultCallBack;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.AsciiString;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    public HttpClient(String host, int port) {
        this.host = host;
        this.port = port;

        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        //.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        ;
    }

    public void start() throws Exception {
        connect();
    }

    private void connect() throws Exception {
        workerGroup = new NioEventLoopGroup();
        try {
            HttpClientHandler clientHandler = new HttpClientHandler(this);
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
                            //pipe.addLast(new HttpObjectAggregator(655336));
                            pipe.addLast(new HttpObjectAggregator(1024*1024*60));
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
            //workerGroup.shutdownGracefully();
        }
    }

    public boolean isActive() {
        if (null == futtureChannel) {
            return false;
        }
        Channel cn = futtureChannel.channel();
        System.out.println(" isactive isRegistered " + cn.isRegistered() + ", isopen " + cn.isOpen() + ", iswritable " + cn.isWritable() + ",isactive " + cn.isActive());
        return cn.isActive();
    }

    public void stop() {
        try {
            if (isActive()) {
                futtureChannel.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        System.out.println("future.isDone() is " + future.isDone());
                    }
                });
            } else {
                if (running) {
                    setResult(null);
                }
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void setResult(byte[] result) {
        this.running = false;
        this.result = result;
        if (null != resultCallback) {
            resultCallback.setResult(result);
        } else {
            System.out.println("resultCallback is null");
        }
    }
    public byte[] getResult() {
        return  this.result;
    }

    public HttpClient configSSL(boolean usessl) {
        this.usessl = usessl;
        return this;
    }
    public HttpClient configAuth(boolean useauth, String user, String pwd) {
        this.useauth = useauth;
        this.user = user;
        this.pwd  = pwd;
        return this;
    }

    public void getMessage(String url, Map<String, Object> mapHeader, ResultCallBack resultCallback) throws URISyntaxException {
        queryMessage(HttpMethod.GET, url, mapHeader, null, resultCallback);
    }
    public void getMessage(String url, Map<String, Object> mapParams, Map<String, Object> mapHeader, ResultCallBack resultCallback) throws URISyntaxException {
        queryMessage(HttpMethod.GET, url, mapParams, mapHeader, null, resultCallback);
    }
    public void postMessage(String url, Map<String, Object> mapHeader, byte[] body, ResultCallBack resultCallback) throws URISyntaxException {
        queryMessage(HttpMethod.POST, url, mapHeader, body, resultCallback);
    }
    public void putMessage(String url, Map<String, Object> mapHeader, byte[] body, ResultCallBack resultCallback) throws URISyntaxException {
        queryMessage(HttpMethod.PUT, url, mapHeader, body, resultCallback);
    }
    private void queryMessage(HttpMethod method, String url, Map<String, Object> mapHeader, byte[] body, ResultCallBack resultCallback) throws URISyntaxException {
        queryMessage(method, url, null, mapHeader, body, resultCallback);
    }

    private void queryMessage(HttpMethod method, String url, Map<String, Object> mapParams, Map<String, Object> mapHeader, byte[] body, ResultCallBack resultCallback) throws URISyntaxException {
        this.resultCallback = resultCallback;

        URI uri;

        String curUrl;
        if (null != mapParams) {
            StringBuilder params = new StringBuilder(128);
            int i=0;
            for (Map.Entry<String, Object> param : mapParams.entrySet()) {
                if (i++ >0) {
                    params.append("&");
                }
                params.append(param.getKey()).append("=").append(param.getValue());
            }
            curUrl = (params.length() > 0) ? (url + "?" + params.toString()) : url;
        } else {
            curUrl = url;
        }
        uri = new URI(curUrl);
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
            for (Map.Entry<String, Object> head : mapHeader.entrySet()) {
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
        Channel cn = futtureChannel.channel();
        System.out.println("isRegistered " + cn.isRegistered() + ", isopen " + cn.isOpen() + ", iswritable " + cn.isWritable() + ",isactive " + cn.isActive());

        // 发送http请求
        futtureChannel.channel().writeAndFlush(request);

        cn = futtureChannel.channel();
        System.out.println("isRegistered " + cn.isRegistered() + ", isopen " + cn.isOpen() + ", iswritable " + cn.isWritable() + ",isactive " + cn.isActive());
    }

    private String  host;
    private int     port;
    private boolean usessl = false;
    private boolean useauth = false;
    private String  user;
    private String pwd;

    private EventLoopGroup workerGroup;
    private ChannelFuture futtureChannel;

    private byte[] result = null;
    private boolean running = true;
    private ResultCallBack resultCallback;

    ObjectMapper mapper = new ObjectMapper();
}
