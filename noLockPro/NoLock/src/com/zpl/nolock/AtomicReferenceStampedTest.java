package com.zpl.nolock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 解决CAS的ABA问题
 * 
 * @author zhangpengliang
 *
 */
public class AtomicReferenceStampedTest {

	private static AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(
			19, 0);//初始化值19元

	public static void main(String[] args) throws Exception {

		//创建3个线程来进行充值,但是只能充值一次。并且还是在小于20元的时候
		for(int i=0;i<3;i++){
			//TimeUnit.SECONDS.sleep(3);
			final int timestamp=money.getStamp();//获取到初始的时间戳--final关键字决定了只能一次==至少是加了一个保证（只能充一次）
			new Thread(new Runnable() {
				public void run() {
					//执行充值
					Integer m=money.getReference();
					if(m<20){
						if(money.compareAndSet(m, m+20, timestamp, timestamp+1)){
							//执行原子操作,如果成功了那就直接打印
							System.out.println("充值成功,线程"+Thread.currentThread().getName()+"充值了20元剩余"+money.getReference());
						}else{
							System.out.println("线程"+Thread.currentThread().getName()+"充值失败");
						}
					}
				}
			}, String.valueOf(i)+"线程").start();
		}
		
		//创建10线程消费
		for(int i=0;i<10;i++){
			new Thread(new Runnable() {
				public void run() {
					while(true){
						Integer m=money.getReference();
						int stamp=money.getStamp();
						if(m>10){
							//消费10元
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(money.compareAndSet(m, m-10, stamp, stamp+1)){
								//消费成功
								System.out.println("线程"+Thread.currentThread().getName()+"消费10元成功,剩余："+money.getReference());
							}else{
								System.out.println("线程"+Thread.currentThread().getName()+"消费失败");
								break;
							}
						}else{
							System.out.println("当前钱数不够10元");
							break;
						}
					}
					
				}
			}, "消费"+String.valueOf(i)).start();
		}
	}

}
