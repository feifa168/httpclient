package com.ft.http.v3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;

public class HttpClientHandler extends ChannelInboundHandlerAdapter {
    private ByteBufToBytes reader;
    private String contentType;
    private HttpClient client;

    public HttpClientHandler(HttpClient client) {
        this.client = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("remoteAddress() is " + ctx.channel().remoteAddress() + " localAddress is" + ctx.channel().localAddress());
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("HttpResponse Header: "+ response.headers());
            //contentType = response.headers().get(HttpHeaderNames.CONTENT_TYPE);
            //System.out.println("CONTENT_TYPE:" + contentType);

            if (HttpUtil.isContentLengthSet(response)) {
                reader = new ByteBufToBytes((int) HttpUtil.getContentLength(response));
            } else {
                reader = new ByteBufToBytes(40960);
            }
        }
        boolean finish = false;
        if (msg instanceof HttpContent) {
            do {
                HttpContent httpContent = (HttpContent) msg;
                ByteBuf content = httpContent.content();
                if (!content.isReadable()) {
                    reader.stop();
                    finish = true;
                    break;
                }
                //String s = Arrays.toString(content.array());
                //displayByteBuf(content, true);
                //System.out.println(content.toString());

                reader.reading(content);
                content.release();
                if (reader.isEnd()) {
                    System.out.println("finish:");
                    finish = true;
                    break;
                }
            } while(false);

            if (finish) {
                System.out.println(reader.toString());

                byte[] result = reader.readFull();
                client.setResult(result);

//                String resultStr = new String(reader.readFull(), "utf8");
//                String s = new String(resultStr.getBytes("utf-8"), "utf8");
////                ObjectMapper mapper = new ObjectMapper();
////                String s2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(s);
//                System.out.println("Server said:" + s);
                ctx.close();
            }
        }
        System.out.println("...");
    }
}
