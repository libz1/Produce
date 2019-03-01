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

public class MainControlServer2 {

	private String StageNo = "3";
	private volatile static MainControlServer2 uniqueInstance;
	private Map<String, MinaTCPClient> ControlList = new LinkedHashMap<String, MinaTCPClient>();
	private Map<String, TCPClientWithRecv> ControlListNew = new LinkedHashMap<String, TCPClientWithRecv>();
	private IBaseDao<TerminalTestNo> iBaseDao_TeminalTestNo;
	private IBaseDao<TerminalLog> iBaseDao_TeminalLog;
	private IBaseDao<TerminalLogDetail> iBaseDao_TeminalLogDetail;

	public static MainControlServer2 getInstance() {
		if (uniqueInstance == null) {
			synchronized (MainControlServer2.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new MainControlServer2();
				}
			}
		}
		return uniqueInstance;
	}

	private MainControlServer2() {
		runTest1();
	}

	private void runTest() {
		iBaseDao_TeminalTestNo = new TerminalTestNoDaoImpl();
		iBaseDao_TeminalLog = new TerminalLogDaoImpl();
		iBaseDao_TeminalLogDetail = new TerminalLogDetailDaoImpl();
		DealData1.getInstance();
		// xuky 2018.07.31 模拟打开16路自检服务
		// int i = 1;
		int a = 370200501, port = 30001;
		String IP = "", name = "", addr = "";
		// 批量创建与自检工装的连接
		for (int i = 1; i <= 16; i++) {
			a = 37020050 + i;
			IP = "129.1.51.21";
			port = 30000 + i; // 端口号
			name = DataConvert.int2String(i - 1); // name序号信息
			addr = DataConvert.int2String(a); // addr终端地址信息
			ControlListNew.put(name, new TCPClientWithRecv(IP, port));
		}

		// 批量开始测试
		for (int i = 1; i <= 16; i++) {
			a = 37020050 + i;
			IP = "129.1.51.21";
			name = DataConvert.int2String(i - 1); // name序号信息
			port = 30000 + i; // 端口号
			TCPClientWithRecv client = ControlListNew.get(name);

			Frame645_New frame645 = new Frame645_New();
			// 发送启动报文 129.1.22.11
			int idx = 11 + i - 1;
			addr = DataConvert.int2HexString(idx, 2) + "1601810000";
			frame645.setAddr(addr);
			frame645.setControl("14");

			// 使用设备的MAC地址作为设备的唯一标示 测试前计算产生MAC地址信息，以此MAC地址信息进行唯一标识
			// MAC 02:ID:YY:MM:tmpsno/256:tmpsno%256
			// [ID]FF：高位的F表台号（1-16），低位的F表位号（0-15） 一般都是16表位的台体
			// [tmpsno] tmpsno 这个 是测试的序号 是单表位的测试序号，FFFF，前面是取整，后面是取余
			// xuky 2018.08.24 因为序号为每个表台+表位、每个月的流水号，所以需要记录到数据库中进行保存
			// 一个表台使用一个软件，多个表位公用一个软件
			String datetime = DateTimeFun.getDateTimeSSS();
			String datetime1 = datetime;
			datetime = datetime.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
			String MeterNo = DataConvert.int2String(i - 1); // xuky 2018.09.05
															// 注意台体编号是从0-15的
			int TestNo = ((TerminalTestNoDaoImpl) iBaseDao_TeminalTestNo).getNo(StageNo, MeterNo,
					datetime.substring(2, 6)); // 当前表位的流水号
			String id1 = DataConvert.IntToBinString(DataConvert.String2Int(StageNo)).substring(28);
			String id2 = DataConvert.IntToBinString(DataConvert.String2Int(MeterNo)).substring(28);
			String id = DataConvert.binStr2HexString(id1 + id2, 2);
			String tmpsno = DataConvert.int2HexString(TestNo / 256, 2) + DataConvert.int2HexString(TestNo % 256, 2);
			String mac = "02" + id + datetime.substring(2, 6) + tmpsno;

			TerminalLog terminalLog = new TerminalLog();
			terminalLog.setAddr(mac);
			terminalLog.setStageNo(StageNo);
			terminalLog.setMeterNo(MeterNo);
			terminalLog.setYyyymm(datetime.substring(2, 6));
			terminalLog.setTestNo(TestNo);
			terminalLog.setOpName("admin");
			terminalLog.setOpBTime(datetime1);
			terminalLog.setOpETime("");
			terminalLog.setResult("");
			terminalLog.setErrResult("");
			TerminalLog terminalLogNew = iBaseDao_TeminalLog.create(terminalLog);

			frame645.setData("04 96 96 01 " + mac + " " + datetime.substring(2, 14));

			String data = frame645.get645Frame();
			String name1 = name, addr1 = addr;
			// 发送数据时，添加超时等待时间
			// String recv = client.sendMessage(data,(long)1000);
			new Thread(() -> {
				// 通过线程的方式，实现并行发送
				sendData(client, data, name1, addr1, terminalLogNew, datetime1);
			}).start();
			// Debug.sleep(5000);
		}

	}

	public void sendData(TCPClientWithRecv client, String data, String name, String addr, TerminalLog terminalLog,
			String datetime1) {
		String begin = datetime1;
		String MAC = terminalLog.getAddr();

		Util698.log(MainControlServer2.class.getName(), "!!!main.send1(no=" + name + " addr=" + addr + "):" + data,
				Debug.LOG_INFO);

		TerminalLogDetail terminalLogDetail = new TerminalLogDetail();
		terminalLogDetail.setRunID(terminalLog.getID());
		terminalLogDetail.setAddr(terminalLog.getAddr());
		terminalLogDetail.setSend(data);
		terminalLogDetail.setSendtime0(begin);
		terminalLogDetail.setSendtime(begin);
		terminalLogDetail.setSendtimes(1);
		terminalLogDetail = iBaseDao_TeminalLogDetail.create(terminalLogDetail);

		String recv = client.sendMessage(data, (long) 3000, 4, "", terminalLogDetail);
		terminalLogDetail.setRecv(recv);
		terminalLogDetail.setRecvtime(Util698.getDateTimeSSS_new());
		iBaseDao_TeminalLogDetail.update(terminalLogDetail);
		// 6800008101160F68940D84969601010101020103010401D816
		Util698.log(MainControlServer2.class.getName(), "!!!main.recv1(no=" + name + " MAC=" + MAC + " addr=" + addr
				+ "):" + recv + " begin:" + begin + " end:" + Util698.getDateTimeSSS_new(), Debug.LOG_INFO);
		if (!recv.equals("nodata")) {
			Frame645_New frame645 = new Frame645_New(recv);
			addr = frame645.getAddr();
			if (frame645.getControl().equals("94") && frame645.getData().subSequence(0, 8).equals("84969601")) {
				// 准备发送下一轮报文 判断是否锁定
				while (true) {
					if (SendLock.getInstance().isUsable()) {
						frame645 = new Frame645_New(addr, "14", "04 96 96 02");
						data = frame645.get645Frame();
						begin = Util698.getDateTimeSSS_new();
						Util698.log(MainControlServer2.class.getName(),
								"!!!main.send2(no=" + name + " MAC=" + MAC + " addr=" + addr + "):" + data,
								Debug.LOG_INFO);

						terminalLogDetail = new TerminalLogDetail();
						terminalLogDetail.setRunID(terminalLog.getID());
						terminalLogDetail.setAddr(terminalLog.getAddr());
						terminalLogDetail.setSend(data);
						terminalLogDetail.setSendtime0(begin);
						terminalLogDetail.setSendtime(begin);
						terminalLogDetail.setSendtimes(1);
						terminalLogDetail = iBaseDao_TeminalLogDetail.create(terminalLogDetail);

						recv = client.sendMessage(data, (long) 3000, 4, "84969602", terminalLogDetail);
						terminalLogDetail.setRecv(recv);
						terminalLogDetail.setRecvtime(Util698.getDateTimeSSS_new());
						iBaseDao_TeminalLogDetail.update(terminalLogDetail);

						Util698.log(MainControlServer2.class.getName(),
								"!!!main.recv2(no=" + name + " MAC=" + MAC + " addr=" + addr + "):" + recv + " begin:"
										+ begin + " end:" + Util698.getDateTimeSSS_new(),
								Debug.LOG_INFO);
						// xuky 2018.08.28 如果收到了数据，就可以解锁了
						SendLock.getInstance().setEnable();
						if (!recv.equals("nodata")) {
							frame645 = new Frame645_New(recv);
							addr = frame645.getAddr();
							if (frame645.getControl().equals("94")
									&& frame645.getData().subSequence(0, 8).equals("84969602")) {
								// 间隔一定的时间
								Debug.sleep(5000);
								frame645 = new Frame645_New(addr, "14", "04 96 96 03");
								data = frame645.get645Frame();
								begin = Util698.getDateTimeSSS_new();
								Util698.log(MainControlServer2.class.getName(),
										"!!!main.send3(no=" + name + " MAC=" + MAC + " addr=" + addr + "):" + data,
										Debug.LOG_INFO);

								terminalLogDetail = new TerminalLogDetail();
								terminalLogDetail.setRunID(terminalLog.getID());
								terminalLogDetail.setAddr(terminalLog.getAddr());
								terminalLogDetail.setSend(data);
								terminalLogDetail.setSendtime0(begin);
								terminalLogDetail.setSendtime(begin);
								terminalLogDetail.setSendtimes(1);
								terminalLogDetail = iBaseDao_TeminalLogDetail.create(terminalLogDetail);

								recv = client.sendMessage(data, (long) 5000, 10, "84969603", terminalLogDetail); // 因为按键检测可能较慢，需要等待更多的时间

								terminalLogDetail.setRecv(recv);
								String endTime = Util698.getDateTimeSSS_new();
								terminalLogDetail.setRecvtime(endTime);
								iBaseDao_TeminalLogDetail.update(terminalLogDetail);

								Util698.log(MainControlServer2.class.getName(),
										"!!!main.recv3 完成自检过程 (no=" + name + " MAC=" + MAC + " addr=" + addr + "):"
												+ recv + " begin:" + begin + " end:" + Util698.getDateTimeSSS_new(),
										Debug.LOG_INFO);
								terminalLog.setOpETime(endTime);
								terminalLog.setResult("1");
								iBaseDao_TeminalLog.update(terminalLog);
								// 1、自检完成，如果自检失败，则暂停后续步骤的执行，输出NG信息
								// 2、进行集中器程序升级，升级完成，集中器重新启动以后，集中器的IP地址会切换为129.1.22.96
								// 3、使用 129.1.22.96地址进行集中器参数设置，重启集中器，
								// 4、检测程序版本、时钟检测，设置各种参数、清交采，设置IP地址为129.1.22.(50
								// + N)
								// 5、对其他集中器进行步骤2-4的操作
								// 6、逐个的将各个集中器的IP地址设置为129.1.22.96

								break;
							} else {
								terminalLog.setOpETime(DateTimeFun.getDateTimeSSS());
								terminalLog.setResult("0");
								terminalLog.setErrResult("3");
								iBaseDao_TeminalLog.update(terminalLog);
							}
						} else {
							terminalLog.setOpETime(DateTimeFun.getDateTimeSSS());
							terminalLog.setResult("0");
							terminalLog.setErrResult("2");
							iBaseDao_TeminalLog.update(terminalLog);
						}
					} else {
						Debug.sleep(200);
					}
				}
			} else {
				terminalLog.setOpETime(DateTimeFun.getDateTimeSSS());
				terminalLog.setResult("0");
				terminalLog.setErrResult("1");
				iBaseDao_TeminalLog.update(terminalLog);

			}
		} else {
			// error
		}
	}

	private void runTest1() {
		iBaseDao_TeminalTestNo = new TerminalTestNoDaoImpl();
		iBaseDao_TeminalLog = new TerminalLogDaoImpl();
		iBaseDao_TeminalLogDetail = new TerminalLogDetailDaoImpl();
		DealData1.getInstance();
		// xuky 2018.07.31 模拟打开16路自检服务
		// int i = 1;
		int port;
		String IP = "", name = "";
		// 批量创建与自检工装的连接
		for (int i = 1; i <= 16; i++) {
			IP = "129.1.51.21";
			port = 30000 + i; // 端口号
			name = DataConvert.int2String(i - 1); // name序号信息
			ControlListNew.put(name, new TCPClientWithRecv(IP, port));
		}

		// 批量开始测试
//		for (int i = 1; i <= 16; i++) {
			// 发送数据时，添加超时等待时间
			// String recv = client.sendMessage(data,(long)1000);
//			int no = i;
			int no = 1;
			new Thread(() -> {
				// 通过线程的方式，实现并行发送
				sendData1(no);
			}).start();
			// Debug.sleep(5000);
//		}

	}

	private Object[] STEP(int i, TCPClientWithRecv client, int step, TerminalLog terminalLog) {
		Object[] ret = new Object[2];
		Frame645_New frame645 = new Frame645_New();
		String data = "", recv = "";
		TerminalLogDetail terminalLogDetail = new TerminalLogDetail();
		String addr = DataConvert.int2HexString(11 + i - 1, 2) + "1601810000";
		String MAC = "";
		if (terminalLog != null)
			MAC = terminalLog.getAddr();
		String name = DataConvert.int2String(i - 1);

		if (step == 0) {
			// 发送启动报文 129.1.22.11
			frame645.setAddr(addr);
			frame645.setControl("14");
			// 使用设备的MAC地址作为设备的唯一标示 测试前计算产生MAC地址信息，以此MAC地址信息进行唯一标识
			// MAC 02:ID:YY:MM:tmpsno/256:tmpsno%256
			// [ID]FF：高位的F表台号（1-16），低位的F表位号（0-15） 一般都是16表位的台体
			// [tmpsno] tmpsno 这个 是测试的序号 是单表位的测试序号，FFFF，前面是取整，后面是取余
			// xuky 2018.08.24 因为序号为每个表台+表位、每个月的流水号，所以需要记录到数据库中进行保存
			// 一个表台使用一个软件，多个表位公用一个软件
			String datetime = DateTimeFun.getDateTimeSSS();
			String datetime1 = datetime;
			datetime = datetime.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
			String MeterNo = DataConvert.int2String(i - 1); // xuky 2018.09.05
															// 注意台体编号是从0-15的
			int TestNo = ((TerminalTestNoDaoImpl) iBaseDao_TeminalTestNo).getNo(StageNo, MeterNo,
					datetime.substring(2, 6)); // 当前表位的流水号
			String id1 = DataConvert.IntToBinString(DataConvert.String2Int(StageNo)).substring(28);
			String id2 = DataConvert.IntToBinString(DataConvert.String2Int(MeterNo)).substring(28);
			String id = DataConvert.binStr2HexString(id1 + id2, 2);
			String tmpsno = DataConvert.int2HexString(TestNo / 256, 2) + DataConvert.int2HexString(TestNo % 256, 2);
			String mac = "02" + id + datetime.substring(2, 6) + tmpsno;

			terminalLog = new TerminalLog();
			terminalLog.setAddr(mac);
			terminalLog.setStageNo(StageNo);
			terminalLog.setMeterNo(MeterNo);
			terminalLog.setYyyymm(datetime.substring(2, 6));
			terminalLog.setTestNo(TestNo);
			terminalLog.setOpName("admin");
			terminalLog.setOpBTime(datetime1);
			terminalLog.setOpETime("");
			terminalLog.setResult("");
			terminalLog.setErrResult("");
			terminalLog = iBaseDao_TeminalLog.create(terminalLog);

			frame645.setData("04 96 96 01 " + mac + " " + datetime.substring(2, 14));

			data = frame645.get645Frame();

			String begin = datetime1;
			Util698.log(MainControlServer2.class.getName(), "!!!main.send1(no=" + name + " addr=" + addr + "):" + data,
					Debug.LOG_INFO);

			terminalLogDetail.setRunID(terminalLog.getID());
			terminalLogDetail.setAddr(terminalLog.getAddr());
			terminalLogDetail.setSend(data);
			terminalLogDetail.setSendtime0(begin);
			terminalLogDetail.setSendtime(begin);
			terminalLogDetail.setSendtimes(1);
			terminalLogDetail = iBaseDao_TeminalLogDetail.create(terminalLogDetail);

			recv = client.sendMessage(data, (long) 3000, 4, "", terminalLogDetail);
			terminalLogDetail.setRecv(recv);
			terminalLogDetail.setRecvtime(Util698.getDateTimeSSS_new());
			iBaseDao_TeminalLogDetail.update(terminalLogDetail);
			// 6800008101160F68940D84969601010101020103010401D816
			Util698.log(MainControlServer2.class.getName(), "!!!main.recv1(no=" + name + " MAC=" + MAC + " addr=" + addr
					+ "):" + recv + " begin:" + begin + " end:" + Util698.getDateTimeSSS_new(), Debug.LOG_INFO);
		}
		if (step == 1) {
			while (true) {
				if (SendLock.getInstance().isUsable()) {
					frame645 = new Frame645_New(addr, "14", "04 96 96 02");
					data = frame645.get645Frame();
					String begin = Util698.getDateTimeSSS_new();
					Util698.log(MainControlServer2.class.getName(),
							"!!!main.send2(no=" + name + " MAC=" + MAC + " addr=" + addr + "):" + data, Debug.LOG_INFO);

					terminalLogDetail = new TerminalLogDetail();
					terminalLogDetail.setRunID(terminalLog.getID());
					terminalLogDetail.setAddr(terminalLog.getAddr());
					terminalLogDetail.setSend(data);
					terminalLogDetail.setSendtime0(begin);
					terminalLogDetail.setSendtime(begin);
					terminalLogDetail.setSendtimes(1);
					terminalLogDetail = iBaseDao_TeminalLogDetail.create(terminalLogDetail);

					recv = client.sendMessage(data, (long) 3000, 4, "84969602", terminalLogDetail);
					terminalLogDetail.setRecv(recv);
					terminalLogDetail.setRecvtime(Util698.getDateTimeSSS_new());
					iBaseDao_TeminalLogDetail.update(terminalLogDetail);

					Util698.log(
							MainControlServer2.class.getName(), "!!!main.recv2(no=" + name + " MAC=" + MAC + " addr="
									+ addr + "):" + recv + " begin:" + begin + " end:" + Util698.getDateTimeSSS_new(),
							Debug.LOG_INFO);
					// xuky 2018.08.28 如果收到了数据，就可以解锁了
					SendLock.getInstance().setEnable();
					break;
				}
				Debug.sleep(200);
			}
		}
		if (step == 2) {
			Debug.sleep(5000);
			frame645 = new Frame645_New(addr, "14", "04 96 96 03");
			data = frame645.get645Frame();
			String begin = Util698.getDateTimeSSS_new();
			Util698.log(MainControlServer2.class.getName(),
					"!!!main.send3(no=" + name + " MAC=" + MAC + " addr=" + addr + "):" + data, Debug.LOG_INFO);

			terminalLogDetail = new TerminalLogDetail();
			terminalLogDetail.setRunID(terminalLog.getID());
			terminalLogDetail.setAddr(terminalLog.getAddr());
			terminalLogDetail.setSend(data);
			terminalLogDetail.setSendtime0(begin);
			terminalLogDetail.setSendtime(begin);
			terminalLogDetail.setSendtimes(1);
			terminalLogDetail = iBaseDao_TeminalLogDetail.create(terminalLogDetail);

			recv = client.sendMessage(data, (long) 5000, 10, "84969603", terminalLogDetail); // 因为按键检测可能较慢，需要等待更多的时间

			terminalLogDetail.setRecv(recv);
			String endTime = Util698.getDateTimeSSS_new();
			terminalLogDetail.setRecvtime(endTime);
			iBaseDao_TeminalLogDetail.update(terminalLogDetail);

			Util698.log(
					MainControlServer2.class.getName(), "!!!main.recv3 完成自检过程 (no=" + name + " MAC=" + MAC + " addr="
							+ addr + "):" + recv + " begin:" + begin + " end:" + Util698.getDateTimeSSS_new(),
					Debug.LOG_INFO);

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

	public Map<String, MinaTCPClient> getServerList() {
		return ControlList;
	}

	public static void main(String args[]) {
		MainControlServer2.getInstance();
	}
}
