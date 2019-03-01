package serial;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialAddress.DataBits;
import org.apache.mina.transport.serial.SerialAddress.FlowControl;
import org.apache.mina.transport.serial.SerialAddress.Parity;
import org.apache.mina.transport.serial.SerialAddress.StopBits;
import org.apache.mina.transport.serial.SerialConnector;

// 服务器、客户端、serial
public class MinaSerialServer {

	// xuky 2017.05.12 不能使用static 会导致多个MinaSerialServer实例的变量都一样
	private IoSession ioSession = null;
	// 串口的名称，需要与其他的进行区别，一般是COM1。。COM2...
	private String name = "";

	private String comID; // 测试用例中用于区分不同串口用
	private String paritycomID; // 测试用例中用于区分不同串口用


	public MinaSerialServer(String comID, String paritycomID) {
		this.comID = comID;
		this.paritycomID = paritycomID;
		init();
	}

	public void init() {

		SerialAddress address = new SerialAddress(comID, 2400, DataBits.DATABITS_8,StopBits.BITS_1, Parity.EVEN, FlowControl.NONE);
		connect(address);
	}

	// 断开串口通道，从通道列表中撤出
	public void disConnect() {
		try {
			if (ioSession != null)
				ioSession.closeNow();
		} catch (Exception e) {
		}
	}

	private void connect(SerialAddress address) {

		SerialConnector connector = new SerialConnector();
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ByteArrayCodecFactory()));
		connector.setHandler(new SerialServerHandlerByte(paritycomID));

		try {
			ConnectFuture future = connector.connect(address);

			future.await();
			if (future.getException() != null) {
				String msg = future.getException().getMessage();
				Util698.log(MinaSerialServer.class.getName(), "开启串口异常"+msg, Debug.LOG_INFO);

			} else {
				// xuky 2017.08.11 首先需要判断future.getException()然后再进行这里的getSession
				ioSession = future.getSession();
				ioSession.setAttribute("name", name);
				ioSession.setAttribute("comID", comID);
//				Util698.log(MinaSerialServer.class.getName(), "ioSession", Debug.LOG_INFO);
			}

		} catch (Exception e) {
			Util698.log(MinaSerialServer.class.getName(), "connect Exception"+e.getMessage(), Debug.LOG_INFO);
		}
		Util698.log(MinaSerialServer.class.getName(), "开启串口"+address+"完成", Debug.LOG_INFO);

	}

	public void sendMessage(byte[] data) {
		// 获取当前连接的session
		if (ioSession != null)
			ioSession.write(data);
		else
			Util698.log(MinaSerialServer.class.getName(), "sendMessage 错误 ：ioSession为空！", Debug.LOG_INFO);
	}

	public static void main(String[] args) {
//		MinaSerialServer minaSerialServer = new MinaSerialServer("71");
	}

	public void sendMessage(String recvData) {
		Util698.log(MinaSerialServer.class.getName(), "端口:"+comID+" send:"+recvData,Debug.LOG_INFO);

		// 获取当前连接的session
		if (ioSession != null)
			ioSession.write(DataConvert.hexString2ByteArray(recvData));
		else
			Util698.log(MinaSerialServer.class.getName(), "sendMessage 错误 ：ioSession为空！", Debug.LOG_INFO);

	}



}
