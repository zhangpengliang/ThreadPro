package com.zpl.threadPool;

import java.util.concurrent.Executor;
/**
 * ���������������̳߳ص�ʵ�ַ�ʽ
 * @author zhangpengliang
 *
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Pool {

	/**
	 * ����һ���̶���С���̳߳�<br>
	 * return new ThreadPoolExecutor(nThreads, nThreads, 0L,
	 * TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	 * 
	 * ����ĺ��Ĵ�С������߳������������趨�����ֵ,�õ������߳��ǣ�����ṹ�������̣߳��޽�
	 */
	ExecutorService fixed = Executors.newFixedThreadPool(5);
	/**
	 * ������������һ���Դ���Ĭ�ϵ��̹߳�����
	 */
	ExecutorService fixed2=Executors.newFixedThreadPool(5, Executors.defaultThreadFactory());

	/**
	 * �����fixed��ֻ࣬������5�ĳ���1�������Ķ�һ��
	 */
	ExecutorService single=Executors.newSingleThreadExecutor();
	
	
}
