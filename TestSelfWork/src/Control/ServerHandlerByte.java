package Control;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.eastsoft.util.DataConvert;
import com.eastsoft.util.Debug;

import Util.RecvData;
import Util.SoftParameter;
import Util.Util698;

public class ServerHandlerByte extends IoHandlerAdapter {

	private IoSession SESSION;

	ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 50, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());

	public IoSession getSESSION() {
		return SESSION;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		Util698.log(ServerHandlerByte.class.getName(), "exceptionCaught:"+cause.getMessage(),Debug.LOG_INFO);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		SESSION = session;
		String comID = (String) session.getAttribute("comID");
		byte[] str = (byte[]) message;

		// xuky 2018.02.01 用于拼装完整报文
		final String recvData = SoftParameter.getInstance().getRecvDataMap().get(comID) + DataConvert.bytes2HexString(str);
		if (!checkData(recvData,comID))
			return;


		SoftParameter.getInstance().getRecvDataMap().put(comID,"");

		String msg = "addr@" + session.getAttribute("logAddr") + ";msg@" + recvData + ";comID@" + comID;
		Util698.log(ServerHandlerByte.class.getName(), "3push 端口:"+comID+" serial recv:"+recvData.substring(recvData.length()-4,recvData.length()),
					Debug.LOG_INFO);
		RecvData.getInstance().push(msg);

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

	private Boolean checkData(String recvData, String comID){
		if (!recvData.substring(recvData.length() - 2).equals("16")) {
			SoftParameter.getInstance().getRecvDataMap().put(comID,recvData);
			return false;
		} else {
			// 首先进行698.45协议报文的判断
			if (recvData.length() >= 4){
				if (recvData.substring(0, 2).equals("68")){
					int len = DataConvert.hexString2Int(recvData.substring(2, 4));
					if (recvData.length() == len *2 + 4)
//						System.out.println("checkData => 698.45 :"+recvData);
						return true;
				}

			}

			int pos1 = recvData.indexOf("68");
			int pos2 = recvData.indexOf("68", pos1+1);
//			System.out.println("68 pos ->" + (pos2-pos1) );

			// xuky 2017.07.19 出现了设备地址中有16的情况
			// 645报文中应该有两处68和最后的16
			if (pos1 < 0 || pos2 < 0  ){
				SoftParameter.getInstance().getRecvDataMap().put(comID,recvData);
				return false;
			}

			// xuky 2017.09.20 出现了设备地址中有68的情况 000000611668
			// FE FE FE FE 68 68 16 61 00 00 00 68 95 00 11 16
			// FE FE FE FE 68 68 16
			if (pos2 - pos1 < 14 ){
				// 判断两个68之间的字符个数
				int pos3 = recvData.indexOf("68", pos2+1);
				if (pos3 < 0){
					SoftParameter.getInstance().getRecvDataMap().put(comID,recvData);
					return false;
				}
				else{
					if (pos3 - pos1 < 14 ){
						int pos4 = recvData.indexOf("68", pos3+1);
						if (pos4 < 0){
							SoftParameter.getInstance().getRecvDataMap().put(comID,recvData);
							return false;
						}
					}
				}
			}
		}
//		System.out.println("checkData => 645 :"+recvData);
		return true;
	}

	public static void main(String[] arg){

	}
}
