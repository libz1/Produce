package FilterNormal;

public class Main {

	public static void main(String[] args) {
		String msg = "��Һ� :) ��<script>�����У�����ҵ...";

		MsgProcessor mp = new MsgProcessor();
		mp.setMsg(msg);

		// ��д����  ���ޱ��
		FilterChain fc = new FilterChain();
		// ����ʽ���
		fc.addFilter(new HTMLFilter())
			.addFilter(new FaceFilter());

		FilterChain fc2 = new FilterChain();
		fc2.addFilter(new SensitiveFilter());
		// 5����ʵ�֣� ���������໥���� ��ΪFilterChain��implements Filter
		fc.addFilter(fc2);

		mp.setFc(fc);


		String result = mp.process();
		System.out.println(result);

		// 5����ʵ�֣�˫�����������ƺ�ʵ�� ��strust java web�ж���������ʵ�ֹ��̣���ʵ�ֿͻ��˵��������Ĺ��ˣ�Ҳʵ�ַ��������ͻ��˵Ĺ���
	}

}
