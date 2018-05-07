package com.zpl.demo01;

import java.util.concurrent.TimeUnit;

/**
 * wait和notify的实现一对一
 * 
 * @author zhangpengliang
 *
 */
public class Test02 {

	public static void main(String[] args) {
		Object obj = new Object();
		Threadnotify threadnotify = new Threadnotify(obj);
		Threadwait threadwait = new Threadwait(obj);
		threadnotify.setName("B");
		threadwait.setName("A");
		threadwait.start();
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadnotify.start();
	}

	static class Threadwait extends Thread {
		private Object obj;

		public Threadwait(Object obj) {
			super();
			this.obj = obj;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			synchronized (obj) {
				try {
					System.out.println("线程" + Thread.currentThread().getName()
							+ "开始等待" + System.currentTimeMillis());
					obj.wait();
					System.out.println("线程" + Thread.currentThread().getName()
							+ "等待结束" + System.currentTimeMillis());

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	static class Threadnotify extends Thread {

		private Object obj;

		public Threadnotify(Object obj) {
			super();
			this.obj = obj;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			synchronized (obj) {
				System.out.println("线程" + Thread.currentThread().getName()
						+ "开始唤醒" + System.currentTimeMillis());
				obj.notify();
				System.out.println("线程" + Thread.currentThread().getName()
						+ "等待结束" + System.currentTimeMillis());

			}
		}

	}

}
