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
	private String READSTR = ""; // ��¼����յ������ݣ��Դ˿����жϵ�ǰ��telnet����ĳ��״̬�����ڽ��к����Ķ�Ӧ����
	private Boolean need_reConnet = false ; //xuky 2017.12.20 �Ƿ���Ҫ�������ӵı�־
	private String IP;
	private String OLDCMD = "";
	private Boolean NOVERIFY = false; // �Ƿ���Ҫ��֤�ı����Ϣ
	private Boolean DEBUGMODEL = false;
	// xuky 2018.09.12 �������Ϣ���ܻ��漯�����Ĳ�ͬ�������仯
	private static String SYS_PROMPT = "[root@(none) ~]#";
	// [root@(none) ~]#
	private String program_name = "init_dev";
	private String program_prompt = "input the choice>";

	// xuky 2017.12.18 ���õȴ�ʱ��Ϊ2.4����Ч���޸�Ϊ1.5�루�ⲿĬ�ϵĵȴ�ʱ��Ϊ2.5�룩
	// xuky 2017.12.19 ��Ϊ��Ҫ��ʱ���ԣ�����ִ�д˴��루2/2��
//	TCLIENT.setDefaultTimeout(1000);
	// xuky 2017.12.21 ���ֳ�ʱ���
	// xuky 2018.01.08 dnʱ���ֳ�ʱ���
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
					// ˫�ؼ�����
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
					// ˫�ؼ�����
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


	// �����նˣ���¼root�û�
	public Boolean root() {

		if (RUNFASTER) return true;

		Boolean ret = false;
		try {

			// xuky 2017.12.19 �����µ�֮ǰ���ͷ�֮ǰ��
			// xuky 2017.12.19 ��Ϊ��Ҫ��ʱ���ԣ�����ִ�д˴��루1/2��
			if (TCLIENT != null)
				TCLIENT.disconnect();
			TCLIENT = new TelnetClient();

			// xuky 2018.01.08 ʹ��ϵͳĬ�ϵĳ�ʱʱ��
//			if (TIMEOUT_USE != 0)
//				TCLIENT.setDefaultTimeout(TIMEOUT_USE);

			Util698.log(TerminalTelnetSingle.class.getName(), "root =>�� connect... " + IP + "��", Debug.LOG_INFO);

			TCLIENT.connect(IP, 23);  // Ĭ��telnet�˿ں�

			IN = TCLIENT.getInputStream();
			OS = TCLIENT.getOutputStream();
			if (readUntil(":", IN).equals(""))
				ret = false;
			else {
				// д���û�����Ϣ root
				writeUtil("root", OS);
//				String msg = readUntil(SYS_PROMPT, IN);
				// xuky 2018.10.15 ��Ϊ��ʾ����Ϣ��ȷ������������ʹ�á�]#�������ж�
				String msg = readUntil("]#", IN);
				if (msg.equals(""))
					ret = false;
				else{
					SYS_PROMPT = msg.substring(msg.indexOf("[root@"));
					ret = true;
				}
			}
			Util698.log(TerminalTelnetSingle.class.getName(), "root �� " + ret + "��", Debug.LOG_INFO);
		} catch (Exception e) {
			String errMsg = e.getMessage();
			if (errMsg.indexOf("timed out") >= 0 || errMsg.indexOf("refuse") >= 0) {
//				javafxutil.f_alert_informationDialog("������ʾ", "�޷�����" + IP + "�նˣ�����");
//				Util698.log(TerminalTelnetSingle.class.getName(), "root Exception => �� �޷�����" + IP + "�նˣ����顿",
//						Debug.LOG_INFO);
			} else {
				e.printStackTrace();
				Util698.log(TerminalTelnetSingle.class.getName(), "root Exception =>�� " + e.getMessage() + "��",
						Debug.LOG_INFO);
			}
			ret = false;
		}
		return ret;

	}

	// ����������ó���
	public Boolean init_dev() {
		Util698.log(TerminalTelnetSingle.class.getName(), program_name+" ��ʼ...", Debug.LOG_INFO);
		// ͨ�����²���������֤
		Boolean ret = false;
		if (READSTR.endsWith(SYS_PROMPT)) {
			Util698.log(TerminalTelnetSingle.class.getName(), "��֤�Ƿ���Ч", Debug.LOG_INFO);
			writeUtil(program_name, OS);
			// xuky 2017.12.14 ����readUntil�ķ���ֵ�ж��Ƿ�ִ������Ч��
			if (readUntil(program_prompt, IN).equals(""))
				ret = false;
			else
				ret = true;
		} else if (READSTR.endsWith(program_prompt)) {
			// xuky 2017.12.20 Ϊ�����ִ�е�Ч�ʣ�ɾ���˴��Ĳ�����
			// xuky 2017.12.20 Ϊ�����ִ�е�Ч�ʣ���ִ�����ʱ���������readUntil���������ɾ��READSTR�Ĳ���
//			Util698.log(TerminalTelnetSingle.class.getName(), "��֤�Ƿ���Ч", Debug.LOG_INFO);
//			writeUtil("88", OS);
//			if (readUntil("input the choice>", IN).equals(""))
//				ret = false;
//			else
//				ret = true;
			ret = true;
		}


		// xuky 2017.12.20 ���ݱ�־�������̵���
		if (need_reConnet){
			need_reConnet = false;
			ret = false;
		}


		if (ret == false) {
			// xuky 2017.11.27 ��������Ͽ������½���
			// ��Ҫ���½�������
			Util698.log(TerminalTelnetSingle.class.getName(), "���½�������", Debug.LOG_INFO);
			ret = root();
			if (ret) {
				writeUtil(program_name, OS);
				if (readUntil(program_prompt, IN).equals(""))
					ret = false;
				else
					ret = true;
			}
		}

		Util698.log(TerminalTelnetSingle.class.getName(), program_name + " ����...״̬" + ret, Debug.LOG_INFO);

		return ret;
	}

	// �˳��������ó���
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

	// ����ָ��λ��,�������¶�
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
				Util698.log(TerminalTelnetSingle.class.getName(), "Segmentation fault =>��" + str + "��",
						Debug.LOG_INFO);

				// ��ƴ�ӵ��ַ�����ָ�����ַ�����βʱ,���ټ�����
				if (str.endsWith(endFlag)) {
					is_find = true; // �����ҵ���־�������˳�whileѭ��
					break; // �˳���ǰfor ѭ��
				}
//				for (int i = 0; i < n; i++) {
//					char c = (char) charBytes[i];
//
//					// // xuky 2017.08.21 ÿ�յ�һ�����ݾͽ���չʾ���ڽ�����������ʱ����ϸ�µ�չʾ��������
//					// String tmp = ""+c;
//					// String[] s = { "ReadUntil", "", tmp };
//					// Publisher.getInstance().publish(s);
//
//					str += c;
//
//					// xuky 2017.09.21 Ŀǰ������������ܳ��ִ��쳣
//					// input the choice>88
//					// Segmentation fault
//					// [root@(none) /]#
//					if (str.indexOf("Segmentation fault") >= 0 && str.endsWith("#"))
//						Util698.log(TerminalTelnetSingle.class.getName(), "Segmentation fault =>��" + str + "��",
//								Debug.LOG_INFO);
//
//					// ��ƴ�ӵ��ַ�����ָ�����ַ�����βʱ,���ټ�����
//					if (str.endsWith(endFlag)) {
//						is_find = true; // �����ҵ���־�������˳�whileѭ��
//						break; // �˳���ǰfor ѭ��
//					}
//				}

				// �˳���ǰwhile ѭ��
				if (is_find)
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// xuky 2017.12.11 ����һ�������ǰ���ִ����n=-1

		READSTR = str;
		if (DEBUGMODEL) {
			Util698.log(TerminalTelnetSingle.class.getName(),
					"readUntil(endFlag��" + endFlag + "��) getData=>��" + READSTR + "��", Debug.LOG_INFO);
			// System.out.println(readStr);
		}

		// xuky 2017.12.20 Ϊ�����Ч�ʣ�����88��ִ�У����Ӵ˴���
		// ���READSTR�а�����DELAY RESET��Ϣ����ʾִ����������Ĳ���������ִ��telnet��д����ǰӦ����������
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

	// д�������
	private Boolean writeUtil(String cmd, OutputStream os, Boolean changeOldCMD) {
		if (os == null)
			return false;
		try {
			cmd = cmd + "\n";
			// xuky 2017.09.21 ��¼���һ�η�������������
			if (changeOldCMD)
				OLDCMD = cmd;

			if (DEBUGMODEL) {
				Util698.log(TerminalTelnetSingle.class.getName(), "write =>��" + cmd + "��", Debug.LOG_INFO);
				// System.out.println(readStr);
			}
			os.write(cmd.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	// �޸Ĳ�����Ϣ�����������޸�ǰȷ��
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

		// xuky 2017.12.14 ���ݵõ�����ʾ��Ϣ�����ж�
		if (READSTR.endsWith(program_prompt)) {
			// ��������������˳�����
			NOVERIFY = true;
			return true;
		}

		if (val == null || val.equals("")) {
			// xuky 2017.09.30 ���ڲ�����Ŀ��ֻ��ִ�У�����¼������
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

	// �޸Ĳ�����Ϣ��������Ҫ�����޸�ȷ��
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

	// ��ȡ�����������
	public String getParam() {
		String ret = "";
		// ���ݵ�ǰ��״̬��������Ӧ�Ĵ���
		if (READSTR.indexOf(SYS_PROMPT) >= 0) {
			writeUtil(program_name, OS);
			ret = readUntil(program_prompt, IN);
		} else if (READSTR.indexOf(program_prompt) >= 0) {
			writeUtil("88", OS);
			ret = readUntil(program_prompt, IN);
		} else {
			ret = "�쳣�������޷���������-" + READSTR;
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

	// ��֤�����������Ƿ���ȷ
	public String getParam(String key, String allMsg) {
		String str1 = "";
		try {
			if (allMsg.indexOf("�쳣") >= 0)
				return "-1";
			if (allMsg.equals(""))
				return "-1";

			String find = "-----" + key + "--";
			// -----10--set gprs apn username:cmnet1
			// �ҵ���1��λ�ã�-----10--
			int pos1 = allMsg.indexOf(find);
			pos1 = pos1 + find.length();
			String str = allMsg.substring(pos1, pos1 + 3);
			str = str + "";
			// �ҵ���2��λ�ã����з���
			int pos2 = allMsg.indexOf("\r", pos1);
			str = allMsg.substring(pos1, pos2);
			// �ҵ���3��λ�ã�:
			int pos3 = str.indexOf(":");
			str1 = str.substring(pos3 + 1);
			str1 = str1 + "";
		} catch (Exception e) {
			Util698.log(TerminalTelnetSingle.class.getName(),
					"getParam err :allMsg=>��" + allMsg + "�� find=>��" + key + "��", Debug.LOG_INFO);
			e.printStackTrace();
		}
		return str1;
	}

	// ��֤�����������Ƿ���ȷ
	public String[] verify(String key, String val) {
		// ret[0] �õ�������
		// ret[1] ��֤�Ľ�� 0ʧ�� 1�ɹ�
		String[] ret = { "", "0" };
		String str1 = getParam(key).trim();
		ret[0] = str1;
		if (str1.equals("-1"))
			ret[1] = "0";
		if (str1.equals(val))
			ret[1] = "1";
		else {
			// xuky 2017.11.13 ����ʱ�����Ƚ�
			if (val.startsWith("[���")) {
				// verify("17-11-13 14:29:45","[���<5����]") // ���������ĸ�ʽΪ [���<nn����]
				String now = Util698.getDateTimeSSS_new();
				// // xuky 2017.11.13 ���ص�����ǰ���пո�
				str1 = str1.trim();
				Long val_teminal = Math.abs(Util698.getMilliSecondBetween_new("20" + str1 + ":000", now));
				String val_expect = val.split("<")[1];
				val_expect = val_expect.substring(0, val_expect.length() - 3); // ȥ������]�Ժ������
				ret[0] = "20" + ret[0] + "<==>" + now;
				// xuky 2017.11.13 �ڴ˽��������滻����ֹ����� xxx.split("-")[1] ֻ����ʾ��������
				// ע�ⲻҪʹ��-��Ϊ��Ϣ��ʾ������
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
		System.out.println("��terminalTelnet.getParam��"+terminalTelnet.getParam(key));
		key = "40";
		System.out.println("��terminalTelnet.changeParam��"+terminalTelnet.changeParam(key, val));

		// xuky 2018.09.19 1��������IP��ַ�������С�������ָ�ʱ��Ϊ11��
		// xuky 2018.09.19 2��IP��ַ�������С�������ָ�ʱ��Ϊ35��
		key = "reboot";
		System.out.println("reboot begin");
		System.out.println("��terminalTelnet.writeThenReadUtil��"+terminalTelnet.writeThenReadUtil(key, SYS_PROMPT));
		Debug.sleep(11000);
		TerminalTelnetSingle.getInstance("").destroy();

		// ֱ��reboot ������ʱ��ǳ���

		// �޸Ļ�ԭ�ȵ�IP��ַ
		if (cycle){
//			terminalTelnet = TerminalTelnetSingle.getInstance(oldIP);
			terminalTelnet = TerminalTelnetSingle.getInstance(newIP);
			System.out.println("reboot end");
			key = "10";
			System.out.println("��terminalTelnet.getParam��"+terminalTelnet.getParam(key));
			TerminalTelnetSingle.getInstance("").destroy();
//			key = "40";
//			val = oldIP;
//			System.out.println("��terminalTelnet.changeParam��"+terminalTelnet.changeParam(key, val));
//			key = "reboot";
//			System.out.println("reboot begin");
//			System.out.println("��terminalTelnet.writeThenReadUtil��"+terminalTelnet.writeThenReadUtil(key, sys_prompt));
//			Debug.sleep(60000);
//			TerminalTelnetSingle.getInstance("").destroy();
//
//			terminalTelnet = TerminalTelnetSingle.getInstance(oldIP);
//			System.out.println("reboot end");
//			key = "10";
//			System.out.println("��terminalTelnet.getParam��"+terminalTelnet.getParam(key));

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
//		// 1����ѯ����
//		System.out.println("��terminalTelnet.getParam��"+terminalTelnet.getParam(key));

//
//		// 2�����ò���
//		System.out.println("��terminalTelnet.changeParam��"+terminalTelnet.changeParam(key, val));
//
//		TerminalTelnetSingle.getInstance("").destroy();
//		terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP, 0);
//		key = "dn 192.168.127.2";
//		// 3�����¼���������
//		System.out.println("��terminalTelnet.writeThenReadUtil��"+terminalTelnet.writeThenReadUtil(key, sys_prompt));
//
//		// 4�����¼�����IP
//		key = "40";
//		val = "192.168.127.246";
//		System.out.println("��terminalTelnet.changeParam��"+terminalTelnet.changeParam(key, val));
//		TerminalTelnetSingle.getInstance("").destroy();
//		terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP, 0);
//
//		// ִ��linux����
////		key = "cat /etc/macaddr";
////		System.out.println("��terminalTelnet.writeThenReadUtil��"+terminalTelnet.writeThenReadUtil(key,val));
//
//		// 192.168.127.244
//		// 192.168.127.245
//
////		// �����ն˳���
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
