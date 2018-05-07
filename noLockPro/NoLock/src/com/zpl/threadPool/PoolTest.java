package com.zpl.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolTest {

	public static void main(String[] args) {
		ExecutorService fixed=Executors.newFixedThreadPool(5);
		
		//设置链表结构的阻塞队列的线程池
		ThreadPoolExecutor excutor=new ThreadPoolExecutor(5, 10, 600, TimeUnit.SECONDS,new LinkedBlockingQueue<>(5));
		ThreadPoolExecutor excutorArray=new ThreadPoolExecutor(5, 10, 600, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
		
		RB b1=new RB();
		FutureTask<String> t1=new FutureTask<String>(b1);
		String str=null;
		
		FutureTask<String> t2=new FutureTask<String>(new TheadA(str), str);
		t2.run();
		try {
			System.out.println(t2.get());
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ExecutionException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
//		t1.run();
		try {
			String n=t1.get();
			System.out.println("dsfds=="+n);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		for(int i=0;i<1;i++){
//			/*TheadA A=new TheadA();
//			excutor.execute(A);
//			excutorArray.execute(A);
//			fixed.execute(A);*/
//			RB b=new RB();
//			Future<String> a=excutor.submit(b);
//			System.out.println("执行了");
//			try {
//				System.out.println(a.get());
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		excutor.shutdown();
//		excutorArray.shutdown();
	}
	
	static class TheadA implements Runnable{
		private String str;

		public TheadA(String str) {
			super();
			this.str = str;
		}

		public TheadA() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			str="233455";
//			try {
//				TimeUnit.SECONDS.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			System.out.println("当前线程"+Thread.currentThread().getName());
		}
		
	}
	
	static class RB implements Callable<String>{

		@Override
		public String call() throws Exception {
			TimeUnit.SECONDS.sleep(10);
			System.out.println("我调用了北京");
			return "BeiJing";
		}
		
	}

}
