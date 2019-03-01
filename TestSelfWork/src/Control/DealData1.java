package Control;
import com.eastsoft.util.Debug;

import Util.Frame645_New;
import Util.Util698;

// �����������ݵ��߳�
public class DealData1 {
	private volatile static DealData1 uniqueInstance;
	private Boolean running = true;

	public void setRunning(Boolean running) {
		this.running = running;
		if (!running)
			uniqueInstance = null;
	}

	public static DealData1 getInstance() {
		if (uniqueInstance == null) {
			synchronized (DealData1.class) {
				if (uniqueInstance == null) {
					// ˫�ؼ�����
					uniqueInstance = new DealData1();
				}
			}
		}
		return uniqueInstance;
	}

	private DealData1() {
		new Thread(()->{
			while(running){
				String msg = RecvData1.getInstance().pop();
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
			String name = msg.split(";")[2].split("@")[1];
			Util698.log(DealData1.class.getName(), "deal addr:"+name+" frame:"+frame,Debug.LOG_INFO);


			String sendData = "";
			Frame645_New frame645 = new Frame645_New(frame,"","");
			if (frame645.getControl().equals("94") && frame645.getData().substring(0,8).equals("84969601")){
//				// �յ�һ����Ŀ���Իظ���׼�������ز���������ԣ���Ҫ������������
				// �յ����ݺ󣬽��л��棬���ӵ�RecvDataBuffer������
				RecvDataBuffer.getInstance().push(msg);
//				int idx = 11 + DataConvert.String2Int(name) -1;
//				frame645.setAddr(DataConvert.int2HexString(idx, 2)+"1601810000");
//				frame645.setControl("14");
//				frame645.setData("04 96 96 02".replaceAll(" ", ""));
//				sendData = frame645.get645Frame();
			}
			if (frame645.getControl().equals("94") && frame645.getData().substring(0,8).equals("84969602")){
//				// �յ��ز���Ŀ�ظ���׼�����а������ԣ���Ҫ������ʱ���⣬��Ҫ���Ƕ����������

			}
			if (frame645.getControl().equals("94") && frame645.getData().substring(0,8).equals("84969603")){
//				// �յ�������Ŀ�ظ����������

			}

			if (!sendData.equals("")){
//				����ֱ�ӷ������ݣ���Ҫ�������ȴ���һ�������յ��˻ظ������ǳ�ʱ�ˣ����ܽ����������
//				Util698.log(DealData1.class.getName(), "send frame:"+sendData,Debug.LOG_INFO);
//				MinaTCPClient server = MainControlServer.getInstance().getServerList().get(name);
//				server.sendMessage(Util698.String2ByteArray(sendData));
			}

		}

	}

	public static void main(String[] args) {

	}

}