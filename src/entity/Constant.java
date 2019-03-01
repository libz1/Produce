package entity;

import util.Util698;

public class Constant {
	public final static String ARCHIVE_SOFT = "1"; // 软件中管理的电表档案
	public final static String ARCHIVE_TER = "2"; // 集中器中管理的电表档案
	public final static String ARCHIVE_RT = "3"; // 路由中管理的电表档案

	public final static String FLAG_INS = "0"; // 新增标志
	public final static String FLAG_UPD = "1"; // 修改标志
	public final static String FLAG_DEL = "2"; // 删除标志
	public static final String CHAR2 = "\n";

	public static String[] getProtocolType(){
		String[] data = { "未知(0)","DL/T645-1997(1)","DL/T645-2007(2)","DL/T698.45(3)","CJ/T188-2004(4)" };
		return data;
	};

	public static String[] getPort(){
		String[] data = { "载波/微功率(F2090201)", "RS485(F2010201)", "交采接口(F2080201)"  };
		return data;
	};

	public static String[] getType2(){
		String[] data = { "未知(0)","单相(1)","三相三线(2)","三相四线(3)" };
		return data;
	};

	// 根据给定的数值，得到其含义信息  val(key)
	public static String getVal(int key, String type){
		String val = "";
		String[] data = (String[]) Util698.getFieldValueByName(type,new Constant());
		for( String str: data ){
			if (str.indexOf("("+key+")") >=0){
				val = str.split("\\(")[0];
				break;
			}
		}

		return val;
	}

	public static String[] getPortRate(){
		String[] data = { "300bps(0)","600bps(1)","1200bps(2)",
			    "2400bps(3)","4800bps(4)","7200bps(5)",
			    "9600bps(6)","19200bps(7)","38400bps(8)",
			    "57600bps(9)","115200bps(10)","自适应(255)" };
		return data;
	};

	public static String[] getFlowControl(){
		String[] data = { "无(0)","硬件(1)","软件(2)" };
		return data;
	};



	public static String[] getTerminalProtocolType(){
		String[] data = { "国网协议","南网协议" };
		return data;
	};

	public static String[] getPARITY(){
		String[] data = { "无(0)","奇ODD(1)","偶EVEN(2)","MARK(3)","SPACE(4)" };
		return data;
	};

	public static String[] getTerminalType(){
		String[] data = { "二合一","三合一" };
		return data;
	};

	public static String[] getTaskType(){
		String[] data = { "普通采集方案(1)","事件采集方案(2)","透明方案(3)",
				"上报方案(4)","脚本方案(5)","实时监控方案(6)" };
		return data;
	};

	public static String[] getTaskPriority(){
		String[] data = { "首要(1)","必要(2)","需要(3)","可能(4)" };
		return data;
	};

	public static String[] getTaskState(){
		String[] data = { "正常(1)","停用(2)" };
		return data;
	};

	public static String[] getIntervalType(){
		String[] data = { "前闭后开(0)","前开后闭(1)","前闭后闭(2)","前开后开(3)" };
		return data;
	};

	public static String[] getTaskStatus(){
		String[] data = { "启用","停用" };
		return data;
	};

	public static void main(String[] args) {
		Constant.getVal(0,"PortRate");
	}

}
