package com.zpl.demo01;

import java.util.ArrayList;
import java.util.List;

/**
 * һ�������ߺͶ��������---���׳��ּ�������
 * 
 * @author zhangpengliang
 *
 */
public class OneProductorAndMoreCustomTest {

	public static void main(String[] args) {
		MyStack m = new MyStack();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					m.push();
				}
			}
		}, "A").start();
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						System.out
								.println("�߳�" + Thread.currentThread().getName()
										+ "�����ߵ��������ǣ�" + m.pop());
					}
				}
			}, "�߳�" + String.valueOf(i)).start();
		}
	}

	static class MyStack {
		private List<String> list = new ArrayList<String>();

		/**
		 * ������
		 */
		synchronized public void push() {
			if (list.size() == 1) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			list.add("anything=" + Math.random());
			System.out.println("�����߷Ž�ȥ���С��" + list.size());
			this.notifyAll();// ����������?//ͬʱ����ҲӦ�øĳ�notifyALL
		}

		synchronized public String pop() {
			/***************
			 * ��ǰ������б�����Ϊ���list.size!=0ʱ,����������߳��п��ܻỽ�������߳�,��������Խ��,��Ϊsize==
			 * 0Ȼ�󻹼��������߿϶�����,
			 *********************/
			// if (list.size() == 0) {
			// try {
			// this.wait();
			// } catch (Exception e) {
			// // TODO: handle exception
			// }
			//
			// }
			// String v = list.remove(0);
			// this.notify();// ����������
			// return v;
			/************** ����Ӧ�øĳ����� **************************/
			while (list.size() == 0) {
				try {
					this.wait();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			String v = list.remove(0);
			this.notifyAll();// ����������
			return v;
		}

	}

}
