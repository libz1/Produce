package SelfServer;
import java.util.Random;

import com.eastsoft.util.Debug;

import Util.Frame645_New;
import Util.Util698;

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
			String part_frame = frame.substring(frame.length()-4,frame.length());
			Util698.log(DealData.class.getName(), "recv info:"+info+" frame:"+part_frame,Debug.LOG_INFO);

			// msg��Ϣ��addr@/129.1.51.21:30008;msg@680...   ���Ի��info����Ҫȥ������ߵ�/
			info = info.substring(1,info.length());

			String sendData = "";
			Frame645_New frame645 = new Frame645_New(frame);
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
				SelfServer selfServer = MainSelfServer.getInstance().getServerList().get(info);
				SocketServer handle = selfServer.getHandle();

				// xuky 2018.08.14 �ӳ�һ����ʱ�䣬Ȼ��������ݻظ���ģ����Թ����У��Լ칤װ��Ҫ����һ��ʱ����ٷ��ؽ��
				Random rand = new Random();
				int rand_val = rand.nextInt(1000);
				Debug.sleep(8000+rand_val);

				Util698.log(DealData.class.getName(), "send from:"+handle.getLocalAddr()+" to:"+handle.getRemoteAddr()+ " frame:"+sendData.substring(sendData.length()-4,sendData.length())+" rand_val:"+rand_val+" recv:"+part_frame,Debug.LOG_INFO);
				handle.sendSocketData(sendData);
			}
		}

	}

	public static void main(String[] args) {

	}

}
