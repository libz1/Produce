package FilterNormal;

public class SensitiveFilter implements Filter {

	@Override
	public String doFilter(String str) {
		return str.replaceAll("����ҵ", "jiuye")
				.replaceAll("����", "");
	}

}
