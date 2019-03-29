package com.ft.http.v3.task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

public class ResultCallBackImpl implements ResultCallBack {
    private static int setNum = 0;
    private static int getNum = 0;
    @Override
    public void setResult(byte[] result) {
        System.out.println("setResult latch.countDown, set result is " + result);
        this.result = result;
        valid = true;
        setNum++;
        System.out.println("###### get number is " + getNum + ", set number is " + setNum);
        latch.countDown();
    }

    @Override
    public byte[] getResult() throws InterruptedException {
        System.out.println("getResult latch.await");
        //if (valid)
        {
            getNum++;
            System.out.println("###### get number is " + getNum + ", set number is " + setNum);
            latch.await();
        }
        System.out.println("getResult ok result is " + result);
        return result;
    }

    private boolean valid = false;
    private byte[] result;
    private CountDownLatch latch = new CountDownLatch(1);
}
