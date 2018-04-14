package com.zpl.demo01;

import java.util.concurrent.TimeUnit;

/**
 * wait��notify��ʵ��һ��һ
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
					System.out.println("�߳�" + Thread.currentThread().getName()
							+ "��ʼ�ȴ�" + System.currentTimeMillis());
					obj.wait();
					System.out.println("�߳�" + Thread.currentThread().getName()
							+ "�ȴ�����" + System.currentTimeMillis());

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
				System.out.println("�߳�" + Thread.currentThread().getName()
						+ "��ʼ����" + System.currentTimeMillis());
				obj.notify();
				System.out.println("�߳�" + Thread.currentThread().getName()
						+ "�ȴ�����" + System.currentTimeMillis());

			}
		}

	}

}
