package com.zpl.futuremodel;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * future模式使用的集中方式
 * @author zhangpengliang
 *
 */
public class ModelTest1 {
	public static void main(String[] args) throws Exception {
		//使用futuretask来执行
		FutureTask<String> future=new FutureTask<>(new Task1());
		future.run();
		System.out.println(future.get());
		
		//使用线程池Future+ExecutorService
		ExecutorService pool=Executors.newSingleThreadExecutor();
		Future<String> f=pool.submit(new Task1());
		System.out.println(f.get());
		
		//使用线程池FutureTask+ExecutorService
		ExecutorService pool2=Executors.newSingleThreadExecutor();
		FutureTask<String> f2=new FutureTask<>(new Task1());
		pool2.submit(f2);
		System.out.println(f2.get());
		
		
		/**
         * 第三种方式:FutureTask + Thread
         */
 
        // 2. 新建FutureTask,需要一个实现了Callable接口的类的实例作为构造函数参数
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Task());
        // 3. 新建Thread对象并启动
        Thread thread = new Thread(futureTask);
        thread.setName("Task thread");
        thread.start();
 
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");
 
        // 4. 调用isDone()判断任务是否结束
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
            // 5. 调用get()方法获取任务结果,如果任务没有执行完成则阻塞等待
            result = futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        System.out.println("result is " + result);
 
		
		
		//Excutors封装runnable成一个callable
		Callable<String> call=Executors.callable(new Task2(), "2300");
		FutureTask<String> f3=new FutureTask<String>(call);
		f3.run();
		System.out.println(f3.get());
	}
	
	
	
	static class Task1 implements Callable<String>{

		@Override
		public String call() throws Exception {
			System.out.println("我执行了Future模式");
			return "FutureModel";
		}
		
	}
	
	
	
	static class Task2 implements Runnable{


		@Override
		public void run() {
			System.out.println("abc");
			
		}
		
	}
	
	 // 1. 继承Callable接口,实现call()方法,泛型参数为要返回的类型
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
