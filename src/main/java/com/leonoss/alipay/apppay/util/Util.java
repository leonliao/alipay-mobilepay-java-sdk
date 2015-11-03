package com.leonoss.alipay.apppay.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leonoss.alipay.apppay.dto.MaxLength;
import com.leonoss.alipay.apppay.dto.Required;
import com.leonoss.alipay.apppay.dto.SignField;

public abstract class Util {
	private static Logger logger = LoggerFactory.getLogger(Util.class);
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALIPAY_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static byte[] hexStringToBytes(String hexStr) {
		if (hexStr == null || "".equals(hexStr)) {
			return null;
		}
		int byteCount = hexStr.length() / 2;
		byte[] arrayOfByte = new byte[byteCount];
		for (int i = 0; i < byteCount; i++) {
			arrayOfByte[i] = (byte) Integer.parseInt(
					hexStr.substring(i * 2, i * 2 + 2), 16);
		}
		return arrayOfByte;
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static byte[] md5(byte[] input) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(input);
			return messageDigest.digest();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void validateField(Object valueObject, Field field) {
		Object value = getFieldValue(valueObject, field);

		// Check whether required field is missing value or with empty
		// String
		if (field.isAnnotationPresent(Required.class)) {
			if (value == null) {
				throw new IllegalArgumentException("Field [" + field.getName()
						+ "] is required.");
			}

			if (field.getType().equals(String.class)) {
				String strValue = (String) value;
				if (Util.isEmpty(strValue)) {
					throw new IllegalArgumentException("Field ["
							+ field.getName() + "] is required.");
				}
			}
		}

		// Check whether String fields exceeds the max length
		if (field.getType().equals(String.class)
				&& field.isAnnotationPresent(MaxLength.class)) {
			int annotatedMaxLength = field.getAnnotation(MaxLength.class)
					.value();
			String strValue = (String) value;
			if (!Util.isEmpty(strValue)
					&& strValue.length() > annotatedMaxLength) {
				throw new IllegalArgumentException("Field [" + field.getName()
						+ "]'s length " + strValue.length()
						+ " exceeds the max length " + annotatedMaxLength);
			}
		}
	}

	/**
	 * Validate the value object according to the annotation
	 * 
	 * @param valueObject
	 * @throws IllegalArgumentException
	 *             if required field is missing or String field exceeds the
	 *             length
	 */
	public static void validateValueObject(Object valueObject) {
		Field[] fields = valueObject.getClass().getDeclaredFields();
		for (Field field : fields) {
			validateField(valueObject, field);
		}
	}

	public static Object getFieldValue(Object valueObject, Field field) {
		try {
			field.setAccessible(true);
			return field.get(valueObject);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException("Should not happen.", e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Should not happen.", e);
		}
	}

	/**
	 * 根据支付宝的签名要求生成sign
	 * 
	 * @param valueObject
	 * @param key
	 * @return
	 */
	public static String generateSignatureAndMarshalToString(
			Object valueObject, String privateKey) {
		if (isEmpty(privateKey)) {
			throw new IllegalStateException(
					"Key is required for computing the signature.");
		}

		// Get all fields annotated with SignField
		Map<String, Object> nameValuesMap = new HashMap<String, Object>();

		Field[] allFields = valueObject.getClass().getDeclaredFields();
		for (Field field : allFields) {
			// Validate whether the field is missing value if required and etc.
			validateField(valueObject, field);
			Object value = Util.getFieldValue(valueObject, field);
			if (field.isAnnotationPresent(SignField.class)) {
				if (field.getType().equals(Map.class)) {
					// Annotated SignField Map must be Map<String, Object>
					@SuppressWarnings("unchecked")
					Map<String, Object> valueMap = (Map<String, Object>) value;
					for (Entry<String, Object> entry : valueMap.entrySet()) {
						nameValuesMap.put(entry.getKey(), entry.getValue());
					}
				} else {
					String signName = field.getAnnotation(SignField.class)
							.signName();
					if (isEmpty(signName)) {
						nameValuesMap.put(field.getName(), value);
					} else {
						nameValuesMap.put(signName, value);
					}
				}
			}
		}

		List<Entry<String, Object>> listOfKeyValuesToBeSigned = new ArrayList<Entry<String, Object>>(
				nameValuesMap.entrySet());
		// Sort the fields
		Collections.sort(listOfKeyValuesToBeSigned,
				new Comparator<Entry<String, Object>>() {
					@Override
					public int compare(Entry<String, Object> o1,
							Entry<String, Object> o2) {
						return o1.getKey().compareToIgnoreCase(o2.getKey());
					}
				});

		// Generate the signed string
		String sperator = "";
		String signInput = "";
		for (int ii = 0; ii < listOfKeyValuesToBeSigned.size(); ii++) {
			if (ii != 0)
				sperator = "&";
			Entry<String, Object> keyValue = listOfKeyValuesToBeSigned.get(ii);
			Object value = keyValue.getValue();
			// 跳过null或者空字符串
			if (value == null) {
				continue;
			}
			if (value != null && (value instanceof String)) {
				String strValue = (String) value;
				if (Util.isEmpty(strValue)) {
					continue;
				}
			}

			if (value != null) {
				signInput += sperator + keyValue.getKey() + "="
						+ EncodingUtil.utf8UrlEncode(value.toString());
			}
		}

		logger.debug("The input to sign: " + signInput);
		byte[] privateKeyInBytes = Base64.getDecoder().decode(
				EncodingUtil.utf8Bytes(privateKey));
		byte[] signedBytes;
		try {
			signedBytes = RsaSignUtil.signWithPrivateKey(privateKeyInBytes,
					EncodingUtil.utf8Bytes(signInput));
			logger.debug("Signature in bytes of alipay:{}",
					BytesUtil.toHexString(signedBytes));
		} catch (GeneralSecurityException e) {
			logger.warn("Got exception when trying to sign in alipay's way: "
					+ signInput, e);
			throw new IllegalArgumentException("The object can not be signed.",
					e);
		}

		String alipaySign = EncodingUtil.utf8UrlEncode(Base64.getEncoder()
				.encodeToString(signedBytes));
		logger.debug("Signature of alipay:{}", alipaySign);
		String marshalled = signInput + "&sign_type=RSA&sign=" + alipaySign;
		logger.debug("Marshalled string:{}", marshalled);
		return marshalled;
	}

	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int index = ThreadLocalRandom.current().nextInt(allChar.length());
			sb.append(allChar.charAt(index));
		}
		return sb.toString();
	}

	public static void mapKeyValuesToObject(Map<String, String> keyValues,
			Object object) {

		// Get all fields annotated with SignField
		Map<String, Field> signNameValuesMap = new HashMap<String, Field>();

		Field[] allFields = object.getClass().getDeclaredFields();
		for (Field field : allFields) {
			if (field.isAnnotationPresent(SignField.class)) {
				String signName = field.getAnnotation(SignField.class)
						.signName();
				if (isEmpty(signName)) {
					signNameValuesMap.put(field.getName(), field);
				} else {
					signNameValuesMap.put(signName, field);
				}
			}
		}
		for (Entry<String, String> entry : keyValues.entrySet()) {
			Field field = signNameValuesMap.get(entry.getKey());
			try {
				if (field != null) {
					field.setAccessible(true);
					if (field.getType().equals(String.class)) {
						field.set(object, entry.getValue());
					} else if (field.getType().equals(BigDecimal.class)) {
						field.set(object, new BigDecimal(entry.getValue()));
					} else if (field.getType().equals(Boolean.class)) {
						field.set(object, Boolean.valueOf(entry.getValue()));
					} else if (field.getType().equals(Date.class)) {
						SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
								ALIPAY_DATE_FORMAT);
						dateTimeFormatter.setTimeZone(TimeZone
								.getTimeZone("GMT+0"));
						try {
							field.set(object,
									dateTimeFormatter.parse(entry.getValue()));
						} catch (ParseException e) {
							throw new IllegalArgumentException("Wrong input "
									+ entry.getValue() + ", expected "
									+ ALIPAY_DATE_FORMAT, e);
						}
					}
					logger.debug("Setting value {} to field {}",
							entry.getValue(), entry.getKey());
				}
			} catch (IllegalAccessException e) {
				throw new IllegalStateException("Should not happen.", e);
			}
		}

	}
}
