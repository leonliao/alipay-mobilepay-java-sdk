package com.leonoss.alipay.apppay.test;

import java.util.Base64;

import org.junit.Before;
import org.junit.Test;

import com.leonoss.alipay.apppay.AliAppPayConf;
import com.leonoss.alipay.apppay.AlipayMobilePayServiceImpl;
import com.leonoss.alipay.apppay.dto.AliAppPayNotification;
import com.leonoss.alipay.apppay.util.EncodingUtil;
import com.leonoss.alipay.apppay.util.RsaSignUtil;



public class TestMobilePayNotificaiton {
	AlipayMobilePayServiceImpl service;
	AliAppPayConf conf;
	/**private key: MIICXAIBAAKBgQC2FrK61OcMRJDuLlQe4cUJ/mM1zoJo6aa0aO0LzKsGsEddtJGOvK/h3+4rpQrgnlvOLJkSgvKb/Za+d39Gf6pB1bsHL6MbeaNRJeu6vn8vlh9pD+vG7vFBTWyn72Zbqv40OnH6W5M/tYfsOs0njwni1LwN8Tc2eQ79pGSnX4ggmwIDAQABAoGARkY6UmrKhOyNdq8Ani6KChJYnbKGmBDc2rHIEo65zsoHsP14U7s0shrnY4a9zPSw/8Asj31kPpm/LOEXiNDKlxkZNdJGUlQ2SP2LQlrtvttlUsalQ+PqGwM5ZaUGcthMvqSzsEQFOTsQMbUcWoV7En9lQ0Q8HfbsyT9Hbx050wkCQQDiqC5jEf0tdpz9lxMKStYZDiENb3X9r3gF26R6jJ02ccInf6em5Q6s8y1hqs1BgWFF3S0j7q2aVAxYxEhUacx9AkEAzalxk408WkcRYI1qs1KqTmqPapVkO2A40uxuXUG2SjR2BuYWLtH9Abl9CTCbfHhc2m3bK1uPkelY6tvzq09k9wJBALjwBTls2imYWKxJ8o05VswGzU1LHvkpjUMqXmBoD3b3BzZ3YHI6/+h6WxMP7H6AdMAuocBp5VjBL3nWXjYSRXkCQA9m3SabsSVhpR+B97euPSraOBtXTMZVkmvZYPqC7U/pVd12Zbrj5vq2FXMWvM45hVqmV3+Lv9jBjPMpvbnanEkCQFQfVx+qzVwPnBKv9U6W9lzFir0kriRljuXaspI+glVp3DhjayuICCfsZBzV1aCJ/kfh21mFWD1VwC6f2+vSn4o=
	public key: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2FrK61OcMRJDuLlQe4cUJ/mM1zoJo6aa0aO0LzKsGsEddtJGOvK/h3+4rpQrgnlvOLJkSgvKb/Za+d39Gf6pB1bsHL6MbeaNRJeu6vn8vlh9pD+vG7vFBTWyn72Zbqv40OnH6W5M/tYfsOs0njwni1LwN8Tc2eQ79pGSnX4ggmwIDAQAB
	refer to http://doc.open.alipay.com/doc2/detail?spm=0.0.0.0.diJyDG&treeId=58&articleId=103242&docType=1 for generating the keys
	And key files are in src/test/resources/*.pem
	 *
	 */
	@Before
	public void setUp() throws Exception {
		conf = new AliAppPayConf(
				"partnerid",
				"sellerId",
				"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALYWsrrU5wxEkO4uVB7hxQn+YzXOgmjpprRo7QvMqwawR120kY68r+Hf7iulCuCeW84smRKC8pv9lr53f0Z/qkHVuwcvoxt5o1El67q+fy+WH2kP68bu8UFNbKfvZluq/jQ6cfpbkz+1h+w6zSePCeLUvA3xNzZ5Dv2kZKdfiCCbAgMBAAECgYBGRjpSasqE7I12rwCeLooKElidsoaYENzascgSjrnOygew/XhTuzSyGudjhr3M9LD/wCyPfWQ+mb8s4ReI0MqXGRk10kZSVDZI/YtCWu2+22VSxqVD4+obAzllpQZy2Ey+pLOwRAU5OxAxtRxahXsSf2VDRDwd9uzJP0dvHTnTCQJBAOKoLmMR/S12nP2XEwpK1hkOIQ1vdf2veAXbpHqMnTZxwid/p6blDqzzLWGqzUGBYUXdLSPurZpUDFjESFRpzH0CQQDNqXGTjTxaRxFgjWqzUqpOao9qlWQ7YDjS7G5dQbZKNHYG5hYu0f0BuX0JMJt8eFzabdsrW4+R6Vjq2/OrT2T3AkEAuPAFOWzaKZhYrEnyjTlWzAbNTUse+SmNQypeYGgPdvcHNndgcjr/6HpbEw/sfoB0wC6hwGnlWMEvedZeNhJFeQJAD2bdJpuxJWGlH4H3t649Kto4G1dMxlWSa9lg+oLtT+lV3XZluuPm+rYVcxa8zjmFWqZXf4u/2MGM8ym9udqcSQJAVB9XH6rNXA+cEq/1Tpb2XMWKvSSuJGWO5dqykj6CVWncOGNrK4gIJ+xkHNXVoIn+R+HbWYVYPVXALp/b69Kfig==",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2FrK61OcMRJDuLlQe4cUJ/mM1zoJo6aa0aO0LzKsGsEddtJGOvK/h3+4rpQrgnlvOLJkSgvKb/Za+d39Gf6pB1bsHL6MbeaNRJeu6vn8vlh9pD+vG7vFBTWyn72Zbqv40OnH6W5M/tYfsOs0njwni1LwN8Tc2eQ79pGSnX4ggmwIDAQAB");
		service = new AlipayMobilePayServiceImpl(conf);
	}

	@Test
	public void test() throws Exception {
		String notificationStr = "body=%E8%BF%99%E9%87%8C%E6%98%AFbody%E5%91%80&buyer_email=test%40test.com&buyer_id=2088222209035185&discount=0.00&gmt_create=2015-07-30+15%3A06%3A02&gmt_payment=2015-07-30+15%3A06%3A02&is_total_fee_adjust=N&notify_id=336d5e98f9d6a59be4b295860e6f881f6i&notify_time=2015-07-30+15%3A06%3A02&notify_type=trade_status_sync&out_trade_no=9945037585983&payment_type=1&price=0.01&quantity=1&seller_email=seller%40seller.com&seller_id=2088922006052301&subject=%E8%BF%99%E9%87%8C%E6%98%AFsubject&total_fee=0.01&trade_no=2015073000001111820060941930&trade_status=TRADE_SUCCESS&use_coupon=N";
		String notificationStrDecoded = EncodingUtil
				.utf8UrlDecode(notificationStr);
		System.out.println("URL Decoded "+ notificationStrDecoded );

		notificationStr += "&sign_type=RSA";
		notificationStr = notificationStr
				+ "&sign="
				+ EncodingUtil.utf8UrlEncode(Base64.getEncoder()
						.encodeToString(
								RsaSignUtil.signWithPrivateKey(
										Base64.getDecoder().decode(
												conf.getPrivateKey()),
										notificationStrDecoded
												.getBytes("UTF-8"))));
		AliAppPayNotification notification = service.unmarshal(notificationStr);
		System.out.println(notification);
	}

}
