package com.zpl.demo01;

import java.util.ArrayList;
import java.util.List;

/**
 * 多消费者多生产者--操作栈
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
			}, "生产线程" + String.valueOf(i)).start();
		}
		for (int i = 5; i < 8; i++) {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						System.out
								.println("线程" + Thread.currentThread().getName()
										+ "消费者弹出来的是：" + m.pop());
					}
				}
			}, "线程" + String.valueOf(i)).start();
		}
	}

	static class MyStack {
		private List<String> list = new ArrayList<String>();

		/**
		 * 生产者
		 */
		synchronized public void push() {
			/***
			 * 这里当多个生产者的时候,如果放进去后,唤醒所有的对应线程,可能会当前的其他wait状态的生产者获取锁资源 导致不停的往size中放
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
			// System.out.println("生产者放进去后大小：" + list.size());
			// this.notifyAll();// 唤醒消费者?//同时这里也应该改成notifyALL
			/*************** 代码应该改为 ******************/
			while (list.size() == 1) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			list.add("anything=" + Math.random());
			System.out.println("生产者放进去后大小：" + list.size());
			this.notifyAll();// 唤醒消费者?//同时这里也应该改成notifyALL

		}

		/**
		 * 消费者
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
			this.notifyAll();// 唤醒生产者
			return v;
		}

	}

}
