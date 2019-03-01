package Util;
import com.eastsoft.util.Debug;

import SelfServer.MainServer;
import SelfServer.SelfServer;
import SelfServer.SocketServer;

// ����������ݵ��߳�
public class DealData {
	private volatile static DealData uniqueInstance;
	private Boolean running = true;

	public void setRunning(Boolean running) {
		this.running = running;
		if (!running)
			uniqueInstance = null;
	}

	public static DealData getInstance() {
		if (uniqueInstance == null) {
			synchronized (DealData.class) {
				if (uniqueInstance == null) {
					// ˫�ؼ�����
					uniqueInstance = new DealData();
				}
			}
		}
		return uniqueInstance;
	}

	private DealData() {
		new Thread(()->{
			while(running){
				String msg = RecvData.getInstance().pop();
				// �����߳���ִ�У���Ϊ���ܻ�Ҫ��ʱ�������ݻظ��Ȳ���
				new Thread(()->{
					doSomeThing(msg);
				}).start();
				Debug.sleep(50);
			}
		}).start();
	}

	private void doSomeThing(String msg){
		if (!msg.equals("")) {
			String frame = msg.split(";")[1].split("@")[1];
			String info = msg.split(";")[0].split("@")[1];
			Util698.log(DealData.class.getName(), "recv info:"+info+" frame:"+frame,Debug.LOG_INFO);

			info = info.substring(1,info.length());
			SelfServer selfServer = MainServer.getInstance().getServerList().get(info);

			String sendData = "";
			Frame645_New frame645 = new Frame645_New(frame,"","");
			if (frame645.getControl().equals("14") && frame645.getData().substring(0,8).equals("04969601")){
				frame645.setControl("94");
				frame645.setData("84 96 96 01 01 01 01 02 01 03 01 04 01".replaceAll(" ", ""));
				sendData = frame645.get645Frame();
			}
			if (frame645.getControl().equals("14") && frame645.getData().substring(0,8).equals("04969602")){
				frame645.setControl("94");
				frame645.setData("84 96 96 02 01 09 01 0C 01".replaceAll(" ", ""));
				sendData = frame645.get645Frame();
			}
			if (frame645.getControl().equals("14") && frame645.getData().substring(0,8).equals("04969603")){
				frame645.setControl("94");
				frame645.setData("84 96 96 03 01 0E 01".replaceAll(" ", ""));
				sendData = frame645.get645Frame();
			}

			if (!sendData.equals("")){
				SocketServer handle = selfServer.getHandle();
				Util698.log(DealData.class.getName(), "send from:"+handle.getLocalAddr()+" to:"+handle.getRemoteAddr()+ " frame:"+sendData,Debug.LOG_INFO);
				handle.sendSocketData(sendData);
			}
		}

	}

	public static void main(String[] args) {

	}

}
