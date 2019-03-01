package serial;

import org.apache.log4j.Logger;

public class Util698 {
	public static Boolean isCompleteFrame(String recvData, String comID){
		try{
			return isCompleteFrame1(recvData,comID );
		}
		catch(Exception e){
			Util698.log(Util698.class.getName(), "isCompleteFrame:" + e.getMessage(), Debug.LOG_INFO);
			Util698.log(Util698.class.getName(), "isCompleteFrame解析:" + recvData+"出现意外数据！", Debug.LOG_INFO);
			return false;
		}
	}

	public static Boolean isCompleteFrame1(String recvData, String comID){

		// xuky 2018.12.22 统一在这里进行数据保存，而不是在后面一个个的保存，之前的处理isCompleteFrame5和isCompleteFrame56的时候，没有进行保存，导致出现不易发现的错误
		SoftParameter.getInstance().getRecvDataMap().put(comID,recvData);

		// xuky 2019.01.09 盛迪台体升源、降源协议
		// 5个F9 开头，总长度为46字节
		//F9 F9 F9 F9 F9 B1 10 00 02 00 10 20 13 88 55 F0 55 F0 55 F0 00 00 27 10 00 00 27 10 00 00 27 10 00 00 00 00 00 00 2E E0 5D C0 00 00 FE C7
		if (recvData.startsWith("F9F9F9F9F9") && (recvData.length() == 92 || recvData.length() == 26) )
			return true;

		if (recvData.equals("B110000200107A35") || recvData.equals("B103021603B7FF")){
			return true;
		}
		if ((recvData.startsWith("4D") || recvData.startsWith("49") ||recvData.startsWith("55")) && recvData.endsWith("3B")){
			return true;
		}

		// xuky 2019.02.14 盛迪台体
		//01H+地址(A——Z)+ 长度+06H(肯定)/15H(否定)+校验位+结束(17H)
//		FEFEFEFEFE010106060617
		if (recvData.startsWith("FEFEFEFEFE01")){
//			&& recvData.length() == 92)
			if (recvData.endsWith("17"))
				return true;
			else
				return false;
		}

		if (!recvData.substring(recvData.length() - 2).equals("16")) {
//			Util698.log(Util698.class.getName(), "isCompleteFrame1 return flase:" + recvData, Debug.LOG_INFO);
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

			// 68 17 00 43 45 AA AA AA AA AA AA 00 5B 4F 05 01 01 40 01 02 00 00 C6 07 16
			if (recvData.indexOf("AAAAAAAAAAAA") >= 0  && recvData.indexOf("6817004345") >= 0){
				Util698.log(Util698.class.getName(), "收到特殊协议:" + recvData, Debug.LOG_INFO);
				return true;
			}

			int pos1 = recvData.indexOf("68");
			int pos2 = recvData.indexOf("68", pos1+1);
//			System.out.println("68 pos ->" + (pos2-pos1) );

			// xuky 2017.07.19 出现了设备地址中有16的情况
			// 645报文中应该有两处68和最后的16
			if (pos1 < 0 || pos2 < 0  ){
				if (pos1>=0){
					// xuky 2018.12.11 需要进行376.2协议报文的判断
					// int pos1 = recvData.indexOf("68");
					String str = recvData.substring(pos1+2,pos1+4);
					int len1 = DataConvert.hexString2Int(str)*2;
					len1 = pos1 + len1;
					// xuky 2019.02.18 出现FEFE6816数据，导致执行异常
					if (recvData.length() < len1){
						Util698.log(Util698.class.getName(), "isCompleteFrame-L1 return flase:" + recvData, Debug.LOG_INFO);
						return false;
					}
					// xuky 2019.02.26 出现680000810116数据，导致执行异常
					if (len1 == 0){
						Util698.log(Util698.class.getName(), "isCompleteFrame-L2 return flase:" + recvData, Debug.LOG_INFO);
						return false;
					}
					str = recvData.substring(len1-2,len1);
					if (str.equals("16"))
						return true;
				}
				Util698.log(Util698.class.getName(), "isCompleteFrame2 return flase:" + recvData, Debug.LOG_INFO);
				return false;
			}

			// xuky 2017.09.20 出现了设备地址中有68的情况 000000611668
			// FE FE FE FE 68 68 16 61 00 00 00 68 95 00 11 16
			// FE FE FE FE 68 68 16
			if (pos2 - pos1 < 14 ){
				// 判断两个68之间的字符个数
				int pos3 = recvData.indexOf("68", pos2+1);
				if (pos3 < 0){
					Util698.log(Util698.class.getName(), "isCompleteFrame3 return flase:" + recvData, Debug.LOG_INFO);
					return false;
				}
				else{
					if (pos3 - pos1 < 14 ){
						int pos4 = recvData.indexOf("68", pos3+1);
						if (pos4 < 0){
							Util698.log(Util698.class.getName(), "isCompleteFrame4 return flase:" + recvData, Debug.LOG_INFO);
							return false;
						}
					}
				}
			}
			// xuky 2018.11.03 出现16H判断异常的情况
			if (pos2 - pos1 == 14 ){
				// xuky 2018.11.12 添加特殊自定义645报文的长度判断
				// 6899999999999968140054FFFFEE01000101020002011801029C01C1FB0245534131000001ACBF092E43B981B1E2480206112233445566030001011801029C01C1FB0245534131000001AD4E60A617726EA74BD1040001020A0102030405060708090A3316
				Boolean is_newFormat = false;
				if (recvData.startsWith("689999999999996814")){
					if (recvData.indexOf("FFFFEE01") >= 0)
						is_newFormat = true;
				}
				if (!is_newFormat){
					String str = recvData.substring(pos2+4,pos2+6);
					int len = DataConvert.hexString2Int(str)*2;
					len = len + 0;
					// xuky 2019.02.19 substring前进行必要的判断，以免出现异常数据
					if (recvData.length() < pos2+6){
						Util698.log(Util698.class.getName(), "isCompleteFrame-L2 return flase:" + recvData, Debug.LOG_INFO);
						return false;
					}
					str = recvData.substring(pos2+6);
					if (str.length() < len){
						Util698.log(Util698.class.getName(), "isCompleteFrame5 return flase:" + recvData, Debug.LOG_INFO);
						return false;
					}
					// 进行最后一个16的判断
					// 68056145520700689F1D878687634B3435CF34F42E357886746433333516472DB902DEE91B16
					// 68546045520700689F1D878687634B3435CF34F42E357886746433333516FB55E76F51BC7B9D3B7016
				}
				else{
					// xuky 2019.02.19 substring前进行必要的判断，以免出现异常数据
					if (recvData.length() < pos2+8){
						Util698.log(Util698.class.getName(), "isCompleteFrame-L2 return flase:" + recvData, Debug.LOG_INFO);
						return false;
					}

					String str = recvData.substring(pos2+4,pos2+8);
					int len = DataConvert.hexString2Int(str)*2;
					str = recvData.substring(pos2+8);
					len = len + 8+4;
					if (str.length() != len){
						Util698.log(Util698.class.getName(), "isCompleteFrame6 return flase:" + recvData, Debug.LOG_INFO);
						return false;
					}
				}
			}
		}
//		System.out.println("checkData => 645 :"+recvData);
		return true;
	}
	public static void log(String className, String message, String level) {
		// log4j
		// 重点和难点在于log4j.properties的定义
		// log4j.properties记录了日志的存储方式、日志内容的格式、日志的存储选项等


		Logger log = Logger.getLogger(className);
		if (level.equals(Debug.LOG_DEBUG))
			log.debug(message);
		if (level.equals(Debug.LOG_INFO))
			log.info(message);
		if (level.equals(Debug.LOG_WARN))
			log.warn(message);
		if (level.equals(Debug.LOG_ERROR))
			log.error(message);
		if (level.equals(Debug.LOG_FATAL))
			log.fatal(message);
	}

}
