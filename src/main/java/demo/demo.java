package demo;

import source.HttpClientKw;
import source.SSHService;

import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class demo {

	public static void main(String[] args) {

		try {
			new SSHService().insertTmOrder();
//			HttpClientKw.loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
//			HttpClientKw.doGet("进入代转订单页面", "http://39.98.231.61:30003/admin/api_order/orders_list?", "page=1&limit=12&sd_id=" + SSHService.id() + "&tid=&order_type=0&pay_status=0&start_time=&end_time=&tab=1", "", "", "", "");
//			HttpClientKw.doPost("点击一键转单", "http://39.98.231.61:30003/admin/api_order/orders_transfer", "sd_id=" + SSHService.id() + "&tid=&order_type=0&pay_status=0&start_time=&end_time=&transfer_type=0", "X-CSRF-TOKEN", HttpClientKw.X_CSRF_TOKEN);
////			HttpClientKw.clickButtonCheck();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	public static void openBrowser(String url) {
		if(Desktop.isDesktopSupported()) {
			try {
				// 创建一个URI实例
				java.net.URI uri = java.net.URI.create(url);
				// 获取当前系统桌面扩展
				Desktop dp = Desktop.getDesktop();
				// 判断系统桌面是否支持要执行的功能
				if(dp.isSupported(Desktop.Action.BROWSE)) {
				    //获取系统默认浏览器打开链接
					dp.browse(uri);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	public static String dateToStamp(String s) throws ParseException {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = null;
		long ts = date.getTime();
		res = String.valueOf(ts);
		try {
			date = simpleDateFormat.parse(s);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public static void writeProperties(String keyname, String keyvalue) {
		Properties props = new Properties();
		String path = System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties"; // 获取项目路径
		try {
			props.load(new FileInputStream(path));
			FileOutputStream fos = new FileOutputStream(new File(path));
			props.setProperty(keyname, keyvalue);
			props.store(fos, "Update '" + keyname + "' value");
			fos.flush();
			fos.close();
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
		}
	}

	/**
	 * 更新（或插入）一对properties信息(主键及其键值) 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param keyname  键名
	 * @param keyvalue 键值
	 */
	public static void updateProperties(String keyname, String keyvalue) {
		Properties props = new Properties();
		String path = System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties"; // 获取项目路径
		try {
			props.load(demo.class.getResourceAsStream(path));
			OutputStream fos = new FileOutputStream(new File(demo.class.getResource(path).toURI()));
			props.setProperty(keyname, keyvalue);
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			props.store(fos, "Update '" + keyname + "' value");
			fos.flush();
			fos.close();

		} catch (IOException | URISyntaxException e) {
			System.err.println("属性文件更新错误");
		}
	}

	/**
	 * 根据主键key读取主键的值value
	 * 
	 * @param key 键名
	 */
	public static String readValue(String key) {
		Properties props = new Properties();
		String filePath = "/config.properties";
		try {
			InputStream in = demo.class.getResourceAsStream(filePath);
			props.load(in);
			String value = props.getProperty(key);
			System.out.println(key + "键的值是：" + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void transferData(String key) {
		updateProperties("goods_number1", "3");
		updateProperties("goods_number2", "2");
		updateProperties("goods_number2", "2");
		updateProperties("goods_number2", "2");
		updateProperties("goods_number2", "2");
		updateProperties("goods_number2", "2");
		updateProperties("goods_number2", "2");

	}

}