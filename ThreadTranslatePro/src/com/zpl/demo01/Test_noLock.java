package com.zpl.demo01;

public class Test_noLock {

	public static void main(String[] args) {
		// TODO ������Ӷ��������ͻᱨ��
		String str = new String("a");
		try {
			str.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
