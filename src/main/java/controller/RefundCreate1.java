package controller;

import source.HttpClientKw;
import source.SSHService;
import org.testng.annotations.Test;

public class RefundCreate1 {
	/**
	 * 用SSHService类创建一个叫做SSHService的名叫ssh的对象
	 * 用HttpClientKw类创建一个叫做HttpClientKw的名叫kw的对象
	 */
	public static HttpClientKw kw = new HttpClientKw();
	public static SSHService ssh = new SSHService();



	@Test(description = "创建天猫仅退款单", priority = 0, enabled = true)
	public void insertTmRefundOnly()   {
        try {
        	ssh.insertTmRefundOnly();
        } catch (Exception e) {
			e.printStackTrace();
		}
	}



}