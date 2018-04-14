package com.zpl.demo01;

/**
 * ��������-��������==����ֵ
 * 
 * @author zhangpengliang
 *
 */
public class MoreCustomAndMoreProductorTest {

	public static void main(String[] args) {
		String lock = new String("");
		P p = new P(lock);
		C c = new C(lock);
		for (int i = 0; i < 10; i++) {// 10�������߳�
			new Thread(new Runnable() {
				public void run() {
					p.setValue();
				}
			}, String.valueOf(i)).start();
		}

		for (int i = 10; i < 20; i++) {// �Ǹ������߳�
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
				// ��ֵΪ�յ�ʱ��,��Ϊ�������Ҿ���Ҫ������������
				while (ObjectValue.value.equals("")) {
					System.out.println("������" + Thread.currentThread().getName()
							+ "Watting �ˡ�");
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// ����Ϊ�յ�ʱ��,���ǾͿ���ֱ������
				System.out.println("������" + Thread.currentThread().getName()
						+ " Runnable ��");
				ObjectValue.value = "";
				// ������֮�����Ǿ���Ҫ֪ͨ���е�����������
				lock.notifyAll();
			}
		}
	}

	/**
	 * ������
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
				// ��ֵ��Ϊ�յ�ʱ��,���ǾͲ���Ҫ����
				while (!ObjectValue.value.equals("")) {
					System.out.println("������" + Thread.currentThread().getName()
							+ "Watting �ˡ�");
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// ��ֵΪ�յ�ʱ�����Ǿ���Ҫ���� ��
				System.out.println("������" + Thread.currentThread().getName()
						+ "Runnable ��");
				String value = System.currentTimeMillis() + "_"
						+ System.nanoTime();
				System.out.println(value);
				ObjectValue.value = value;
				// ������֮��������Ҫ֪ͨ���е�����������
				lock.notifyAll();

			}
		}

	}
}
