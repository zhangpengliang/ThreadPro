package com.zpl.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {

	private final Sync sync=new Sync(2);
	private static final class Sync extends AbstractQueuedSynchronizer {
		public Sync(int count) {
			if (count <= 0) {
				throw new IllegalArgumentException("count 值必须大于2");
			}
			setState(count);
		}

		/**
		 * 如果小于0，就说明没有获取到锁，直接放到同步队列中
		 * @param arg
		 * @return
		 */
		@Override
		protected int tryAcquireShared(int arg) {
			for(;;){
				int current=getState();
				int newcount=current-arg;
				if(newcount<0||compareAndSetState(current, newcount)){
					return newcount;
				}
			}
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			for(;;){
				int current=getState();
				int newcount=current+arg;
				if(compareAndSetState(current, newcount)){
					return true;
				}
			}
		}

	}

	@Override
	public void lock() {
		sync.acquireShared(1);

	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		

	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		sync.releaseShared(1);

	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
