package controller;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import org.testng.annotations.Test;

public class DeliveryOrder1 {
	/**
	 * 用SSHService类创建一个叫做SSHService的名叫ssh的对象
	 * 用HttpClientKw类创建一个叫做HttpClientKw的名叫kw的对象
	 */
	public static HttpClientKw kw = new HttpClientKw();
	public static SSHService ssh = new SSHService();

	/**
	 * 从config.properties文件中获取到ownerCode、dataBase、shopCode、、、
	 */
	String dataBase = SSHService.dataBase();
	String shopName = SSHService.shopName();
	String ownerCode = SSHService.ownerCode();
	String ShopCode = SSHService.shopCode();
	String goods_number1 = SSHService.goods_number1();
	String goods_number2 = SSHService.goods_number2();
	String goods_price1 = SSHService.goods_price1();
	String goods_price2 = SSHService.goods_price2();
	String goods_barcode1 = SSHService.goods_barcode1();
	String goods_barcode2 = SSHService.goods_barcode2();
	String discount1 = SSHService.discount1();
	String discount2 = SSHService.discount2();
	public String body;

	/**
	 *  订单通知发货
	 */
	@Test(description = "订单通知发货", priority = 0, enabled = true)
	public void delivery_To_Hub()   {
        try {
			body = "{\"deliveryOrder\":{\"deliveryOrderCode\":\""+ SSHService.order_sn() +"\",\"preDeliveryOrderCode\":\"\",\"preDeliveryOrderId\":\"\",\"orderType\":\"JYCK\",\"warehouseCode\":\"BQJX01\",\"orderFlag\":\"NORMAL\",\"sourcePlatformCode\":\"TM\",\"createTime\":\""+ SSHService.NowTime +"\",\"placeOrderTime\":\""+ SSHService.NowTime +"\",\"payTime\":\""+SSHService.NowTime+"\",\"payNo\":\"\",\"operateTime\":\""+SSHService.NowTime+"\",\"logisticsCode\":\"SF\",\"logisticsName\":\"\\u987a\\u4e30\\u901f\\u8fd0\",\"expressCode\":\"\",\"invoiceFlag\":\"N\",\"shopNick\":\""+ownerCode+"\",\"buyerNick\":\""+SSHService.TesterName()+"\",\"totalAmount\":\""+ssh.totalPrice1+"\",\"itemAmount\":\""+ssh.totalPrice1+"\",\"discountAmount\":\""+SSHService.discount1()+"\",\"gotAmount\":\""+ssh.totalPrice1+"\",\"sellerMessage\":\"\",\"buyerMessage\":\"\",\"remark\":\"\",\"logisticsAreaCode\":null,\"receiverInfo\":{\"name\":\""+SSHService.TesterName()+"\",\"tel\":\"13100000000\",\"mobile\":\"13100000000\",\"province\":\"\\u4e0a\\u6d77\",\"city\":\"\\u4e0a\\u6d77\\u5e02\",\"area\":\"\\u957f\\u5b81\\u533a\",\"detailAddress\":\"\\u4e0a\\u6d77\\u5e02\\u957f\\u5b81\\u533a\\u63a5\\u53e3\\u81ea\\u52a8\\u5316\",\"town\":null},\"serviceCode\":2},\"extendProps\":{\"baoJia\":0,\"baoJia_amount\":0,\"isPresell\":\"\",\"mappingCode\":\"\",\"proCode\":\"\",\"PresellOrder\":0,\"payStatus\":1},\"orderLines\":{\"orderLine\":[{\"orderLineNo\":"+ SSHService.id() +",\"sourceOrderCode\":\""+SSHService.deal_code()+"\",\"subSourceOrderCode\":\"\",\"itemCode\":\"" + goods_barcode1 + "\",\"ownerCode\":\""+ownerCode+"\",\"inventoryType\":\"ZP\",\"planQty\":"+goods_number1+",\"actualPrice\":\""+ssh.totalPrice1+"\",\"retailPrice\":\""+ssh.totalPrice1+"\",\"discountAmount\":"+discount1+",\"extendProps\":{\"barCode\":\""+ goods_barcode1 +"\"}}]}}";
            AutoLogger.log.info("该接口请求体： " + body);
            HttpClientKw.loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
            HttpClientKw.doGet("点击销售订单","http://39.98.231.61:30003/admin/order/list","","","","","");
        	HttpClientKw.doGet("进入详情页","http://39.98.231.61:30003/admin/order/detail?","page=1&limit=12&order_id="+ SSHService.order_id()+"","","","","");
        	HttpClientKw.doPost("点击发货","http://39.98.231.61:30003/admin/order/notice_shipping","orders%5B%5D="+SSHService.order_id()+"&platform=0&sd_id="+SSHService.id()+"","X-CSRF-TOKEN",HttpClientKw.token);
        	HttpClientKw.doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+HttpClientKw.token+"","_ati","884980082140");
        } catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  发货数据校验，拿出interface表中的数据与oms的数据进行对比
	 */
	@Test(description = "OMS →> HUB数据校验", priority = 1, enabled = true)
	public void delivery_Data_Check()   {
		try {
			SSHService.delivery_dataCheck(body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}