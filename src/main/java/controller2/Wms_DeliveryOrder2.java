package controller2;

import common.AutoLogger;
import org.testng.annotations.Test;
import source.HttpClientKw;
import source.SSHService;

public class Wms_DeliveryOrder2 {

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
    String goods_number1 = SSHService.goods_number1();
    String goods_number2 = SSHService.goods_number2();
    String goods_barcode1 = SSHService.goods_barcode1();
    String goods_barcode2 = SSHService.goods_barcode2();
    String discount1 =SSHService.discount1();
    String discount2 = SSHService.discount2();
    String body  = "{\"deliveryOrder\":{\"deliveryOrderCode\":\"" + SSHService.order_sn() + "\",\"preDeliveryOrderCode\":\"\",\"preDeliveryOrderId\":\"\",\"orderType\":\"JYCK\",\"warehouseCode\":\"BQJX01\",\"orderFlag\":\"NORMAL\",\"sourcePlatformCode\":\"TM\",\"createTime\":\"" + SSHService.NowTime + "\",\"placeOrderTime\":\"" + SSHService.NowTime + "\",\"payTime\":\"" + SSHService.NowTime + "\",\"payNo\":\"\",\"operateTime\":\"" + SSHService.NowTime + "\",\"logisticsCode\":\"SF\",\"logisticsName\":\"\\u987a\\u4e30\\u901f\\u8fd0\",\"expressCode\":\"\",\"invoiceFlag\":\"N\",\"shopNick\":\"" + shopCode + "\",\"buyerNick\":\"\",\"totalAmount\":\"" + ssh.totalPrice + "\",\"itemAmount\":\"" + ssh.totalPrice + "\",\"discountAmount\":\"\",\"gotAmount\":\"" + ssh.totalPrice + "\",\"sellerMessage\":\"\",\"buyerMessage\":\"\",\"remark\":\"\",\"logisticsAreaCode\":null,\"receiverInfo\":{\"name\":\""+SSHService.TesterName()+"\",\"tel\":\"13100000000\",\"mobile\":\"13100000000\",\"province\":\"\\u4e0a\\u6d77\",\"city\":\"\\u4e0a\\u6d77\\u5e02\",\"area\":\"\\u957f\\u5b81\\u533a\",\"detailAddress\":\"\\u4e0a\\u6d77\\u5e02\\u957f\\u5b81\\u533a\\u63a5\\u53e3\\u81ea\\u52a8\\u5316\",\"town\":null,\"email\":\"\"},\"serviceCode\":2,\"extendProps\":{\"PaymentTerm\":\"OTHER\",\"CarrierCharges\":\"0.00\"}},\"extendProps\":{\"baoJia\":0,\"baoJia_amount\":0,\"isPresell\":\"\",\"mappingCode\":\"\",\"proCode\":\"\",\"PresellOrder\":0,\"payStatus\":1},\"orderLines\":{\"orderLine\":[{\"orderLineNo\":" + SSHService.order_goods_id1() + ",\"sourceOrderCode\":\"" + SSHService.deal_code() + "\",\"subSourceOrderCode\":\"\",\"itemCode\":\"" + goods_barcode1 + "\",\"ownerCode\":\"" + brandName + "\",\"inventoryType\":\"ZP\",\"planQty\":" + goods_number1 + ",\"actualPrice\":\"" + ssh.totalPrice1 + "\",\"retailPrice\":\"" + ssh.totalPrice1 + "\",\"discountAmount\":"+SSHService.discount1()+",\"extendProps\":{\"barCode\":\"" + goods_barcode1 + "\",\"Comment1\":\"\",\"Comment2\":\"" + SSHService.goods_name1 + "\"}},{\"orderLineNo\":" + SSHService.order_goods_id2() + ",\"sourceOrderCode\":\"" + SSHService.deal_code() + "\",\"subSourceOrderCode\":\"\",\"itemCode\":\"" + goods_barcode2 + "\",\"ownerCode\":\"" + brandName + "\",\"inventoryType\":\"ZP\",\"planQty\":" + goods_number2 + ",\"actualPrice\":\"" + ssh.totalPrice2 + "\",\"retailPrice\":\"" + ssh.totalPrice2 + "\",\"discountAmount\":"+SSHService.discount2()+",\"extendProps\":{\"barCode\":\"" + goods_barcode2 + "\",\"Comment1\":\"\",\"Comment2\":\"" + SSHService.goods_name2 + "\"}}]}}";


    /**
     * 订单通知发货
     */
    @Test(description = "订单通知发货",  enabled = true)
    public void delivery_To_Hub()  {
        try {
            HttpClientKw.loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
            AutoLogger.log.info("该接口请求体： " + body);
        	HttpClientKw.doGet("点击销售订单","http://39.98.231.61:30003/admin/order/list","","","","","");
        	HttpClientKw.doGet("进入详情页","http://39.98.231.61:30003/admin/order/detail?","page=1&limit=12&order_id="+ SSHService.order_id()+"","","","","");
        	HttpClientKw.doPost("点击发货","http://39.98.231.61:30003/admin/order/notice_shipping","orders%5B%5D="+SSHService.order_id()+"&platform=0&sd_id="+SSHService.id()+"","X-CSRF-TOKEN",HttpClientKw.token);
        	HttpClientKw.doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+HttpClientKw.token+"","_ati","884980082140");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}