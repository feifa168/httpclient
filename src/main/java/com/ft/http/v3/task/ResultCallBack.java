package com.ft.http.v3.task;

public interface ResultCallBack {
    void    setResult(byte[] result);
    byte[]  getResult() throws InterruptedException;
}
