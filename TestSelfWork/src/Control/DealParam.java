package Control;

import java.util.List;

import DataBase.IBaseDao;
import Entity.Param;
import Entity.ParamDaoImpl;
import Util.Util698;

public class DealParam {
	String TERMINAL_IP = "", returnResult = "";

	// xuky 2018.10.10 根据用户定义的参数列表信息，进行集中器参数设置及验证、含集中器程序升级
	private volatile static DealParam uniqueInstance;
	private IBaseDao<Param> iBaseDao_Param;

	public static DealParam getInstance() {
		if (uniqueInstance == null) {
			synchronized (DealParam.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
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
//		// 1、查询参数
//		System.out.println("【terminalTelnet.getParam】"+terminalTelnet.changeParam(key,val));
	}

	// 3、通过telnet通信
	// 没有关于检验和设置的区别控制 没有关于[误差<5分钟]的控制
	private void deal_telnet(Param p) {

		TerminalTelnetSingle terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP);
		String key = p.getKeyname(), val = p.getValue();

		// xuky 2017.10.17
		// 1、非数字的，表示需要直接执行linux命令
		// 2、如果期望回复数据的格式是xxx%，表示判断是否包含此xxx数据
		// 3、如果返回的数据长度小于100，则掐头去尾（不保留发出的命令、不保留返回的提示符信息），仅仅保留中间部分数据
		if (!Util698.isNumber(key)) {
			// 1、非数字的，表示需要直接执行linux命令  "[root@(none) /]#"
			// xuky 2018.10.15 根据后台获得信息，自动设定“提示符”信息
			String end = terminalTelnet.getSYS_PROMPT();

			if (key.indexOf("dn") == 0) {
				// xuky 2018.01.08 设置超时时间
				TerminalTelnetSingle.getInstance("").destroy();
				terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP, 0);
			} else if (!terminalTelnet.getREADSTR().endsWith(end)) {
				// 如果当前的提示符不是 [root@(none) /]#，需要重新连接
				TerminalTelnetSingle.getInstance("").destroy();
				terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP);
			}
			// Util698.log(DealSendData.class.getName(), "linux: finish
			// init", Debug.LOG_INFO);
			String ret = terminalTelnet.writeThenReadUtil(key, end);

			// xuky 2017.11.17 如果无法连接终端，无法返回数据，需要进行如下的处理
			if (ret == null || ret.equals(""))
				ret = " ";
			else
				// 掐头去尾（不保留发出的命令、不保留返回的提示符信息），仅仅保留中间部分数据
				ret = ret.substring(key.length() + 2, ret.length() - end.length() - 2);

			String result = ret;
			// 3、如果返回的数据长度小于100，则掐头去尾（不保留发出的命令、不保留返回的提示符信息），仅仅保留中间部分数据
			// if (ret.length() > 100)
			// result = " ";

			// 2、如果期望回复数据的格式是xxx%，表示判断是否包含此xxx数据
			if (val.endsWith("%")) {
				val = val.substring(0, val.length() - 1);
				if (ret.indexOf(val) >= 0)
					returnResult = "ok-" + result;
				else
					returnResult = "err-" + result;
			} else {
				// 2、如果期望回复数据的格式是%xxx，表示判断是否xxx为结束的数据 判断升级是否成功，必须是最后的成功
				if (val.startsWith("%")) {
					val = val.substring(1, val.length());
					if (ret.endsWith(val)) {
						// xuky 2017.11.07 防止最后的字样是“不成功”
						if (ret.endsWith("不" + val))
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
				// xuky 2018.01.08 恢复默认超时时间
				terminalTelnet = TerminalTelnetSingle.getInstance(TERMINAL_IP);

			}

		} else {
			// 设置参数
			if (terminalTelnet.changeParam(key, val) == false) {
				// changeParam返回false,表示telnet操作异常，此时无需进行回应，等待超时即可
				return;
			}
			// xuky 2017.10.09 如果期望数据为空，表示无需检查回复数据
			if (val == null || val.equals("")) {
				returnResult = "ok- ";
			} else {
				// 验证参数
				String[] result = terminalTelnet.verify(key, val);
				if (result[1].equals("1"))
					returnResult = "ok-" + result[0];
				else
					returnResult = "err-" + result[0];
			}

		}

//		// xuky 2017.11.29 在这里发送消息，以便后续程序进行跟进处理
//		// telent时的处理过程
//		iBaseDao_ProduceCaseResult.update(p);
//		Util698.log(DealSendData.class.getName(), "ProduceCaseResult.update：" + p.getADDR()+"-"+p.getName()+"-"+p.getID()+"-telnet", Debug.LOG_INFO);
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
