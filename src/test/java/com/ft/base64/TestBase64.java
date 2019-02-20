package com.ft.base64;

import org.junit.Test;

import java.util.Base64;

public class TestBase64 {
    @Test
    public void test13423() {
        //headers.set(HttpHeaderNames.AUTHORIZATION, "Basic YWRtaW46YWRtaW5AMTIz");
        String s = "Basic " + new String(Base64.getEncoder().encode("admin:admin@123".getBytes()));
        System.out.println(s);
    }
}
