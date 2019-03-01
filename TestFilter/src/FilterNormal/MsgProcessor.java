package FilterNormal;

public class MsgProcessor {
	private String msg;

	// 3级别实现：（过滤器链！  处理链    【设计模式之责任链】 ）
	// 可以动态的配置过滤器数组的内容、执行的先后次序
	Filter[] filters = {new HTMLFilter(), new SensitiveFilter(), new FaceFilter()};
	// 上述列表信息可以写在配置文件中，在外部变更即可

	// 4级别实现：复用已有的链条，将已有的链条添加到新链条的中间部分
	FilterChain fc = new FilterChain();

	public FilterChain getFc() {
		return fc;
	}

	public void setFc(FilterChain fc) {
		this.fc = fc;
	}

	public String process() {
		// 4级别实现：
		String ret4 = fc.doFilter(msg);
		return ret4;

//		// 3级别实现：
//		String ret3 = msg;
//		for(Filter f: filters)
//			ret3 = f.doFilter(ret3);
//		return ret3;

//		// 2级别实现：
//		String ret2 = new HTMLFilter().doFilter(msg);
//		ret2 = new SensitiveFilter().doFilter(ret2);
//		return ret2;
//
//
//		// 1级别实现：以下的内容在逻辑上是会不断修改的。准备使用比较容易的处理方法
//		// html
//		String ret1 = msg.replaceAll("<", "[")
//					.replaceAll(">", "]");
//		// sensitive
//		ret1 = ret1.replaceAll("被就业", "jiuye")
//				.replaceAll("敏感", "");
//		return ret1;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
