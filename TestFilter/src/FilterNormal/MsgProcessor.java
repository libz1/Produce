package FilterNormal;

public class MsgProcessor {
	private String msg;

	// 3����ʵ�֣�������������  ������    �����ģʽ֮�������� ��
	// ���Զ�̬�����ù�������������ݡ�ִ�е��Ⱥ����
	Filter[] filters = {new HTMLFilter(), new SensitiveFilter(), new FaceFilter()};
	// �����б���Ϣ����д�������ļ��У����ⲿ�������

	// 4����ʵ�֣��������е������������е�������ӵ����������м䲿��
	FilterChain fc = new FilterChain();

	public FilterChain getFc() {
		return fc;
	}

	public void setFc(FilterChain fc) {
		this.fc = fc;
	}

	public String process() {
		// 4����ʵ�֣�
		String ret4 = fc.doFilter(msg);
		return ret4;

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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
