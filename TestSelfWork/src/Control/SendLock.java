package Control;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SendLock {
	private volatile static SendLock uniqueInstance;

	private Lock lock = new ReentrantLock(); // 注意这个地方

	private Boolean USABLE = true; // 是否可用

	public static SendLock getInstance() {
		if (uniqueInstance == null) {
			synchronized (SendLock.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new SendLock();
				}
			}
		}
		return uniqueInstance;
	}

	private SendLock() {
	}

	// 判断是否被锁定
	public Boolean isUsable() {
		lock.lock();
		Boolean ret = true;
		try {

			if (USABLE) { // 如果可用
				ret = true; // 返回true
				USABLE = false; // 设置为不可用
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
			USABLE = true; // 设置可用
		} finally {
			lock.unlock();
		}
	}

}
