package SelfServer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.eastsoft.util.Debug;

import Util.Publisher;
import Util.Util698;


public class SelfServer {
	private String IP = "129.1.51.21";
	private int port = 30001;
	private String addr = "37020051";
	private ServerSocket socketServer;
	private SocketServer handle;

	public SelfServer(String IP, int port, String addr){
		this.IP = IP;
		this.port = port;
		this.addr = addr;
		init();
	}

	public SelfServer(){
		init();
	}

	private void init(){
		new Thread(() -> {
			initInThread();
		}).start();
	}

	private void initInThread(){
		InetAddress bindAddr;
		try {
			bindAddr = InetAddress.getByName(IP);
			socketServer = new ServerSocket(port, 8, bindAddr);
			String msg = "�ɹ�new ServerSocket IP:"+IP +" port:"+port+" addr:"+addr;
			Util698.log(SelfServer.class.getName(), msg,Debug.LOG_INFO);

			Object[] s = { "SelfServer_msg", "", msg };
			Publisher.getInstance().publish(s);

			while (true) {
				// ÿ�����µĿͻ������ӵ������������ʱ������һ���µ�socket <== {Socket socket =
				// server.accept();}
				// ��SocketServer.invoke�����ж���Щ��socket���д����ֱ����̣߳�������ش���
				Socket socket = socketServer.accept();
				msg = "���µ�����  Ŀ��:"+socket.getLocalSocketAddress()+" Դ:"+socket.getRemoteSocketAddress();
				Util698.log(SelfServer.class.getName(), msg, Debug.LOG_INFO);
				Object[] s1 = { "SelfServer_msg", "", msg };
				Publisher.getInstance().publish(s1);

				handle = new SocketServer();
				handle.invoke(socket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public SocketServer getHandle() {
		return handle;
	}

	public static void main(String args[]){
		DealData.getInstance();
		new SelfServer();
	}
}
