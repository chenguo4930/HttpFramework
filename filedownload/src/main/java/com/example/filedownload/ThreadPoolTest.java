/*
 * ThreadPoolTest    2017-03-31
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.filedownload;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-31
 */
public class ThreadPoolTest {

    static class MyRunnable implements Runnable {

        public volatile boolean flag = true;

        @Override
        public void run() {
            while (flag && !Thread.interrupted()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                System.out.println("running----------");
            }
        }
    }

    public static void main(String args[]) {

        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnable.flag = false;
        thread.interrupt();
//        runnable.flag = false;
    }
}