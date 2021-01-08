package com.bamboo.leaf.core.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程名称工厂
 * @author zhuzhi
 * @date 2020/11/19
 */
public class NamedThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    /**
     * 线程号
     */
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String namePrefix;
    
    private final boolean daemon;

    public NamedThreadFactory(String namePrefix, boolean daemon) {
        this.daemon = daemon;
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
    }

    public NamedThreadFactory(String namePrefix) {
        this(namePrefix, false);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + "-thread-" + threadNumber.getAndIncrement(), 0);
        t.setDaemon(daemon);
        return t;
    }

}
