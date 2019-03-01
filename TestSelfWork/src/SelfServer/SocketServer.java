package SelfServer;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.eastsoft.util.DataConvert;
import com.eastsoft.util.Debug;

import Util.Util698;

// ��PrefixMain.start()�����б�����ģ�����invoke״̬����ʱ��������
public class SocketServer {
	String localAddr = "", remoteAddr;

	private Socket CLIENT_SEND= null;

	public void invoke(Socket client) throws IOException {
		// �����µ��߳̽�����ش���
		new Thread(() -> dealNewConnect(client)).start();
	}

	public void sendSocketData(String sendData){
		sendData(CLIENT_SEND, sendData);
	};

	private void dealNewConnect(Socket client) {
		CLIENT_SEND = client;

//		String devAddr = client.getRemoteSocketAddress().toString();
		localAddr = client.getLocalSocketAddress().toString();
		remoteAddr = client.getRemoteSocketAddress().toString();

		InputStream in = null;
		try {
			in = client.getInputStream();
			// ѭ�����������ݣ�ʼ�ղ��˳�
			while (true) {
				try {
					// ����byte����ģʽ��ȡ����
					String msg = readData(in, localAddr);
					Util698.log(SocketServer.class.getName(), "SocketServer recv=>" + msg,Debug.LOG_INFO);
				} catch (Exception e) {
					// xuky 2016.08.10 ����������ݳ��ִ��󣬾��˳�
					Util698.log(SocketServer.class.getName(), "SocketServer �����˳��� localAddr:" + localAddr +" remoteAddr:"+remoteAddr,Debug.LOG_INFO);
					// xuky 2017.05.08 ����Ҫ���к�������ɾ��������е����ݵ�
					break;
				}
				Debug.sleep(50);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				in.close();
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String readData(InputStream is, String devAddr) throws Exception {
		// ����byte����ģʽ��ȡ����
		byte[] receiveByte = new byte[4096];
		int messageLength = is.read(receiveByte);
		// System.out.println("readData is.read:"+messageLength);
		// xuky 2017.05.08 �������Ӷ˶Ͽ������
		// ��������ԭ�ȵ�throws Exception
		// if (messageLength == -1){
		// return "";
		// }
		byte[] currReceiveByte = new byte[messageLength];
		for (int i = 0; i < messageLength; i++)
			currReceiveByte[i] = receiveByte[i];

		String recvData = "";
		if (currReceiveByte != null)
			// byteתΪ�ַ���
			recvData = DataConvert.bytes2HexString(currReceiveByte);

		// ͨ�ŵ�ַ ͨ������
		String msg = "addr@" + devAddr + ";" + "msg@" + recvData;

		// �յ�������ӵ�RecvData����������
//		Util698.log(SocketServer.class.getName(), "RecvData.push msg:"+msg,
//		Debug.LOG_INFO);
		RecvData.getInstance().push(msg);
		return msg;
	}

	private void sendData(Socket client, String sData) {
		OutputStream os = null;
		try {
			if (client == null){
				Util698.log(SocketServer.class.getName(), "Socket send�쳣��������  data:"+sData, Debug.LOG_INFO);
				return;
			}
			os = client.getOutputStream();
			sendData(os, sData);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// xuky 2016.08.10 �ر�OutputStream�ᵼ��socket�رգ����Բ�ִ��
				// �ο� http://blog.csdn.net/justoneroad/article/details/6962567
				// os.close();
			} catch (Exception e) {
			}
		}
	}

	private void sendData(OutputStream os, String sData) {
		// ��������
		try {
			byte[] byteData = new byte[sData.length() / 2];
			// ��16�����ַ���תΪByte����
			byteData = DataConvert.hexString2ByteArray(sData);
//			System.out.println("sendData=>" + sData);
			Util698.log(SocketServer.class.getName(), "SocketServer send=>" + sData, Debug.LOG_INFO);
			os.write(byteData);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getLocalAddr() {
		return localAddr;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public static void main(String[] args) throws IOException {

//		// �����˿�Ϊ10000
//		ServerSocket server = new ServerSocket(10000);
//		while (true) {
//			Socket socket = server.accept();
//			invoke(socket);
//		}
	}

}
