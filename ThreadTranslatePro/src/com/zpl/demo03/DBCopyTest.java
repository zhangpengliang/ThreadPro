package com.zpl.demo03;

/**
 * 等待/通知交叉备份。（有序交叉工作）
 * 
 * @author zhangpengliang
 *
 */
public class DBCopyTest {

	public static void main(String[] args) {
		DBTools db = new DBTools();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					db.backupA();
				}
			}, "A" + String.valueOf(i)).start();
		}

		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					db.backupB();
				}
			}, "B" + String.valueOf(i)).start();
		}

	}

	static class DBTools {
		private volatile boolean prevIsA = false;

		synchronized public void backupA() {
			try {
				while (!prevIsA) {
					wait();
				}
				System.out.println("★★★★★★★★");
				prevIsA = false;
				notifyAll();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		synchronized public void backupB() {
			try {
				while (prevIsA) {
					wait();
				}
				System.out.println("☆☆☆☆☆☆☆☆");
				prevIsA = true;
				notifyAll();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
