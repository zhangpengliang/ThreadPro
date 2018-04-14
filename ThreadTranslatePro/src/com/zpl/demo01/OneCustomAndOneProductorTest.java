package com.zpl.demo01;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个消费者一个生产者操作栈
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
					System.out.println("消费者弹出来的是：" + m.pop());
				}
			}
		}, "B").start();
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
			this.notify();// 唤醒消费者
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
			this.notify();// 唤醒生产者
			return v;
		}

	}
}
