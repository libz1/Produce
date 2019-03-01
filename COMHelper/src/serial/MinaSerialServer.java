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

// ���������ͻ��ˡ�serial
public class MinaSerialServer {

	// xuky 2017.05.12 ����ʹ��static �ᵼ�¶��MinaSerialServerʵ���ı�����һ��
	private IoSession ioSession = null;
	// ���ڵ����ƣ���Ҫ�������Ľ�������һ����COM1����COM2...
	private String name = "";

	private String comID; // �����������������ֲ�ͬ������
	private String paritycomID; // �����������������ֲ�ͬ������


	public MinaSerialServer(String comID, String paritycomID) {
		this.comID = comID;
		this.paritycomID = paritycomID;
		init();
	}

	public void init() {

		SerialAddress address = new SerialAddress(comID, 2400, DataBits.DATABITS_8,StopBits.BITS_1, Parity.EVEN, FlowControl.NONE);
		connect(address);
	}

	// �Ͽ�����ͨ������ͨ���б��г���
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
				Util698.log(MinaSerialServer.class.getName(), "���������쳣"+msg, Debug.LOG_INFO);

			} else {
				// xuky 2017.08.11 ������Ҫ�ж�future.getException()Ȼ���ٽ��������getSession
				ioSession = future.getSession();
				ioSession.setAttribute("name", name);
				ioSession.setAttribute("comID", comID);
//				Util698.log(MinaSerialServer.class.getName(), "ioSession", Debug.LOG_INFO);
			}

		} catch (Exception e) {
			Util698.log(MinaSerialServer.class.getName(), "connect Exception"+e.getMessage(), Debug.LOG_INFO);
		}
		Util698.log(MinaSerialServer.class.getName(), "��������"+address+"���", Debug.LOG_INFO);

	}

	public void sendMessage(byte[] data) {
		// ��ȡ��ǰ���ӵ�session
		if (ioSession != null)
			ioSession.write(data);
		else
			Util698.log(MinaSerialServer.class.getName(), "sendMessage ���� ��ioSessionΪ�գ�", Debug.LOG_INFO);
	}

	public static void main(String[] args) {
//		MinaSerialServer minaSerialServer = new MinaSerialServer("71");
	}

	public void sendMessage(String recvData) {
		Util698.log(MinaSerialServer.class.getName(), "�˿�:"+comID+" send:"+recvData,Debug.LOG_INFO);

		// ��ȡ��ǰ���ӵ�session
		if (ioSession != null)
			ioSession.write(DataConvert.hexString2ByteArray(recvData));
		else
			Util698.log(MinaSerialServer.class.getName(), "sendMessage ���� ��ioSessionΪ�գ�", Debug.LOG_INFO);

	}



}
