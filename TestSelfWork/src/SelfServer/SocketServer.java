package SelfServer;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.eastsoft.util.DataConvert;
import com.eastsoft.util.Debug;

import Util.Util698;

// 在PrefixMain.start()函数中被激活的，处于invoke状态，随时接收数据
public class SocketServer {
	String localAddr = "", remoteAddr;

	private Socket CLIENT_SEND= null;

	public void invoke(Socket client) throws IOException {
		// 开启新的线程进行相关处理
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
			// 循环，接收数据，始终不退出
			while (true) {
				try {
					// 按照byte流的模式读取数据
					String msg = readData(in, localAddr);
					Util698.log(SocketServer.class.getName(), "SocketServer recv=>" + msg,Debug.LOG_INFO);
				} catch (Exception e) {
					// xuky 2016.08.10 如果接收数据出现错误，就退出
					Util698.log(SocketServer.class.getName(), "SocketServer 链接退出！ localAddr:" + localAddr +" remoteAddr:"+remoteAddr,Debug.LOG_INFO);
					// xuky 2017.05.08 还需要进行后续处理，删除界面的中的数据等
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
		// 按照byte流的模式读取数据
		byte[] receiveByte = new byte[4096];
		int messageLength = is.read(receiveByte);
		// System.out.println("readData is.read:"+messageLength);
		// xuky 2017.05.08 处理连接端断开的情况
		// 还是沿用原先的throws Exception
		// if (messageLength == -1){
		// return "";
		// }
		byte[] currReceiveByte = new byte[messageLength];
		for (int i = 0; i < messageLength; i++)
			currReceiveByte[i] = receiveByte[i];

		String recvData = "";
		if (currReceiveByte != null)
			// byte转为字符串
			recvData = DataConvert.bytes2HexString(currReceiveByte);

		// 通信地址 通信内容
		String msg = "addr@" + devAddr + ";" + "msg@" + recvData;

		// 收到数据添加到RecvData单例对象中
//		Util698.log(SocketServer.class.getName(), "RecvData.push msg:"+msg,
//		Debug.LOG_INFO);
		RecvData.getInstance().push(msg);
		return msg;
	}

	private void sendData(Socket client, String sData) {
		OutputStream os = null;
		try {
			if (client == null){
				Util698.log(SocketServer.class.getName(), "Socket send异常，无连接  data:"+sData, Debug.LOG_INFO);
				return;
			}
			os = client.getOutputStream();
			sendData(os, sData);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// xuky 2016.08.10 关闭OutputStream会导致socket关闭，所以不执行
				// 参考 http://blog.csdn.net/justoneroad/article/details/6962567
				// os.close();
			} catch (Exception e) {
			}
		}
	}

	private void sendData(OutputStream os, String sData) {
		// 发送数据
		try {
			byte[] byteData = new byte[sData.length() / 2];
			// 将16进制字符串转为Byte数组
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

//		// 监听端口为10000
//		ServerSocket server = new ServerSocket(10000);
//		while (true) {
//			Socket socket = server.accept();
//			invoke(socket);
//		}
	}

}
