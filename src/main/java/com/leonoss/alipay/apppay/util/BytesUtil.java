package com.leonoss.alipay.apppay.util;

public abstract class BytesUtil {

	public static String toHexString(byte[] input) {
		if (input == null) {
			return "";
		}

		StringBuilder hexString = new StringBuilder();
		for (byte b : input) {
			if ((b & 0xff) < 0x10)// 0~F前面不零
				hexString.append("0");
			hexString.append(Integer.toHexString(0xFF & b));
		}

		return hexString.toString();
	}

	public static byte[] fromHexString(String input) {
		if (input == null || input.trim().length() == 0
				|| input.trim().length() % 2 != 0) {
			throw new IllegalArgumentException("Input " + input
					+ " is not valid. Must be not empty and length%2=0");
		}

		byte[] result = new byte[input.length() / 2];
		for (int i = 0; i < input.length(); i += 2) {
			int intValue = Integer.valueOf(input.substring(i, i + 2), 16);
			result[i / 2] = (byte)(0x000000ff & intValue);
		}
		return result;
	}
}
