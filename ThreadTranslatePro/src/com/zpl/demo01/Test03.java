package com.zpl.demo01;

import java.util.concurrent.TimeUnit;

/**
 * 当wait()之后调用interrupt()方法会报异常
 * 
 * @author zhangpengliang
 *
 */
public class Test03 {

	public static void main(String[] args) {
		ThreadA a = new ThreadA();
		a.start();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a.interrupt();// 执行中断操作。同时呢也就

	}

	static class ThreadA extends Thread {
		@Override
		public synchronized void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				System.out.println("开始wait....");
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
	}

}
