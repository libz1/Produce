package Control;

import java.util.List;

import DataBase.IBaseDao;
import Entity.Param;
import Entity.ParamDaoImpl;
import Util.Util698;

public class DealParam {
	String TERMINAL_IP = "", returnResult = "";

	// xuky 2018.10.10 �����û�����Ĳ����б���Ϣ�����м������������ü���֤������������������
	private volatile static DealParam uniqueInstance;
	private IBaseDao<Param> iBaseDao_Param;

	public static DealParam getInstance() {
		if (uniqueInstance == null) {
			synchronized (DealParam.class) {
				if (uniqueInstance == null) {
					// ˫�ؼ�����
					uniqueInstance = new DealParam();
				}
			}
		}
		return uniqueInstance;
	}

	private DealParam() {
		runTest1();
	}

	private void runTest1() {
		iBaseDao_Param = new ParamDaoImpl();
		TERMINAL_IP = "192.168.127.244";
		List<Param> list = iBaseDao_Param.retrieve("where (note1<>'0' or note1 = null) ", "order by serial");
		for( Param p: list ){
//			System.out.println(p.getName());
			deal_telnet(p);
		}
//		TerminalTelnetSingle terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP);
//		String key = "2" , val = "172.017.017.057";
//		// 1����ѯ����
//		System.out.println("��terminalTelnet.getParam��"+terminalTelnet.changeParam(key,val));
	}

	// 3��ͨ��telnetͨ��
	// û�й��ڼ�������õ�������� û�й���[���<5����]�Ŀ���
	private void deal_telnet(Param p) {

		TerminalTelnetSingle terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP);
		String key = p.getKeyname(), val = p.getValue();

		// xuky 2017.10.17
		// 1�������ֵģ���ʾ��Ҫֱ��ִ��linux����
		// 2����������ظ����ݵĸ�ʽ��xxx%����ʾ�ж��Ƿ������xxx����
		// 3��������ص����ݳ���С��100������ͷȥβ��������������������������ص���ʾ����Ϣ�������������м䲿������
		if (!Util698.isNumber(key)) {
			// 1�������ֵģ���ʾ��Ҫֱ��ִ��linux����  "[root@(none) /]#"
			// xuky 2018.10.15 ���ݺ�̨�����Ϣ���Զ��趨����ʾ������Ϣ
			String end = terminalTelnet.getSYS_PROMPT();

			if (key.indexOf("dn") == 0) {
				// xuky 2018.01.08 ���ó�ʱʱ��
				TerminalTelnetSingle.getInstance("").destroy();
				terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP, 0);
			} else if (!terminalTelnet.getREADSTR().endsWith(end)) {
				// �����ǰ����ʾ������ [root@(none) /]#����Ҫ��������
				TerminalTelnetSingle.getInstance("").destroy();
				terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP);
			}
			// Util698.log(DealSendData.class.getName(), "linux: finish
			// init", Debug.LOG_INFO);
			String ret = terminalTelnet.writeThenReadUtil(key, end);

			// xuky 2017.11.17 ����޷������նˣ��޷��������ݣ���Ҫ�������µĴ���
			if (ret == null || ret.equals(""))
				ret = " ";
			else
				// ��ͷȥβ��������������������������ص���ʾ����Ϣ�������������м䲿������
				ret = ret.substring(key.length() + 2, ret.length() - end.length() - 2);

			String result = ret;
			// 3��������ص����ݳ���С��100������ͷȥβ��������������������������ص���ʾ����Ϣ�������������м䲿������
			// if (ret.length() > 100)
			// result = " ";

			// 2����������ظ����ݵĸ�ʽ��xxx%����ʾ�ж��Ƿ������xxx����
			if (val.endsWith("%")) {
				val = val.substring(0, val.length() - 1);
				if (ret.indexOf(val) >= 0)
					returnResult = "ok-" + result;
				else
					returnResult = "err-" + result;
			} else {
				// 2����������ظ����ݵĸ�ʽ��%xxx����ʾ�ж��Ƿ�xxxΪ���������� �ж������Ƿ�ɹ������������ĳɹ�
				if (val.startsWith("%")) {
					val = val.substring(1, val.length());
					if (ret.endsWith(val)) {
						// xuky 2017.11.07 ��ֹ���������ǡ����ɹ���
						if (ret.endsWith("��" + val))
							returnResult = "err-" + result;
						else
							returnResult = "ok-" + result;
					} else
						returnResult = "err-" + result;
				} else {
					if (ret.equals(val))
						returnResult = "ok-" + result;
					else
						returnResult = "err-" + result;
				}
			}

			if (key.indexOf("dn") == 0) {
				TerminalTelnetSingle.getInstance("").destroy();
				// xuky 2018.01.08 �ָ�Ĭ�ϳ�ʱʱ��
				terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP);

			}

		} else {
			// ���ò���
			if (terminalTelnet.changeParam(key, val) == false) {
				// changeParam����false,��ʾtelnet�����쳣����ʱ������л�Ӧ���ȴ���ʱ����
				return;
			}
			// xuky 2017.10.09 �����������Ϊ�գ���ʾ������ظ�����
			if (val == null || val.equals("")) {
				returnResult = "ok- ";
			} else {
				// ��֤����
				String[] result = terminalTelnet.verify(key, val);
				if (result[1].equals("1"))
					returnResult = "ok-" + result[0];
				else
					returnResult = "err-" + result[0];
			}

		}

//		// xuky 2017.11.29 �����﷢����Ϣ���Ա����������и�������
//		// telentʱ�Ĵ������
//		iBaseDao_ProduceCaseResult.update(p);
//		Util698.log(DealSendData.class.getName(), "ProduceCaseResult.update��" + p.getADDR()+"-"+p.getName()+"-"+p.getID()+"-telnet", Debug.LOG_INFO);
//
//		ProduceCaseResult produceCaseResult_tmp = (ProduceCaseResult) Util698.objClone(p, new ProduceCaseResult(), "");
//		Object[] s2 = { "DealTestCase", "old", produceCaseResult_tmp,"deal_telnet" };
//		PublisherShowList.getInstance().publish(s2);
//		produceCaseResult_tmp = null;
//
//		String[] s = { "recv frame", "user data", returnResult, port };
//		PublisherFrame.getInstance().publish(s);

		return;
	}

	public static void main(String[] arg) {
		DealParam.getInstance();
	}

}
