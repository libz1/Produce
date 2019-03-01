package FilterWeb;
import java.util.ArrayList;
import java.util.List;

// implements Filter ��Ϊ�˸��߼���ʵ��
// ����FilterChain����ͨ��addFilter�ķ���ʵ�ֵ���
public class WEBFilterChain implements WEBFilterInterface{
	List<WEBFilterInterface> filters = new ArrayList<WEBFilterInterface>();

	int index = 0;
//	// ��ͨ�ı��
//	public void addFilter(Filter f){
//		filters.add(f);
//	}
	// ����ʽ���  ���ص����Լ�
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

		// �ݹ����ʵ�ֹ���
		f.doFilter(request, response, webfc);
//		for( WEBFilter f: filters)
//			f.doFilter(request, response,webfc);

	}
}
