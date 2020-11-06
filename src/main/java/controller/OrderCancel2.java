package controller;

import common.AutoLogger;
import org.testng.annotations.Test;
import source.HttpClientKw;
import source.SSHService;

public class OrderCancel2 {

	/**
	 * 用SSHService类创建一个叫做SSHService的名叫ssh的对象
	 * 用HttpClientKw类创建一个叫做HttpClientKw的名叫kw的对象
	 */
	SSHService ssh = new SSHService();
	HttpClientKw kw = new HttpClientKw();

	/**
	 * 从config.properties文件中获取到ownerCode、dataBase、shopCode
	 */
	String shopCode = SSHService.shopCode();
	String deal_code = SSHService.deal_code();
	String order_sn = SSHService.order_sn();
	String cancelReason = SSHService.cancelReason();
	String TesterName = SSHService.TesterName();
	public String body;


	/**
	 * 作废订单               切记CancelReason和Canceler均需要转为  unicode 编码转过去
	 *  order_id: 订单号
	 *
	 */
	@Test(description = "取消/作废订单", priority = 0, enabled = true)
	public void order_Cancel()   {
		try {
			body = "{\"DealCode\":\""+ deal_code +"\",\"shopCode\":\""+shopCode+"\",\"orderId\":\""+ SSHService.order_sn +"\",\"CancelReason\":\""+SSHService.cancelReason()+"\",\"Canceler\":\""+HttpClientKw.Encode(TesterName)+"\",\"CancelDate\":\""+SSHService.NowTime+"\"}";
			AutoLogger.log.info("该接口请求体： " + body);
			HttpClientKw.loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
			HttpClientKw.doGet("点击销售订单","http://39.98.231.61:30003/admin/order/list","","","","","");
			HttpClientKw.doGet("进入详情页","http://39.98.231.61:30003/admin/order/detail?","page=1&limit=12&order_id="+ SSHService.order_id()+"","","","","");
			HttpClientKw.doPost("点击订单作废","http://39.98.231.61:30003/admin/order/invalid","orders%5B%5D="+SSHService.order_id()+"&platform=0&remark=%E4%B8%83%E5%A4%A9%E6%97%A0%E7%90%86%E7%94%B1","X-CSRF-TOKEN",HttpClientKw.token);
			HttpClientKw.doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+HttpClientKw.token+"","_ati","884980082140");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  作废订单数据校验
	 */
	@Test(description = "OMS →> HUB数据校验", priority = 1, enabled = true)
	public void cancel_Check()   {
		try {
			SSHService.orderCancel_dataCheck(body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}