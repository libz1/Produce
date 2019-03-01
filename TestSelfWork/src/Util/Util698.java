package Util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.eastsoft.util.DataConvert;
import com.eastsoft.util.Debug;


public class Util698 {
	private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

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
	// xuky 2018.05.10 参考 https://blog.csdn.net/e_wsq/article/details/70812637
	// 重新定位log4j.properties的位置，放在外面，用户可以自行调整
	public static void InitLog4jConfig() {
	}

	public static byte[] String2ByteArray(String data){
		byte[] byteData = new byte[data.length() / 2];
		// 将16进制字符串转为Byte数组
		byteData = DataConvert.hexString2ByteArray(data);
		return byteData;
	}
	static public Long getMilliSecondBetween_new(String aDatime1, String aDatime2) {
		Long diff = (long) 0;
		try{
			if (aDatime2.equals("") || aDatime2 == "")
				diff = (long)0;
			LocalDateTime d1 = LocalDateTime.parse(aDatime1, format);
			LocalDateTime d2 = LocalDateTime.parse(aDatime2, format);
			diff = Duration.between(d2,  d1).toMillis();
			// xuky 2018.08.03 考虑到前后两个值可能顺序颠倒，所以取值绝对值
			diff = Math.abs(diff);
		}
		catch(Exception e)
		{
			Util698.log(Util698.class.getName(), "getMilliSecondBetween_new Exception:"+e.getMessage(), Debug.LOG_INFO);
		}
		return diff;
	};

	static public String getDateTimeSSS_new() {
		String dateStr = "";
		try{
			dateStr = format.format(LocalDateTime.now());
		}
		catch (Exception e){
			Util698.log(Util698.class.getName(), "getDateTimeSSS_new Exception:" + e.getMessage(), Debug.LOG_INFO);
			// xuky 2018.06.06 处理之前出现的异常问题
			Debug.sleep(100);
			getDateTimeSSS_new();
		}
		return dateStr;
	};
	public static Object getFirstObject(List list) {
		Object ret = null;
		if (list != null)
			if (list.size() > 0)
				ret = list.get(0);
		return ret;
	}
	// 获取URL 时间
	public static String getURLDateTime1() {
		String url = "http://api.k780.com:88/?app=life.time&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";
		String ret = "";
		try {
			ret = getURLData(url);
		} catch (Exception e) {
			Util698.log(Util698.class.getName(), "getURLDateTime1 Exception:" + e.getMessage(), Debug.LOG_INFO);
			if (e.getMessage().equals("api.k780.com"))
				System.out.println("getURLDateTime err-> 无法连接" + url + "，请检查网络情况！");
			else {
				System.out.println("getURLDateTime err->" + e.getMessage());
				e.printStackTrace();
			}
		}
		if (!ret.equals("")) {
			String[] str = ret.split("datetime_1\":\"");
			if (str != null || str.length >= 1) {
				ret = str[1];
				ret = ret.substring(0, ret.indexOf("\""));
			}
		}
		return ret;
	}
	// 获取URL数据
	public static String getURLData(String urlString) throws Exception {
		URL url = new URL(urlString);
		URLConnection urlConnection = url.openConnection(); // 打开连接
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8")); // 获取输入流
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}
	// 根据给定的时间修改本地PC时间信息
	public static Boolean setSystemDateTime(String datetime) {
		Boolean ret = true;
		if (datetime == null || datetime.equals("") || datetime.length() != "2017-11-06 10:11:14".length())
			return false;

		String osName = System.getProperty("os.name");
		String cmd = "";
		try {
			if (osName.matches("^(?i)Windows.*$")) {// Window 系统
				// 格式 HH:mm:ss
				String time = datetime.split(" ")[1];
				cmd = "  cmd /c time " + time;
				Process process = Runtime.getRuntime().exec(cmd);
				// 格式：yyyy-MM-dd
				String date = datetime.split(" ")[0];
				cmd = " cmd /c date " + date;
				Runtime.getRuntime().exec(cmd);
				Util698.log(Util698.class.getName(), "修改本地时钟"+datetime, Debug.LOG_INFO);
			}
		} catch (Exception e) {
			Util698.log(Util698.class.getName(), "setSystemDateTime Exception:"+e.getMessage(), Debug.LOG_INFO);
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}
	// 根据对象的属性名获取对象的属性值
	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);

			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});

			// xuky 2017.03.10 用户录入的采集器地址有空格，导致后续处理异常，对此异常数据进行处理
			if (value != null) {
				String type = value.getClass().toString();
				if (type.indexOf("String") >= 0) {
					if (!type.equals("class [Ljava.lang.String;")) {
						String tmp = (String) value;
						tmp = tmp.trim();
						value = tmp;
					}
				}
			}
			return value;
		} catch (Exception e) {
			Util698.log(Util698.class.getName(), "getFieldValueByName Exception:" + e.getMessage(), Debug.LOG_INFO);
			e.printStackTrace();
			return null;
		}
	}
	// 得到多个对象的属性内容，各个属性内容转为字符串，使用,进行分隔
	public static String getObjectAttrs(Object o, String getter) {
		// 有,分隔，表示有多个字段
		String ret = "";
		String[] tmp = getter.split(",");
		for (String str : tmp) {
			Object value = null;
			try {
				Method method = o.getClass().getMethod(str, new Class[] {});
				value = method.invoke(o, new Object[] {});
			} catch (Exception e) {
				Util698.log(Util698.class.getName(), "getObjectAttrs Exception:" + e.getMessage(), Debug.LOG_INFO);
				e.printStackTrace();
			}
			if (value == null)
				ret += ",";
			else {
				String type = value.getClass().toString();
				if (type.indexOf("String") >= 0)
					ret += (String) value + ",";

				if (type.toLowerCase().indexOf("int") >= 0)
					ret += DataConvert.int2String((int) value) + ",";
			}
		}

		return ret;
	}

	public static String getGetter(String attrName) {
		String firstLetter = attrName.substring(0, 1).toUpperCase();
		String getter = "get" + firstLetter + attrName.substring(1);
		return getter;
	}

	public static String getSetter(String attrName) {
		String firstLetter = attrName.substring(0, 1).toUpperCase();
		String getter = "set" + firstLetter + attrName.substring(1);
		return getter;
	}
	// 根据对象的属性名设置对象的属性值
	// 需要有函数名称、函数参数（因为存在重载情况，通过参数类型进行函数判断）
	public static void setFieldValueByName(String fieldName, Object o, Object val) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "set" + firstLetter + fieldName.substring(1);

			Map<String, Object> info = getFiledsInfoByName(o, fieldName);
			Object valType = info.get("type");
			// xuku 2017.03.27 存在数据与对象属性不匹配的情况，需要进行数据处理
			String attr_type = ((Class<?>) valType).getName();
			String data_type = "";
			if (val != null) {
				data_type = val.getClass().getName();
				// 传入的参数类型为string，但是对象属性的类型为int，进行数据转换处理
				if (data_type.toLowerCase().indexOf("string") >= 0 && attr_type.equals("int")) {
					val = DataConvert.String2Int((String) val);
				}
				if (data_type.toLowerCase().indexOf("int") >= 0 && attr_type.equals("string")) {
					val = DataConvert.int2String((int) val);
				}
				Method method = o.getClass().getMethod(getter, new Class[] { (Class) valType });
				method.invoke(o, new Object[] { val });
			}
		} catch (Exception e) {
			Util698.log(Util698.class.getName(), "setFieldValueByName Exception:" + e.getMessage(), Debug.LOG_INFO);
			e.printStackTrace();
		}
	}
	// 根据属性名称获取属性信息
	public static Map<String, Object> getFiledsInfoByName(Object o, String name) {
		Field[] fields = o.getClass().getDeclaredFields();
		Map<String, Object> infoMap = new HashMap<String, Object>();
		for (Field f : fields) {
			if (f.getName().toLowerCase().equals(name.toLowerCase())) {
				infoMap.put("type", f.getType());
				infoMap.put("name", f.getName());
				infoMap.put("value", getFieldValueByName(f.getName(), o));
				break;
			}
		}
		fields = null;
		return infoMap;
	}
	// 得到对象的属性内容
	public static Object getObjectAttr(Object o, String getter) {
		Object value = null;
		try {
			Method method = o.getClass().getMethod(getter, new Class[] {});
			value = method.invoke(o, new Object[] {});
		} catch (Exception e) {
			Util698.log(Util698.class.getName(), "getObjectAttr Exception:" + e.getMessage(), Debug.LOG_INFO);
			e.printStackTrace();
		}
		return value;
	}
	public static Object[] getFieldValueTypeByName(String fieldName, Object o) {
		Object[] ret = new Object[2];
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);

			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			String type = "";
			// xuky 2017.03.10 用户录入的采集器地址有空格，导致后续处理异常，对此异常数据进行处理
			if (value != null) {
				type = value.getClass().toString();
				if (type.indexOf("String") >= 0) {
					if (!type.equals("class [Ljava.lang.String;")) {
						String tmp = (String) value;
						tmp = tmp.trim();
						value = tmp;
					}
				}
			}
			ret[0] = value;
			ret[1] = type;
		} catch (Exception e) {
			Util698.log(Util698.class.getName(), "getFieldValueTypeByName Exception:" + e.getMessage(), Debug.LOG_INFO);
			e.printStackTrace();
			return null;
		}
		return ret;
	}
	// xuky 如果使用getFieldValueTypeByName 会因为数据为空，无法返回具体的属性类型
	public static String getFieldTypeByName(String fieldName, Object o) {
		List<Map> infoList = getFiledsInfo(o);
		String name, type;
		for (Map<String, Object> info : infoList) {
			if (info.get("name").toString().equals(fieldName))
				return info.get("type").toString();
		}
		return "";
	}
	// 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
	// 参考http://blog.csdn.net/linshutao/article/details/7693625
	public static List getFiledsInfo(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		List<Map> list = new ArrayList();
		Map<String, Object> infoMap = null;

		String type = "";
		for (int i = 0; i < fields.length; i++) {
			infoMap = new HashMap<String, Object>();
			type = fields[i].getType().toString();
			// xuky 2016.11.15
			if (type.toLowerCase().indexOf("list") < 0) {
				infoMap.put("type", fields[i].getType());
				infoMap.put("name", fields[i].getName());
				infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
				list.add(infoMap);
			}
		}
		infoMap = null;
		fields = null;
		return list;
	}
	// 判断是否是整数数值
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {

			return false;
		}
	}

	// 判断是否是整数数值
	public static boolean isNumber(String value) {
		return isInteger(value);
	}

	public static void main(String[] arg){

	}


}
