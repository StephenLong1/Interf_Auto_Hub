package demo;

import source.SSHService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import common.AutoLogger;

public class ProTest {
    public static String nowTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 格式为yyyyMMddHHmmss

    public static void main(String[] args) {
        
    	try {
			new SSHService().insertTmReturnRefundS();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    
}
