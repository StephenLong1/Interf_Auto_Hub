package controller2;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import org.testng.annotations.Test;

public class Wms_ReturnOrderConfirm {

	/**
	 * 从config.properties文件中获取到ownerCode、dataBase、shopCode、、、
	 */
	String dataBase = SSHService.dataBase();
	String return_sn = SSHService.return_sn();
	String ownerCode = SSHService.ownerCode();


	@Test(description = "WMS退货入库回传", enabled = true)
	public void stockOutConfirm()   {
        try {
        	String info = "database="+dataBase+"&order_sn="+return_sn+"";
            HttpClientKw.doGet("WMS退货入库回传","http://192.168.11.111:8080/returnOrderConfirm?",info,"","","","");
        } catch (Exception e) {
			e.printStackTrace();
		}
	}



}