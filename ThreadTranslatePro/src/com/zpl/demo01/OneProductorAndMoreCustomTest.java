package com.zpl.demo01;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个生产者和多个消费者---容易出现假死现象
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
			if (list.size() == 1) {
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

		synchronized public String pop() {
			/***************
			 * 当前代码会有报错，因为如果list.size!=0时,清除掉后唤醒线程有可能会唤醒消费线程,导致数组越界,因为size==
			 * 0然后还继续往后走肯定报错,
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
			// this.notify();// 唤醒生产者
			// return v;
			/************** 我们应该改成这样 **************************/
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
