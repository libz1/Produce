package serial;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HelpInstance {

	Map<String, MinaSerialServer> serialList = new ConcurrentHashMap<String, MinaSerialServer>();

	private volatile static HelpInstance uniqueInstance;

	public static HelpInstance getInstance() {
		if (uniqueInstance == null) {
			synchronized (HelpInstance.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new HelpInstance();
				}
			}
		}
		return uniqueInstance;
	}

	private HelpInstance() {
		//
		// ����COM71 �յ����ݺ��͸� COM27
		// ����COM27 �յ����ݺ��͸� COM71

		Util698.log(HelpInstance.class.getName(), "��������...", Debug.LOG_INFO);
		// MinaSerialServer minaSerialServer = null;
			MinaSerialServer minaSerialServer = new MinaSerialServer("COM20", "COM72");
			serialList.put("COM20", minaSerialServer);

			minaSerialServer = new MinaSerialServer("COM72", "COM20");
			serialList.put("COM72", minaSerialServer);

			minaSerialServer = new MinaSerialServer("COM21", "COM74");
			serialList.put("COM21", minaSerialServer);
			minaSerialServer = new MinaSerialServer("COM74", "COM21");
			serialList.put("COM74", minaSerialServer);

	}

	public Map<String, MinaSerialServer> getSerialList() {
		return serialList;
	}

	public static void main(String[] args) {
		HelpInstance.getInstance();
	}

}
