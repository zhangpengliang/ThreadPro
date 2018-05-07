package com.zpl.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore是计数信号量。Semaphore管理一系列许可证。<br>
 * 每个acquire方法阻塞，直到有一个许可证可以获得然后拿走一个许可证；<br>
 * 每个release方法增加一个许可证，这可能会释放一个阻塞的acquire方法。<br>
 * 然而，其实并没有实际的许可证这个对象，Semaphore只是维持了一个可获得许可证的数量。<br>
 * Semaphore经常用于限制获取某种资源的线程数量
 * 
 * @author zhangpengliang
 *
 */
public class SemaphoreTest {
	// 创建5个许可--每次只能有五个线程进去拿到锁

	public static void main(String[] args) {
		Service service = new Service();//共享变量
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						service.get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, "线程" + String.valueOf(i)).start();
		}
	}

	/**
	 * 设置为共享实例。添加Semaphore信号量来控制每次访问的线程数
	 * @author zhangpengliang
	 *
	 */
	static class Service {
		// Semaphore semaphore = new Semaphore(5);
		Semaphore semaphore = new Semaphore(5, true);

		public void get() throws InterruptedException {
			try {
				for (;;) {
					//这里尝试拿到几个信号量就必须释放几个信号量
					if (semaphore.tryAcquire(2)) {
						//这里如果有多个线程同时进来,最好只是读操作<个人建议>
						System.out.println(Thread.currentThread().getName() + "获取共享数据");
						TimeUnit.SECONDS.sleep(5);
						semaphore.release(2);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
