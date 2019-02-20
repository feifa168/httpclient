package com.ft.http.v3;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Map;

public class NettyHttpClient {
    private String  host;
    private int     port;
    private boolean usessl = false;
    private boolean useauth = false;
    private String  user;
    private String pwd;

    private EventLoopGroup workerGroup;
    private ChannelFuture futtureChannel;

    //public NettyHttpClient() {}
    public NettyHttpClient(String host, int port) {
        this.host = host;
        this.port = port;
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
                        // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                        pipe.addLast(new HttpRequestEncoder());
                        //pipe.addLast(new JsonObjectDecoder());
                        pipe.addLast(new NettyHttpClientHandler());
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
            futtureChannel.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
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
        // 构建http请求
        HttpHeaders headers = request.headers();
        headers.set(HttpHeaderNames.HOST, host)
//                .set(HttpHeaderNames.ACCEPT, "*/*")
//                .set(HttpHeaderNames.CONNECTION, "keep-alive")
//                .set(HttpHeaderNames.ACCEPT_ENCODING, "gzip, deflate")
//                .set(HttpHeaderNames.CONTENT_LENGTH, body.length)
        ;

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
}
