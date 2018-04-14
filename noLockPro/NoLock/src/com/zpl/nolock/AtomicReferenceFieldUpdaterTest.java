package com.zpl.nolock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * AtomicReferenceFieldUpdater���޸Ĺ��������ĳ���ֶ��޸ġ���AtomicIntegerFieldUpdater���ơ�ֻ�������˸�Class(String.class)
 * @author zhangpengliang
 *
 */
public class AtomicReferenceFieldUpdaterTest {

	static class Student {
		volatile String name;// ����Ҫ�޸ĵĶ�����ֶ�.���ֶβ��ܱ�private����,���뱻volatile����
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
			.newUpdater(Student.class, String.class, "name");//ʹ�÷�������ȡ
	public static void main(String[] args) {
		Student s=new Student("����",25);
		for(int i=0;i<100;i++){
			final String value=refUpdater.get(s);//��һ�еĴ��ھ���˵��ֻ���޸�һ��
			//����10���߳����޸���������ֵ��
			new Thread(new Runnable() {
				public void run() {
					while(true){
//						refUpdater.getAndSet(s, Thread.currentThread().getName());
//						String v=refUpdater.get(s);
//						System.out.println(v);
//						break;
//						String value=refUpdater.get(s);//��һ�еĴ���˵�����߳�������Դ�������޸�
						if(refUpdater.compareAndSet(s, value, Thread.currentThread().getName())){
							System.out.println("�߳�"+Thread.currentThread().getName()+"�޸ĳɹ���--"+refUpdater.get(s));
							break;
						}else{
							System.out.println("�߳�"+Thread.currentThread().getName()+"�޸�ʧ��");
							break;
						}
					}
				}
			}, "�߳�"+String.valueOf(i)).start();;
		}
		
	}
}
