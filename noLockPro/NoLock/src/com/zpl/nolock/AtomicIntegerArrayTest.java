package com.zpl.nolock;

import java.util.concurrent.atomic.AtomicIntegerArray;
/**
 * ��AtomicIntegerArray���Ƶ��У�AtomicLongArray,AtomicReferenceArray��������ص�ԭ������
 * @author zhangpengliang
 *
 */
public class AtomicIntegerArrayTest {
	private static AtomicIntegerArray arr = new AtomicIntegerArray(10);// ��ʼ������

	public static class ThreadA implements Runnable {

		@Override
		public void run() {
			// �����������ֵ
			for (int k = 0; k < 1000; k++) {
				// ��������������������һ����,���Ƿ��ص�ֵ�ǲ�һ����,incrementAndGet���ȼ�1Ȼ�󷵻�+1��Ľ��
				// arr.incrementAndGet(k%arr.length());
				arr.getAndIncrement(k % arr.length());// ���1����+1����ǰ��ֵ
			}
			System.out.println(arr);

		}

	}

	public static void main(String[] args) throws InterruptedException {
		Thread A = new Thread(new ThreadA());
		A.start();
		A.join();// ��A�߳�ִ�����,�ſ���ִ�к���Ĵ���
		int a = arr.get(5);// ��ȡ
		System.out.println(a);
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						/*
						 * compareAndSet��������ԭ�Ӳ�����
						 * AtomicIntegerArray������AtomicInteger���һ��.<br>
						 * ֻ�Ƕ��˸�����
						 */
						if (arr.compareAndSet(5, a, 9)) {
							System.out.println(
									"�߳�" + Thread.currentThread().getName()
											+ "�޸ĳɹ�=" + arr.get(5));
						} else {
							System.out.println(
									"�߳�" + Thread.currentThread().getName()
											+ "�޸�ʧ��");
							break;
						}
					}
				}
			}, String.valueOf(i)).start();
			;
		}

	}
}
