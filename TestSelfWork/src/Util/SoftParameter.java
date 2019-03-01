package Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.eastsoft.fio.FileToRead;
import com.eastsoft.fio.FileToWrite;
import com.google.gson.Gson;

import Entity.UserManager;


/**
 * 软件变量.
 * <p>
 *
 * @author xuky
 * @version 2016.09.18
 */
public class SoftParameter {
	// 数据类型的选择控制
	private String DBTYPE = "netdb";
	private String progeam_name = "集中器整机自检测试系统";
	private UserManager userManager = null;

	// 断包处理过程
	Map<String, String> recvDataMap = new HashMap<String, String>();
	public Map<String, String> getRecvDataMap() {
		return recvDataMap;
	}

	public void setRecvDataMap(Map<String, String> recvDataMap) {
		this.recvDataMap = recvDataMap;
	}

	// 单例模式：静态变量 uniqueInstance 类的唯一实例
	private volatile static SoftParameter uniqueInstance;

	public static SoftParameter getInstance(String str) {
		if (uniqueInstance == null) {
			synchronized (SoftParameter.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new Gson().fromJson(str, SoftParameter.class);
				}
			}
		}
		return uniqueInstance;
	}

	private SoftParameter() {
	}

	public static SoftParameter getInstance() {
		if (uniqueInstance == null) {
			synchronized (SoftParameter.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					String str = new FileToRead().readLocalFile1("arc\\SoftParameter.json");
					uniqueInstance = new Gson().fromJson(str, SoftParameter.class);
					// xuky 2018.05.10 使用外部的配置文件
					Util698.InitLog4jConfig();
					uniqueInstance.recvDataMap = new HashMap<String, String>();
				}
			}
		}
		return uniqueInstance;
	}

	public void init() {
		String str = new Gson().toJson(this);
		FileToWrite.writeLocalFile1("arc\\SoftParameter.json", str);
	}
	// xuky 2017.03.02 确保线程安全
	public synchronized String saveParam() {
		String str = getParamString();
		FileToWrite.writeLocalFile1("arc\\SoftParameter.json", str);
		return str;
	}

	public String getParamString() {
		// xuky 2018.03.14 CaseList无需进行记录
		uniqueInstance.recvDataMap = new HashMap<String, String>();
		return new Gson().toJson(uniqueInstance);
	}

	// xuky 2014.09.10 调整为需要接收传入的字符串参数
	public void refresh(String str) {
		uniqueInstance = null;
		getInstance(str);
	}

	public String getDBTYPE() {
		return DBTYPE;
	}
	public void setDBTYPE(String dBTYPE) {
		DBTYPE = dBTYPE;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getProgeam_name() {
		return progeam_name;
	}

	public void setProgeam_name(String progeam_name) {
		this.progeam_name = progeam_name;
	}

	public static void main(String[] args) throws IOException {
		SoftParameter softParameter = new SoftParameter();
		uniqueInstance = softParameter;
		softParameter.saveParam();
		// softParameter.init();
	}


}
