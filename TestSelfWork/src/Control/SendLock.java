package Control;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SendLock {
	private volatile static SendLock uniqueInstance;

	private Lock lock = new ReentrantLock(); // ע������ط�

	private Boolean USABLE = true; // �Ƿ����

	public static SendLock getInstance() {
		if (uniqueInstance == null) {
			synchronized (SendLock.class) {
				if (uniqueInstance == null) {
					// ˫�ؼ�����
					uniqueInstance = new SendLock();
				}
			}
		}
		return uniqueInstance;
	}

	private SendLock() {
	}

	// �ж��Ƿ�����
	public Boolean isUsable() {
		lock.lock();
		Boolean ret = true;
		try {

			if (USABLE) { // �������
				ret = true; // ����true
				USABLE = false; // ����Ϊ������
			} else {
				ret = false;
			}

		} finally {
			lock.unlock();
		}
		return ret;
	}

	public void setEnable() {
		lock.lock();
		try {
			USABLE = true; // ���ÿ���
		} finally {
			lock.unlock();
		}
	}

}
