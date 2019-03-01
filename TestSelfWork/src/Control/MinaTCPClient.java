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

// ���������ͻ��ˡ�serial
public class MinaTCPClient {

	// xuky 2017.05.12 ����ʹ��static �ᵼ�¶��MinaSerialServerʵ���ı�����һ��
	private IoSession ioSession = null;
	private String name = "";  // name�����Ϣ
	private String logAddr = ""; // addr�ն˵�ַ��Ϣ
	private String IP = "";
	private int port;
	private int STATUS; // ״̬ 0 δ���� 1�ɹ� -1������ -2�Ѵ�

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

	// �Ͽ�����ͨ������ͨ���б��г���
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
				// xuky 2017.08.11 ������Ҫ�ж�future.getException()Ȼ���ٽ��������getSession
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
		// ����ʹ�ô��ڶ���洢ͨ�Ų���

		MinaTCPClient minaSocketClient = new MinaTCPClient("1","3702001","129.1.51.21",30001);
		String sData = "68 15 00 43 03 11 11 11 11 00 60 6C 05 01 01 40 01 02 00 00 C6 07 16";
		sData = sData.replaceAll(" ", "");
		byte[] byteData = new byte[sData.length() / 2];
		// ��16�����ַ���תΪByte����
		byteData = DataConvert.hexString2ByteArray(sData);
		minaSocketClient.sendMessage(byteData);
//

	}

}
