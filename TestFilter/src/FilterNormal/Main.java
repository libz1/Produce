package FilterNormal;

public class Main {

	public static void main(String[] args) {
		String msg = "大家好 :) ，<script>，敏感，被就业...";

		MsgProcessor mp = new MsgProcessor();
		mp.setMsg(msg);

		// 先写测试  极限编程
		FilterChain fc = new FilterChain();
		// 链条式编程
		fc.addFilter(new HTMLFilter())
			.addFilter(new FaceFilter());

		FilterChain fc2 = new FilterChain();
		fc2.addFilter(new SensitiveFilter());
		// 5级别实现： 过滤器的相互叠加 因为FilterChain的implements Filter
		fc.addFilter(fc2);

		mp.setFc(fc);


		String result = mp.process();
		System.out.println(result);

		// 5级别实现：双向过滤器的设计和实现 ，strust java web中都是这样的实现过程，即实现客户端到服务器的过滤，也实现服务器到客户端的过滤
	}

}
