package com.example.androidbase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.ClipboardManager;
import android.widget.EditText;
import android.widget.Toast;

public class Tool {

	public static String getLocalVerName(Context mContext) {
		// 添加版本号管理，软件发布时，在manifest文件从标注即�?
		String versionName = "";
		try {
			PackageManager pam = mContext.getPackageManager();
			PackageInfo pinfo = pam.getPackageInfo(mContext.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			// 展示给用户的版本信息
			versionName = pinfo.versionName;

			// 软件、应用商店判断的内容 只是�?��数据，每次更新的时�?，维护这个数据的内容
			// int versionCode = pinfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	public static int getLocalVerCode(Context mContext) {
		// 添加版本号管理，软件发布时，在manifest文件从标注即�?
		int versionCode = 0;
		try {
			PackageManager pam = mContext.getPackageManager();
			PackageInfo pinfo = pam.getPackageInfo(mContext.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			// 展示给用户的版本信息
			versionCode = pinfo.versionCode;

			// 软件、应用商店判断的内容 只是�?��数据，每次更新的时�?，维护这个数据的内容
			// int versionCode = pinfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public static int String2Int(String data) {
		int ret = 0;
		if (data == null || data.equals("") )
			return 0;
		ret = Integer.parseInt(data);
		return ret;
	}

	public static String int2String(int data) {
		String ret = "";
		ret = String.valueOf(data);
		return ret;
	}

	/**
	 * 得到当前时间的日期字符串
	 * 
	 * @return <code>String<code/>yyyy-MM-dd
	 */
	public static String getDate() {
		/*
		 * 得到yyyy-MM-dd HH:mm:ss格式的日�?时间字符�?
		 */
		// - 2 * 24 * 60 * 60 * 1000
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		return dateStr;
	};

	/**
	 * 得到当前时间（前、后n天）的日期字符串
	 * 
	 * @param aDay
	 *            <code>int</code> 增减天数，正数为向后，负数为向前
	 * 
	 * @return <code>String<code/>yyyy-MM-dd
	 */
	static public String getDate(int aDay, String date) {
		/*
		 * 得到yyyy-MM-dd HH:mm:ss格式的日�?时间字符�?
		 */
		// Date d = new Date();
		String f = "yyyy-MM-dd";
		Date d = string2Date(date, f);
		SimpleDateFormat sdf = new SimpleDateFormat(f);
		String dateStr = sdf.format(d.getTime() + aDay * 24 * 60 * 60 * 1000);
		return dateStr;
	};

	public static Date string2Date(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// 弹窗显示确认信息，确认后执行某个操作
	public static void popWinRun(final Context context, String msg,
			final String runName) {
		Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("确认[" + msg + "]？");
		// builder.setTitle("确认[" + msg + "]？");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				runMethod(context, runName);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}
	
	
	// 弹窗显示确认信息，确认后执行某个操作
	public static void popWinRunOnly(final Context context, String msg,
			final String runName) {
		Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg);
		// builder.setTitle("确认[" + msg + "]？");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				runMethod(context, runName);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}
	

	// 弹窗显示提示信息，确认 ,可以
	public static void popTxtWinNoCopy(final Context context, String msg) {
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("系统提示");
		builder.setMessage(msg);
//		builder.setPositiveButton("复制上述信息后关闭", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE); 
//				cmb.setText(et.getText().toString());
//				Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
//			}
//		});
		builder.setNegativeButton("关闭", null);
		builder.create().show();
	}

	
	// 弹窗显示提示信息，确认 ,可以
	public static void popTxtWinRun(final Context context, String msg) {
		final EditText et = new EditText(context);
		et.setWidth(200);
		et.setHeight(300);
		et.setText(msg);
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("系统提示");
		builder.setView(et); 
//		builder.setPositiveButton("复制上述信息后关闭", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE); 
//				cmb.setText(et.getText().toString());
//				Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
//			}
//		});
		builder.setNegativeButton("关闭", null);
		builder.create().show();
	}
	

	// 弹窗显示确认信息，确认后执行某个操作
	public static void popChooseWinRun(final Context context, String msg,
			final String runName, final CharSequence[] items) {
		Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("请选择");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				String func = runName + Tool.int2String(item);
				runMethod(context, func);
				// items[item];
			}
		});
		// builder.setMessage("确认[" + msg + "]？");
		// builder.setTitle("确认[" + msg + "]？");
		// builder.setPositiveButton("确认", new DialogInterface.OnClickListener()
		// {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// runMethod(context, runName);
		// }
		// });

		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	// 参考http://outofmemory.cn/code-snippet/2689/java-usage-mirror-execution-method
	// JAVA反射技术，动态调用函数
	protected static void runMethod(Object obj, String funName) {
		Method appendMethod;
		// Method[] method;
		try {
			// retrieve the method named "append"
			Class c = obj.getClass();
			// 通过以下方法可以获得所有的函数名称列表
			Method[] method = c.getMethods();
			method = null;
			
			// for (Method m : method) {
			// String name = m.getName();
			// System.out.println(name);
			// }
			//  发现只能找到public类型的函数
			appendMethod = c.getMethod(funName);
			// invoke the method with the specified argument
			appendMethod.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static SoapObject setProperty(SoapObject rpc, String type,
			String param) {
		//  2015.08.03 如果最后是|,无法进行分隔
		if (param.substring(param.length()-1, param.length()).equals("|"))
			param = param+ " ";
		int n = getSplitNum(param, "\\|");
		if (n == 0) {
			// 无参数
		} else {
			for (int i = 0; i < n; i++) {
				String str = getSplitParam(type, "\\|", i);
				if (str.indexOf("[int]") >= 0) {
					String temp = getSplitParam(param, "\\|", i);
					if (temp.equals(" ") || temp.equals("")){
						str = getSplitParam(str, "\\[", 0);
						rpc.addProperty(str, "");
					}
					else{
						int num = Tool.String2Int(temp);
						str = getSplitParam(str, "\\[", 0);
						rpc.addProperty(str, num);
					}
				} else if (str.indexOf("[date]") >= 0) {
					String temp = getSplitParam(param, "\\|", i);
					if (temp.indexOf(" ") < 0) {
						temp = temp + " 00:00:00";
					}
					Date oldTime = Tool
							.string2Date(temp, "yyyy-MM-dd HH:mm:ss");
					// 参考http://blog.csdn.net/tyjhfield/article/details/8705908
					str = getSplitParam(str, "\\[", 0);
					rpc.addProperty(str, Tool.getStrForWebSvr(oldTime));
				} else {
					rpc.addProperty(str, getSplitParam(param, "\\|", i));
				}
			}
		}
		return rpc;
	}

	//  2015-05-19向上级抛出异常
	public static String RunWebServices(String nameSpace, String endPoint,
			String function, String type, String param, int timeout) throws Exception {
		String soapAction = "";

		// // 以下信息应该从配置文件中获得
		// nameSpace = Prm_soft.getInstance().getNameSpace();
		// endPoint = Prm_soft.getInstance().getEndPoint();
		// // nameSpace = "http://qdlkd.com/"; // 命名空间
		// // endPoint = "http://qdlkd.xicp.cn:8089/Android/BarCode.asmx"; //
		// EndPoint

		soapAction = nameSpace + function; // SOAP Action

		// // 指定WebService的命名空间和调用的方法名
		// SoapObject rpc = new SoapObject(nameSpace, methodName);

		// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
		// 配置文件中定义参数的类型，以便将参数添加到rpc中

		// 目前有三种类型：字符串、数值、日期

		// setProperty(rpc,param,type);

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		SoapObject rpc = new SoapObject(nameSpace, function);

		// 根据参数类型及数据进行rpc数据设置
		rpc = setProperty(rpc, type, param);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		//  2015-05-19 添加超时时间参数
		HttpTransportSE transport = new HttpTransportSE(endPoint,timeout*1000);
		String result = "";
		// 调用WebService
		transport.call(soapAction, envelope);

		// 获取返回的数据
		SoapObject object = (SoapObject) envelope.bodyIn;
		// 获取返回的结果
		result = object.getProperty(0).toString();

		// String str = type + "^" + param + "^" + result;
		// GETResult = str;
		//
		// // 触发对象的hadler进行异步处理
		// handler_job();

		return result;
	}

	public static String RunWebServices(String nameSpace, String endPoint,
			String function, SoapObject rpc) {
		String methodName = "", soapAction = "";

		// // 以下信息应该从配置文件中获得
		// nameSpace = Prm_soft.getInstance().getNameSpace();
		// endPoint = Prm_soft.getInstance().getEndPoint();
		// // nameSpace = "http://qdlkd.com/"; // 命名空间
		// // endPoint = "http://qdlkd.xicp.cn:8089/Android/BarCode.asmx"; //
		// EndPoint

		methodName = function; // 调用的方法名称
		soapAction = nameSpace + function; // SOAP Action

		// // 指定WebService的命名空间和调用的方法名
		// SoapObject rpc = new SoapObject(nameSpace, methodName);

		// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
		// 配置文件中定义参数的类型，以便将参数添加到rpc中

		// 目前有三种类型：字符串、数值、日期

		// setProperty(rpc,param,type);

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(endPoint);
		String result = "";
		try {
			// 调用WebService
			transport.call(soapAction, envelope);

			// 获取返回的数据
			SoapObject object = (SoapObject) envelope.bodyIn;
			// 获取返回的结果
			result = object.getProperty(0).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// String str = type + "^" + param + "^" + result;
		// GETResult = str;
		//
		// // 触发对象的hadler进行异步处理
		// handler_job();

		return result;
	}

	// 在远程调用传递参数时，需要提供的dateTime类型数据
	public static String getStrForWebSvr(Date oldTime) {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				.format(oldTime);
	}

	// 睡眠
	public static void activity_sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 从字符串列表中返回指定字符串的位置，用于下拉列表控件的显示内容设置
	public static int getPositon(String[] strArray, String aData) {
		//  2015.06.09 修改默认的数据为0
		int pos = 0;
		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i].equals(aData)) {
				pos = i;
				break;
			}
		}
		return pos;
	}

	// 获得以某个符号分隔的多段数据中的一段数据
	public static String getSplitParam(String param, String split, int index) {
		if (param == null)
			return null;
		String[] array = param.split(split);
		if (index >= array.length)
			return "";
		else {
			if (array[index].equals(" "))
				return "";
			else
				return array[index];
		}
	}

	// 获得以某个符号分隔的多段数据的段数
	public static int getSplitNum(String param, String split) {
		int num = 0;
		String[] array = param.split(split);
		num = array.length;
		if (num == 1)
			if (array[0].equals(""))
				num = 0;
		return num;
	}

	public static void viewToObj(Object obj, Activity a, Class c) {
		try {
			Class classType = obj.getClass();

			Field[] fs = classType.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				// 设置些属性是可以访问的
				f.setAccessible(true);

				// 得到属性的类型
				String type = f.getType().toString();

				// 得到属性的名称
				String name = f.getName();

				// 给属性设值
				if (type.endsWith("String")) {
					f.set(obj, getEditTextData(name.toLowerCase(), a, c));
				} else if (type.endsWith("int") || type.endsWith("Integer")) {
					f.set(obj, 1);
				} else {
					System.out.println(f.getType() + "\t");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 参考 http://blog.csdn.net/kmyhy/article/details/6583804
	public static String getEditTextData(String id, Activity a, Class c) {
		// 依据name取得Field对象
		Field f;
		int resId = 0;
		try {
			f = c.getField(id);
			// 取得int值
			resId = f.getInt(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		EditText et = (EditText) a.findViewById(resId);
		return et.getText().toString();
	}

	// 根据对象中的属性，设置界面中相关控件的内容，界面中控件的id信息是对象属性的全小写字母
	// 关联setEditTextData代码
	public static void objectToView(Object obj, Activity a, Class c) {
		try {
			Class classType = obj.getClass();

			Field[] fs = classType.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				// 设置些属性是可以访问的
				f.setAccessible(true);
				// 得到属性的值
				Object val = f.get(obj);
				// 得到属性的名称
				String name = f.getName();
				// 设置控件的数据
				if (!name.equals("uniqueInstance"))
					setEditTextData(name.toLowerCase(), (String) val, a, c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 参考 http://blog.csdn.net/kmyhy/article/details/6583804
	public static void setEditTextData(String id, String data, Activity a,
			Class c) {
		// 依据name取得Field对象
		Field f;
		int resId = 0;
		try {
			f = c.getField(id);
			// 取得int值
			resId = f.getInt(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		EditText et = (EditText) a.findViewById(resId);
		et.setText(data);
	}

	// 参考 http://www.2cto.com/kf/201402/277230.html
	// 利用JAVA的反射技术，动态获取相关信息
	// 根据文本信息，获得R.drawable.xxx的id信息
	public static int get_class_id(Class c, String txt) {
		int id = 0;
		try {
			Field f;
			f = c.getField(txt);
			id = f.getInt(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	// 数值转为约定长度的字符串
	public static String int2BCDString(int data, int charnum) {
		String ret = "";
		String temp = "";
		temp = Integer.toString(data);
		for (int i = 1; i <= charnum; i++) {
			temp = "0" + temp;
		}
		ret = temp.substring(temp.length() - charnum, temp.length());
		ret = ret.toUpperCase();
		return ret;
	}

	// 组织得到用来显示九宫格文字及图片的map集合
	public static ArrayList<HashMap<String, Object>> getView_ArrayList(
			int maxNum, Class pic, Class text, Context c, Boolean[] rights) {

		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();

		for (int i = 1; i <= maxNum; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			int id = Tool.get_class_id(pic, "n0");
			// 最后一个模块，是系统模块，无需权限控制
			if (i == maxNum) {
				id = Tool.get_class_id(pic, "n" + Tool.int2String(i));
			}
			if (rights[i])
				id = Tool.get_class_id(pic, "n" + Tool.int2String(i));
			map.put("ItemImage", id);
			id = Tool.get_class_id(text, "gridview" + Tool.int2String(i));
			map.put("ItemText", Tool.int2BCDString(i, 2)
					+ c.getResources().getString(id));
			// 将每个map添加到集合中
			lstImageItem.add(map);
		}
		return lstImageItem;

	}
	
	
	public static String getJsonData(String name, String jsondata){
		String ret = "";
		//[{"djid":"2015052800000001","djno":"120150528001","billno":"","dbdate":"2015/5/28 0:00:00","dkwno":"001","dkwname":"1区","placed":"F","opid":"001","opname":"测试"}]
		// .点 	匹配除“\r\n”之外的任何单个字符。要匹配包括“\r\n”在内的任何字符，请使用像“[\s\S]”的模式。
//		[\s\S]
//		Pattern p = Pattern.compile("\""+name+"\":\"(.*?)\"");
		//  2015.07.28 数据中可能包含回车换行符
		Pattern p = Pattern.compile("\""+name+"\":\"([\\s\\S]*?)\"");
		Matcher m = p.matcher(jsondata);
		if (m.find()) ret = m.group(1);
		return ret;
	}
	
	public static void p(Object o){
		System.out.println(o);
	}
	
	public static void Vibrator(Activity a,long milliseconds){
		VibratorUtil.Vibrate(a, milliseconds);
	}
	
	
	
	public static void main(String[] args) {
		String json = "[{\"djid\":\"2015052800000001\",\"djno\":\"120150528001\",\"billno\":\"\",\"dbdate\":\"2015/5/28 0:00:00\",\"dkwno\":\"001\",\"dkwname\":\"1区\",\"placed\":\"F\",\"opid\":\"001\",\"opname\":\"测试\"}]";
		String name = "dkwno";
		p(getJsonData(name,json));
	}
	
	

}
