package com.zpl.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore�Ǽ����ź�����Semaphore����һϵ�����֤��<br>
 * ÿ��acquire����������ֱ����һ�����֤���Ի��Ȼ������һ�����֤��<br>
 * ÿ��release��������һ�����֤������ܻ��ͷ�һ��������acquire������<br>
 * Ȼ������ʵ��û��ʵ�ʵ����֤�������Semaphoreֻ��ά����һ���ɻ�����֤��������<br>
 * Semaphore�����������ƻ�ȡĳ����Դ���߳�����
 * 
 * @author zhangpengliang
 *
 */
public class SemaphoreTest {
	// ����5�����--ÿ��ֻ��������߳̽�ȥ�õ���

	public static void main(String[] args) {
		Service service = new Service();//�������
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
			}, "�߳�" + String.valueOf(i)).start();
		}
	}

	/**
	 * ����Ϊ����ʵ�������Semaphore�ź���������ÿ�η��ʵ��߳���
	 * @author zhangpengliang
	 *
	 */
	static class Service {
		// Semaphore semaphore = new Semaphore(5);
		Semaphore semaphore = new Semaphore(5, true);

		public void get() throws InterruptedException {
			try {
				for (;;) {
					//���ﳢ���õ������ź����ͱ����ͷż����ź���
					if (semaphore.tryAcquire(2)) {
						//��������ж���߳�ͬʱ����,���ֻ�Ƕ�����<���˽���>
						System.out.println(Thread.currentThread().getName() + "��ȡ��������");
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
