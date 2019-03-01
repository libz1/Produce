package FilterWeb;

public class WEBMain {

	public static void main(String[] args) {
		String msg = "��Һ� :) ��<script>�����У�����ҵ...";

		Request request = new Request();
		request.setRequstString(msg);
		Response response = new Response();
		response.setResponseString(msg);

		WEBMsgProcessor mp = new WEBMsgProcessor();
		mp.setRequset(request);
		mp.setResponse(response);

		// ��д����  ���ޱ��
		WEBFilterChain fc = new WEBFilterChain();
		// ����ʽ��� ���������
		fc.addFilter(new WEBHTMLFilter())
			.addFilter(new WEBFaceFilter())
			.addFilter(new WEBSensitiveFilter())
			;

		// response�Ĺ������������Ӧ��������request�෴  ���������
		mp.setFc(fc);

		mp.process();
		System.out.println(request.getRequstString());

		System.out.println(response.getResponseString());

		// 5����ʵ�֣�˫�����������ƺ�ʵ�� ��strust java web�ж���������ʵ�ֹ��̣���ʵ�ֿͻ��˵��������Ĺ��ˣ�Ҳʵ�ַ��������ͻ��˵Ĺ���
		// �ݹ����ʵ�ֹ���
//		WEBFilter f = filters.get(index);
//		index ++;
//		f.doFilter(request, response, webfc);
//		request.setRequstString(request.getRequstString().replaceAll("<", "[").replaceAll(">", "]")+"---htmlFilter");
//		webfc.doFilter(request, response, webfc);
//		response.setResponseString(response.getResponseString().replaceAll("<", "[").replaceAll(">", "]")+"---htmlFilter");


		// 6����ʵ��  filter������������������������˳�������ִ����һ��������
		// �Ӽ����ǶȽ������������������һ����
//		String str = request.getRequstString();
//		if (str.indexOf(":\\)") < 0){
//			return;
//		}

	}

}
