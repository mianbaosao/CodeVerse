package com.mianbao.subject.domain.config;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义名称的线程工厂
 *
 * @Author:bread
 * @Date: 2024-08-26 17:11
 */
public class CustomNameThreadFactory implements ThreadFactory {

    //AtomicInteger 是一个线程安全的整数类型，确保在并发环境下，线程池编号是唯一且递增的。
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    //这个线程组 (ThreadGroup) 用于组织线程。线程组允许多个线程被管理为一个整体，特别是在调试或管理线程的时候。
    private final ThreadGroup group;
    //用于给线程编号
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    //线程名称的前缀，包括线程池的编号。每个线程名称将以这个前缀开始，后面会接上线程编号
    private final String namePrefix;
    CustomNameThreadFactory(String name) {
        //通过 SecurityManager 来获取当前线程的线程组。如果没有安全管理器，则使用当前线程的线程组。
        SecurityManager s = System.getSecurityManager();
        //自定义前缀：namePrefix 是线程名称的前缀。如果 name 参数为空或仅包含空白字符，则默认使用 "pool" 作为名称前缀。
        //然后通过 poolNumber.getAndIncrement() 获取唯一的线程池编号，并构造一个前缀字符串，例如 "pool-1-thread-"。
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        if (StringUtils.isBlank(name)) {
            name = "pool";
        }
        namePrefix = name + "-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon()){
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY){
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
/**
 *  static class DefaultThreadFactory implements ThreadFactory {
 *         private static final AtomicInteger poolNumber = new AtomicInteger(1);
 *         private final ThreadGroup group;
 *         private final AtomicInteger threadNumber = new AtomicInteger(1);
 *         private final String namePrefix;
 *
 *         DefaultThreadFactory() {
 *             SecurityManager s = System.getSecurityManager();
 *             group = (s != null) ? s.getThreadGroup() :
 *                                   Thread.currentThread().getThreadGroup();
 *             namePrefix = "pool-" +
 *                           poolNumber.getAndIncrement() +
 *                          "-thread-";
 *         }
 *
 *         public Thread newThread(Runnable r) {
 *             Thread t = new Thread(group, r,
 *                                   namePrefix + threadNumber.getAndIncrement(),
 *                                   0);
 *             if (t.isDaemon())
 *                 t.setDaemon(false);
 *             if (t.getPriority() != Thread.NORM_PRIORITY)
 *                 t.setPriority(Thread.NORM_PRIORITY);
 *             return t;
 *         }
 */
}
