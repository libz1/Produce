package SelfServer;
import java.util.LinkedHashMap;
import java.util.Map;

import com.eastsoft.util.DataConvert;
import com.eastsoft.util.Debug;

public class MainSelfServer {

	private volatile static MainSelfServer uniqueInstance;
	private Map<String, SelfServer> ServerList = new LinkedHashMap<String, SelfServer>();

	public static MainSelfServer getInstance() {
		if (uniqueInstance == null) {
			synchronized (MainSelfServer.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new MainSelfServer();
				}
			}
		}
		return uniqueInstance;
	}

	private MainSelfServer(){
		DealData.getInstance();
		// xuky 2018.07.31 模拟打开16路自检服务
		for(int i=1;i<=16;i++){
			int a = 37020050+i;
			String IP = "192.168.127.2";
			int port = 30000+i; // 端口号
			// 启动模拟自检工装的SelfServer
			Debug.sleep(100);
			ServerList.put(IP+":"+DataConvert.int2String(port), new SelfServer(IP,port,DataConvert.int2String(a)));
		}

	}

	public Map<String, SelfServer> getServerList() {
		return ServerList;
	}

	public static void main(String args[]){
		MainSelfServer.getInstance();
	}
}
