package com.zpl.concurrent;

import java.util.List;
import java.util.Vector;

/**
 * ����һ���򵥵��̳߳�
 * 
 * @author zhangpengliang
 *
 */
public class ThreadPool {
	private static ThreadPool instance = null;
	// �յ��̶߳���
	private List<Worker> idleThreads;
	// ���е��߳�����
	private int threadCounter = 0;
	// �Ƿ�ر��̳߳�
	private boolean isShutdown = false;

	public ThreadPool() {
		this.idleThreads = new Vector<Worker>(5);
		this.threadCounter = 0;
	}

	public synchronized static ThreadPool getInstance() {
		if (instance == null) {
			instance = new ThreadPool();
		}
		return instance;
	}

	protected synchronized void repool(Worker worker) {
		if (!isShutdown) {
			// �̳߳�û�йر�,�ͷŵ��̳߳���
			idleThreads.add(worker);
		} else {
			// ����̳߳عر���,��ô����߳�Ҳ��Ҫ�ر�
			worker.shutdown();
		}

	}

	protected synchronized void shutdown() {
		// �ر��̳߳�
		isShutdown = true;
		// ���������̵߳Ĺرչ���
		for (Worker worker : idleThreads) {
			worker.shutdown();
		}
	}

	public synchronized void start(Runnable target) {
		Worker worker = null;
		if (idleThreads.size() > 0) {
			// ˵���п��е��߳�
			int size = idleThreads.size();
			worker = idleThreads.get(size - 1);// ��ȡһ��worker
			idleThreads.remove(size - 1);// ���̳߳����Ƴ�
			worker.setTarget(target);
		} else {
			// ˵��û�п��е��߳�
				threadCounter++;
				worker = new Worker(target, "�½��߳�" + String.valueOf(threadCounter), this);
				idleThreads.add(worker);
				worker.start();
		}
	}
}
