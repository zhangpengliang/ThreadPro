package com.zpl.demo01;

/**
 * 多消费者-多生产者==操作值
 * 
 * @author zhangpengliang
 *
 */
public class MoreCustomAndMoreProductorTest {

	public static void main(String[] args) {
		String lock = new String("");
		P p = new P(lock);
		C c = new C(lock);
		for (int i = 0; i < 10; i++) {// 10个生产线程
			new Thread(new Runnable() {
				public void run() {
					p.setValue();
				}
			}, String.valueOf(i)).start();
		}

		for (int i = 10; i < 20; i++) {// 是个消费线程
			new Thread(new Runnable() {
				public void run() {
					c.getValue();
				}
			}, String.valueOf(i)).start();
		}
	}

	static class ObjectValue {
		public static String value = "";
	}

	static class C {
		private String lock;

		public C(String lock) {
			super();
			this.lock = lock;
		}

		public void getValue() {
			synchronized (lock) {
				// 当值为空的时候,作为消费者我就需要等生产者生产
				while (ObjectValue.value.equals("")) {
					System.out.println("消费者" + Thread.currentThread().getName()
							+ "Watting 了☆");
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 当不为空的时候,我们就可以直接消费
				System.out.println("消费者" + Thread.currentThread().getName()
						+ " Runnable 了");
				ObjectValue.value = "";
				// 消费完之后我们就需要通知所有的生产者生产
				lock.notifyAll();
			}
		}
	}

	/**
	 * 生产者
	 * 
	 * @author zhangpengliang
	 *
	 */
	static class P {
		private String lock;

		public P(String lock) {
			super();
			this.lock = lock;
		}

		public void setValue() {
			synchronized (lock) {
				// 当值不为空的时候,我们就不需要生产
				while (!ObjectValue.value.equals("")) {
					System.out.println("生产者" + Thread.currentThread().getName()
							+ "Watting 了★");
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 当值为空的时候我们就需要生产 了
				System.out.println("生产者" + Thread.currentThread().getName()
						+ "Runnable 了");
				String value = System.currentTimeMillis() + "_"
						+ System.nanoTime();
				System.out.println(value);
				ObjectValue.value = value;
				// 生产完之后我们需要通知所有的消费者消费
				lock.notifyAll();

			}
		}

	}
}
