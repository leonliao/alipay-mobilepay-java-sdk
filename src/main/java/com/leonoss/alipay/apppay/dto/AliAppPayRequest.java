package com.leonoss.alipay.apppay.dto;

import java.math.BigDecimal;

/**
 * 参照http://doc.open.alipay.com/doc2/detail?spm=0.0.0.0.T7RAod&treeId=59&
 * articleId=103663&docType=1
 * 
 * @author leon
 *
 */
public class AliAppPayRequest {
	// 接口名称 接口名称，固定值。
	@Required
	@SignField
	private final String service = "mobile.securitypay.pay";
	// 合作者身份ID 签约的支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
	@Required
	@SignField
	@MaxLength(16)
	private String partner;
	// 参数编码字符集 商户网站使用的编码格式，固定为utf-8。
	@Required
	@SignField
	private final String _input_charset = "utf-8";
	// 服务器异步通知页面路径 支付宝服务器主动通知商户网站里指定的页面http路径。
	@Required
	@SignField
	@MaxLength(200)
	private String notify_url;
	// 客户端号 标识客户端。
	@SignField
	private String app_id;
	// 客户端来源 标识客户端来源。参数值内容约定如下：appenv=”system=客户端平台名^version=业务系统版本”
	@SignField
	private String appenv;
	// 商户网站唯一订单号 支付宝合作商户网站唯一订单号。
	@Required
	@SignField
	@MaxLength(64)
	private String out_trade_no;
	// 商品名称 商品的标题/交易标题/订单标题/订单关键字等。该参数最长为128个汉字。
	@Required
	@SignField
	@MaxLength(128)
	private String subject;
	// 支付类型 支付类型。默认值为：1（商品购买）。
	@Required
	@SignField
	@MaxLength(4)
	private final String payment_type = "1";
	// 卖家支付宝账号 卖家支付宝账号（邮箱或手机号码格式）或其对应的支付宝唯一用户号（以2088开头的纯16位数字）。
	@Required
	@SignField
	@MaxLength(16)
	private String seller_id;
	// 总金额 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
	@Required
	@SignField
	private BigDecimal total_fee;
	// 商品详情 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
	@Required
	@SignField
	@MaxLength(512)
	private String body;
	// 是否发起实名校验 T：发起实名校验；
	// 不发起实名校验。
	@SignField
	@MaxLength(1)
	private String rn_check;
	// 未付款交易的超时时间
	// 设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭。当用户输入支付密码、点击确认付款后（即创建支付宝交易后）开始计时。取值范围：1m～15d，或者使用绝对时间（示例格式：2014-06-13
	// @16:00:00）。m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。该参数数值不接受小数点，如1.5h，可转换为90m。
	@SignField
	private String it_b_pay;
	// 授权令牌
	// 开放平台返回的包含账户信息的token（授权令牌，商户在一定时间内对支付宝某些服务的访问权限）。通过授权登录后获取的alipay_open_id，作为该参数的value，登录授权账户即会为支付账户。
	@SignField
	@MaxLength(32)
	private String extern_token;
	// 商户业务扩展参数 业务扩展参数，支付宝特定的业务需要添加该字段，json格式。
	// 商户接入时和支付宝协商确定。
	@SignField
	@MaxLength(128)
	private String out_context;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getAppenv() {
		return appenv;
	}

	public void setAppenv(String appenv) {
		this.appenv = appenv;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public BigDecimal getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getRn_check() {
		return rn_check;
	}

	public void setRn_check(String rn_check) {
		this.rn_check = rn_check;
	}

	public String getIt_b_pay() {
		return it_b_pay;
	}

	public void setIt_b_pay(String it_b_pay) {
		this.it_b_pay = it_b_pay;
	}

	public String getExtern_token() {
		return extern_token;
	}

	public void setExtern_token(String extern_token) {
		this.extern_token = extern_token;
	}

	public String getOut_context() {
		return out_context;
	}

	public void setOut_context(String out_context) {
		this.out_context = out_context;
	}

	public String getService() {
		return service;
	}

	public String get_input_charset() {
		return _input_charset;
	}

	public String getPayment_type() {
		return payment_type;
	}

	@Override
	public String toString() {
		return "AliAppPayRequest [service=" + service + ", partner=" + partner
				+ ", _input_charset=" + _input_charset + ", notify_url="
				+ notify_url + ", app_id=" + app_id + ", appenv=" + appenv
				+ ", out_trade_no=" + out_trade_no + ", subject=" + subject
				+ ", payment_type=" + payment_type + ", seller_id=" + seller_id
				+ ", total_fee=" + total_fee + ", body=" + body + ", rn_check="
				+ rn_check + ", it_b_pay=" + it_b_pay + ", extern_token="
				+ extern_token + ", out_context=" + out_context + "]";
	}
}
