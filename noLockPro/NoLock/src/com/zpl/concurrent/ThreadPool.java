package com.zpl.concurrent;

import java.util.List;
import java.util.Vector;

/**
 * 构建一个简单的线程池
 * 
 * @author zhangpengliang
 *
 */
public class ThreadPool {
	private static ThreadPool instance = null;
	// 空的线程队列
	private List<Worker> idleThreads;
	// 已有的线程总数
	private int threadCounter = 0;
	// 是否关闭线程池
	private boolean isShutdown = false;

	public ThreadPool() {
		this.idleThreads = new Vector<Worker>(5);
		this.threadCounter = 0;
	}

	public synchronized static ThreadPool getInstance() {
		if (instance == null) {
			instance = new ThreadPool();
		}
		return instance;
	}

	protected synchronized void repool(Worker worker) {
		if (!isShutdown) {
			// 线程池没有关闭,就放到线程池中
			idleThreads.add(worker);
		} else {
			// 如果线程池关闭了,那么这个线程也需要关闭
			worker.shutdown();
		}

	}

	protected synchronized void shutdown() {
		// 关闭线程池
		isShutdown = true;
		// 调用所有线程的关闭工作
		for (Worker worker : idleThreads) {
			worker.shutdown();
		}
	}

	public synchronized void start(Runnable target) {
		Worker worker = null;
		if (idleThreads.size() > 0) {
			// 说明有空闲的线程
			int size = idleThreads.size();
			worker = idleThreads.get(size - 1);// 获取一个worker
			idleThreads.remove(size - 1);// 从线程池中移出
			worker.setTarget(target);
		} else {
			// 说明没有空闲的线程
				threadCounter++;
				worker = new Worker(target, "新建线程" + String.valueOf(threadCounter), this);
				idleThreads.add(worker);
				worker.start();
		}
	}
}
