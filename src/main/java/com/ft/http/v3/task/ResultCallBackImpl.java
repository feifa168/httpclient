package com.ft.http.v3.task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

public class ResultCallBackImpl implements ResultCallBack {
    @Override
    public void setResult(byte[] result) {
        System.out.println("setResult latch.countDown, set result is " + result);
        this.result = result;
        latch.countDown();
    }

    @Override
    public byte[] getResult() throws InterruptedException {
        System.out.println("getResult latch.await");
        latch.await();
        System.out.println("getResult ok result is " + result);
        return result;
    }

    private byte[] result;
    private CountDownLatch latch = new CountDownLatch(1);
}
