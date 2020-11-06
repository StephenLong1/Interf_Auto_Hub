package controller;

import source.SSHService;
import org.testng.annotations.Test;

public class OrderCreate1 {
	/**
	 * 用SSHService类创建一个叫做SSHService的名叫ssh的对象
	 */
	public static SSHService ssh = new SSHService();

	/**
	 *  订单通知发货
	 */
	@Test(description = "新建天猫单", priority = 0, enabled = true)
	public void getTMOrder() {
        try {
			ssh.insertTmOrder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}