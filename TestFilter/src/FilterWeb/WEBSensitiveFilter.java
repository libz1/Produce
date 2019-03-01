package FilterWeb;

public class WEBSensitiveFilter implements WEBFilterInterface {

	@Override
	public void doFilter(Request request, Response response, WEBFilterChain webfc) {
		// 先执行当前过滤器
		request.setRequstString(request.getRequstString().replaceAll("被就业", "jiuye").replaceAll("敏感", "")+"---senstiveFilter");
		// 调用下一级过滤器
		webfc.doFilter(request, response, webfc);
		// 等于是全部的倒序了，这里是最后执行的
		// 非常巧妙的实现了整个过程
		response.setResponseString(response.getResponseString().replaceAll("被就业", "jiuye").replaceAll("敏感", "")+"---senstiveFilter");
	}

}
