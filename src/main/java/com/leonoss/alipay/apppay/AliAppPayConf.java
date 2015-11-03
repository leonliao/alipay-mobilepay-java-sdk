package com.leonoss.alipay.apppay;

import static com.leonoss.alipay.apppay.util.Util.isEmpty;

/**
 * 商户RSA密钥的生成请参考
 * http://doc.open.alipay.com/doc2/detail?treeId=58&articleId=103242&docType=1
 * 
 * @author leon
 *
 */
public class AliAppPayConf {
	private final String partnerId;
	private final String sellerId;
	private final String privateKey;
	private final String aliPublicKey;

	/**
	 * 
	 * @param partnerId
	 *            PID
	 * @param sellerId
	 *            卖家支付宝账号（邮箱或手机号码格式）或其对应的支付宝唯一用户号（以2088开头的纯16位数字）。<a href=
	 *            "http://doc.open.alipay.com/doc2/detail?spm=0.0.0.0.T7RAod&treeId=59&articleId=103663&docType=1"
	 *            >参见文档</a>
	 * @param privateKey
	 *            商户PEM格式私钥,去掉-----BEGIN RSA PRIVATE KEY-----和-----END RSA
	 *            PRIVATE KEY-----
	 * @param aliPublicKey
	 *            支付宝PEM格式公钥，去掉------BEGIN 和-----END两行
	 */
	public AliAppPayConf(String partnerId, String sellerId, String privateKey,
			String aliPublicKey) {
		super();
		this.partnerId = partnerId;
		this.sellerId = sellerId;
		this.privateKey = privateKey;
		this.aliPublicKey = aliPublicKey;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public String getAliPublicKey() {
		return aliPublicKey;
	}

	public boolean isReady() {
		return !isEmpty(sellerId) && !isEmpty(aliPublicKey)
				&& !isEmpty(privateKey) && !isEmpty(partnerId);
	}

	@Override
	public String toString() {
		return "AliAppPayConf [partnerId=" + partnerId + ", sellerId="
				+ sellerId + ", privateKey=" + privateKey + ", aliPublicKey="
				+ aliPublicKey + "]";
	}

}
