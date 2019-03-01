package Util;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//  �������ݻ����� (����ģʽ�������յ������ݶ���������)
public class RecvData {
	private volatile static RecvData uniqueInstance;

	Queue<String> recvData = new LinkedList<String>();

	public static RecvData getInstance() {
		if (uniqueInstance == null) {
			synchronized (RecvData.class) {
				if (uniqueInstance == null) {
					// ˫�ؼ�����
					uniqueInstance = new RecvData();
				}
			}
		}
		return uniqueInstance;
	}

	public void push(String data) {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			recvData.offer(data);
		} finally {
			lock.unlock();
		}
	}

	public String pop() {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			String str = recvData.poll();
			if (str == null)
				str = "";
			return str;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {

	}

}
