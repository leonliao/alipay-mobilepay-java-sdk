package com.leonoss.alipay.apppay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Introduced for avoiding catching UnsupportedEncodingException
 * @author leon
 *
 */
public abstract class EncodingUtil {

	public static byte[] utf8Bytes(String input) {
		try {
			return input.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// must not happen
			throw new IllegalStateException(
					"Why UTF-8 is not supported in this system!", e);
		}
	}
	
	public static String utf8UrlEncode(String input) {
		try {
			return URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// must not happen
			throw new IllegalStateException(
					"Why UTF-8 is not supported in this system!", e);
		}
	}
	
	
	public static String utf8UrlDecode(String input) {
		try {
			return URLDecoder.decode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// must not happen
			throw new IllegalStateException(
					"Why UTF-8 is not supported in this system!", e);
		}
	}

}
