package com.zpl.nolock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference示例
 * 
 * @author zhangpengliang
 *
 */
public class AtomicReferenceTest {
	private static Person person;// 共享的对象
	private static AtomicReference<Person> arp;//

	public static void main(String[] args) {
		person = new Person("张三", 15);
		arp = new AtomicReference<AtomicReferenceTest.Person>(person);
		// 线程1
		new Thread(new Runnable() {
			public void run() {
				Person p1 = new Person("李四", 16);//这里只能新new 一个对象实例.如果直接用person.set..还是不安全的。如何直接对对象字段操作呢？
				if (arp.compareAndSet(person, p1)) {//compareAndSet是一个原子操作
					System.out.println("线程A-执行更新成功");
					person = arp.get();// 获取到更新的值
					System.out.println(person.getName());
				} else {
					System.out.println("更新失败。。。。");
				}
			}
		}, "A").start();
		// 线程2
		new Thread(new Runnable() {
			public void run() {
				Person p2 = new Person("李kui", 11);
				for (;;) {
					if (arp.compareAndSet(person, p2)) {
						System.out.println("线程B-执行更新成功");
						person = arp.get();
						System.out.println("线程B设置的值为：" + person.getName());
						break;
					} else {
						System.out.println("线程B-更新失败。。。。");
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
