package com.zpl.demo01;

import java.util.ArrayList;
import java.util.List;

/**
 * һ��������һ�������߲���ջ
 * 
 * @author zhangpengliang
 *
 */
public class OneCustomAndOneProductorTest {

	public static void main(String[] args) {
		MyStack m = new MyStack();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					m.push();
				}
			}
		}, "A").start();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.println("�����ߵ��������ǣ�" + m.pop());
				}
			}
		}, "B").start();
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
			this.notify();// ����������
		}

		synchronized public String pop() {
			if (list.size() == 0) {
				try {
					this.wait();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			String v = list.remove(0);
			this.notify();// ����������
			return v;
		}

	}
}
