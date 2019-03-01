package FilterWeb;

public class WEBHTMLFilter implements WEBFilterInterface {

	@Override
	public void doFilter(Request request, Response response, WEBFilterChain webfc) {
		request.setRequstString(request.getRequstString().replaceAll("<", "[").replaceAll(">", "]")+"---htmlFilter");
		webfc.doFilter(request, response, webfc);
		response.setResponseString(response.getResponseString().replaceAll("<", "[").replaceAll(">", "]")+"---htmlFilter");
	}

}
