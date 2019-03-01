package FilterWeb;
import java.util.ArrayList;
import java.util.List;

// implements Filter 是为了更高级的实现
// 两个FilterChain可以通过addFilter的方法实现叠加
public class WEBFilterChain implements WEBFilterInterface{
	List<WEBFilterInterface> filters = new ArrayList<WEBFilterInterface>();

	int index = 0;
//	// 普通的编程
//	public void addFilter(Filter f){
//		filters.add(f);
//	}
	// 链条式编程  返回的是自己
	public WEBFilterChain addFilter(WEBFilterInterface f){
		filters.add(f);
		return this;
	}


//	@Override
//	public void doFilter(Request request, Response response) {
//		for( WEBFilter f: filters)
//			f.doFilter(request, response,this);
//
//	}


	@Override
	public void doFilter(Request request, Response response, WEBFilterChain webfc) {
		if (index == filters.size())
			return;
		WEBFilterInterface f = filters.get(index);
		index ++;

		// 递归调用实现过程
		f.doFilter(request, response, webfc);
//		for( WEBFilter f: filters)
//			f.doFilter(request, response,webfc);

	}
}
