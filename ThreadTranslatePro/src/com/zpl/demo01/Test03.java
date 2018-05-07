package com.zpl.demo01;

import java.util.concurrent.TimeUnit;

/**
 * ��wait()֮�����interrupt()�����ᱨ�쳣
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
		a.interrupt();// ִ���жϲ�����ͬʱ��Ҳ��

	}

	static class ThreadA extends Thread {
		@Override
		public synchronized void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				System.out.println("��ʼwait....");
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
	}

}
