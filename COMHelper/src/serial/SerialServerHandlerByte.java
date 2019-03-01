package serial;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;


public class SerialServerHandlerByte extends IoHandlerAdapter {

	private IoSession SESSION;
	String paritycomID;

	public SerialServerHandlerByte(String paritycomID) {
		this.paritycomID = paritycomID;
	}

	public IoSession getSESSION() {
		return SESSION;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		SESSION = session;
		String comID = (String) session.getAttribute("comID");
		byte[] str = (byte[]) message;
		// xuky 2018.02.01 用于拼装完整报文
		String old = SoftParameter.getInstance().getRecvDataMap().get(comID);
		if (old == null) old = "";
		String thisRecv = DataConvert.bytes2HexString(str);
//		Util698.log("SerialServerHandlerByte", "thisRecv:"+thisRecv,Debug.LOG_INFO);
		final String recvData = old + thisRecv;

		if (!Util698.isCompleteFrame(recvData,comID))
			return;
		SoftParameter.getInstance().getRecvDataMap().put(comID,"");


//		String msg = "addr@" + session.getAttribute("logAddr") + ";msg@" + recvData + ";comID@" + comID;
//		Util698.log(SerialServerHandlerByte.class.getName(), "端口:"+comID+" serial recv:"+recvData.substring(recvData.length()-4,recvData.length()),
//					Debug.LOG_INFO);
		Util698.log(SerialServerHandlerByte.class.getName(), "端口:"+comID+" recv:"+recvData,Debug.LOG_INFO);
		//需要进行数据处理
		//


		HelpInstance.getInstance().getSerialList().get(paritycomID).sendMessage(recvData);;
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}


	public static void main(String[] arg){
	}
}
