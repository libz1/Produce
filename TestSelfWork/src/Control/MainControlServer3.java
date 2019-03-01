package Control;

import java.util.LinkedHashMap;
import java.util.Map;

import com.eastsoft.util.DataConvert;
import com.eastsoft.util.DateTimeFun;
import com.eastsoft.util.Debug;

import DataBase.IBaseDao;
import Entity.TerminalLog;
import Entity.TerminalLogDaoImpl;
import Entity.TerminalLogDetail;
import Entity.TerminalLogDetailDaoImpl;
import Entity.TerminalTestNo;
import Entity.TerminalTestNoDaoImpl;
import Util.Frame645_New;
import Util.Util698;

public class MainControlServer3 {

	private String StageNo = "3";
	private volatile static MainControlServer3 uniqueInstance;
	private Map<String, TCPClientWithRecv> ControlListNew = new LinkedHashMap<String, TCPClientWithRecv>();
	private IBaseDao<TerminalTestNo> iBaseDao_TeminalTestNo;
	private IBaseDao<TerminalLog> iBaseDao_TeminalLog;
	private IBaseDao<TerminalLogDetail> iBaseDao_TeminalLogDetail;

	public static MainControlServer3 getInstance() {
		if (uniqueInstance == null) {
			synchronized (MainControlServer3.class) {
				if (uniqueInstance == null) {
					// ˫�ؼ�����
					uniqueInstance = new MainControlServer3();
				}
			}
		}
		return uniqueInstance;
	}

	private MainControlServer3() {
		runTest1();
	}

	private void runTest1() {
		iBaseDao_TeminalTestNo = new TerminalTestNoDaoImpl();
		iBaseDao_TeminalLog = new TerminalLogDaoImpl();
		iBaseDao_TeminalLogDetail = new TerminalLogDetailDaoImpl();
		DealData1.getInstance();
		// xuky 2018.07.31 ģ���16·�Լ����
		// int i = 1;
		int port;
		String IP = "", name = "";
		// �����������Լ칤װ������
		for (int i = 1; i <= 16; i++) {
			IP = "129.1.51.21";
			port = 30000 + i; // �˿ں�
			name = DataConvert.int2String(i - 1); // name�����Ϣ
			ControlListNew.put(name, new TCPClientWithRecv(IP, port));
		}

		// ������ʼ����
		for (int i = 1; i <= 16; i++) {
			// ��������ʱ����ӳ�ʱ�ȴ�ʱ��
			// String recv = client.sendMessage(data,(long)1000);
			int no = i;
			// int no = 1;
			new Thread(() -> {
				// ͨ���̵߳ķ�ʽ��ʵ�ֲ��з���
				sendData1(no);
			}).start();
			// xuky 2018.09.07 ȷ��ִ��ִ�в��ԣ���Ϊ�����ļ����һ��ִ�еĹ���
			Debug.sleep(2000);
		}

	}

	private void SendDataDetail(String addr, String data645, String datetime, String name,
			TerminalLogDetail terminalLogDetail, TerminalLog terminalLog, TCPClientWithRecv client, String MAC, int i,
			int waitTime, int retry, String expect) {

		// 1����֯��Ҫ���͵�����
		Frame645_New frame645 = new Frame645_New(addr, "14", data645);
		String data = frame645.get645Frame();
		i++;  // ��ʾ��Ŵ�1���ⲿ�������Ϊ��0��
		String begin = datetime;
		Util698.log(MainControlServer3.class.getName(),
				"!!!main.send" + i + "(no=" + name + " addr=" + addr + "):" + data, Debug.LOG_INFO);
		// 2�������ݿ��м�¼�˴η��͹���
		terminalLogDetail.setRunID(terminalLog.getID());
		terminalLogDetail.setAddr(terminalLog.getAddr());
		terminalLogDetail.setSend(data);
		terminalLogDetail.setSendtime0(begin);
		terminalLogDetail.setSendtime(begin);
		terminalLogDetail.setSendtimes(1);
		terminalLogDetail.setCaseno(i);
		terminalLogDetail.setExpect(expect);
		terminalLogDetail = iBaseDao_TeminalLogDetail.create(terminalLogDetail);
		// 3��ʵ�ʷ������ݣ��ҵȴ�������Ժ󣬵õ����ͽ��
		String recv = client.sendMessage(data, (long) waitTime, retry, expect, terminalLogDetail);
		// 4����¼���ͽ��
		terminalLogDetail.setRecv(recv);
		terminalLogDetail.setRecvtime(Util698.getDateTimeSSS_new());
		iBaseDao_TeminalLogDetail.update(terminalLogDetail);
		String flag = " �յ����� ";
		if (i == 3) flag = " �Լ���� ";
		Util698.log(MainControlServer3.class.getName(), "!!!main.recv" + i + flag+ "(no=" + name + " MAC=" + MAC + " addr="
				+ addr + "):" + recv + " begin:" + begin + " end:" + Util698.getDateTimeSSS_new(), Debug.LOG_INFO);

	}

	private Object[] STEP(int i, TCPClientWithRecv client, int step, TerminalLog terminalLog) {
		Object[] ret = new Object[2];
		String MAC = "", data645 = "";
		String addr = DataConvert.int2HexString(11 + i - 1, 2) + "1601810000";
		String name = DataConvert.int2String(i - 1);
		TerminalLogDetail terminalLogDetail = new TerminalLogDetail();
		if (terminalLog != null)
			MAC = terminalLog.getAddr();
		int waitTime = 0, retry = 0;

		if (step == 0) {
			// ������������ 129.1.22.11
			String datetime = DateTimeFun.getDateTimeSSS();
			Object[] macAndNo = getMAC(datetime, i);
			String mac = (String) macAndNo[1];
			int TestNo = (int) macAndNo[0];

			terminalLog = new TerminalLog();
			terminalLog.setAddr(mac);
			terminalLog.setStageNo(StageNo);
			terminalLog.setMeterNo(DataConvert.int2String(i - 1));
			terminalLog.setYyyymm(datetime.substring(0, 7));
			terminalLog.setTestNo(TestNo);
			terminalLog.setOpName("admin");
			terminalLog.setOpBTime(datetime);
			terminalLog.setOpETime("");
			terminalLog.setResult("");
			terminalLog.setErrResult("");
			terminalLog = iBaseDao_TeminalLog.create(terminalLog);
			waitTime = 3000;
			retry = 10;
			data645 = "04 96 96 01 " + mac + " "
					+ datetime.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").substring(2, 14);
			SendDataDetail(addr, data645, datetime, name, terminalLogDetail, terminalLog, client, MAC, step, waitTime, retry,
					"84969601");

		}
		if (step == 1) {
			while (true) {
				if (SendLock.getInstance().isUsable()) {
					data645 = "04 96 96 02";
					String datetime = DateTimeFun.getDateTimeSSS();
					waitTime = 3000;
					retry = 10;
					SendDataDetail(addr, data645, datetime, name, terminalLogDetail, terminalLog, client, MAC, step,
							waitTime, retry, "84969602");
					// xuky 2018.08.28 ����յ������ݣ��Ϳ��Խ�����
					SendLock.getInstance().setEnable();
					break;
				}
				Debug.sleep(200);
			}
		}
		if (step == 2) {
			// ���5��󣬽��а�����⣬��Ϊ��������ִ�й��̿��ܻ�Ⱦ೤
			Debug.sleep(5000);
			data645 = "04 96 96 03";
			String datetime = DateTimeFun.getDateTimeSSS();
			waitTime = 5000;
			retry = 20;
			SendDataDetail(addr, data645, datetime, name, terminalLogDetail, terminalLog, client, MAC, step, waitTime, retry,
					"84969603");
		}

		ret[0] = terminalLog;
		ret[1] = terminalLogDetail;
		return ret;
	}

	public void sendData1(int i) {
		String name = DataConvert.int2String(i - 1), recv = ""; // name�����Ϣ
		TCPClientWithRecv client = ControlListNew.get(name);
		TerminalLog terminalLog = null;
		for (int j = 0; j < 3; j++) {
			Object[] recvArray = STEP(i, client, j, terminalLog);
			TerminalLogDetail terminalLogDetail = (TerminalLogDetail) recvArray[1];
			if (j == 0)
				terminalLog = (TerminalLog) recvArray[0];
			recv = terminalLogDetail.getRecv();
			if (recv.equals("nodata")) {
				terminalLog.setOpETime(DateTimeFun.getDateTimeSSS());
				terminalLog.setResult("0");
				terminalLog.setErrResult(DataConvert.int2String(i));
				iBaseDao_TeminalLog.update(terminalLog);
				break;
			} else {
				Frame645_New frame645 = new Frame645_New(recv);
				Boolean ok = false;
				if (frame645.getControl().equals("94")) {
					if (j == 0 && frame645.getData().subSequence(0, 8).equals("84969601"))
						ok = true;
					if (j == 1 && frame645.getData().subSequence(0, 8).equals("84969602"))
						ok = true;
					if (j == 2 && frame645.getData().subSequence(0, 8).equals("84969603")) {
						ok = true;
						terminalLog.setOpETime(terminalLogDetail.getRecvtime());
						terminalLog.setResult("1");
						iBaseDao_TeminalLog.update(terminalLog);
					}
				}
				if (!ok) {
					terminalLog.setOpETime(DateTimeFun.getDateTimeSSS());
					terminalLog.setResult("0");
					terminalLog.setErrResult(DataConvert.int2String(i));
					iBaseDao_TeminalLog.update(terminalLog);
					break;
				}
			}
		}
	}

	private Object[] getMAC(String datetime, int i) {
		// ʹ���豸��MAC��ַ��Ϊ�豸��Ψһ��ʾ ����ǰ�������MAC��ַ��Ϣ���Դ�MAC��ַ��Ϣ����Ψһ��ʶ
		// MAC 02:ID:YY:MM:tmpsno/256:tmpsno%256
		// [ID]FF����λ��F��̨�ţ�1-16������λ��F��λ�ţ�0-15�� һ�㶼��16��λ��̨��
		// [tmpsno] tmpsno ��� �ǲ��Ե���� �ǵ���λ�Ĳ�����ţ�FFFF��ǰ����ȡ����������ȡ��
		// xuky 2018.08.24 ��Ϊ���Ϊÿ����̨+��λ��ÿ���µ���ˮ�ţ�������Ҫ��¼�����ݿ��н��б���
		// һ����̨ʹ��һ������������λ����һ�����

		Object[] ret = new Object[2];
		datetime = datetime.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		String MeterNo = DataConvert.int2String(i - 1); // xuky 2018.09.05
														// ע��̨�����Ǵ�0-15��
		int TestNo = ((TerminalTestNoDaoImpl) iBaseDao_TeminalTestNo).getNo(StageNo, MeterNo, datetime.substring(2, 6)); // ��ǰ��λ����ˮ��
		String id1 = DataConvert.IntToBinString(DataConvert.String2Int(StageNo)).substring(28);
		String id2 = DataConvert.IntToBinString(DataConvert.String2Int(MeterNo)).substring(28);
		String id = DataConvert.binStr2HexString(id1 + id2, 2);
		String tmpsno = DataConvert.int2HexString(TestNo / 256, 2) + DataConvert.int2HexString(TestNo % 256, 2);
		String mac = "02" + id + datetime.substring(2, 6) + tmpsno;
		ret[0] = TestNo;
		ret[1] = mac;
		return ret;
	}

	public static void main(String args[]) {
		MainControlServer3.getInstance();
	}
}
