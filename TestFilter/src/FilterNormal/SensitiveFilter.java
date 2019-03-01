package FilterNormal;

public class SensitiveFilter implements Filter {

	@Override
	public String doFilter(String str) {
		return str.replaceAll("被就业", "jiuye")
				.replaceAll("敏感", "");
	}

}
