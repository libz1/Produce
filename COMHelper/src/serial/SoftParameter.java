package serial;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftParameter {

	Map<String, String> recvDataMap = new ConcurrentHashMap<String, String>();
	Map<String, String> CompleteDataMap = new ConcurrentHashMap<String, String>();
	private volatile static SoftParameter uniqueInstance;
	public static SoftParameter getInstance() {
		if (uniqueInstance == null) {
			synchronized (SoftParameter.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new SoftParameter();
				}
			}
		}
		return uniqueInstance;
	}

	private SoftParameter() {
	}

	public Map<String, String> getRecvDataMap() {
		return recvDataMap;
	}

	public void setRecvDataMap(Map<String, String> recvDataMap) {
		this.recvDataMap = recvDataMap;
	}

	public Map<String, String> getCompleteDataMap() {
		return CompleteDataMap;
	}

	public void setCompleteDataMap(Map<String, String> completeDataMap) {
		CompleteDataMap = completeDataMap;
	}



}
