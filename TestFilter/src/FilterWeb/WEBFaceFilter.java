package FilterWeb;

public class WEBFaceFilter implements WEBFilterInterface {

	@Override
	public void doFilter(Request request, Response response, WEBFilterChain webfc) {
		String str = request.getRequstString();
		if (str.indexOf(":\\)") < 0){
			return;
		}
		request.setRequstString(str.replaceAll(":\\)", "^-^")+"---faceFilter");
		webfc.doFilter(request, response, webfc);
		response.setResponseString(response.getResponseString().replaceAll(":\\)", "^-^")+"---faceFilter");

	}

}
