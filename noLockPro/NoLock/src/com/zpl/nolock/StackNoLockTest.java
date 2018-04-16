package com.zpl.nolock;

/**
 * 实现一个无锁的Stack，并写一段测试代码（多线程访问），证明这个Stack是线程安全的。给出程序以及运行的截图
 */
import java.util.concurrent.atomic.AtomicReference;

public class StackNoLockTest {
	static LockFreeStack<Object> stack = new LockFreeStack<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Push[] pushs = new Push[10];
		Pop[] pop = new Pop[10];
		for (int i = 0; i < 10; i++) {
			pushs[i] = new Push();
			pop[i] = new Pop();
		}
		for (int i = 0; i < 10; i++) {
			pushs[i].start();
			// pop[i].start();
		}
		System.out.println(123);
		for (int i = 0; i < 10; i++) {
			try {
				pushs[i].join();
				pop[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	static class Push extends Thread {
		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				stack.push("Random->" + Math.random());
			}
		}
	}

	static class Pop extends Thread {
		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				System.out.println("已出栈：" + stack.pop());
			}
		}
	}

	static class LockFreeStack<V> {

		public LockFreeStack() {
			super();
			// TODO Auto-generated constructor stub
		}

		private AtomicReference<Node<V>> aref = new AtomicReference<Node<V>>();

		public void push(V v) {
			Node<V> newnode = new Node<V>(v, null);
			Node<V> oldnode = null;
			do {
				oldnode = aref.get();
				newnode.setNextNode(oldnode);// 先进先出
			} while (aref.compareAndSet(oldnode, newnode));
		}

		public V pop() {
			Node<V> oldnode = null;
			Node<V> newnode = null;
			do {
				oldnode = aref.get();
				if (oldnode == null) {
					// 说明是空的
					continue;
				}
				newnode = oldnode.getNextNode();

			} while (oldnode == null || !aref.compareAndSet(oldnode, newnode));
			return oldnode.getValue();
		}

		class Node<T> {
			// 构造一个链表的节点
			private T value;
			private Node<T> nextNode;

			public Node(T value, Node<T> nextNode) {
				super();
				this.value = value;
				this.nextNode = nextNode;
			}

			public T getValue() {
				return value;
			}

			public void setValue(T value) {
				this.value = value;
			}

			public Node<T> getNextNode() {
				return nextNode;
			}

			public void setNextNode(Node<T> nextNode) {
				this.nextNode = nextNode;
			}

		}

	}

}
