package com.zpl.nolock;

import java.util.concurrent.TimeUnit;

/**
 * ����˵����final���εı���ֻ�ܸ�ֵһ��,֮��ֵ�Ͳ����ٱ䡣<br>
 * ������ͨ��final int a=b.getage();bʵ���仯��ageҲ�仯������Ҳ���޷���ֵ�ġ�
 * 
 * @author zhangpengliang
 *
 */
public class Test {

	public static void main(String[] args) throws InterruptedException {
		int a = 5;
		for (int i = 0; i < 3; i++) {
			TimeUnit.SECONDS.sleep(5);//���߳�˯5����a=10
			System.out.println("��ʼ��b��ֵ");
			final int b = a;
			System.out.println("��b��ֵ����:b="+b);//����ڶ����̴߳�ӡ������Ȼ��5,˵�����ǵ���֤�ɹ���
			new Thread(new Runnable() {
				public void run() {
					int a = 10;
					System.out.println("��a��ֵΪ10");
				}
			}).start();
		}
	}
}
