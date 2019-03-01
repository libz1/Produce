package Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.eastsoft.fio.FileToRead;
import com.eastsoft.fio.FileToWrite;
import com.google.gson.Gson;

import Entity.UserManager;


/**
 * �������.
 * <p>
 *
 * @author xuky
 * @version 2016.09.18
 */
public class SoftParameter {
	// �������͵�ѡ�����
	private String DBTYPE = "netdb";
	private String progeam_name = "�����������Լ����ϵͳ";
	private UserManager userManager = null;

	// �ϰ��������
	Map<String, String> recvDataMap = new HashMap<String, String>();
	public Map<String, String> getRecvDataMap() {
		return recvDataMap;
	}

	public void setRecvDataMap(Map<String, String> recvDataMap) {
		this.recvDataMap = recvDataMap;
	}

	// ����ģʽ����̬���� uniqueInstance ���Ψһʵ��
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
					// ˫�ؼ�����
					String str = new FileToRead().readLocalFile1("arc\\SoftParameter.json");
					uniqueInstance = new Gson().fromJson(str, SoftParameter.class);
					// xuky 2018.05.10 ʹ���ⲿ�������ļ�
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
	// xuky 2017.03.02 ȷ���̰߳�ȫ
	public synchronized String saveParam() {
		String str = getParamString();
		FileToWrite.writeLocalFile1("arc\\SoftParameter.json", str);
		return str;
	}

	public String getParamString() {
		// xuky 2018.03.14 CaseList������м�¼
		uniqueInstance.recvDataMap = new HashMap<String, String>();
		return new Gson().toJson(uniqueInstance);
	}

	// xuky 2014.09.10 ����Ϊ��Ҫ���մ�����ַ�������
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
