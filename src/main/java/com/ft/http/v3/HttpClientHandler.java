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

    private void displayBufHex(byte[] buf, boolean hex) {
        int len = buf.length;
        for (int i=0; i<len; i++) {
            if (0 == (i & 0xF )) {
                if (i != 0) {
                    System.out.println("");
                }
                System.out.printf("%08X ", i);
            }
            if (hex) {
                System.out.printf("%02X ", buf[i]);
            } else {
                System.out.printf("%c ", buf[i]);
            }
        }
        System.out.println("");
    }

    public void testDisplay() {
        displayBufHex("abcdeferetesdvfsxrtwetwetwetwgsdgsdg".getBytes(), false);
        displayBufHex("abcdeferetesdvfsxrtwetwetwetwgsdgsdg".getBytes(), true);
    }

    private void displayByteBuf(ByteBuf buf, boolean hex) {
        if (buf.hasArray()) {
            byte[] arrs = buf.array();
            int offset = buf.arrayOffset() + buf.readerIndex();
            int len = buf.readableBytes();
            System.out.println(buf.toString());
            System.out.println("len is " + len + ", offset is " + offset + "array is ");
            displayBufHex(arrs, hex);
        } else if (!buf.hasArray()) {
            int len = buf.readableBytes();
            byte[] arrs = new byte[len];
            int offset = buf.readerIndex();
            buf.getBytes(buf.readerIndex(), arrs);
            System.out.println(buf.toString());
            System.out.println("len is " + len + ", offset is " + offset + "array is ");
            displayBufHex(arrs, hex);
        }
        buf.resetReaderIndex();
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
