package controller2;

import common.AutoLogger;
import source.HttpClientKw;
import source.SSHService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static org.testng.Assert.assertTrue;

public class Sales_Shipping_Data {

	/**
	 * 从config.properties文件中获取到ownerCode、dataBase、shopCode、、、
	 */
	String ownerCode = SSHService.ownerCode();
	String deal_code = SSHService.deal_code();
	public String body;


	/**
	 *  定时任务：发货数据推送
	 */
	@Test(description = "开启定时任务 ->> 订单发货数据推送", priority = 3, enabled = true)
	public void execute_Timed_Task() throws SQLException {
		try {
			SSHService.sales_data_push(deal_code);
			HttpClientKw.executeTimedTask(ownerCode);
			SSHService.sales_data_check(deal_code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}