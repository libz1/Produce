package Control;

import DataBase.IBaseDao;
import Entity.TerminalTestNo;
import Entity.TerminalTestNoDaoImpl;

public class Main {

	public static void main(String[] args) {
		IBaseDao<TerminalTestNo> iBaseDao_TeminalTestNo = new TerminalTestNoDaoImpl();

		class MyRunable implements Runnable {
			@Override
			public void run() {
				// ������ʾ��ǰ���̱߳�־
				System.out.println(((TerminalTestNoDaoImpl) iBaseDao_TeminalTestNo).getNo("3", "12", "201809")+" Thread:"+this );
//				System.out.println(((TeminalTestNoDaoImpl) iBaseDao_TeminalTestNo).getNoNeedLock("3", "12", "201809")+" Thread:"+this );
			};
		}

		for (int i = 0; i < 100; i++) {
			// ��֤�ڶ��̵߳Ĳ����������ȡ�����Ϣ����ȷ���
			// ʹ����Queuelock
			new Thread(new MyRunable()).start();

//			new Thread(()->{
//				System.out.println(((TeminalTestNoDaoImpl) iBaseDao_TeminalTestNo).getNo("3", "12", "201809"));
//			}).start();
		}

	}

}
