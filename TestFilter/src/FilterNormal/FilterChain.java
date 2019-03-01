package FilterNormal;
import java.util.ArrayList;
import java.util.List;

// implements Filter ��Ϊ�˸��߼���ʵ��
// ����FilterChain����ͨ��addFilter�ķ���ʵ�ֵ���
public class FilterChain implements Filter{
	List<Filter> filters = new ArrayList<Filter>();
//	// ��ͨ�ı��
//	public void addFilter(Filter f){
//		filters.add(f);
//	}
	// ����ʽ���  ���ص����Լ�
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
