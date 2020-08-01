package com.wustzdy.springboot.maven.test.temp.synchronizedTest;

public class StaticSynchronizedTest {
    public static synchronized void test1() {
        for (int i = 5; i > 0; i--) {
            System.out.println("当前线程"+Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test2() {
        synchronized (this.getClass()) {
            for (int i = 5; i > 0; i--) {
                System.out.println("当前线程"+Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        StaticSynchronizedTest staticSynchronizedTest = new StaticSynchronizedTest();
        Thread test1 = new Thread(new Runnable() {
            public void run() {
                StaticSynchronizedTest.test1();
            }
        }, "test1");
        test1.start();
        Thread test2 = new Thread(new Runnable() {
            public void run() {
                staticSynchronizedTest.test2();
            }
        }, "test2");
        test2.start();
    }
}
