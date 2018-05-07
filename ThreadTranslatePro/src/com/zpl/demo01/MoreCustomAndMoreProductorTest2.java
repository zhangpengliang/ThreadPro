package com.zpl.demo01;

import java.util.ArrayList;
import java.util.List;

/**
 * �������߶�������--����ջ
 * 
 * @author zhangpengliang
 *
 */
public class MoreCustomAndMoreProductorTest2 {

	public static void main(String[] args) {
		MyStack m = new MyStack();
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						m.push();
					}
				}
			}, "�����߳�" + String.valueOf(i)).start();
		}
		for (int i = 5; i < 8; i++) {
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
			/***
			 * ���ﵱ��������ߵ�ʱ��,����Ž�ȥ��,�������еĶ�Ӧ�߳�,���ܻᵱǰ������wait״̬�������߻�ȡ����Դ ���²�ͣ����size�з�
			 **/
			// if (list.size() == 1) {
			// try {
			// this.wait();
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// list.add("anything=" + Math.random());
			// System.out.println("�����߷Ž�ȥ���С��" + list.size());
			// this.notifyAll();// ����������?//ͬʱ����ҲӦ�øĳ�notifyALL
			/*************** ����Ӧ�ø�Ϊ ******************/
			while (list.size() == 1) {
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

		/**
		 * ������
		 * 
		 * @return
		 */
		synchronized public String pop() {
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
