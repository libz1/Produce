package FilterWeb;

public interface WEBFilterInterface {
	// 对字符串进行处理
	void doFilter(Request request,Response response , WEBFilterChain webfc);
}
