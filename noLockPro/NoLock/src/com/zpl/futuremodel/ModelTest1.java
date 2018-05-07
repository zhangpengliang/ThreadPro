package com.zpl.futuremodel;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * futureģʽʹ�õļ��з�ʽ
 * @author zhangpengliang
 *
 */
public class ModelTest1 {
	public static void main(String[] args) throws Exception {
		//ʹ��futuretask��ִ��
		FutureTask<String> future=new FutureTask<>(new Task1());
		future.run();
		System.out.println(future.get());
		
		//ʹ���̳߳�Future+ExecutorService
		ExecutorService pool=Executors.newSingleThreadExecutor();
		Future<String> f=pool.submit(new Task1());
		System.out.println(f.get());
		
		//ʹ���̳߳�FutureTask+ExecutorService
		ExecutorService pool2=Executors.newSingleThreadExecutor();
		FutureTask<String> f2=new FutureTask<>(new Task1());
		pool2.submit(f2);
		System.out.println(f2.get());
		
		
		/**
         * �����ַ�ʽ:FutureTask + Thread
         */
 
        // 2. �½�FutureTask,��Ҫһ��ʵ����Callable�ӿڵ����ʵ����Ϊ���캯������
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Task());
        // 3. �½�Thread��������
        Thread thread = new Thread(futureTask);
        thread.setName("Task thread");
        thread.start();
 
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");
 
        // 4. ����isDone()�ж������Ƿ����
        if(!futureTask.isDone()) {
            System.out.println("Task is not done");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int result = 0;
        try {
            // 5. ����get()������ȡ������,�������û��ִ������������ȴ�
            result = futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        System.out.println("result is " + result);
 
		
		
		//Excutors��װrunnable��һ��callable
		Callable<String> call=Executors.callable(new Task2(), "2300");
		FutureTask<String> f3=new FutureTask<String>(call);
		f3.run();
		System.out.println(f3.get());
	}
	
	
	
	static class Task1 implements Callable<String>{

		@Override
		public String call() throws Exception {
			System.out.println("��ִ����Futureģʽ");
			return "FutureModel";
		}
		
	}
	
	
	
	static class Task2 implements Runnable{


		@Override
		public void run() {
			System.out.println("abc");
			
		}
		
	}
	
	 // 1. �̳�Callable�ӿ�,ʵ��call()����,���Ͳ���ΪҪ���ص�����
    static class Task  implements Callable<Integer> {
 
        @Override
        public Integer call() throws Exception {
            System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");
            int result = 0;
            for(int i = 0; i < 100;++i) {
                result += i;
            }
 
            Thread.sleep(3000);
            return result;
        }
    }
}
