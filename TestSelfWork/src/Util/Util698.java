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
		// �ص���ѵ�����log4j.properties�Ķ���
		// log4j.properties��¼����־�Ĵ洢��ʽ����־���ݵĸ�ʽ����־�Ĵ洢ѡ���

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
	// xuky 2018.05.10 �ο� https://blog.csdn.net/e_wsq/article/details/70812637
	// ���¶�λlog4j.properties��λ�ã��������棬�û��������е���
	public static void InitLog4jConfig() {
	}

	public static byte[] String2ByteArray(String data){
		byte[] byteData = new byte[data.length() / 2];
		// ��16�����ַ���תΪByte����
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
			// xuky 2018.08.03 ���ǵ�ǰ������ֵ����˳��ߵ�������ȡֵ����ֵ
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
			// xuky 2018.06.06 ����֮ǰ���ֵ��쳣����
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
	// ��ȡURL ʱ��
	public static String getURLDateTime1() {
		String url = "http://api.k780.com:88/?app=life.time&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";
		String ret = "";
		try {
			ret = getURLData(url);
		} catch (Exception e) {
			Util698.log(Util698.class.getName(), "getURLDateTime1 Exception:" + e.getMessage(), Debug.LOG_INFO);
			if (e.getMessage().equals("api.k780.com"))
				System.out.println("getURLDateTime err-> �޷�����" + url + "���������������");
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
	// ��ȡURL����
	public static String getURLData(String urlString) throws Exception {
		URL url = new URL(urlString);
		URLConnection urlConnection = url.openConnection(); // ������
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8")); // ��ȡ������
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}
	// ���ݸ�����ʱ���޸ı���PCʱ����Ϣ
	public static Boolean setSystemDateTime(String datetime) {
		Boolean ret = true;
		if (datetime == null || datetime.equals("") || datetime.length() != "2017-11-06 10:11:14".length())
			return false;

		String osName = System.getProperty("os.name");
		String cmd = "";
		try {
			if (osName.matches("^(?i)Windows.*$")) {// Window ϵͳ
				// ��ʽ HH:mm:ss
				String time = datetime.split(" ")[1];
				cmd = "  cmd /c time " + time;
				Process process = Runtime.getRuntime().exec(cmd);
				// ��ʽ��yyyy-MM-dd
				String date = datetime.split(" ")[0];
				cmd = " cmd /c date " + date;
				Runtime.getRuntime().exec(cmd);
				Util698.log(Util698.class.getName(), "�޸ı���ʱ��"+datetime, Debug.LOG_INFO);
			}
		} catch (Exception e) {
			Util698.log(Util698.class.getName(), "setSystemDateTime Exception:"+e.getMessage(), Debug.LOG_INFO);
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}
	// ���ݶ������������ȡ���������ֵ
	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);

			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});

			// xuky 2017.03.10 �û�¼��Ĳɼ�����ַ�пո񣬵��º��������쳣���Դ��쳣���ݽ��д���
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
	// �õ����������������ݣ�������������תΪ�ַ�����ʹ��,���зָ�
	public static String getObjectAttrs(Object o, String getter) {
		// ��,�ָ�����ʾ�ж���ֶ�
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
	// ���ݶ�������������ö��������ֵ
	// ��Ҫ�к������ơ�������������Ϊ�������������ͨ���������ͽ��к����жϣ�
	public static void setFieldValueByName(String fieldName, Object o, Object val) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "set" + firstLetter + fieldName.substring(1);

			Map<String, Object> info = getFiledsInfoByName(o, fieldName);
			Object valType = info.get("type");
			// xuku 2017.03.27 ����������������Բ�ƥ����������Ҫ�������ݴ���
			String attr_type = ((Class<?>) valType).getName();
			String data_type = "";
			if (val != null) {
				data_type = val.getClass().getName();
				// ����Ĳ�������Ϊstring�����Ƕ������Ե�����Ϊint����������ת������
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
	// �����������ƻ�ȡ������Ϣ
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
	// �õ��������������
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
			// xuky 2017.03.10 �û�¼��Ĳɼ�����ַ�пո񣬵��º��������쳣���Դ��쳣���ݽ��д���
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
	// xuky ���ʹ��getFieldValueTypeByName ����Ϊ����Ϊ�գ��޷����ؾ������������
	public static String getFieldTypeByName(String fieldName, Object o) {
		List<Map> infoList = getFiledsInfo(o);
		String name, type;
		for (Map<String, Object> info : infoList) {
			if (info.get("name").toString().equals(fieldName))
				return info.get("type").toString();
		}
		return "";
	}
	// ��ȡ��������(type)��������(name)������ֵ(value)��map��ɵ�list
	// �ο�http://blog.csdn.net/linshutao/article/details/7693625
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
	// �ж��Ƿ���������ֵ
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {

			return false;
		}
	}

	// �ж��Ƿ���������ֵ
	public static boolean isNumber(String value) {
		return isInteger(value);
	}

	public static void main(String[] arg){

	}


}
