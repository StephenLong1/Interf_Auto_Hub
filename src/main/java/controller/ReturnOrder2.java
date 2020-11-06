package controller;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ReturnOrder2 {

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
    String dataBase = SSHService.dataBase();
    String shopCode = SSHService.shopCode();
    String goods_number1 = SSHService.goods_number1();
    String goods_number2 = SSHService.goods_number2();
    String goods_barcode1 = SSHService.goods_barcode1();
    String goods_barcode2 = SSHService.goods_barcode2();
    String goods_price1 = SSHService.goods_price1();
    String goods_price2 = SSHService.goods_price2();
    String oid1 = SSHService.oid1();
    String oid2 = SSHService.oid2();


    String body;

    /**
     * OMS推送退货单到对接平台
     * ReceiptAmount: 退款金额(pandora使用)
     * sourceOrderCode：平台订单号tid
     * subSourceOrderCode： 子订单号oid
     * itemCode： 商品sku
     * orderType  (THRK=退货入库;HHRK=换货入库;只传英文编码)
     */
    @Test(description = "OMS推送退货单到对接平台", priority = 3, enabled = true)
    public void return_To_Hub() {
        try {
            body = "{\"returnOrder\":{\"returnOrderCode\":\"" + SSHService.return_sn() + "\",\"warehouseCode\":\"BQJX01\",\"orderType\":\"THRK\",\"preDeliveryOrderCode\":\"" + SSHService.order_sn() + "\",\"logisticsCode\":\"SF\",\"logisticsName\":\"SF\",\"expressCode\":\"12345678\",\"buyerNick\":\""+SSHService.TesterName()+"\",\"senderInfo\":{\"name\":\""+SSHService.TesterName()+"\",\"mobile\":\"13100000000\",\"province\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"city\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"area\":\"\\u4e0a\\u6d77\\u000d\\u000a\",\"detailAddress\":\"\\u4e0a\\u6d77\\u5e02\\u957f\\u5b81\\u533a\\u63a5\\u53e3\\u81ea\\u52a8\\u5316\"},\"remark\":\"\",\"returnReason\":\""+SSHService.returnReason()+"\"},\"orderLines\":{\"orderLine\":[{\"orderLineNo\":"+SSHService.order_return_goods_id()+",\"sourceOrderCode\":\"" + SSHService.deal_code() + "\",\"subSourceOrderCode\":\"" + SSHService.oid1() + "\",\"ownerCode\":\"" + ownerCode + "\",\"itemCode\":\""+goods_barcode1+"\",\"planQty\":"+goods_number1+",\"snList\":{\"sn\":[]},\"extendProps\":{\"preOrderId\":\"\",\"preSubOrderId\":\"\",\"barCode\":\"" + goods_barcode1 + "\",\"UnitPrice\":\""+goods_price1+".00\"}},{\"orderLineNo\":"+SSHService.order_return_goods_id2()+",\"sourceOrderCode\":\""+SSHService.deal_code()+"\",\"subSourceOrderCode\":\""+oid2+"\",\"ownerCode\":\""+ownerCode+"\",\"itemCode\":\""+goods_barcode2+"\",\"planQty\":"+goods_number2+",\"snList\":{\"sn\":[]},\"extendProps\":{\"preOrderId\":\"\",\"preSubOrderId\":\"\",\"barCode\":\""+goods_barcode2+"\",\"UnitPrice\":\""+goods_price2+".00\"}}]},\"extendProps\":{\"shopCode\":\""+shopCode+"\",\"ReceiptAmount\":\""+ssh.totalPrice+"\",\"Zip\":\"000000\"}}";
            AutoLogger.log.info("该接口请求体： " + body);
			HttpClientKw.loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
	        String info = "page=1&limit=12&sd_id="+SSHService.id()+"&return_sn=&deal_code=&order_sn=&return_match=&return_source=&return_push_status=0&mobile=&consignee=&invoice_no=&add_time_start=&add_time_end=&completed_time_start=&completed_time_end=&return_lab=0&buyer_nick=&refresh_fee=-1&goods_barcode=&return_warehouse=&match_time_start=&match_time_end=&current_tab=1&symbol_fee=-1&refund_fee=&status=&return_status=";
	        HttpClientKw.doGet("进入退换货界面","http://39.98.231.61:30003/admin/return/list?",info,"","","","");
	        HttpClientKw.doGet("进入详情页面","http://39.98.231.61:30003/admin/return/retutn_goods?","page=1&limit=10&return_id="+SSHService.return_id()+"","","","","");
	        HttpClientKw.doGet("点击推送退货单","http://39.98.231.61:30003/admin/return/pushwms?","returns="+SSHService.id()+"&is_push=1",HttpClientKw.token,HttpClientKw.X_CSRF_TOKEN,"","");
	        HttpClientKw.doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+HttpClientKw.token+"","_ati","884980082140");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 推送退货单数据校验
     */
    @Test(description = "OMS →> HUB数据校验", priority = 4, enabled = true)
    public void return_Data_Check() {
        try {
            SSHService.returnOrderS_data_Check(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}