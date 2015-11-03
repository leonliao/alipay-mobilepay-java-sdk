package com.leonoss.alipay.apppay;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leonoss.alipay.apppay.dto.AliAppPayNotification;
import com.leonoss.alipay.apppay.dto.AliAppPayRequest;
import com.leonoss.alipay.apppay.exception.AliPayServiceException;
import com.leonoss.alipay.apppay.exception.InvalidSignatureException;
import com.leonoss.alipay.apppay.util.EncodingUtil;
import com.leonoss.alipay.apppay.util.RsaSignUtil;
import com.leonoss.alipay.apppay.util.Util;

public class AlipayMobilePayServiceImpl implements AlipayMobilePayService {
	private AliAppPayConf conf;
	private static Logger logger = LoggerFactory
			.getLogger(AlipayMobilePayServiceImpl.class);

	public AlipayMobilePayServiceImpl(AliAppPayConf aliAppPayConf) {
		this.conf = aliAppPayConf;
		validateConf();
	}

	private void validateConf() {
		if (conf == null || !conf.isReady()) {
			throw new IllegalArgumentException(
					"The configuration is null, or not ready(some parameters are missing)."
							+ conf);
		}
	}

	@Override
	public String marshalMobilePayRequest(AliAppPayRequest appPayRequest) {
		return Util.generateSignatureAndMarshalToString(appPayRequest,
				conf.getPrivateKey());
	}

	@Override
	public AliAppPayNotification unmarshal(String notificationStr)
			throws AliPayServiceException {
		logger.debug("Received notification string "+ notificationStr);
		String toBeSigned = "";
		String sign = "";
		String signType = "";
		String[] splitted = notificationStr.split("&");
		// Required by Aliapy to sort
		// 去掉 sign 和 sign_type 两个参数,将其他参数按照字母顺序升序排列,再把所有 数组值以“&”字符连接起来:
		Arrays.sort(splitted);

		AliAppPayNotification notification = new AliAppPayNotification();
		Map<String, String> keyValues = new HashMap<String, String>();
		for (String oneEquation : splitted) {
			String[] keyValue = oneEquation.split("=");
			logger.debug("Key values when extracting: "
					+ Arrays.toString(keyValue));

			if (oneEquation.startsWith("sign=")) {
				if (keyValue.length > 1) {
					sign = EncodingUtil.utf8UrlDecode(keyValue[1]);
					keyValues.put("sign", signType);
				}
			} else if (oneEquation.startsWith("sign_type=")) {
				if (keyValue.length > 1) {
					signType = EncodingUtil.utf8UrlDecode(keyValue[1]);
					if (!signType.equals("RSA")) {
						throw new IllegalArgumentException(
								"Only RSA sign type is supported!");
					}

					keyValues.put("sign_type", signType);
				}
			} else {
				String key = keyValue[0];
				String value = EncodingUtil.utf8UrlDecode(keyValue[1]);
				keyValues.put(key, value);

				toBeSigned = toBeSigned + key;
				if (keyValue.length > 1) {
					toBeSigned = toBeSigned + "=" + value;
				}
				toBeSigned += "&";
			}
		}
		// cut last &
		toBeSigned = toBeSigned.substring(0, toBeSigned.length() - 1);
		logger.debug("After extract the key values and reconcatenating : {}",
				toBeSigned);
		logger.debug("Key value map after unmarshalling {}", keyValues);

		try {
			if (!RsaSignUtil.isSignatureValid(toBeSigned.getBytes("UTF-8"),
					Base64.getDecoder().decode(sign), Base64.getDecoder()
							.decode(conf.getAliPublicKey()))) {
				throw new InvalidSignatureException("Signature is not valid!");
			}
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			logger.error("RSA signature validation failed:" + e.getMessage());
			throw new InvalidSignatureException(e);
		}
		logger.debug("Before mapping object {}", notification);
		Util.mapKeyValuesToObject(keyValues, notification);
		logger.debug("After mapping object {}", notification);
		notification.setSign(sign);
		notification.setSign_type(signType);

		logger.debug("To be signed {}, sign{}, sign_type {}", toBeSigned, sign,
				signType);

		return notification;
	}

}
