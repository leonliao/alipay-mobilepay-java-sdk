package com.leonoss.alipay.apppay.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 实现<a href="http://doc.open.alipay.com/doc2/detail?treeId=59&articleId=103666&docType=1">支付宝服务器异步通知官方文档</a>的资料说明。
 * @author leon
 *
 */
public class AliAppPayNotification {

	@SignField
	@Required
	private Date notify_time;
	@SignField
	@Required
	private String notify_type;
	@SignField
	@Required
	private String notify_id;
	@SignField
	@Required
	private String sign_type;
	@SignField
	@Required
	private String sign;
	@SignField
	private String out_trade_no;
	@SignField
	private String subject;
	@SignField
	private String payment_type;
	@SignField
	private String trade_no;
	@SignField
	private String trade_status;
	@SignField
	private String seller_id;
	@SignField
	private String seller_email;
	@SignField
	private String buyer_id;
	@SignField
	private String buyer_email;
	@SignField
	private BigDecimal total_fee;
	@SignField
	private BigDecimal quantity;
	@SignField
	private BigDecimal price;
	@SignField
	private String body;
	@SignField
	private Date gmt_create;
	@SignField
	private Date gmt_payment;
	@SignField
	@MaxLength(1)
	private String is_total_fee_adjust;
	@SignField
	@MaxLength(1)
	private String use_coupon;
	@SignField
	private String discount;
	@SignField
	private String refund_status;
	@SignField
	private Date gmt_refund;

	public Date getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(Date notify_time) {
		this.notify_time = notify_time;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public BigDecimal getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}

	public Date getGmt_payment() {
		return gmt_payment;
	}

	public void setGmt_payment(Date gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	public boolean isIs_total_fee_adjust() {
		return is_total_fee_adjust.equals("Y");
	}

	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}

	public boolean isUse_coupon() {
		return use_coupon.equals("Y");
	}

	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getRefund_status() {
		return refund_status;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}

	public Date getGmt_refund() {
		return gmt_refund;
	}

	public void setGmt_refund(Date gmt_refund) {
		this.gmt_refund = gmt_refund;
	}

	@Override
	public String toString() {
		return "AliAppPayNotification [notify_time=" + notify_time
				+ ", notify_type=" + notify_type + ", notify_id=" + notify_id
				+ ", sign_type=" + sign_type + ", sign=" + sign
				+ ", out_trade_no=" + out_trade_no + ", subject=" + subject
				+ ", payment_type=" + payment_type + ", trade_no=" + trade_no
				+ ", trade_status=" + trade_status + ", seller_id=" + seller_id
				+ ", seller_email=" + seller_email + ", buyer_id=" + buyer_id
				+ ", buyer_email=" + buyer_email + ", total_fee=" + total_fee
				+ ", quantity=" + quantity + ", price=" + price + ", body="
				+ body + ", gmt_create=" + gmt_create + ", gmt_payment="
				+ gmt_payment + ", is_total_fee_adjust=" + is_total_fee_adjust
				+ ", use_coupon=" + use_coupon + ", discount=" + discount
				+ ", refund_status=" + refund_status + ", gmt_refund="
				+ gmt_refund + "]";
	}
}
