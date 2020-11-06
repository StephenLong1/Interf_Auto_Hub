package controller;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class DeliveryBack {

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
	String return_sn = SSHService.return_sn();
	String TesterName = SSHService.TesterName();
	String goods_barcode1 = SSHService.goods_barcode1();
	String goods_price1 = SSHService.goods_price1();
	String goods_number1 = SSHService.goods_number1();
	String outBizCode = order_sn + "-1195985";

	String body;

    @Test(description = "订单发货回传", priority = 0, enabled = true)
    public void delivery_To_Oms() {
        try {
            String body = "{\"deliveryOrderCode\":\"" + order_sn + "\",\"orderType\":\"JYCK\",\"warehouseCode\":\"BQJX01\",\"outBizCode\":\"" + outBizCode + "\",\"confirmType\":\"0\",\"orderConfirmTime\":\"" + SSHService.NowTime + "\",\"deliveryOrderId\":null,\"updatedDate\":\""+SSHService.NowTime+"\",\"logisticsName\":\"SF\",\"logisticsNo\":\"SF1111111111113\"}";
            AutoLogger.log.info("该接口请求体： " + body);
            String url = HttpClientKw.urlJoint("http://39.98.231.61:30003/api/fromduijie?", body, "delivery.order", "json", "2.0", brandName, "21534044");
            HttpClientKw.doPostJson(url, body, "订单发货回传");
            AutoLogger.log.info(body);
            boolean r = kw.assertExpectContains("success", "$.flag", "订单发货回传");
            assertTrue(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * OMS推送退货单到对接平台
     * ReceiptAmount: 退款金额(pandora使用)
     * sourceOrderCode：平台订单号tid
     * subSourceOrderCode： 子订单号oid
     * itemCode： 商品sku
     * orderType  (THRK=退货入库;HHRK=换货入库;只传英文编码)
     */
    @Test(description = "OMS推送退货单到对接平台", priority = 1, enabled = false)
    public void return_To_Hub() {
        try {
            body = "{\"returnOrder\":{\"returnOrderCode\":\"" + return_sn + "\",\"warehouseCode\":\"BQJX01\",\"orderType\":\"THRK\",\"preDeliveryOrderCode\":\"" + order_sn + "\",\"logisticsCode\":\"SF\",\"logisticsName\":\"SF\",\"expressCode\":\"12345678\",\"buyerNick\":\"123\",\"senderInfo\":{\"name\":\""+TesterName+"\",\"mobile\":\"13100000000\",\"email\":\"\",\"province\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"city\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"area\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"detailAddress\":\"\\u4e0a\\u6d77\\u5e02\\u957f\\u5b81\\u533a\\u63a5\\u53e3\\u81ea\\u52a8\\u5316\"},\"remark\":\"\",\"returnReason\":\""+HttpClientKw.DeCode(TesterName)+"\"},\"orderLines\":{\"orderLine\":[{\"orderLineNo\":" + SSHService.returnLineId1 + ",\"sourceOrderCode\":\"" + SSHService.deal_code + "\",\"subSourceOrderCode\":\"" + SSHService.oid + "\",\"ownerCode\":\"" + brandName + "\",\"itemCode\":\"" + goods_barcode1 + "\",\"planQty\":" + goods_number1 + ",\"snList\":{\"sn\":[]},\"inventoryType\":\"ZP\",\"extendProps\":{\"preOrderId\":\"\",\"preSubOrderId\":\"" + SSHService.outer_order_id + "\",\"barCode\":\"" + goods_barcode1 + "\",\"UnitPrice\":\"" + goods_price1 + ".00\"}}]},\"extendProps\":{\"shopCode\":\"" + shopCode + "\",\"ReceiptAmount\":\"" + ssh.totalPrice1 + "\",\"Zip\":\"000000\"}}";
            AutoLogger.log.info("该接口请求体： " + body);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "OMS →> HUB数据校验", priority = 2, enabled = false)
    public void return_Data_Check()  {
        try {
            SSHService.returnOrder_data_Check(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}