package Util;

import java.util.Observable;
import java.util.Observer;

// xuky 2018.06.04 ��Ϣ����
public class MessageCenter implements Observer {
	public String SelfServer_msg = "";

	private volatile static MessageCenter uniqueInstance;
	public static MessageCenter getInstance() {
		if (uniqueInstance == null)
			synchronized (MessageCenter.class) {
				if (uniqueInstance == null)
					// ˫�ؼ�����
					uniqueInstance = new MessageCenter();
			}
		return uniqueInstance;
	}
	private MessageCenter() {
		Publisher.getInstance().addObserver(this);
	}

	@Override
	public synchronized void update(Observable o, Object arg) {
		try {
			Object[] s = (Object[]) arg;
			if (s[0].equals("SelfServer_msg")) {
				SelfServer_msg = (String) s[2];
				System.out.println("SelfServer_msg:"+SelfServer_msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
