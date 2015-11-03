package com.leonoss.alipay.apppay;

import com.leonoss.alipay.apppay.dto.AliAppPayNotification;
import com.leonoss.alipay.apppay.dto.AliAppPayRequest;
import com.leonoss.alipay.apppay.exception.AliPayServiceException;

public interface AlipayMobilePayService {

	public String marshalMobilePayRequest(AliAppPayRequest appPayRequest);

	public AliAppPayNotification unmarshal(String notification) throws AliPayServiceException;

}
