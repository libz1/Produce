package FilterWeb;

public class WEBMain {

	public static void main(String[] args) {
		String msg = "大家好 :) ，<script>，敏感，被就业...";

		Request request = new Request();
		request.setRequstString(msg);
		Response response = new Response();
		response.setResponseString(msg);

		WEBMsgProcessor mp = new WEBMsgProcessor();
		mp.setRequset(request);
		mp.setResponse(response);

		// 先写测试  极限编程
		WEBFilterChain fc = new WEBFilterChain();
		// 链条式编程 正序处理过程
		fc.addFilter(new WEBHTMLFilter())
			.addFilter(new WEBFaceFilter())
			.addFilter(new WEBSensitiveFilter())
			;

		// response的过滤器处理过程应该正好与request相反  倒序处理过程
		mp.setFc(fc);

		mp.process();
		System.out.println(request.getRequstString());

		System.out.println(response.getResponseString());

		// 5级别实现：双向过滤器的设计和实现 ，strust java web中都是这样的实现过程，即实现客户端到服务器的过滤，也实现服务器到客户端的过滤
		// 递归调用实现过程
//		WEBFilter f = filters.get(index);
//		index ++;
//		f.doFilter(request, response, webfc);
//		request.setRequstString(request.getRequstString().replaceAll("<", "[").replaceAll(">", "]")+"---htmlFilter");
//		webfc.doFilter(request, response, webfc);
//		response.setResponseString(response.getResponseString().replaceAll("<", "[").replaceAll(">", "]")+"---htmlFilter");


		// 6级别实现  filter遇到不满足条件的情况，就退出，不再执行下一级过滤器
		// 从技术角度讲，拦截器与过滤器是一样的
//		String str = request.getRequstString();
//		if (str.indexOf(":\\)") < 0){
//			return;
//		}

	}

}
