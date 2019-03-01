package Control;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.eastsoft.util.DataConvert;
import com.eastsoft.util.DateTimeFun;
import com.eastsoft.util.Debug;

import Util.Util698;
import base.ByteArrayCodecFactory;

// 服务器、客户端、serial
public class MinaTCPClient {

	// xuky 2017.05.12 不能使用static 会导致多个MinaSerialServer实例的变量都一样
	private IoSession ioSession = null;
	private String name = "";  // name序号信息
	private String logAddr = ""; // addr终端地址信息
	private String IP = "";
	private int port;
	private int STATUS; // 状态 0 未连接 1成功 -1不存在 -2已打开

	public MinaTCPClient(String name, String logAddr, String IP, int port) {
		this.name = name;
		this.logAddr = logAddr;
		this.IP = IP;
		this.port = port;
		init();


	}

	public void init() {
		STATUS = 0;

		InetSocketAddress address = new InetSocketAddress(IP, port);
		connect(address);
	}

	// 断开串口通道，从通道列表中撤出
	public void disConnect() {
		try {
			if (ioSession != null)
				ioSession.closeNow();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void connect(InetSocketAddress address) {

		NioSocketConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ByteArrayCodecFactory()));
		connector.setHandler(new SerialServerHandlerByte());
		try {
			ConnectFuture future = connector.connect(address);
			future.await();
			if (future.getException() != null) {
				String msg = future.getException().getMessage();
				if (msg.equals("Serial port not found"))
					STATUS = -1;
				else
					STATUS = -2;
			} else {
				// xuky 2017.08.11 首先需要判断future.getException()然后再进行这里的getSession
				ioSession = future.getSession();
				ioSession.setAttribute("logAddr", logAddr);
				ioSession.setAttribute("name", name);
				STATUS = 1;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Util698.log(MinaTCPClient.class.getName(), "future = connector.connect end. "+IP+":"+port + " "+DateTimeFun.getDateTimeSSS(),
				Debug.LOG_INFO);

	}

	public void sendMessage(byte[] data) {
		ioSession.write(data);
	}

	public String getName() {
		return name;
	}

	public int getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}

	public static void main(String[] args) {
		// 继续使用串口对象存储通信参数

		MinaTCPClient minaSocketClient = new MinaTCPClient("1","3702001","129.1.51.21",30001);
		String sData = "68 15 00 43 03 11 11 11 11 00 60 6C 05 01 01 40 01 02 00 00 C6 07 16";
		sData = sData.replaceAll(" ", "");
		byte[] byteData = new byte[sData.length() / 2];
		// 将16进制字符串转为Byte数组
		byteData = DataConvert.hexString2ByteArray(sData);
		minaSocketClient.sendMessage(byteData);
//

	}

}
