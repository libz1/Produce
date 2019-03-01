package FilterWeb;

public class WEBMsgProcessor {
	private Request requset;
	private Response response;

	// 3����ʵ�֣�������������  ������    �����ģʽ֮�������� ��
	// ���Զ�̬�����ù�������������ݡ�ִ�е��Ⱥ����
	WEBFilterInterface[] filters = {new WEBHTMLFilter(), new WEBSensitiveFilter()};
	// �����б���Ϣ����д�������ļ��У����ⲿ�������

	// 4����ʵ�֣��������е������������е�������ӵ����������м䲿��
	WEBFilterChain fc = new WEBFilterChain();

	public WEBFilterChain getFc() {
		return fc;
	}

	public void setFc(WEBFilterChain fc) {
		this.fc = fc;
	}

	public void process() {

		fc.doFilter(requset,response,fc);

		// 4����ʵ�֣�
//		String ret4 = fc.doFilter(msg);
//		return ret4;

//		// 3����ʵ�֣�
//		String ret3 = msg;
//		for(Filter f: filters)
//			ret3 = f.doFilter(ret3);
//		return ret3;

//		// 2����ʵ�֣�
//		String ret2 = new HTMLFilter().doFilter(msg);
//		ret2 = new SensitiveFilter().doFilter(ret2);
//		return ret2;
//
//
//		// 1����ʵ�֣����µ��������߼����ǻ᲻���޸ĵġ�׼��ʹ�ñȽ����׵Ĵ�����
//		// html
//		String ret1 = msg.replaceAll("<", "[")
//					.replaceAll(">", "]");
//		// sensitive
//		ret1 = ret1.replaceAll("����ҵ", "jiuye")
//				.replaceAll("����", "");
//		return ret1;
	}

	public Request getRequset() {
		return requset;
	}

	public void setRequset(Request requset) {
		this.requset = requset;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}


}
