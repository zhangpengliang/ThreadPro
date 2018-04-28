package com.zpl.concurrent;

/**
 * 工作线程
 * 
 * @author zhangpengliang
 *
 */
public class Worker extends Thread {

	private boolean isIdle = false;// 是否空闲

	private Runnable target;// 执行的任务

	private ThreadPool threadpool;

	private boolean isShutdown = false;// 是否关闭线程

	public Worker(Runnable target, String name, ThreadPool threadpool) {
		super(name);
		this.target = target;
		this.threadpool = threadpool;
	}
	

	public Worker(ThreadPool threadpool,String name) {
		super(name);
		this.threadpool = threadpool;
	}


	public Runnable getTarget() {
		return target;
	}


	public synchronized void setTarget(Runnable target) {
		this.target = target;
		this.notifyAll();//唤醒
	}


	@Override
	public void run() {
		super.run();
		while (!isShutdown) {
			isIdle = false;
			if (target != null) {
				System.out.println(Thread.currentThread().getName()+"开始工作");
				target.run();
			}
			// 结束任务
			isIdle = true;
			// 只要没有关闭就一直执行
			try {
				synchronized (this) {
					if (isIdle) {
						// 先放进去到线程池
						threadpool.repool(this);
						//
						this.wait();
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void shutdown(){
			this.isShutdown=true;
			this.notifyAll();
	}

}
