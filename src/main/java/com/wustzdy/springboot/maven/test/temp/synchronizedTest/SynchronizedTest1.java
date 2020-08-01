package com.wustzdy.springboot.maven.test.temp.synchronizedTest;

class Test1Synchronized implements Runnable {
    private int count = 0;

    @Override
    public synchronized void run() {////对当前对象加锁
        for (int i = 0; i < 10; i++) {
            System.out.println("当前线程" + Thread.currentThread().getName() + "---" + count++);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

/**https://www.bbsmax.com/A/Gkz1aLkqzR/
 *  同步函数加锁
 * 两个线程同时访问同一个对象的methoA和methodB则会发生竞争，必须等待其中一个执行完成后另一个才会执行。
 * */
public class SynchronizedTest1 {
    public static void main(String[] args) {
        Test1Synchronized test1Synchronized = new Test1Synchronized();
        Thread thread1 = new Thread(test1Synchronized);
        Thread thread2 = new Thread(test1Synchronized);
        thread1.start();
        thread2.start();
    }
}
/***
n
 */