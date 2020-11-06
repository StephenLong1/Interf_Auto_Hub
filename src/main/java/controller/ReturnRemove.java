package controller;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import org.testng.annotations.Test;

public class ReturnRemove {

    /**
     * 用SSHService类创建一个叫做SSHService的名叫ssh的对象
     * 用HttpClientKw类创建一个叫做HttpClientKw的名叫kw的对象
     */
    SSHService ssh = new SSHService();
    HttpClientKw kw = new HttpClientKw();
    
    /**
     * 从config.properties文件中获取到ownerCode、dataBase、shopCode、、、
     */
    String ownerCode = SSHService.ownerCode();
    String return_sn = SSHService.return_sn();
    String shopCode = SSHService.shopCode();
    String goods_number1 = SSHService.goods_number1();
    String goods_barcode1 = SSHService.goods_barcode1();
    String goods_price1 = SSHService.goods_price1();
    String cancelReason = SSHService.cancelReason();
    String return_id = SSHService.return_id();
    String body;


    /**
     * OMS退货单作废通知对接平台
     */
    @Test(description = "OMS退货单作废通知对接平台", priority = 0, enabled = true)
    public void returnRemove_To_Hub() {
        try {
        	body = "{\"warehouseCode\":\"BQJX01\",\"ownerCode\":\"" + ownerCode + "\",\"orderCode\":\"" + return_sn + "\",\"orderType\":\"THRK\",\"cancelReason\":\""+cancelReason+"\"}";
            AutoLogger.log.info("该接口请求体： " + body);
			HttpClientKw.loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
	        String info = "page=1&limit=12&sd_id="+SSHService.id()+"&return_sn=&deal_code=&order_sn=&return_match=&return_source=&return_push_status=0&mobile=&consignee=&invoice_no=&add_time_start=&add_time_end=&completed_time_start=&completed_time_end=&return_lab=0&buyer_nick=&refresh_fee=-1&goods_barcode=&return_warehouse=&match_time_start=&match_time_end=&current_tab=1&symbol_fee=-1&refund_fee=&status=&return_status=";
	        HttpClientKw.doGet("进入退换货界面","http://39.98.231.61:30003/admin/return/list?",info,"","","","");
	        HttpClientKw.doGet("进入详情页面","http://39.98.231.61:30003/admin/return/retutn_goods?","page=1&limit=10&return_id="+SSHService.return_id()+"","","","","");
	        HttpClientKw.doGet("点击推送作废退货单","http://39.98.231.61:30003/admin/return/invalid?","returns%5B%5D="+SSHService.return_id()+"&is_push=1",HttpClientKw.token,HttpClientKw.X_CSRF_TOKEN,"","");
	        HttpClientKw.doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+HttpClientKw.token+"","_ati","884980082140");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退货数据校验
     */
    @Test(description = "OMS →> HUB数据校验", priority = 1, enabled = true)
    public void returnRemove_Data_Check() {
        try {
            SSHService.returnRemove_Data_Check(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}