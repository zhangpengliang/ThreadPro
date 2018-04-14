package com.zpl.nolock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReferenceʾ��
 * 
 * @author zhangpengliang
 *
 */
public class AtomicReferenceTest {
	private static Person person;// ����Ķ���
	private static AtomicReference<Person> arp;//

	public static void main(String[] args) {
		person = new Person("����", 15);
		arp = new AtomicReference<AtomicReferenceTest.Person>(person);
		// �߳�1
		new Thread(new Runnable() {
			public void run() {
				Person p1 = new Person("����", 16);//����ֻ����new һ������ʵ��.���ֱ����person.set..���ǲ���ȫ�ġ����ֱ�ӶԶ����ֶβ����أ�
				if (arp.compareAndSet(person, p1)) {//compareAndSet��һ��ԭ�Ӳ���
					System.out.println("�߳�A-ִ�и��³ɹ�");
					person = arp.get();// ��ȡ�����µ�ֵ
					System.out.println(person.getName());
				} else {
					System.out.println("����ʧ�ܡ�������");
				}
			}
		}, "A").start();
		// �߳�2
		new Thread(new Runnable() {
			public void run() {
				Person p2 = new Person("��kui", 11);
				for (;;) {
					if (arp.compareAndSet(person, p2)) {
						System.out.println("�߳�B-ִ�и��³ɹ�");
						person = arp.get();
						System.out.println("�߳�B���õ�ֵΪ��" + person.getName());
						break;
					} else {
						System.out.println("�߳�B-����ʧ�ܡ�������");
					}
				}
			}
		}, "B").start();
	}

	static class Person {
		private String name;
		private int age;

		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

	}
}
