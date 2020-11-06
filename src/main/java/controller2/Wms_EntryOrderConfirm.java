package controller2;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import org.testng.annotations.Test;

public class Wms_EntryOrderConfirm {

	/**
	 * 从config.properties文件中获取到ownerCode、dataBase、shopCode、、、
	 */
	String dataBase = SSHService.dataBase();
	String order_sn = SSHService.order_sn();
	String ownerCode = SSHService.ownerCode();


	@Test(description = "WMS采购入仓回写", priority = 0, enabled = true)
	public void entryOrderConfirm()   {
        try {
        	String info = "database="+dataBase+"&order_sn="+order_sn+"";
            HttpClientKw.doGet("WMS采购入仓回写","http://192.168.11.111:8080/entryOrderConfirm?",info,"","","","");
        } catch (Exception e) {
			e.printStackTrace();
		}
	}



}