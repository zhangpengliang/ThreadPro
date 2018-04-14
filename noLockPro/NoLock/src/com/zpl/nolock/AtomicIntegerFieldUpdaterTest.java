package com.zpl.nolock;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * ����ע�������û��system.out������³������������
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
	 * �����ȡstudent���е��ֶ�age,���ֶα�����volatile���β��Ҳ�����private.�������student��age����Ϊ����
	 */
	private static AtomicIntegerFieldUpdater<Student> fu = AtomicIntegerFieldUpdater
			.newUpdater(Student.class, "age");

	public static void main(String[] args) {
		Student st = new Student("����", 0);
		java.util.List<Thread> v = new ArrayList<Thread>();
		for (int i = 0; i < 5; i++) {
			ThreadA a = new ThreadA(st);
			Thread b = new Thread(a, "�߳�" + String.valueOf(i));
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
			// System.out.println("��ǰ" + Thread.currentThread().getName()
			// + "ִ������֮��Ľ��" + fu.get(st));
			// ����
			int expect = fu.get(st);
			int value = expect + 1;
			// System.out.println(fu.get(st));
			// Ϊʲô����һ�о�û�в�������,���Ӿͻ��в��������أ�ԭ����:��println�������һ������synchronized(this)
			// �������system.out.println�Ļ����ǿ��Լ�һ���������������Ϊͬ������顣
			// �������ǵ�compareAndSet��ԭ�Ӳ���,����������Ĵ�ӡ������һ���,�������ǵ�һ���߳��������,�ڶ����߳�ֱ�ӹ�����,��������expectֵ�ǸĹ��ģ���ʱ��
			// ���ֿ���ִ��ԭ�Ӳ�������1�ˡ��������ǵ�һ�δ�ӡ��û�д�ӡ�������ĵ�һ����������ֵ��

			/**
			 * �������������һ��System.out.println(expect),����Ϳ���ʵ��ͬ����ԭ����:���������system.
			 * out�������Ǹ�system.out �ڶ���߳���ִ��ʱͬ���ġ���ΪԴ���������������ͬ������飩
			 */
			// synchronized (st) {
			if (fu.compareAndSet(st, expect, value)) {
				System.out.println("��ǰ" + Thread.currentThread().getName()
						+ "ִ�������ɹ�֮��Ľ��" + fu.get(st));
			} else {
				System.out.println(
						"��ǰ" + Thread.currentThread().getName() + "ִ��ʧ��");
			}
			// }

		}
	}

}
