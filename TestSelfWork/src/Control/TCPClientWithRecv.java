package Control;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.eastsoft.util.DataConvert;
import com.eastsoft.util.DateTimeFun;
import com.eastsoft.util.Debug;

import Entity.TerminalLogDetail;
import Util.Util698;
import base.ByteArrayCodecFactory;

// ���������ͻ��ˡ�serial
public class TCPClientWithRecv {
	private IoSession ioSession = null;
	private String RecvData = "",IP = "";
	private int port;

	public TCPClientWithRecv(String IP, int port) {
		this.IP = IP;
		this.port = port;
		InetSocketAddress address = new InetSocketAddress(IP, port);
		connect(address);
	}

	// �Ͽ�����ͨ������ͨ���б��г���
	public void disConnect() {
		try {
			if (ioSession != null)
				ioSession.closeNow();
		} catch (Exception e) {
			Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " disConnect Exception:"+e.getMessage(),Debug.LOG_INFO);
		}
	}

	public class SerialServerHandlerByteNew extends IoHandlerAdapter {
		@Override
		public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
			Util698.log(SerialServerHandlerByteNew.class.getName(), IP+":"+port + " exceptionCaught:"+cause.getMessage(),Debug.LOG_INFO);
		}
		@Override
		public void messageReceived(IoSession session, Object message) throws Exception {
			byte[] str = (byte[]) message;
			RecvData = DataConvert.bytes2HexString(str);
			Util698.log(SerialServerHandlerByteNew.class.getName(), IP+":"+port + " RecvData:"+RecvData,Debug.LOG_INFO);
		}
	}

	private void connect(InetSocketAddress address) {
		NioSocketConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ByteArrayCodecFactory()));
		connector.setHandler(new SerialServerHandlerByteNew());
		try {
			ConnectFuture future = connector.connect(address);
			future.await();
			if (future.getException() != null) {
				Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " future.getException:"+future.getException().getMessage(),Debug.LOG_INFO);
			} else {
				// xuky 2017.08.11 ������Ҫ�ж�future.getException()Ȼ���ٽ��������getSession
				ioSession = future.getSession();
				Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " future = connector.connect end. "+DateTimeFun.getDateTimeSSS(),
						Debug.LOG_INFO);
			}
		} catch (Exception e) {
			Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " connect Exception:"+e.getMessage(),Debug.LOG_INFO);
		}
	}

	// �г�ʱ�ȴ�ʱ��ķ��ͺ���
	public String sendMessage(String data,Long timeOut) {
		// �ȴ�ʱ��
		data = data.replaceAll(" ", "");
		// ��¼����ʱ��
		String beginTime = Util698.getDateTimeSSS_new();
		Long diff = Util698.getMilliSecondBetween_new(Util698.getDateTimeSSS_new(), beginTime);
		RecvData = "nodata";
		ioSession.write(Util698.String2ByteArray(data));
		Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " SendData:"+data,Debug.LOG_INFO);
		while (RecvData.equals("nodata") && diff < timeOut){
//			Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " RecvData:"+RecvData +" diff:"+diff +" waitTime:"+timeOut,Debug.LOG_INFO);
			Debug.sleep(10);
			diff = Util698.getMilliSecondBetween_new(Util698.getDateTimeSSS_new(), beginTime);
		}
		return RecvData;
	}

	public String sendMessage(String data,Long timeOut,int sendTimes)  throws Exception {
		return sendMessage(data,timeOut,sendTimes,"", null);
	}

	// �г�ʱ�ȴ�ʱ�䡢�����Դ����ĵķ��ͺ���
	public String sendMessage(String data,Long timeOut,int sendTimes, String expect,TerminalLogDetail terminalLogDetail)  {
		data = data.replaceAll(" ", "");
		// ��¼����ʱ��
		String beginTime = "";
		Long diff = (long)0;
		RecvData = "nodata";
		Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " SendData:"+data,Debug.LOG_INFO);
		int num = 1;
		while (num <= sendTimes){
			Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " num:"+num + " sendTimes:"+sendTimes,Debug.LOG_INFO);
			// ��������Դ��������Ͷ��ִ��  ��beginTime��diff���в�����ʼ��
			beginTime = Util698.getDateTimeSSS_new();
			diff = (long)0;
			if (num != 1)  // �״η���ʱ��ǰ���Ѿ�д���˴˷���ʱ����Ϣ
				terminalLogDetail.setSendtime(beginTime);
			terminalLogDetail.setSendtimes(terminalLogDetail.getSendtimes()+1);
			Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " SendData:"+data,Debug.LOG_INFO);
			try{
				ioSession.write(Util698.String2ByteArray(data));
			}
			catch(Exception e ){
				Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " SendData Error:"+e.getMessage(),Debug.LOG_INFO);
			}
			while (RecvData.equals("nodata") && diff < timeOut){
//				Util698.log(TCPClientWithRecv.class.getName(), IP+":"+port + " RecvData:"+RecvData +" diff:"+diff +" waitTime:"+timeOut,Debug.LOG_INFO);
				Debug.sleep(10);
				diff = Util698.getMilliSecondBetween_new(Util698.getDateTimeSSS_new(), beginTime);
			}
			if (!RecvData.equals("nodata"))  {
				if (expect.equals(""))
					break; // ����յ����ݾ��˳�
				else{
					// ���expect���ݲ�Ϊ�գ���ʾ��Ҫ�Խ��յ����ݽ����ж�
					if (RecvData.indexOf(expect)>=0)
						break;
					else
						RecvData = "nodata";
				}
			}

			// ���Դ�������
			num ++;
		}
		return RecvData;
	}

	public static void main(String[] args) {
		TCPClientWithRecv minaSocketClient = new TCPClientWithRecv("129.1.51.21",30001);
		String sData = "68 15 00 43 03 11 11 11 11 00 60 6C 05 01 01 40 01 02 00 00 C6 07 16";
		sData = sData.replaceAll(" ", "");
		byte[] byteData = new byte[sData.length() / 2];
		// ��16�����ַ���תΪByte����
		byteData = DataConvert.hexString2ByteArray(sData);
//		minaSocketClient.sendMessage(byteData,(long)5000);
		minaSocketClient.sendMessage(sData,(long)5000);
	}

}
