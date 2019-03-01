package FilterNormal;

public class HTMLFilter implements Filter {

	@Override
	public String doFilter(String str) {
		return str.replaceAll("<", "[")
				.replaceAll(">", "]");
	}

}
