package com.zpl.demo02;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 通过管道进行线程间通信:字符流
 * 
 * @author zhangpengliang
 *
 */
public class Test02 {

	public static void main(String[] args) {
		try {
			WriteData w = new WriteData();
			ReadData r = new ReadData();
			PipedWriter out = new PipedWriter();
			PipedReader in = new PipedReader();

			out.connect(in);
			// in.connect(out);
			ThreadWrite write = new ThreadWrite(w, out);
			ThreadRead read = new ThreadRead(r, in);
			write.setName("写");
			read.setName("读");
			/****************
			 * 读线程先启动后,当没有数据写入的时候,线程阻塞在in.read(byte);中, 直到有数据被写入,才继续往下运行
			 * 
			 * 
			 * 
			 ****************/
			write.start();

			// try {
			// TimeUnit.SECONDS.sleep(3);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			read.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static class ThreadRead extends Thread {
		private ReadData read;
		private PipedReader in;

		public ThreadRead(ReadData read, PipedReader in) {
			super();
			this.read = read;
			this.in = in;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			read.readData(in);
		}
	}

	static class ThreadWrite extends Thread {
		private WriteData write;
		private PipedWriter out;

		public ThreadWrite(WriteData write, PipedWriter out) {
			super();
			this.write = write;
			this.out = out;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			write.writeMethod(out);
		}
	}

	static class WriteData {
		/**
		 * 写数据
		 * 
		 * @param out
		 */
		public void writeMethod(PipedWriter out) {
			System.out.println("Write :");
			try {
				for (int i = 0; i < 300; i++) {
					String outData = "" + (i + 1);
					out.write(outData);
					System.out.print(outData);
				}
				System.out.println();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	static class ReadData {
		public void readData(PipedReader in) {
			try {
				System.out.println("Read :");
				char[] data = new char[20];// 一次读20个字节
				int length = in.read(data);// 线程没有读到数据就会一直被阻塞到这里
				while (length != -1) {
					String newData = new String(data, 0, length);
					System.out.print(newData);
					length = in.read(data);
				}
				System.out.println();
				in.close();

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
