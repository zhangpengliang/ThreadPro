package com.zpl.nolock;

import java.util.concurrent.atomic.AtomicIntegerArray;
/**
 * 与AtomicIntegerArray类似的有：AtomicLongArray,AtomicReferenceArray等数组相关的原子类型
 * @author zhangpengliang
 *
 */
public class AtomicIntegerArrayTest {
	private static AtomicIntegerArray arr = new AtomicIntegerArray(10);// 初始化长度

	public static class ThreadA implements Runnable {

		@Override
		public void run() {
			// 对数组进行设值
			for (int k = 0; k < 1000; k++) {
				// 下面的两个操作结果都是一样的,但是返回的值是不一样的,incrementAndGet是先加1然后返回+1后的结果
				// arr.incrementAndGet(k%arr.length());
				arr.getAndIncrement(k % arr.length());// 后加1返回+1操作前的值
			}
			System.out.println(arr);

		}

	}

	public static void main(String[] args) throws InterruptedException {
		Thread A = new Thread(new ThreadA());
		A.start();
		A.join();// 等A线程执行完后,才可以执行后面的代码
		int a = arr.get(5);// 获取
		System.out.println(a);
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						/*
						 * compareAndSet是无锁的原子操作。
						 * AtomicIntegerArray操作和AtomicInteger差不多一样.<br>
						 * 只是多了个坐标
						 */
						if (arr.compareAndSet(5, a, 9)) {
							System.out.println(
									"线程" + Thread.currentThread().getName()
											+ "修改成功=" + arr.get(5));
						} else {
							System.out.println(
									"线程" + Thread.currentThread().getName()
											+ "修改失败");
							break;
						}
					}
				}
			}, String.valueOf(i)).start();
			;
		}

	}
}
