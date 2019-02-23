package com.ft.http.v3.task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ResultCallBackImpl implements ResultCallBack {
    @Override
    public void setResult(byte[] result) {
        this.result = result;
        latch.countDown();
    }

    @Override
    public byte[] getResult() throws InterruptedException {
        latch.await();
        return result;
    }

    private byte[] result;
    private CountDownLatch latch = new CountDownLatch(1);
}
