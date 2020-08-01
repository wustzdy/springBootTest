package com.wustzdy.springboot.maven.test.temp.synchronizedTest;

class Test2Synchronized implements Runnable {
    private int count = 0;

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println("当前线程" + Thread.currentThread().getName() + "--" + count++);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

/**
 *  同步代码块加锁
 * 两个线程同时访问同一个对象的methoA和methodB则会发生竞争，必须等待其中一个执行完成后另一个才会执行。
 * */
public class SynchronizedTest2 {
    public static void main(String[] args) {
        Test2Synchronized testSynchronized = new Test2Synchronized();
        Thread thread1 = new Thread(testSynchronized);
        Thread thread2 = new Thread(testSynchronized);
        thread1.start();
        thread2.start();
    }
}


