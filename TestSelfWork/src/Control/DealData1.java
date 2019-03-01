package Control;
import com.eastsoft.util.Debug;

import Util.Frame645_New;
import Util.Util698;

// 处理接收数据的线程
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
					// 双重检查加锁
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
				// 放在线程中执行，因为可能还要延时进行数据回复等操作
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
//				// 收到一般项目测试回复，准备进行载波及红外测试，需要考虑阻塞问题
				// 收到数据后，进行缓存，添加到RecvDataBuffer队列中
				RecvDataBuffer.getInstance().push(msg);
//				int idx = 11 + DataConvert.String2Int(name) -1;
//				frame645.setAddr(DataConvert.int2HexString(idx, 2)+"1601810000");
//				frame645.setControl("14");
//				frame645.setData("04 96 96 02".replaceAll(" ", ""));
//				sendData = frame645.get645Frame();
			}
			if (frame645.getControl().equals("94") && frame645.getData().substring(0,8).equals("84969602")){
//				// 收到载波项目回复，准备进行按键测试，需要考虑延时问题，需要考虑多次重试问题

			}
			if (frame645.getControl().equals("94") && frame645.getData().substring(0,8).equals("84969603")){
//				// 收到按键项目回复，测试完成

			}

			if (!sendData.equals("")){
//				不能直接发送数据，需要处理，等待上一个报文收到了回复，或是超时了，才能进行这个操作
//				Util698.log(DealData1.class.getName(), "send frame:"+sendData,Debug.LOG_INFO);
//				MinaTCPClient server = MainControlServer.getInstance().getServerList().get(name);
//				server.sendMessage(Util698.String2ByteArray(sendData));
			}

		}

	}

	public static void main(String[] args) {

	}

}
