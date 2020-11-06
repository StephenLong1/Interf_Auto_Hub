package demo;

import source.SSHService;

import java.io.*;
import java.util.Properties;

public class demo2 {
    public  static  String filePath = System.getProperty("user.dir") + "\\src\\main\\java\\props\\config.properties";

    public static SSHService ssh = new SSHService();

    public static void main(String[] args) {
        try {
            ssh.insertTmReturnRefund();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeProperties(String keyname, String keyvalue) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(filePath));
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            props.setProperty(keyname, keyvalue);
            props.store(fos, "Update '" + keyname + "' value");
            fos.flush();
            fos.close();
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }

    public static String readProperties(String key) {
        Properties props = new Properties();
        try {
            InputStream in = new FileInputStream(new File(filePath));
            props.load(in);
            key = props.getProperty(key);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}