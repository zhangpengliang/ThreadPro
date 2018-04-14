package com.zpl.demo02;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Test {

	public static void main(String[] args) {
		try {
			WriteData w = new WriteData();
			ReadData r = new ReadData();
			PipedOutputStream out = new PipedOutputStream();
			PipedInputStream in = new PipedInputStream();

			out.connect(in);
			// in.connect(out);
			ThreadWrite write = new ThreadWrite(w, out);
			ThreadRead read = new ThreadRead(r, in);
			write.setName("д");
			read.setName("��");
			/****************
			 * ���߳���������,��û������д���ʱ��,�߳�������in.read(byte);��, ֱ�������ݱ�д��,�ż�����������
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
		private PipedInputStream in;

		public ThreadRead(ReadData read, PipedInputStream in) {
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
		private PipedOutputStream out;

		public ThreadWrite(WriteData write, PipedOutputStream out) {
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
		 * д����
		 * 
		 * @param out
		 */
		public void writeMethod(PipedOutputStream out) {
			System.out.println("Write :");
			try {
				for (int i = 0; i < 300; i++) {
					String outData = "" + (i + 1);
					out.write(outData.getBytes());
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
		public void readData(PipedInputStream in) {
			try {
				System.out.println("Read :");
				byte[] data = new byte[20];// һ�ζ�20���ֽ�
				int length = in.read(data);// �߳�û�ж������ݾͻ�һֱ������������
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
