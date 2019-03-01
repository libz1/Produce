package serial;

public class DataConvert {
	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			ret += hex;
		}
		ret = ret.toUpperCase();
		return ret;
	}
	public static int hexString2Int(String data) {
		int ret = 0;
		ret = Integer.valueOf(data, 16);
		return ret;
	}
	public static byte[] hexString2ByteArray(String param) {
		param = param.replaceAll(" ", "");
		byte[] result = new byte[param.length() / 2];
		for (int i = 0, j = 0; j < param.length(); i++) {
			result[i] = (byte) Integer.parseInt(param.substring(j, j + 2), 16);
			j += 2;
		}
		return result;
	}

}
