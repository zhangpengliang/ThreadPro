package com.zpl.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class TwinsLockTest {
	final static Lock lock = new TwinsLock();

	public static void main(String[] args) {

		for(int i=0;i<10;i++){
			Worker w=new Worker();
//			w.setDaemon(true);
			System.out.println(i);
			w.setName("Ïß³Ì"+String.valueOf(i));
			w.start();
		}
		for(int i=0;i<10;i++){
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	static class Worker extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			while (true) {
				lock.lock();
				try {
					TimeUnit.SECONDS.sleep(1);
					System.out.println(Thread.currentThread().getName());
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					lock.unlock();
					break;
				}
			}

		}
	}

}
