package com.zpl.nolock;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 尤其注意这个在没有system.out的情况下出现数据脏读。
 * 
 * @author zhangpengliang
 *
 */
public class AtomicIntegerFieldUpdaterTest {
	static class Student {
		private String name;
		public volatile int age;

		public Student(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}
	}

	/**
	 * 反射获取student类中的字段age,该字段必须是volatile修饰并且不能是private.并把这个student（age）作为共享
	 */
	private static AtomicIntegerFieldUpdater<Student> fu = AtomicIntegerFieldUpdater
			.newUpdater(Student.class, "age");

	public static void main(String[] args) {
		Student st = new Student("张三", 0);
		java.util.List<Thread> v = new ArrayList<Thread>();
		for (int i = 0; i < 5; i++) {
			ThreadA a = new ThreadA(st);
			Thread b = new Thread(a, "线程" + String.valueOf(i));
			v.add(b);
		}
		for (int i = 0; i < 5; i++) {
			Thread th = v.get(i);
			th.start();
		}

	}

	static class ThreadA implements Runnable {
		private Student st;

		public ThreadA(Student st) {
			super();
			this.st = st;
		}

		public void run() {
			// fu.getAndIncrement(st);
			// System.out.println("当前" + Thread.currentThread().getName()
			// + "执行新增之后的结果" + fu.get(st));
			// 或者
			int expect = fu.get(st);
			int value = expect + 1;
			// System.out.println(fu.get(st));
			// 为什么加这一行就没有并发问题,不加就会有并发问题呢？原因是:在println里面加了一把锁。synchronized(this)
			// 如果不加system.out.println的话我们可以加一个锁，把下面的作为同步代码块。
			// 这里我们的compareAndSet是原子操作,但是与下面的打印并不是一体的,可能我们第一个线程增加完后,第二个线程直接过来了,而且他的expect值是改过的，这时候
			// 就又可以执行原子操作增加1了。导致我们第一次打印并没有打印出真正的第一次增加完后的值。

			/**
			 * 至于上面添加了一个System.out.println(expect),代码就可以实现同步的原因是:我们下面的system.
			 * out与上面那个system.out 在多个线程下执行时同步的。因为源代码中添加了锁（同步代码块）
			 */
			// synchronized (st) {
			if (fu.compareAndSet(st, expect, value)) {
				System.out.println("当前" + Thread.currentThread().getName()
						+ "执行新增成功之后的结果" + fu.get(st));
			} else {
				System.out.println(
						"当前" + Thread.currentThread().getName() + "执行失败");
			}
			// }

		}
	}

}
