package controller2;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import org.testng.annotations.Test;

public class Wms_StockOutConfirm {

	/**
	 * 从config.properties文件中获取到ownerCode、dataBase、shopCode、、、
	 */
	String dataBase = SSHService.dataBase();
	String order_sn = SSHService.order_sn();
	String ownerCode = SSHService.ownerCode();


	@Test(description = "WMS采购退货回传", enabled = true)
	public void stockOutConfirm()   {
        try {
        	String info = "database="+dataBase+"&order_sn="+order_sn+"";
            HttpClientKw.doGet("WMS采购退货回传","http://192.168.11.111:8080/stockOutConfirm?",info,"","","","");
        } catch (Exception e) {
			e.printStackTrace();
		}
	}



}