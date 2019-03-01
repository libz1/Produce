package Control;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//  �������ݻ����� (����ģʽ�������յ������ݶ���������)
public class RecvData1 {
	private volatile static RecvData1 uniqueInstance;

	Queue<String> recvData = new LinkedList<String>();

	public static RecvData1 getInstance() {
		if (uniqueInstance == null) {
			synchronized (RecvData1.class) {
				if (uniqueInstance == null) {
					// ˫�ؼ�����
					uniqueInstance = new RecvData1();
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
