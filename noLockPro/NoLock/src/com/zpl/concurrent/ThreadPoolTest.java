package com.zpl.concurrent;

import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) {
		ThreadPool pool = ThreadPool.getInstance();

		pool.start(new ThreadA());
		pool.start(new ThreadB());
		pool.start(new ThreadC());
		pool.start(new ThreadD());

		pool.start(new ThreadE());
		pool.start(new ThreadF());
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.shutdown();
	}

	static class ThreadA implements Runnable {

		@Override
		public void run() {
			System.out.println("A");

		}
	}

	static class ThreadB implements Runnable {

		@Override
		public void run() {
			System.out.println("B");

		}
	}

	static class ThreadC implements Runnable {

		@Override
		public void run() {
			System.out.println("C");

		}
	}

	static class ThreadD implements Runnable {

		@Override
		public void run() {
			System.out.println("D");

		}
	}

	static class ThreadE implements Runnable {

		@Override
		public void run() {
			System.out.println("E");

		}
	}

	static class ThreadF implements Runnable {

		@Override
		public void run() {
			System.out.println("F");

		}
	}

}
