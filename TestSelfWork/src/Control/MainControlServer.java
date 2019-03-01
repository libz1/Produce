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

public class MainControlServer {

	private String StageNo = "3";
	private volatile static MainControlServer uniqueInstance;
	private Map<String, TCPClientWithRecv> ControlListNew = new LinkedHashMap<String, TCPClientWithRecv>();
	private IBaseDao<TerminalTestNo> iBaseDao_TeminalTestNo;
	private IBaseDao<TerminalLog> iBaseDao_TeminalLog;
	private IBaseDao<TerminalLogDetail> iBaseDao_TeminalLogDetail;

	public static MainControlServer getInstance() {
		if (uniqueInstance == null) {
			synchronized (MainControlServer.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new MainControlServer();
				}
			}
		}
		return uniqueInstance;
	}

	private MainControlServer() {
		runTest1();
	}

	private void runTest1() {
		iBaseDao_TeminalTestNo = new TerminalTestNoDaoImpl();
		iBaseDao_TeminalLog = new TerminalLogDaoImpl();
		iBaseDao_TeminalLogDetail = new TerminalLogDetailDaoImpl();
		DealData1.getInstance();
		// xuky 2018.07.31 模拟打开16路自检服务
		// int i = 1;
		int port, max = 1;
		String IP = "", name = "";
		// 批量创建与自检工装的连接

		// 12345
		for (int i = 1; i <= max; i++) {
			IP = "192.168.127.11";
			port = 7000 + i-1; // 端口号
			name = DataConvert.int2String(i - 1); // name序号信息
			ControlListNew.put(name, new TCPClientWithRecv(IP, port));
		}

		// 批量开始测试
		for (int i = 1; i <= max; i++) {
			// 发送数据时，添加超时等待时间
			// String recv = client.sendMessage(data,(long)1000);
			int no = i;
			// int no = 1;
			new Thread(() -> {
				// 通过线程的方式，实现并行发送
				sendData1(no);
			}).start();
			// xuky 2018.09.07 确保执行执行测试，因为按键的检测有一个执行的过程
			Debug.sleep(2000);
		}

	}

	private void SendDataDetail(String addr, String data645, String datetime, String name,
			TerminalLogDetail terminalLogDetail, TerminalLog terminalLog, TCPClientWithRecv client, String MAC, int i,
			int waitTime, int sendTimes, String expect)  {

		// 1、组织需要发送的数据
		Frame645_New frame645 = new Frame645_New(addr, "14", data645);
		String data = frame645.get645Frame();
		i++;  // 显示序号从1起，外部传入参数为从0起
		String begin = datetime;
		Util698.log(MainControlServer.class.getName(),
				"!!!main.send" + i + "(no=" + name + " addr=" + addr + "):" + data, Debug.LOG_INFO);
		// 2、在数据库中记录此次发送过程
		terminalLogDetail.setRunID(terminalLog.getID());
		terminalLogDetail.setAddr(terminalLog.getAddr());
		terminalLogDetail.setSend(data);
		terminalLogDetail.setSendtime0(begin);  // 首次发送数据时间
		terminalLogDetail.setSendtime(begin);
		terminalLogDetail.setSendtimes(0); // 设置发送次数为0，在后续实际发送数据时进行累加
		terminalLogDetail.setCaseno(i);
		terminalLogDetail.setExpect(expect);
		terminalLogDetail = iBaseDao_TeminalLogDetail.create(terminalLogDetail);
		// 3、实际发送数据，且等待多次重试后，得到发送结果
		String recv = client.sendMessage(data, (long) waitTime, sendTimes, expect, terminalLogDetail);
		// 4、记录发送结果
		terminalLogDetail.setRecv(recv);
		terminalLogDetail.setRecvtime(Util698.getDateTimeSSS_new());
		iBaseDao_TeminalLogDetail.update(terminalLogDetail);
		String flag = " 收到数据 ";
		if (i == 3) flag = " 自检完成 ";
		Util698.log(MainControlServer.class.getName(), "!!!main.recv" + i + flag+ "(no=" + name + " MAC=" + MAC + " addr="
				+ addr + "):" + recv + " begin:" + begin + " end:" + Util698.getDateTimeSSS_new(), Debug.LOG_INFO);
		// 准备进行其他测试
		// 1、连接某台集中器的特殊IP，进行集中器程序更新
		// 2、更新完成，重启集中器，连接129.1.22.96进行各项参数设置
		// 3、时钟检测、程序版本检测、清交采
		// 4、切换集中器IP为特殊IP
		// 5、对其他集中器按照步骤从1-4继续操作
		// 6、所以集中器执行完成后，逐个对集中器进行IP切换129.1.22.96
	}

	private Object[] STEP(int i, TCPClientWithRecv client, int step, TerminalLog terminalLog) throws Exception {
		Object[] ret = new Object[2];
		String MAC = "", data645 = "";
		String addr = DataConvert.int2HexString(11 + i - 1, 2) + "1601810000";
		String name = DataConvert.int2String(i - 1);
		TerminalLogDetail terminalLogDetail = new TerminalLogDetail();
		if (terminalLog != null)
			MAC = terminalLog.getAddr();
		// 发送时的等待时间，重试次数
		int waitTime = 0, sendTimes = 0;

		if (step == 0) {
			// 发送启动报文 129.1.22.11
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
			waitTime = 20000;
			sendTimes = 1;
			data645 = "04 96 96 01 " + mac + " "
					+ datetime.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").substring(2, 14);
			SendDataDetail(addr, data645, datetime, name, terminalLogDetail, terminalLog, client, MAC, step, waitTime, sendTimes,
					"84969601");

		}
		if (step == 1) {
			while (true) {
				if (SendLock.getInstance().isUsable()) {
					data645 = "04 96 96 02";
					String datetime = DateTimeFun.getDateTimeSSS();
					waitTime = 20000;
					sendTimes = 1;
					SendDataDetail(addr, data645, datetime, name, terminalLogDetail, terminalLog, client, MAC, step,
							waitTime, sendTimes, "84969602");
					// xuky 2018.08.28 如果收到了数据，就可以解锁了
					SendLock.getInstance().setEnable();
					break;
				}
				Debug.sleep(200);
			}
		}
		if (step == 2) {
			// 间隔5秒后，进行按键检测，因为按键检测的执行过程可能会比距长
			Debug.sleep(5000);
			data645 = "04 96 96 03";
			String datetime = DateTimeFun.getDateTimeSSS();
			waitTime = 50000;
			sendTimes = 1;
			SendDataDetail(addr, data645, datetime, name, terminalLogDetail, terminalLog, client, MAC, step, waitTime, sendTimes,
					"84969603");
		}

		ret[0] = terminalLog;
		ret[1] = terminalLogDetail;
		return ret;
	}

	public void sendData1(int i) {
		String name = DataConvert.int2String(i - 1), recv = ""; // name序号信息
		TCPClientWithRecv client = ControlListNew.get(name);
		TerminalLog terminalLog = null;
		for (int j = 0; j < 3; j++) {

			// 通过STEP()进行三次调用，实现与一台自检工装的交互过程
			Object[] recvArray = null;
			try{
				recvArray = STEP(i, client, j, terminalLog);
			}
			catch (Exception e){
				Util698.log(MainControlServer.class.getName(),
						"sendData1 Error:" + e.getMessage(), Debug.LOG_INFO);
			}
			if (recvArray == null)
				return;
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
						// 自检测试完成，测试结果为通过  可以进行后续的参数设置等操作，但是必须是独占性的操作
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
		// 使用设备的MAC地址作为设备的唯一标示 测试前计算产生MAC地址信息，以此MAC地址信息进行唯一标识
		// MAC 02:ID:YY:MM:tmpsno/256:tmpsno%256
		// [ID]FF：高位的F表台号（1-16），低位的F表位号（0-15） 一般都是16表位的台体
		// [tmpsno] tmpsno 这个 是测试的序号 是单表位的测试序号，FFFF，前面是取整，后面是取余
		// xuky 2018.08.24 因为序号为每个表台+表位、每个月的流水号，所以需要记录到数据库中进行保存
		// 一个表台使用一个软件，多个表位共用一个软件

		Object[] ret = new Object[2];
		datetime = datetime.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
//		String MeterNo = DataConvert.int2String(i - 1); // xuky 2018.09.05  注意台体编号是从0-15的
		// xuky 2019.02.13 为了避免无解，直接表位信息从1起到32
		String MeterNo = DataConvert.int2String(i);
		int TestNo = ((TerminalTestNoDaoImpl) iBaseDao_TeminalTestNo).getNo(StageNo, MeterNo, datetime.substring(2, 6)); // 当前表位的流水号
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
		MainControlServer.getInstance();
	}
}
