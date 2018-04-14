package com.zpl.nolock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * AtomicReferenceFieldUpdater对修改关联对象的某个字段修改、和AtomicIntegerFieldUpdater类似。只不过多了个Class(String.class)
 * @author zhangpengliang
 *
 */
public class AtomicReferenceFieldUpdaterTest {

	static class Student {
		volatile String name;// 我们要修改的对象的字段.该字段不能被private修饰,必须被volatile修饰
		int age;

		public Student(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		public Student() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

	}
	public static AtomicReferenceFieldUpdater<Student, String> refUpdater = AtomicReferenceFieldUpdater
			.newUpdater(Student.class, String.class, "name");//使用反射来获取
	public static void main(String[] args) {
		Student s=new Student("李四",25);
		for(int i=0;i<100;i++){
			final String value=refUpdater.get(s);//这一行的存在就是说明只能修改一次
			//创建10个线程来修改这个对象的值。
			new Thread(new Runnable() {
				public void run() {
					while(true){
//						refUpdater.getAndSet(s, Thread.currentThread().getName());
//						String v=refUpdater.get(s);
//						System.out.println(v);
//						break;
//						String value=refUpdater.get(s);//这一行的存在说明多线程争夺资源来进行修改
						if(refUpdater.compareAndSet(s, value, Thread.currentThread().getName())){
							System.out.println("线程"+Thread.currentThread().getName()+"修改成功！--"+refUpdater.get(s));
							break;
						}else{
							System.out.println("线程"+Thread.currentThread().getName()+"修改失败");
							break;
						}
					}
				}
			}, "线程"+String.valueOf(i)).start();;
		}
		
	}
}
