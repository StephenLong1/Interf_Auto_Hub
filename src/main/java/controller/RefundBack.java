package controller;

import common.AutoLogger;
import org.testng.annotations.Test;
import source.HttpClientKw;
import source.SSHService;

import static org.testng.Assert.assertTrue;

public class RefundBack {

	/**
	 * 用SSHService类创建一个叫做SSHService的名叫ssh的对象
	 * 用HttpClientKw类创建一个叫做HttpClientKw的名叫kw的对象
	 */
	SSHService ssh = new SSHService();
	HttpClientKw kw = new HttpClientKw();

	/**
	 * 从config.properties文件中获取到ownerCode、dataBase、shopCode
	 */
	String brandName = SSHService.ownerCode();
	String shopCode = SSHService.shopCode();
	String order_sn = SSHService.order_sn();
	String refund_sn = SSHService.refund_sn();
	String deal_code = SSHService.deal_code();
	String oid = SSHService.oid();
	String TesterName = SSHService.TesterName();
	String RefundFee = SSHService.refund_fee();
	String outBizCode = order_sn + "-1195985";
    String refundStatus = SSHService.refundStatus();

	String body;

    @Test(description = "确认退款回传接口", priority = 0, enabled = true)
    public void refund_To_Confirm() {
        try {
            String body = "{\"RefundSn\":\"" + refund_sn + "\",\"DealCode\":\"" + deal_code + "\",\"ShopCode\":\"" + shopCode + "\",\"Status\":\""+refundStatus+"\",\"Oid\":\"" + oid + "\",\"RefundFee\":\"" + RefundFee + "\",\"Operator\":\""+TesterName+"\",\"RefundTime\":\"" + SSHService.NowTime + "\"}";
            AutoLogger.log.info("该接口请求体： "+body);
            String url = HttpClientKw.urlJoint("http://39.98.231.61:30003/api/fromduijie?", body, "refund.confirm", "json", "2.0", brandName, "21534044");
            HttpClientKw.doPostJson(url, body, "退款确认");
            boolean r = kw.assertExpectContains("success", "$.flag", "退款确认");
            assertTrue(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}