package com.wustzdy.springboot.maven.test.temp.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadParameter {
    //参数初始化
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数量大小
    private static final int corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    //线程池最大容纳线程数
    private static final int maximumPoolSize = CPU_COUNT * 2 + 1;
    //线程空闲后的存活时长
    private static final int keepAliveTime = 30;

    //任务过多后，存储任务的一个阻塞队列
    BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();

    //线程的创建工厂
    ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AdvacnedAsyncTask #" + mCount.getAndIncrement());
        }
    };

//    //线程池任务满载后采取的任务拒绝策略
//    RejectedExecutionHandler rejectHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
//
//    //线程池对象，创建线程
//    ThreadPoolExecutor mExecute = new ThreadPoolExecutor(
//            corePoolSize, //线程池的核心线程数
//            maximumPoolSize,//线程池所能容纳的最大线程数
//            keepAliveTime,//线程的空闲时间
//            TimeUnit.SECONDS,//keepAliveTime对应的单位
//            workQueue,//线程池中的任务队列
//            threadFactory, //线程工厂
//            rejectHandler//当任务无法被执行时的拒绝策略
//}

}

