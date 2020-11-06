package controller2;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.sql.SQLException;

import static org.testng.Assert.assertTrue;

public class Return_Finish_Data {


    /**
     * 从config.properties文件中获取到ownerCode、dataBase、shopCode
     */
    String brandName = SSHService.ownerCode();
    String dataBase = SSHService.dataBase;
    String shopCode = SSHService.shopCode();
    String goods_number = SSHService.goods_number1();
    String goods_barcode1 = SSHService.goods_barcode1();
    String goods_price1 = SSHService.goods_price1();
    String deal_code = SSHService.ownerCode();
    String body;
    String body2;

    @Test(description = "开启定时任务 ->> 订单退货数据推送【退货推送节点为：完结触发推送】", enabled = false)
    public void execute_Timed_Task() {
        try {
            SSHService.returnEnd_data_push(deal_code);
            HttpClientKw.executeTimedTask(brandName);
            SSHService.return_data_check(deal_code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}