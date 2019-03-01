package Control;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.net.telnet.TelnetClient;

import com.eastsoft.util.Debug;

import Util.Util698;

public class TerminalTelnetSingle {
	private volatile static TerminalTelnetSingle uniqueInstance;
	private TelnetClient TCLIENT;
	private InputStream IN;
	private OutputStream OS;
	private String READSTR = ""; // 记录最后收到的数据，以此可以判断当前的telnet处于某个状态，便于进行后续的对应操作
	private Boolean need_reConnet = false ; //xuky 2017.12.20 是否需要重新连接的标志
	private String IP;
	private String OLDCMD = "";
	private Boolean NOVERIFY = false; // 是否需要验证的标记信息
	private Boolean DEBUGMODEL = false;
	// xuky 2018.09.12 下面的信息可能会随集中器的不同而有所变化
	private static String SYS_PROMPT = "[root@(none) ~]#";
	// [root@(none) ~]#
	private String program_name = "init_dev";
	private String program_prompt = "input the choice>";

	// xuky 2017.12.18 设置等待时间为2.4秒无效，修改为1.5秒（外部默认的等待时间为2.5秒）
	// xuky 2017.12.19 因为需要超时重试，必须执行此代码（2/2）
//	TCLIENT.setDefaultTimeout(1000);
	// xuky 2017.12.21 出现超时情况
	// xuky 2018.01.08 dn时出现超时情况
//	private int TIMEOUT_USE = 1500;
	private int TIMEOUT_USE =10000;

	Boolean RUNFASTER = false;

	// public String getReadStr() {
	// return readStr;
	// }
	public static TerminalTelnetSingle getInstance(String terminalIP) {
		if (uniqueInstance == null) {
			synchronized (TerminalTelnetSingle.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new TerminalTelnetSingle(terminalIP);
				}
			}
		}
		return uniqueInstance;
	}
	public static TerminalTelnetSingle getInstance(String terminalIP,int timeout) {
		if (uniqueInstance == null) {
			synchronized (TerminalTelnetSingle.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new TerminalTelnetSingle(terminalIP,timeout);
				}
			}
		}
		return uniqueInstance;
	}

	private TerminalTelnetSingle(String terminalIP) {
		IP = terminalIP;
		READSTR = "";

		root();
	}

	private TerminalTelnetSingle(String terminalIP,int timeout) {
		IP = terminalIP;
		READSTR = "";
		TIMEOUT_USE = timeout;
		root();
	}

	public void destroy() {
		if (TCLIENT != null)
			try {
				TCLIENT.disconnect();
				uniqueInstance = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}


	public int getTIMEOUT_USE() {
		return TIMEOUT_USE;
	}

	public void setTIMEOUT_USE(int tIMEOUT_USE) {
		TIMEOUT_USE = tIMEOUT_USE;
	}


	// 连接终端，登录root用户
	public Boolean root() {

		if (RUNFASTER) return true;

		Boolean ret = false;
		try {

			// xuky 2017.12.19 建立新的之前，释放之前的
			// xuky 2017.12.19 因为需要超时重试，必须执行此代码（1/2）
			if (TCLIENT != null)
				TCLIENT.disconnect();
			TCLIENT = new TelnetClient();

			// xuky 2018.01.08 使用系统默认的超时时间
//			if (TIMEOUT_USE != 0)
//				TCLIENT.setDefaultTimeout(TIMEOUT_USE);

			Util698.log(TerminalTelnetSingle.class.getName(), "root =>【 connect... " + IP + "】", Debug.LOG_INFO);

			TCLIENT.connect(IP, 23);  // 默认telnet端口号

			IN = TCLIENT.getInputStream();
			OS = TCLIENT.getOutputStream();
			if (readUntil(":", IN).equals(""))
				ret = false;
			else {
				// 写入用户名信息 root
				writeUtil("root", OS);
//				String msg = readUntil(SYS_PROMPT, IN);
				// xuky 2018.10.15 因为提示符信息不确定，所以这里使用“]#”进行判断
				String msg = readUntil("]#", IN);
				if (msg.equals(""))
					ret = false;
				else{
					SYS_PROMPT = msg.substring(msg.indexOf("[root@"));
					ret = true;
				}
			}
			Util698.log(TerminalTelnetSingle.class.getName(), "root 【 " + ret + "】", Debug.LOG_INFO);
		} catch (Exception e) {
			String errMsg = e.getMessage();
			if (errMsg.indexOf("timed out") >= 0 || errMsg.indexOf("refuse") >= 0) {
//				javafxutil.f_alert_informationDialog("操作提示", "无法连接" + IP + "终端，请检查");
//				Util698.log(TerminalTelnetSingle.class.getName(), "root Exception => 【 无法连接" + IP + "终端，请检查】",
//						Debug.LOG_INFO);
			} else {
				e.printStackTrace();
				Util698.log(TerminalTelnetSingle.class.getName(), "root Exception =>【 " + e.getMessage() + "】",
						Debug.LOG_INFO);
			}
			ret = false;
		}
		return ret;

	}

	// 进入参数设置程序
	public Boolean init_dev() {
		Util698.log(TerminalTelnetSingle.class.getName(), program_name+" 开始...", Debug.LOG_INFO);
		// 通过如下操作进行验证
		Boolean ret = false;
		if (READSTR.endsWith(SYS_PROMPT)) {
			Util698.log(TerminalTelnetSingle.class.getName(), "验证是否有效", Debug.LOG_INFO);
			writeUtil(program_name, OS);
			// xuky 2017.12.14 根据readUntil的返回值判断是否执行是有效的
			if (readUntil(program_prompt, IN).equals(""))
				ret = false;
			else
				ret = true;
		} else if (READSTR.endsWith(program_prompt)) {
			// xuky 2017.12.20 为了提高执行的效率，删减此处的操作，
			// xuky 2017.12.20 为了提高执行的效率，在执行完毕时，根据情况readUntil函数中添加删除READSTR的操作
//			Util698.log(TerminalTelnetSingle.class.getName(), "验证是否有效", Debug.LOG_INFO);
//			writeUtil("88", OS);
//			if (readUntil("input the choice>", IN).equals(""))
//				ret = false;
//			else
//				ret = true;
			ret = true;
		}


		// xuky 2017.12.20 根据标志进行流程调整
		if (need_reConnet){
			need_reConnet = false;
			ret = false;
		}


		if (ret == false) {
			// xuky 2017.11.27 可能网络断开，重新进行
			// 需要重新进行连接
			Util698.log(TerminalTelnetSingle.class.getName(), "重新进行连接", Debug.LOG_INFO);
			ret = root();
			if (ret) {
				writeUtil(program_name, OS);
				if (readUntil(program_prompt, IN).equals(""))
					ret = false;
				else
					ret = true;
			}
		}

		Util698.log(TerminalTelnetSingle.class.getName(), program_name + " 结束...状态" + ret, Debug.LOG_INFO);

		return ret;
	}

	// 退出参数设置程序
	public Boolean init_dev_quit() {
		Boolean ret = false;
		if (READSTR.indexOf(SYS_PROMPT) >= 0) {
			ret = true;
		}
		if (READSTR.indexOf(program_prompt) >= 0) {
			writeUtil("98", OS);
			if (readUntil(SYS_PROMPT, IN).equals(""))
				ret = false;
			else
				ret = true;
		}
		return ret;
	}

	// 读到指定位置,不在向下读
	public String readUntil(String endFlag, InputStream in) {

		Debug.sleep(500);

		if (in == null)
			return "";

		InputStreamReader isr = new InputStreamReader(in);

		char[] charBytes = new char[1024];
		int n = 0;
		boolean is_find = false;
		String str = "";
		try {
			while ((n = isr.read(charBytes)) != -1) {
				str += charArrayToStr(charBytes,n).trim();
				if (str.indexOf("Segmentation fault") >= 0 && str.endsWith("#"))
				Util698.log(TerminalTelnetSingle.class.getName(), "Segmentation fault =>【" + str + "】",
						Debug.LOG_INFO);

				// 当拼接的字符串以指定的字符串结尾时,不再继续读
				if (str.endsWith(endFlag)) {
					is_find = true; // 设置找到标志，用于退出while循环
					break; // 退出当前for 循环
				}
//				for (int i = 0; i < n; i++) {
//					char c = (char) charBytes[i];
//
//					// // xuky 2017.08.21 每收到一点数据就进行展示，在进行升级操作时可以细致的展示升级过程
//					// String tmp = ""+c;
//					// String[] s = { "ReadUntil", "", tmp };
//					// Publisher.getInstance().publish(s);
//
//					str += c;
//
//					// xuky 2017.09.21 目前集中器程序可能出现此异常
//					// input the choice>88
//					// Segmentation fault
//					// [root@(none) /]#
//					if (str.indexOf("Segmentation fault") >= 0 && str.endsWith("#"))
//						Util698.log(TerminalTelnetSingle.class.getName(), "Segmentation fault =>【" + str + "】",
//								Debug.LOG_INFO);
//
//					// 当拼接的字符串以指定的字符串结尾时,不再继续读
//					if (str.endsWith(endFlag)) {
//						is_find = true; // 设置找到标志，用于退出while循环
//						break; // 退出当前for 循环
//					}
//				}

				// 退出当前while 循环
				if (is_find)
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// xuky 2017.12.11 还有一种情况，前面的执行是n=-1

		READSTR = str;
		if (DEBUGMODEL) {
			Util698.log(TerminalTelnetSingle.class.getName(),
					"readUntil(endFlag【" + endFlag + "】) getData=>【" + READSTR + "】", Debug.LOG_INFO);
			// System.out.println(readStr);
		}

		// xuky 2017.12.20 为了提高效率，避免88的执行，增加此代码
		// 如果READSTR中包含了DELAY RESET信息，表示执行了重启类的操作，后续执行telnet的写操作前应该重新连接
		if (READSTR.indexOf("DELAY RESET") >= 0)
			need_reConnet = true;

		return str;
	}

	private String charArrayToStr(char[] charBytes, int n){
		String ret = "";
		for (int i = 0; i < n; i++) {
			char c = (char) charBytes[i];
			ret += c;
		}
		return ret;
	}

	private Boolean writeUtil(String cmd, OutputStream os) {
		if (os == null)
			return false;
		return writeUtil(cmd, os, true);
	}

	// 写入命令方法
	private Boolean writeUtil(String cmd, OutputStream os, Boolean changeOldCMD) {
		if (os == null)
			return false;
		try {
			cmd = cmd + "\n";
			// xuky 2017.09.21 记录最近一次发出的命令内容
			if (changeOldCMD)
				OLDCMD = cmd;

			if (DEBUGMODEL) {
				Util698.log(TerminalTelnetSingle.class.getName(), "write =>【" + cmd + "】", Debug.LOG_INFO);
				// System.out.println(readStr);
			}
			os.write(cmd.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	// 修改参数信息，其中无需修改前确认
	public boolean changeParam(String key, String val) {
		Boolean ret = false;

		// Util698.log(TerminalTelnetSingle.class.getName(), "init_dev [begin]
		// key=" + key, Debug.LOG_INFO);

		if (!init_dev())
			return ret;

		// Util698.log(TerminalTelnetSingle.class.getName(), "init_dev [end]
		// key=" + key, Debug.LOG_INFO);

		writeUtil(key, OS);

		if (readUntil(">", IN).equals(""))
			return false;

		// xuky 2017.12.14 根据得到的提示信息进行判断
		if (READSTR.endsWith(program_prompt)) {
			// 无需继续操作，退出即可
			NOVERIFY = true;
			return true;
		}

		if (val == null || val.equals("")) {
			// xuky 2017.09.30 存在部分项目，只是执行，无需录入数据
		} else {
			writeUtil(val, OS);
			if (readUntil(">", IN).equals(""))
				return false;
		}

		writeUtil("y", OS);
		if (readUntil(program_prompt, IN).equals(""))
			return false;

		// if (!init_dev_quit())
		// return ret;
		ret = true;
		return ret;
	}

	// 修改参数信息，其中需要进行修改确认
	private void changeParamWithCheck(String key, String val) {
		writeUtil(key, OS);
		if (readUntil(">", IN).equals(""))
			return;

		writeUtil("y", OS);
		if (readUntil(">", IN).equals(""))
			return;

		writeUtil(val, OS);
		if (readUntil(">", IN).equals(""))
			return;
		writeUtil("y", OS);

		if (readUntil(program_prompt, IN).equals(""))
			return;
	}

	private void runParam(String key) {
		writeUtil(key, OS);
		if (readUntil(">", IN).equals(""))
			return;

		writeUtil("y", OS);

		if (readUntil(program_prompt, IN).equals(""))
			return;
	}

	public String writeThenReadUtil(String cmd, String data) {
		if (READSTR.endsWith(program_prompt)) {
			writeUtil("98", OS);
			if (readUntil(SYS_PROMPT, IN).equals(""))
				return "";
		}
		writeUtil(cmd, OS, true);
		if (readUntil(data, IN).equals(""))
			return "";
		return READSTR;
	}

	// 获取整体参数数据
	public String getParam() {
		String ret = "";
		// 根据当前的状态，进行相应的处理
		if (READSTR.indexOf(SYS_PROMPT) >= 0) {
			writeUtil(program_name, OS);
			ret = readUntil(program_prompt, IN);
		} else if (READSTR.indexOf(program_prompt) >= 0) {
			writeUtil("88", OS);
			ret = readUntil(program_prompt, IN);
		} else {
			ret = "异常：程序无法处理的情况-" + READSTR;
		}
		return ret;
	}

	public String getParam(String key) {
		String ret = "";
		try {
			String allMsg = getParam();
			ret = getParam(key, allMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	// 验证参数的数据是否正确
	public String getParam(String key, String allMsg) {
		String str1 = "";
		try {
			if (allMsg.indexOf("异常") >= 0)
				return "-1";
			if (allMsg.equals(""))
				return "-1";

			String find = "-----" + key + "--";
			// -----10--set gprs apn username:cmnet1
			// 找到第1个位置：-----10--
			int pos1 = allMsg.indexOf(find);
			pos1 = pos1 + find.length();
			String str = allMsg.substring(pos1, pos1 + 3);
			str = str + "";
			// 找到第2个位置：换行符号
			int pos2 = allMsg.indexOf("\r", pos1);
			str = allMsg.substring(pos1, pos2);
			// 找到第3个位置：:
			int pos3 = str.indexOf(":");
			str1 = str.substring(pos3 + 1);
			str1 = str1 + "";
		} catch (Exception e) {
			Util698.log(TerminalTelnetSingle.class.getName(),
					"getParam err :allMsg=>【" + allMsg + "】 find=>【" + key + "】", Debug.LOG_INFO);
			e.printStackTrace();
		}
		return str1;
	}

	// 验证参数的数据是否正确
	public String[] verify(String key, String val) {
		// ret[0] 得到的数据
		// ret[1] 验证的结果 0失败 1成功
		String[] ret = { "", "0" };
		String str1 = getParam(key).trim();
		ret[0] = str1;
		if (str1.equals("-1"))
			ret[1] = "0";
		if (str1.equals(val))
			ret[1] = "1";
		else {
			// xuky 2017.11.13 进行时钟误差比较
			if (val.startsWith("[误差")) {
				// verify("17-11-13 14:29:45","[误差<5分钟]") // 测试用例的格式为 [误差<nn分钟]
				String now = Util698.getDateTimeSSS_new();
				// // xuky 2017.11.13 返回的数据前面有空格
				str1 = str1.trim();
				Long val_teminal = Math.abs(Util698.getMilliSecondBetween_new("20" + str1 + ":000", now));
				String val_expect = val.split("<")[1];
				val_expect = val_expect.substring(0, val_expect.length() - 3); // 去掉分钟]以后的数据
				ret[0] = "20" + ret[0] + "<==>" + now;
				// xuky 2017.11.13 在此进行数据替换，防止后面的 xxx.split("-")[1] 只能显示部分数据
				// 注意不要使用-作为信息显示用内容
				ret[0] = ret[0].replaceAll("\\-", "\\\\") + " ~ " + val_teminal;
				if (Long.parseLong(val_expect) * 60 * 1000 > val_teminal)
					ret[1] = "1";
				else
					ret[1] = "0";
			}
		}
		return ret;
	}

	public Boolean getNOVERIFY() {
		return NOVERIFY;
	}

	public void setNOVERIFY(Boolean nOVERIFY) {
		NOVERIFY = nOVERIFY;
	}

	public String getREADSTR() {
		return READSTR;
	}

	private static void changeIP(String oldIP,String newIP, Boolean cycle){
		TerminalTelnetSingle terminalTelnet = TerminalTelnetSingle.getInstance(oldIP);
		String key = "40", val = newIP;
		key = "10";
		System.out.println("【terminalTelnet.getParam】"+terminalTelnet.getParam(key));
		key = "40";
		System.out.println("【terminalTelnet.changeParam】"+terminalTelnet.changeParam(key, val));

		// xuky 2018.09.19 1、不进行IP地址变更，最小的重启恢复时间为11秒
		// xuky 2018.09.19 2、IP地址变更，最小的重启恢复时间为35秒
		key = "reboot";
		System.out.println("reboot begin");
		System.out.println("【terminalTelnet.writeThenReadUtil】"+terminalTelnet.writeThenReadUtil(key, SYS_PROMPT));
		Debug.sleep(11000);
		TerminalTelnetSingle.getInstance("").destroy();

		// 直接reboot ，启动时间非常快

		// 修改回原先的IP地址
		if (cycle){
//			terminalTelnet = TerminalTelnetSingle.getInstance(oldIP);
			terminalTelnet = TerminalTelnetSingle.getInstance(newIP);
			System.out.println("reboot end");
			key = "10";
			System.out.println("【terminalTelnet.getParam】"+terminalTelnet.getParam(key));
			TerminalTelnetSingle.getInstance("").destroy();
//			key = "40";
//			val = oldIP;
//			System.out.println("【terminalTelnet.changeParam】"+terminalTelnet.changeParam(key, val));
//			key = "reboot";
//			System.out.println("reboot begin");
//			System.out.println("【terminalTelnet.writeThenReadUtil】"+terminalTelnet.writeThenReadUtil(key, sys_prompt));
//			Debug.sleep(60000);
//			TerminalTelnetSingle.getInstance("").destroy();
//
//			terminalTelnet = TerminalTelnetSingle.getInstance(oldIP);
//			System.out.println("reboot end");
//			key = "10";
//			System.out.println("【terminalTelnet.getParam】"+terminalTelnet.getParam(key));

		}

	}


	public static String getSYS_PROMPT() {
		return SYS_PROMPT;
	}
	public static void setSYS_PROMPT(String sYS_PROMPT) {
		SYS_PROMPT = sYS_PROMPT;
	}
	public static void main(String[] arg) {

//		changeIP("192.168.127.244","192.168.127.246",true);
		changeIP("192.168.127.246","192.168.127.244",true);

//		String TERMINAL_IP = "192.168.127.246";
//		TerminalTelnetSingle terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP);

//		String key = "10" , val = "CMNET";
//		// 1、查询参数
//		System.out.println("【terminalTelnet.getParam】"+terminalTelnet.getParam(key));

//
//		// 2、设置参数
//		System.out.println("【terminalTelnet.changeParam】"+terminalTelnet.changeParam(key, val));
//
//		TerminalTelnetSingle.getInstance("").destroy();
//		terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP, 0);
//		key = "dn 192.168.127.2";
//		// 3、更新集中器程序
//		System.out.println("【terminalTelnet.writeThenReadUtil】"+terminalTelnet.writeThenReadUtil(key, sys_prompt));
//
//		// 4、更新集中器IP
//		key = "40";
//		val = "192.168.127.246";
//		System.out.println("【terminalTelnet.changeParam】"+terminalTelnet.changeParam(key, val));
//		TerminalTelnetSingle.getInstance("").destroy();
//		terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP, 0);
//
//		// 执行linux命令
////		key = "cat /etc/macaddr";
////		System.out.println("【terminalTelnet.writeThenReadUtil】"+terminalTelnet.writeThenReadUtil(key,val));
//
//		// 192.168.127.244
//		// 192.168.127.245
//
////		// 更新终端程序
////		{
////			String command = "cat /etc/macaddr";
////			String end = "[root@(none) /]#";
////			String ret = terminalTelnet.writeThenReadUtil(command, end);
////			ret = ret.substring(command.length() + 2, ret.length() - end.length() - 1);
////			System.out.println(ret);
////		}

//		terminalTelnet.destroy();
	}


}
