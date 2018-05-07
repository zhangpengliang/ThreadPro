package com.zpl.threadPool;

import java.util.concurrent.Executor;
/**
 * 这里我们来看下线程池的实现方式
 * @author zhangpengliang
 *
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Pool {

	/**
	 * 创建一个固定大小的线程池<br>
	 * return new ThreadPoolExecutor(nThreads, nThreads, 0L,
	 * TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	 * 
	 * 这里的核心大小和最大线程数都是我们设定的这个值,用的阻塞线程是：链表结构的阻塞线程，无界
	 */
	ExecutorService fixed = Executors.newFixedThreadPool(5);
	/**
	 * 这里我们用了一个自带的默认的线程工厂。
	 */
	ExecutorService fixed2=Executors.newFixedThreadPool(5, Executors.defaultThreadFactory());

	/**
	 * 这里和fixed差不多，只不过是5改成了1，其他的都一样
	 */
	ExecutorService single=Executors.newSingleThreadExecutor();
	
	
}
