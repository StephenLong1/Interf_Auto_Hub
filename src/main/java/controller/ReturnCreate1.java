package controller;

import org.testng.annotations.Test;
import source.HttpClientKw;
import source.SSHService;

public class ReturnCreate1 {
	/**
	 * 用SSHService类创建一个叫做SSHService的名叫ssh的对象
	 * 用HttpClientKw类创建一个叫做HttpClientKw的名叫kw的对象
	 */
	public static HttpClientKw kw = new HttpClientKw();
	public static SSHService ssh = new SSHService();



	@Test(description = "创建天猫退货退款单", priority = 0, enabled = true)
	public void insertTmRefundOnly()   {
        try {
        	ssh.insertTmReturnRefund();
        } catch (Exception e) {
			e.printStackTrace();
		}
	}



}