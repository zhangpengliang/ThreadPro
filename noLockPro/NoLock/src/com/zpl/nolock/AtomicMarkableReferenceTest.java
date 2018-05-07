package com.zpl.nolock;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicMarkableReference���ǽ�һ��booleanֵ���Ƿ��и��ĵı�ǣ�<br>
 * ���ʾ������İ汾��ֻ��������true��false��<br>
 * �޸ĵ�ʱ�����������汾��֮�������л��������������ܽ��ABA�����⣬ֻ�ǻή��ABA���ⷢ���ļ��ʶ��ѣ�
 * 
 * @author zhangpengliang
 *
 */
public class AtomicMarkableReferenceTest {
	static AtomicReference atomicReference = new AtomicReference();
	static String ref1 = "aaa";
	static AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference<String>(
			ref1, false);

	static Integer ref2 = 111;
	static AtomicStampedReference atomicStampedReference = new AtomicStampedReference<Integer>(
			ref2, 0);

	public static void main(String[] args) {
		Demo demo1 = new Demo("aaa", "111");
		Demo demo2 = new Demo("bbb", "222");
		atomicReference.set(demo1);
		atomicReference.compareAndSet(demo1, demo2);

		if (atomicMarkableReference.isMarked() != true) {
			atomicMarkableReference.set("bbb", true);
		}
		;

		if (atomicStampedReference.getStamp() == 0) {
			atomicStampedReference.set(new Integer("222"), 1);
		}
	}

	static class Demo {
		public Demo(String name, String address) {
			this.name = name;
			this.address = address;
		}

		private String name;
		private String address;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
	}

}