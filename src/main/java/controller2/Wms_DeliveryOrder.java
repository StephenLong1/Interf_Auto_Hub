package controller2;

import common.AutoLogger;
import org.testng.annotations.Test;
import source.HttpClientKw;
import source.SSHService;

public class Wms_DeliveryOrder {
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
	String ownerCode = SSHService.ownerCode();
	String goods_number1 = SSHService.goods_number1();

	String goods_barcode1 = SSHService.goods_barcode1();
	String discount1 = SSHService.discount1();
	public String body;

	/**
	 *  订单通知发货
	 */
	@Test(description = "订单通知发货",enabled = true)
	public void delivery_To_Hub()   {
        try {
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




}