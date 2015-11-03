package com.leonoss.alipay.apppay.util;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public abstract class RsaSignUtil {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";

	public static byte[] signWithPrivateKey(byte[] privateKeyBytes, byte[] data)
			throws GeneralSecurityException {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(
				privateKeyBytes);
		KeyFactory keyFactory;
		keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
		return signature.sign();
	}
	
	
	
	public static boolean isSignatureValid(byte[] data, byte[] sign, byte[] publicKeyBytes)
			throws GeneralSecurityException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory
				.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
		Signature signature = java.security.Signature
				.getInstance("SHA1WithRSA");
		signature.initVerify(pubKey);
		signature.update(data);
		return signature.verify(sign);
	}
}
