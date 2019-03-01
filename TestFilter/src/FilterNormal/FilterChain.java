package FilterNormal;
import java.util.ArrayList;
import java.util.List;

// implements Filter 是为了更高级的实现
// 两个FilterChain可以通过addFilter的方法实现叠加
public class FilterChain implements Filter{
	List<Filter> filters = new ArrayList<Filter>();
//	// 普通的编程
//	public void addFilter(Filter f){
//		filters.add(f);
//	}
	// 链条式编程  返回的是自己
	public FilterChain addFilter(Filter f){
		filters.add(f);
		return this;
	}

	public String doFilter(String msg){
		String ret = msg;
		for(Filter f: filters)
			ret = f.doFilter(ret);
		return ret;
	}
}
