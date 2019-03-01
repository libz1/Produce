package SelfServer;
import java.util.LinkedHashMap;
import java.util.Map;

import com.eastsoft.util.DataConvert;

import Util.DealData;

public class MainServer {

	private volatile static MainServer uniqueInstance;
	private Map<String, SelfServer> ServerList = new LinkedHashMap<String, SelfServer>();

	public static MainServer getInstance() {
		if (uniqueInstance == null) {
			synchronized (MainServer.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new MainServer();
				}
			}
		}
		return uniqueInstance;
	}

	private MainServer(){
		DealData.getInstance();
		// xuky 2018.07.31 模拟打开16路自检服务
		for(int i=1;i<=16;i++){
			int a = 37020050+i;
			String IP = "129.1.51.21";
			int port = 30000+i; // 端口号
			ServerList.put(IP+":"+DataConvert.int2String(port), new SelfServer(IP,port,DataConvert.int2String(a)));
		}
	}

	public Map<String, SelfServer> getServerList() {
		return ServerList;
	}

	public static void main(String args[]){
		MainServer.getInstance();
	}
}
