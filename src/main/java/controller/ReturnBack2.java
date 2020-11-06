package controller;

import common.AutoLogger;
import org.testng.annotations.Test;
import source.HttpClientKw;
import source.SSHService;

import java.sql.SQLException;

import static org.testng.Assert.assertTrue;

public class ReturnBack2 {

	/**
	 * 用SSHService类创建一个叫做SSHService的名叫ssh的对象
	 * 用HttpClientKw类创建一个叫做HttpClientKw的名叫kw的对象
	 */
	SSHService ssh = new SSHService();
	HttpClientKw kw = new HttpClientKw();

	/**
	 * 从config.properties文件中获取到ownerCode、dataBase、shopCode
	 */
	String ownerCode = SSHService.ownerCode();
	String deal_code = SSHService.deal_code();
	String order_sn = SSHService.order_sn();
	String return_sn = SSHService.return_sn();
	String TesterName = SSHService.TesterName();
	String goods_barcode1 = SSHService.goods_barcode1();
	String returnReason = SSHService.returnReason();
	String goods_number1 = SSHService.goods_number2();
	String orderType = SSHService.orderType();
    String outBizCode = order_sn + "-1195985";
    String oid = SSHService.oid2();
    String order_return_goods_id = SSHService.order_return_goods_id2();



	/**
	 * 退货入库回写oms
	 * returnOrder  退货单信息
	 * returnOrderCode   退货单号 = return_sn
	 * returnOrderId   外部订单编号 = outer_order_id
	 * warehouseCode  仓库编码 =
	 * orderLineNo   单据行号（盲收不起作用）
	 * outBizCode  外部业务编码(消息ID;用于去重;ISV对于同一请求;分配一个唯一性的编码。用来保证因为网络等原因导致重复传输;请求不会 被重复处理)
	 * orderType  (THRK=退货入库;HHRK=换货入库;只传英文编码)
	 * logisticsCode 物流公司编码 (SF=顺丰、EMS=标准快递、YTO=圆通 、ZTO=中通(ZTO)、HTKY=百世汇通、STO=申通、YUNDA=韵达、OTHER=其他)
	 * status  状态(卡地亚状态说明：0050=收到退货；0100=接受退货；0200=拒绝退货 、天梭状态说明：0-质检不通过；1-质检通过)
	 */
	@Test(description = "退货入库回写oms -> 自动匹配退货商品", priority = 5, enabled = false)
	public void return_To_Oms2 ()  {
		try {
			String body = "{\"returnOrder\":{\"returnOrderCode\":\"" + return_sn + "\",\"returnOrderId\":\"" + SSHService.outer_order_id + "\",\"warehouseCode\":\"BQJX01\",\"orderType\":\"THRK\",\"orderConfirmTime\":\"" + SSHService.nowTime + "\",\"logisticsCode\":\"SF\",\"expressCode\":\"12345678\",\"returnReason\":七天无理由,\"remark\":null,\"outBizCode\":\"" + outBizCode + "\",\"senderInfo\":{\"name\":\""+TesterName+"\",\"mobile\":\"13100000000\",\"province\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"city\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"area\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"detailAddress\":\"\\u4e0a\\u6d77\\u5e02\\u957f\\u5b81\\u533a\\u63a5\\u53e3\\u81ea\\u52a8\\u5316\"}},\"extendProps\":{\"ownerCode\":\"" + ownerCode + "\"},\"orderLines\":{\"orderLine\":[{\"orderLineNo\":"+order_return_goods_id+",\"sourceOrderCode\":\""+deal_code+"\",\"subSourceOrderCode\":\"" + oid + "\",\"linOwnerCode\":\"" + ownerCode + "\",\"itemCode\":\"" + goods_barcode1 + "\",\"inventoryType\":\"ZP\",\"planQty\":"+goods_number1+",\"actualQty\":\""+goods_number1+"\"},{\"extendProps\":{\"subOrderId\":\"\",\"status\":1,\"outerReturnId\":null},\"orderLineNo\":"+SSHService.order_return_goods_id()+",\"sourceOrderCode\":\"\",\"subSourceOrderCode\":\""+deal_code+"\",\"linOwnerCode\":\""+ownerCode+"\",\"itemCode\":\""+goods_barcode1+"\",\"inventoryType\":\"ZP\",\"planQty\":"+goods_number1+",\"actualQty\":\""+goods_number1+"\"}]}}";
			AutoLogger.log.info(body);
			String url = HttpClientKw.urlJoint("http://39.98.231.61:30003/api/fromqimen?", body, "returnorder.confirm", "json", "2.0", ownerCode, "21534044");
			HttpClientKw.doPostJson(url, body, "退货入库回写oms");
			boolean r = kw.assertExpectContains("success", "$.flag", "退货入库回写oms");
			assertTrue(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(description = "退货入库回写oms -> 自动匹配退货商品", priority = 5, enabled = true)
	public void return_To_Oms() throws SQLException {
		try {
			String body = "{\"returnOrder\":{\"returnOrderCode\":\"" + return_sn + "\",\"returnOrderId\":\""+SSHService.outer_order_id+"\",\"warehouseCode\":\"BQJX01\",\"orderType\":\""+orderType+"\",\"orderConfirmTime\":\""+SSHService.NowTime+"\",\"logisticsCode\":\"SF\",\"expressCode\":\"12345678\",\"returnReason\":null,\"remark\":\"\",\"outBizCode\":\""+outBizCode+"\",\"senderInfo\":{\"name\":\""+TesterName+"\",\"mobile\":\"13100000000\",\"province\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"city\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"area\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"detailAddress\":\"\\u4e0a\\u6d77\\u5e02\\u957f\\u5b81\\u533a\\u63a5\\u53e3\\u81ea\\u52a8\\u5316\"}},\"extendProps\":{\"ownerCode\":\""+ownerCode+"\"},\"orderLines\":{\"orderLine\":[{\"orderLineNo\":"+order_return_goods_id+",\"sourceOrderCode\":\""+deal_code+"\",\"subSourceOrderCode\":\""+oid+"\",\"linOwnerCode\":\""+ownerCode+"\",\"itemCode\":\"" + goods_barcode1 + "\",\"inventoryType\":\"ZP\",\"planQty\":"+goods_number1+",\"actualQty\":\""+goods_number1+"\",\"snList\":{\"sn\":null},\"extendProps\":{\"subOrderId\":\"\",\"status\":\"1\",\"outerReturnId\":\"\",\"reason\":\"\",\"OrderId\":\"\"}}]}}";
			AutoLogger.log.info(body);
			String url = HttpClientKw.urlJoint("http://39.98.231.61:30003/api/fromqimen?", body, "returnorder.confirm", "json", "2.0", ownerCode, "21534044");
			HttpClientKw.doPostJson(url, body, "退货入库回写oms");                // returnorder.confirm  return.status
			boolean r = kw.assertExpectContains("success", "$.flag", "退货入库回写oms");
			assertTrue(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}