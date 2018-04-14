package com.zpl.nolock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ���CAS��ABA����
 * 
 * @author zhangpengliang
 *
 */
public class AtomicReferenceStampedTest {

	private static AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(
			19, 0);//��ʼ��ֵ19Ԫ

	public static void main(String[] args) throws Exception {

		//����3���߳������г�ֵ,����ֻ�ܳ�ֵһ�Ρ����һ�����С��20Ԫ��ʱ��
		for(int i=0;i<3;i++){
			//TimeUnit.SECONDS.sleep(3);
			final int timestamp=money.getStamp();//��ȡ����ʼ��ʱ���--final�ؼ��־�����ֻ��һ��==�����Ǽ���һ����֤��ֻ�ܳ�һ�Σ�
			new Thread(new Runnable() {
				public void run() {
					//ִ�г�ֵ
					Integer m=money.getReference();
					if(m<20){
						if(money.compareAndSet(m, m+20, timestamp, timestamp+1)){
							//ִ��ԭ�Ӳ���,����ɹ����Ǿ�ֱ�Ӵ�ӡ
							System.out.println("��ֵ�ɹ�,�߳�"+Thread.currentThread().getName()+"��ֵ��20Ԫʣ��"+money.getReference());
						}else{
							System.out.println("�߳�"+Thread.currentThread().getName()+"��ֵʧ��");
						}
					}
				}
			}, String.valueOf(i)+"�߳�").start();
		}
		
		//����10�߳�����
		for(int i=0;i<10;i++){
			new Thread(new Runnable() {
				public void run() {
					while(true){
						Integer m=money.getReference();
						int stamp=money.getStamp();
						if(m>10){
							//����10Ԫ
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(money.compareAndSet(m, m-10, stamp, stamp+1)){
								//���ѳɹ�
								System.out.println("�߳�"+Thread.currentThread().getName()+"����10Ԫ�ɹ�,ʣ�ࣺ"+money.getReference());
							}else{
								System.out.println("�߳�"+Thread.currentThread().getName()+"����ʧ��");
								break;
							}
						}else{
							System.out.println("��ǰǮ������10Ԫ");
							break;
						}
					}
					
				}
			}, "����"+String.valueOf(i)).start();
		}
	}

}
