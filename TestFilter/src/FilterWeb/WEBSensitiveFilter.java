package FilterWeb;

public class WEBSensitiveFilter implements WEBFilterInterface {

	@Override
	public void doFilter(Request request, Response response, WEBFilterChain webfc) {
		// ��ִ�е�ǰ������
		request.setRequstString(request.getRequstString().replaceAll("����ҵ", "jiuye").replaceAll("����", "")+"---senstiveFilter");
		// ������һ��������
		webfc.doFilter(request, response, webfc);
		// ������ȫ���ĵ����ˣ����������ִ�е�
		// �ǳ������ʵ������������
		response.setResponseString(response.getResponseString().replaceAll("����ҵ", "jiuye").replaceAll("����", "")+"---senstiveFilter");
	}

}
