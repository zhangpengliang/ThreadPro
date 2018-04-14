package com.zpl.nolock;

import java.util.concurrent.TimeUnit;

/**
 * 用来说明：final修饰的变量只能赋值一次,之后值就不会再变。<br>
 * 即便是通过final int a=b.getage();b实例变化，age也变化，这样也是无法赋值的。
 * 
 * @author zhangpengliang
 *
 */
public class Test {

	public static void main(String[] args) throws InterruptedException {
		int a = 5;
		for (int i = 0; i < 3; i++) {
			TimeUnit.SECONDS.sleep(5);//主线程睡5秒让a=10
			System.out.println("开始给b赋值");
			final int b = a;
			System.out.println("给b赋值结束:b="+b);//如果第二个线程打印的是仍然是5,说明我们的验证成功了
			new Thread(new Runnable() {
				public void run() {
					int a = 10;
					System.out.println("给a赋值为10");
				}
			}).start();
		}
	}
}
