package com.example.androidbase;

import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Xml;

public class Ver_Version {


	/**
	 * 得到服务器端版本号
	 * 
	 * @param is输入流
	 * @param type
	 *            :InputStream
	 * @return 服务器端版本号
	 * @throws Exception
	 */
	public static int getXMLVer(InputStream is) throws Exception {

		XmlPullParser parser = Xml.newPullParser();

		parser.setInput(is, "utf-8");

		// System.out.println("测试XML解析1");
		String Ver = "0";

		int type = parser.getEventType();
		// System.out.println("测试XML解析2"+type);

		// 循环到末尾
		while (type != XmlPullParser.END_DOCUMENT) {

			switch (type) {

			// 获取<version>6.0</version>中的内容
			case XmlPullParser.START_TAG:
				if ("version".equals(parser.getName())) {
					Ver = parser.nextText();
					System.out.println("Version:" + Ver);
				}
				System.out.println("Ve");
				break;

			}
			type = parser.next();
		}
		// System.out.println("解析xml" + Ver);
		int i = 0;
		try {
			i = Tool.String2Int(Ver);
		} catch (Exception e) {
			e.printStackTrace();
			i = 0;
		}
		return i;
	}

}
