package com.zpl.concurrent;

/**
 * �����߳�
 * 
 * @author zhangpengliang
 *
 */
public class Worker extends Thread {

	private boolean isIdle = false;// �Ƿ����

	private Runnable target;// ִ�е�����

	private ThreadPool threadpool;

	private boolean isShutdown = false;// �Ƿ�ر��߳�

	public Worker(Runnable target, String name, ThreadPool threadpool) {
		super(name);
		this.target = target;
		this.threadpool = threadpool;
	}
	

	public Worker(ThreadPool threadpool,String name) {
		super(name);
		this.threadpool = threadpool;
	}


	public Runnable getTarget() {
		return target;
	}


	public synchronized void setTarget(Runnable target) {
		this.target = target;
		this.notifyAll();//����
	}


	@Override
	public void run() {
		super.run();
		while (!isShutdown) {
			isIdle = false;
			if (target != null) {
				System.out.println(Thread.currentThread().getName()+"��ʼ����");
				target.run();
			}
			// ��������
			isIdle = true;
			// ֻҪû�йرվ�һֱִ��
			try {
				synchronized (this) {
					if (isIdle) {
						// �ȷŽ�ȥ���̳߳�
						threadpool.repool(this);
						//
						this.wait();
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void shutdown(){
			this.isShutdown=true;
			this.notifyAll();
	}

}
