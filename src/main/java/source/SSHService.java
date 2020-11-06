package source;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import common.AutoLogger;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author Stephen Long<龙张立>
 * @version 2.0
 */
public class SSHService {


    /**
     * 定义好Sql使用的关键字,为私有
     */
    private static Session session;
    private static Connection connection = null;
    private static Statement stm = null;
    private static ResultSet rs = null;
    private static java.sql.PreparedStatement ps = null;

    /**
     * 以下定义的各字段为全局变量：为插入数据Sql中的字段,以及和接口请求体的字段
     */
    public static String timeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
    public static String nowTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 格式为yyyyMMddHHmmss
    public static String NowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());// 格式为yyyy-MM-dd HH:mm:ss
    public static String deal_code;           // 平台订单号
    public static String order_sn;

    // 系统自动生成的唯一单号，根据天猫单号和时间戳组合而成
    public static String outBizCode = order_sn + "-1195985";  // outBizCode 号
    public static String oid = deal_code;    // 子订单号     如果只有一个订单就和deal_code相同

    public static String oid1; 
    public static String oid2;

    public static String outer_order_id = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 2)));
    public static String outer_goods_id1 = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 2)));
    public static String outer_goods_id2 = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 2)));

    public static String order_id;           // 关联order_info和order_goods
    public static String order_goods_id1;     // order_goods中id
    public static String order_goods_id2;
    public static String planQty;            // 预计发货数量
    public static String actualQty;          // 实际发货数量
    public static String goods_number1;       // 商品行1对应的数量
    public static String goods_number2;       // 商品行2对应的数量
    public static String goods_barcode1;     // order_goods中goods_barcode
    public static String goods_barcode2;     // 多订单多商品中的商品sku
    public static String discount1;       // 商品行1对应的优惠
    public static String discount2;       // 商品行2对应的优惠
    public static String totalDiscount;   // 商品行1和商品行2对应的优惠的总和

    public static String goods_price1;       // 商品单价
    public static String goods_price2;       // 商品单价
    public static String goods_name1 = "自动化测试商品1";        // 商品名
    public static String goods_name2 = "自动化测试商品2";        // 商品名

//    public static String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties";
    public static String filePath = System.getProperty("user.dir")+"/config.properties";
    public static String ownerCode;          // 货主
    public static String dataBase;       // OMS数据库名   = "fuoms_" + ownerCode()
    public static String id;                 // 店铺id
    public static String shopName;
    public static String TesterName;
    public static String cancelReason;
    public static String emailContent;
    public static String hub_dataBase = "bq_interface";       // HUB_interface数据库

    public static String hub_delivery_order; // = String.format("%s_delivery_order", ownerCode());     // 通知发货数据的表-interface-
    public static String hub_order_cancel;// = String.format("%s_order_cancel", ownerCode());      // 订单取消-interface-
    public static String hub_return_order;// = String.format("%s_return_order", ownerCode());      // 退货-interface-
    public static String hub_refund; // = String.format("%s_refund", ownerCode());                  // 请求退款-interface-
    public static String hub_return_confirm; // = String.format("%s_return_confirm", ownerCode());  // 退货入库回写-interface-
    public static String hub_order_remove; // = String.format("%s_order_remove", ownerCode());  // 退货单作废-interface-

    // 调用该方法, 将获取的ownerCode于对应的hub表名进行拼接,传值为全局变量
    public static String hub_interface() {
        hub_delivery_order = ownerCode() + "_delivery_order";
        hub_order_cancel =   ownerCode() + "_order_cancel";
        hub_return_order =   ownerCode() + "_return_order";
        hub_refund =   ownerCode() + "_refund";
        hub_return_confirm =   ownerCode() + "_return_confirm";
        hub_order_remove =   ownerCode() + "_order_remove";
        return hub_delivery_order;
    }


    public static String hub_sales_data = String.format("%s_sales_data", ownerCode());       // 发货数据推送-interface-
    public static String hub_return_data = String.format("%s_return_data", ownerCode());     // 退货数据推送-interface-


    public static String shopCode;           // 店铺编号
    public static String refund_fee1;         // 商品行1退货的费用
    public static String refund_fee2;         // 商品行2退货的费用

    public static String returnLineId1;      // order_return_goods中的Id
    public static String returnLineId2;      // order_return_goods中的Id
    public static String order_return_goods_id;   // order_return_goods中的Id
    public static String order_return_goods_id2;
    public static String orderLineId1;       // order_goods中的Id
    public static String orderLineId2;       // order__goods中的Id
    public static String cron_id;            // OMS定时任务对应的不同类型的id
    public static String lasttime;           // 获取oms_synchronize_log中interface_deliver_data的shippingtime, 注:  获取发货数据的sql语句中需关联此字段
    public static String lasttime2;          // 获取oms_synchronize_log中interface_return_data的shippingtime, 注:  获取发货数据的sql语句中需关联此字段
    public static String return_match_time;  // order_return表里的match_time字段

    /**
     * 退货   注意：退货单号开头必须为th
     */
    public static String return_sn = "th2" + String.valueOf(System.currentTimeMillis());          // 退货单号
    public static String return_id;          // 关联order_return和order_return_goods的return_id
    public static String return_deal_code;   // return_id与return_deal_code相同


    /**
     * 退款  注意：退款单号开头必须为tk
     */
    public static String refund_sn = "tk2" + String.valueOf(System.currentTimeMillis());  // 退款单号 Z
    public static String refund_deal_code = String.valueOf(System.currentTimeMillis());      // 退款平台订单号
    public static String refund_id;          // 退款id

    /**
     * 从config.properties中获取到对应的值, 并赋值给上面已定义好的全局变量
     */
    public static String TesterPassword() {
    	String TesterPassword = null;
    	TesterPassword = readProperties("TesterPassword");
        return TesterPassword;
    }    
    
    public static String goods_barcode1() {    // 第一个barcode
        goods_barcode1 = readProperties("goods_barCode1");
        return goods_barcode1;
    }     // 第一个barcode

    public static String goods_barcode2() {
        goods_barcode2 = readProperties("goods_barCode2");
        return goods_barcode2;
    }     // 第二个barcode

    public static String goods_number1() {
        goods_number1 = readProperties("goods_number1");
        return goods_number1;
    }// sku的商品数量

    public static String goods_number2() {
        goods_number2 = readProperties("goods_number2");
        return goods_number2;
    }// sku的商品数量

    public static String goods_price1() {
        goods_price1 = readProperties("goods_price1");
        return goods_price1;
    }// 第一个商品价格

    public static String goods_price2() {
        goods_price2 = readProperties("goods_price2");
        return goods_price2;
    }// 第二个商品价格


    public static String ownerCode() {
        ownerCode = readProperties("ownerCode");
        return ownerCode;
    }          // 货主

    public static String shopCode() {
        shopCode = readProperties("ShopCode");
        return shopCode;
    }           // 门店编码

    public static String id() {
        id = readProperties("id");
        return id;
    }             // 门店id

    public static String order_id() {
        order_id = readProperties("order_id");
        return order_id;
    }             // 门店id

    public static String order_goods_id1() {
        order_goods_id1 = readProperties("order_goods_id1");
        return order_goods_id1;
    }

    public static String order_goods_id2() {
        order_goods_id2 = readProperties("order_goods_id2");
        return order_goods_id2;
    }             // 门店id


    public static String discount1() {
        discount1 = new DecimalFormat("0.00").format(new BigDecimal(readProperties("discount1")));
        return discount1;
    }

    public static String discount2() {
        discount2 = new DecimalFormat("0.00").format(new BigDecimal(readProperties("discount2")));
        return discount2;
    }

    public static String totalDiscount() {
        totalDiscount = new DecimalFormat("0.00").format(BigDecimal.valueOf(Double.parseDouble(discount1()) + Double.parseDouble(discount2())));
        return totalDiscount;
    }


    public static String shopName() {
        shopName = readProperties("shopName");
        return shopName;
    }

    public static String TesterName() {
        TesterName = readProperties("TesterName");
        return TesterName;
    }

    public static String deal_code() {
        deal_code = readProperties("deal_code");
        return deal_code;
    }

    
    public static String oid1() {
    	oid1 = readProperties("oid1");
        return oid1;
    }
    
    public static String oid2() {
    	oid2 = readProperties("oid2");
        return oid2;
    }



    public static String order_sn() {
        order_sn = readProperties("order_sn");
        return order_sn;
    }

    public static String cron_id() {
        cron_id = readProperties("cron_id");
        return cron_id;
    }

    
    public static String dataBase() {
        dataBase = "fuoms_" + readProperties("ownerCode");
        return dataBase;
    }
    
    public static String cancelReason() {
    	cancelReason =  readProperties("cancelReason");
        return cancelReason;
    }

    
    public static String return_sn() {
        return_sn =  readProperties("return_sn");
        return return_sn;
    }

    public static String return_sn1() {
        String return_sn1 =  readProperties("return_sn1");
        return return_sn1;
    }

    public static String return_sn2() {
    	String return_sn2 =  readProperties("return_sn2");
        return return_sn2;
    }

    public static String refund_sn() {
        refund_sn =  readProperties("refund_sn");
        return refund_sn;
    }

    public static String return_id() {
        return_id =  readProperties("return_id");
        return return_id;
    }
    
    public static String refund_fee1() {
        refund_fee1 = new DecimalFormat("0.00").format(new BigDecimal(readProperties("refund_fee1")));
        return refund_fee1;
    }

    
    public static String refund_fee() {
    	String refund_fee = new DecimalFormat("0.00").format(new BigDecimal(readProperties("refund_fee")));
        return refund_fee;
    }

    public static String refund_fee2() {
        refund_fee2 = new DecimalFormat("0.00").format(new BigDecimal(readProperties("refund_fee2")));
        return refund_fee2;
    }

    public static String refundStatus() {
        String refundStatus = null;
        refundStatus = readProperties("refundStatus");
        return refundStatus;
    }

    public static String orderType() {
        String orderType = null;
        orderType = readProperties("orderType");
        return orderType;
    }

    public static String order_return_goods_id() {
        order_return_goods_id = readProperties("order_return_goods_id");
        return order_return_goods_id;
    }

    public static String order_return_goods_id2() {
    	order_return_goods_id2 = readProperties("order_return_goods_id2");
        return order_return_goods_id2;
    }
    
    public static String returnReason() {
        String returnReason = null;
        returnReason = readProperties("returnReason");
        return returnReason;
    }

    public static String refund_deal_code() {
        String refund_deal_code = null;
        refund_deal_code = readProperties("refund_deal_code");
        return refund_deal_code;
    }



    // 通过生成时间戳的方法生成deal_code <每调用一次, 生成的结果都不同>
    public static void dealCode() {
        deal_code = new SimpleDateFormat("MMddHHmmssSSS").format(new Date());
        order_sn = deal_code + "a";
        oid1 = deal_code;
        NowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        id = id();
        writeProperties("deal_code", deal_code);
        writeProperties("order_sn", order_sn);
        writeProperties("oid1", oid1);
    }
    
    // 通过生成时间戳的方法生成oid <每调用一次, 生成的结果都不同>
    public static String oid() {
        oid1 = nowTime ; // 子订单号1
        oid2 = nowTime.substring(0,6) + (int) ((Math.random() * 9 + 1) * Math.pow(10, 7)); // 子订单号2
        writeProperties("oid1", oid1);
        writeProperties("oid2", oid2);
        return oid1;
    }

    // 生成退款单   <每调用一次, 生成的结果都不同>
    public static void refundSn(){
        refund_id = new SimpleDateFormat("MMddHHmmssSSS").format(new Date());          // 退货单号
        refund_deal_code = refund_id;
        NowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        writeProperties("refund_deal_code", refund_deal_code);
    }



    /**
     * 根据主键key读取主键的值value
     *
     * @param key 键名
     */
    public static String readProperties(String key) {
        Properties props = new Properties();
        try {
            InputStream in = new FileInputStream(filePath);
            props.load(in);
            key = props.getProperty(key);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据主键和值写入配置文件
     *
     * @param keyname
     * @param keyvalue
     */
    public static void writeProperties(String keyname, String keyvalue) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(filePath));
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            props.setProperty(keyname, keyvalue);
            props.store(fos, "Update '" + keyname + "' value");
            fos.flush();
            fos.close();
        } catch (Exception e) {
            System.err.println("属性文件更新错误");
            System.out.println(e.fillInStackTrace());
        }
    }

    /**
     * // 为第一个sku的价格乘以数量 = orderLine<通知发货的请求体> 中的actualPrice和retailPrice
     * // 为第二个sku的价格乘以数量 = orderLine<通知发货的请求体> 中的actualPrice和retailPrice
     */
    public double totalPrice1S = Integer.parseInt(goods_price1()) * Integer.parseInt(goods_number1());
    public double totalPrice2S = Integer.parseInt(goods_price2()) * Integer.parseInt(goods_number2());
    public String totalPrice1 = String.format("%.2f", totalPrice1S);
    public String totalPrice2 = String.format("%.2f", totalPrice2S);
    public double totalNumber = Integer.parseInt(goods_number1()) + Integer.parseInt(goods_number2());
    public double totalPriceS = Double.parseDouble(totalPrice1) + Double.parseDouble(totalPrice2);
    public String totalPrice = totalPriceS + "0";
    public String totalRefund = totalPriceS + "0";


    // 连接数据库,查询语句
    public static Connection conn(String db) {
        HttpClientKw kw = new HttpClientKw();
        // 常规       fuoms_mulberry    fuoms_diesel
        String ip = "rm-vy12uinfci570vvt9393.mysql.rds.aliyuncs.com";
        String name = "bqiter_14";
        String password = "omn3it@r_14";
        // SSH
        String ssh_ip = "39.98.213.178";
        String ssh_name = "bqroot";
        String ssh_password = "^zz75=QGp4^vHOYVRg5#";
        int localPort = 0;

        try {
            localPort = openSSH();
//            AutoLogger.log.info("尝试使用SSH连接" + db + "数据库");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // &autoReconnect=true&failOverReadOnly=false
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost" + ":" + localPort + "/" + db + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8", name, password);
            // 设置超时时间
            DriverManager.setLoginTimeout(10);
            AutoLogger.log.info("SSH连接<- " + db + " ->数据库成功");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return connection;
    }

    // 通过ssh连接
    public static int openSSH() throws JSchException {
        JSch jSch = new JSch();
        session = jSch.getSession("bqroot", "39.98.213.178", 22);
        session.setPassword("^zz75=QGp4^vHOYVRg5#");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
//        AutoLogger.log.info(session.getServerVersion());
        int assinged_port = session.setPortForwardingL(33007, "rm-vy12uinfci570vvt9393.mysql.rds.aliyuncs.com", 3306);
//        AutoLogger.log.info("localhost:" + assinged_port);
        return assinged_port;
    }

    // 关闭SSH数据库连接
    public static void closeSSH() {
        session.disconnect();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AutoLogger.log.info("关闭数据库成功");
    }




    /**
     * 场景一：拉取天猫单订单流程
     */
    public void insertTmOrder() throws Exception  {
        dealCode();
        // 插入一个oid订单
        try {
            conn("sys_info_2");
            String jdp_response = "{\"trade_fullinfo_get_response\":{\"trade\":{\"tid\":" + deal_code + ",\"tid_str\":" + deal_code + ",\"status\":\"WAIT_SELLER_SEND_GOODS\",\"type\":\"fixed\",\"seller_nick\":\"" + goods_name1 + "\",\"buyer_nick\":\"" + TesterName() + "\",\"created\":\"" + NowTime + "\",\"modified\":\"" + NowTime + "\",\"trade_attr\":\"\",\"adjust_fee\":\"0.00\",\"alipay_no\":0,\"alipay_point\":0,\"available_confirm_fee\":\"0\",\"buyer_alipay_no\":\"~FNbisHV+kaOGgDInjZQGfSluORk3CkzodS7xtISGXK4=~1~\",\"buyer_area\":\"上海市长宁区接口自动化\",\"buyer_cod_fee\":\"0.00\",\"buyer_email\":\"~iAD9y7COjX3Cmvd+M/ddfNs0T22VzPHqgSo1oISM1pM=~1~\",\"buyer_obtain_point_fee\":0,\"buyer_rate\":false,\"cod_fee\":\"0.00\",\"cod_status\":\"NEW_CREATED\",\"coupon_fee\":0,\"commission_fee\":\"0.00\",\"discount_fee\":\"" + SSHService.totalDiscount() + "\",\"end_time\":\"2019-11-22 14:45:19\",\"has_post_fee\":true,\"has_yfx\":false,\"is_3D\":false,\"is_brand_sale\":false,\"is_daixiao\":false,\"is_force_wlb\":false,\"is_sh_ship\":false,\"is_lgtype\":false,\"is_part_consign\":false,\"is_wt\":false,\"num\":\"" + goods_number1 + "\",\"num_iid\":\"565777068579\",\"orders\":{\"order\":[{\"adjust_fee\":\"0.00\",\"buyer_rate\":false,\"cid\":50012906,\"discount_fee\":\"" + SSHService.totalDiscount() + "\",\"divide_order_fee\":\"" + totalPrice1 + "\",\"fqg_num\":6,\"is_daixiao\":false,\"is_fqg_s_fee\":true,\"is_oversold\":false,\"num\":\"" + goods_number1 + "\",\"num_iid\":565777068579,\"oid\":" + SSHService.oid1 + ",\"oid_str\":" + SSHService.deal_code + ",\"order_from\":\"WAP,JHS,WAP\",\"outer_sku_id\":\"" + goods_barcode1() + "\",\"part_mjz_discount\":\"" + discount1() + "\",\"payment\":\"" + totalPrice1 + "\",\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i4/1718039157/O1CN012HVxExCidge6jO2_!!1718039157.jpg\",\"price\":\"" + goods_price1() + "\",\"refund_status\":\"NO_REFUND\",\"seller_rate\":false,\"seller_type\":\"B\",\"sku_id\":\"" + goods_barcode1() + "\",\"sku_properties_name\":\"颜色分类:黑色;尺码:40\",\"snapshot_url\":\"o:360570147701044029_1\",\"title\":\"" + goods_name1 + "\",\"total_fee\":\"" + totalPrice1 + "\"}]},\"pay_time\":\"" + NowTime + "\",\"payment\":" + totalPrice1 + ",\"pcc_af\":0,\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i4/1718039157/O1CN012HVxExCidge6jO2_!!1718039157.jpg\",\"point_fee\":0,\"post_fee\":\"0.00\",\"price\":\"" + goods_price1() + "\",\"promotion_details\":{\"promotion_detail\":[{\"discount_fee\":\"" + totalDiscount() + "\",\"id\":360570147701044030,\"promotion_desc\":\"2月日常收藏券:省20.00元\",\"promotion_id\":\"shopbonus-7782704691_58432120220-848491044228\",\"promotion_name\":\"2月日常收藏券\"},{\"discount_fee\":\"" + totalDiscount() + "\",\"id\":360570147701044030,\"promotion_desc\":\"聚划算:省730.00元\",\"promotion_id\":\"Tmall$saleJhs-3153345006_60022344426\",\"promotion_name\":\"聚划算\"}]},\"real_point_fee\":0,\"received_payment\":\"0.00\",\"receiver_address\":\"上海市长宁区接口自动化\",\"receiver_city\":\"上海市\",\"receiver_country\":\"\",\"receiver_district\":\"长宁区\",\"receiver_mobile\":\"13100000000\",\"receiver_name\":\"" + TesterName() + "\",\"receiver_state\":\"上海\",\"receiver_town\":\"临虹路\",\"receiver_zip\":\"000000\",\"seller_alipay_no\":\"***ll@clarks.com\",\"seller_can_rate\":false,\"seller_cod_fee\":\"0.00\",\"seller_email\":\"tmall@clarks.com\",\"seller_memo\":\"\",\"seller_flag\":0,\"seller_mobile\":\"13100000000\",\"seller_name\":\"0\",\"seller_rate\":false,\"service_tags\":{\"logistics_tag\":[{\"logistic_service_tag_list\":{\"logistic_service_tag\":[{\"service_tag\":\"origAreaId=320302\",\"service_type\":\"100\"}]},\"order_id\":\"360570147701044029\"}]},\"shipping_type\":\"express\",\"sid\":\"360570147701044029\",\"snapshot_url\":\"o:360570147701044029_1\",\"title\":\"" + goods_name1 + "\",\"total_fee\":" + totalPrice1 + ",\"trade_from\":\"WAP,JHS,WAP\"}}}";
            String sqlOrder = "INSERT INTO jdp_tb_trade(tid,status,type,seller_nick,buyer_nick,created,modified,jdp_hashcode,jdp_response,jdp_created,jdp_modified)"
                    + "VALUES ('" + deal_code + "','WAIT_SELLER_SEND_GOODS','fixed',\"" + shopName() + "\",'" + TesterName() + "','" + NowTime + "','" + NowTime + "',2037739774,'" + jdp_response + "','" + NowTime + "','" + NowTime + "');";
            // 建立查询
            stm = connection.createStatement();
            // 执行插入语句
            int a = stm.executeUpdate(sqlOrder);
//            AutoLogger.log.info("sys_info2_trade创建成功：" + a + "个订单" + " " + "" + "生成的平台订单号为：" + deal_code);
        } catch (SQLException e) {
            AutoLogger.log.info("SQL语句有误,  数据报错");
            e.printStackTrace();
        } finally {
            closeSSH();
        }

        // 获取店铺id
        try {
            conn(dataBase());
            String sqlQuery = "SELECT * FROM sys_shops where shop_name = '" + shopName() + "';";
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
                id = rs.getString("id");
                String sqlResult = "在表中【sys_cron】获取到的" + "id: " + id;
            }
            writeProperties("id", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 插入oms_tb_trade表里
        try {
            String sqlOrder = "INSERT INTO oms_tb_trade(tid,status,seller_nick,buyer_nick,pay_status,order_type,created,jdp_modified,transfer,add_time)VALUES ('" + deal_code + "','WAIT_SELLER_SEND_GOODS','" + shopName() + "','" + TesterName() + "','1','1','" + NowTime + "','" + NowTime + "',0," + timeStamp + ");";
            // 建立查询
            stm = connection.createStatement();
            // 执行插入语句
            int a = stm.executeUpdate(sqlOrder);
            AutoLogger.log.info("天猫订单创建成功：" + a + "个订单" + " " + "" + "生成的平台订单号为：" + deal_code);
        } catch (SQLException e) {
            AutoLogger.log.info("SQL语句有误,  数据报错");
            e.printStackTrace();
        }

        // 一键转单
        HttpClientKw.executeTimeTask_orderTransfer();

        // 获取店铺id
        try {
            String sql = "select * from sys_shops where shop_code='" + shopCode() + "';";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                id = rs.getString("id");
                if (id.length() != 0 || order_id.length() != 0) {
//                    AutoLogger.log.info("接口 -->> 【获取店铺id】 id:" + id);
                    writeProperties("id", id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 再一次点击一键转单

        // 查询出order_goods中的id
        try {
            String sqlQuery = "SELECT * FROM `order_goods` where tid =" + deal_code + ";";
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
                order_goods_id1 = rs.getString("id");
                orderLineId1 = rs.getString("id");
                outer_goods_id1 = rs.getString("outer_goods_id");
                goods_barcode1 = rs.getString("goods_barcode");
                String sqlResult = "获取到的" + "goods_barcode: " + goods_barcode1 + " " + "LineId：" + " " + order_goods_id1 + " "
                        + "outer_goods_id:" + outer_goods_id1;
//                AutoLogger.log.info(sqlResult);
                writeProperties("order_goods_id1", order_goods_id1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // 一键审核前,在order_info里获取order_id和sys_shops中的id
        try {
            String sql = "select * from order_info where deal_code = " + deal_code + ";";
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sql);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
                order_id = rs.getString("order_id");
                outer_order_id = rs.getString("outer_order_id");
                String sqlResult = "在表中【order_info】获取到的" + "order_id: " + order_id + " " + "outer_order_id: " + outer_order_id;
                writeProperties("order_id", order_id);
                AutoLogger.log.info(sqlResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 检查是否已转单至OMS的order_info表
        try {
            String sql = "select * from order_info where deal_code = " + deal_code + ";";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            String dealCode = null;
            while (rs.next()) {
                dealCode = rs.getString("deal_code");
                if (dealCode.length() != 0) {
                    AutoLogger.log.info("接口 -->> 【OMS系统一键转单成功】 tid:" + dealCode);
                } else {
                    AutoLogger.log.info("接口 -->> 【OMS系统一键转单失败】 tid:" + dealCode);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        HttpClientKw.clickButtonCheck();
        closeSSH();
        AutoLogger.log.info("----------------------------------------------------------------------------------------------------------");
    }

    /**
     * 场景二：拉取天猫多订单流程
     */
    public void insertTmOrderS() throws Exception {
        // 插入多个oid订单
        dealCode();
        oid();
        try {
            conn("sys_info_2");
            String jdp_response = "{\"trade_fullinfo_get_response\":{\"trade\":{\"tid\":" + deal_code + ",\"tid_str\":" + deal_code + ",\"status\":\"WAIT_SELLER_SEND_GOODS\",\"type\":\"fixed\",\"seller_nick\":\"" + goods_name1 + "\",\"buyer_nick\":\"" + TesterName() + "\",\"created\":\"" + NowTime + "\",\"modified\":\"" + NowTime + "\",\"trade_attr\":\"\",\"adjust_fee\":\"0.00\",\"alipay_no\":0,\"alipay_point\":0,\"available_confirm_fee\":\"0\",\"buyer_alipay_no\":\"~FNbisHV+kaOGgDInjZQGfSluORk3CkzodS7xtISGXK4=~1~\",\"buyer_area\":\"上海市长宁区接口自动化\",\"buyer_cod_fee\":\"0.00\",\"buyer_email\":\"~iAD9y7COjX3Cmvd+M/ddfNs0T22VzPHqgSo1oISM1pM=~1~\",\"buyer_obtain_point_fee\":0,\"buyer_rate\":false,\"cod_fee\":\"0.00\",\"cod_status\":\"NEW_CREATED\",\"coupon_fee\":0,\"commission_fee\":\"0.00\",\"discount_fee\":\"0.00\",\"end_time\":\"" + NowTime + "\",\"has_post_fee\":true,\"has_yfx\":false,\"is_3D\":false,\"is_brand_sale\":false,\"is_daixiao\":false,\"is_force_wlb\":false,\"is_sh_ship\":false,\"is_lgtype\":false,\"is_part_consign\":false,\"is_wt\":false,\"num\":\"" + totalNumber + "\",\"num_iid\":\"565777068579\",\"orders\":{\"order\":[{\"adjust_fee\":\"0.00\",\"buyer_rate\":false,\"cid\":50012906,\"discount_fee\":\"0.00\",\"divide_order_fee\":\"" + totalPrice1 + "\",\"fqg_num\":6,\"is_daixiao\":false,\"is_fqg_s_fee\":true,\"is_oversold\":false,\"num\":\"" + goods_number1() + "\",\"num_iid\":565777068579,\"oid\":" + oid1 + ",\"oid_str\":" + oid1 + ",\"order_from\":\"WAP,JHS,WAP\",\"outer_sku_id\":\"" + goods_barcode1() + "\",\"part_mjz_discount\":\"" + discount1() + "\",\"payment\":\"" + totalPrice1 + "\",\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i4/1718039157/O1CN012HVxExCidge6jO2_!!1718039157.jpg\",\"price\":\"" + goods_price1() + "\",\"refund_status\":\"NO_REFUND\",\"seller_rate\":false,\"seller_type\":\"B\",\"sku_id\":\"" + goods_barcode1() + "\",\"sku_properties_name\":\"颜色分类:黑色;尺码:40\",\"snapshot_url\":\"o:360570147701044029_1\",\"title\":\"" + goods_name1 + "\",\"total_fee\":\"" + totalPrice1 + "\"},{\"adjust_fee\":\"0.00\",\"buyer_rate\":false,\"cid\":50012906,\"discount_fee\":\"" + discount1() + "\",\"divide_order_fee\":\"" + totalPrice2 + "\",\"fqg_num\":6,\"is_daixiao\":false,\"is_fqg_s_fee\":true,\"is_oversold\":false,\"num\":\"" + goods_number2() + "\",\"num_iid\":565777068579,\"oid\":" + oid2 + ",\"oid_str\":" + oid2 + ",\"order_from\":\"WAP,JHS,WAP\",\"outer_sku_id\":\"" + goods_barcode2() + "\",\"part_mjz_discount\":\"" + discount2() + "\",\"payment\":\"" + goods_price2() + "\",\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i4/1718039157/O1CN012HVxExCidge6jO2_!!1718039157.jpg\",\"price\":0,\"refund_status\":\"NO_REFUND\",\"seller_rate\":false,\"seller_type\":\"B\",\"sku_id\":0,\"sku_properties_name\":\"颜色分类:黑色;尺码:40\",\"snapshot_url\":\"o:360570147701044029_1\",\"title\":\"" + goods_name2 + "\",\"total_fee\":\"" + totalPrice2 + "\"}]},\"pay_time\":\"" + NowTime + "\",\"payment\":" + totalPrice + ",\"pcc_af\":0,\"pic_path\":\"https://img.alicdn.com/bao/uploaded/i4/1718039157/O1CN012HVxExCidge6jO2_!!1718039157.jpg\",\"point_fee\":0,\"post_fee\":\"0.00\",\"price\":\"" + totalPrice + "\",\"promotion_details\":{\"promotion_detail\":[{\"discount_fee\":\"" + totalDiscount() + "\",\"id\":360570147701044030,\"promotion_desc\":\"2月日常收藏券:省20.00元\",\"promotion_id\":\"shopbonus-7782704691_58432120220-848491044228\",\"promotion_name\":\"2月日常收藏券\"},{\"discount_fee\":\"" + totalDiscount() + "\",\"id\":360570147701044030,\"promotion_desc\":\"聚划算:省730.00元\",\"promotion_id\":\"Tmall$saleJhs-3153345006_60022344426\",\"promotion_name\":\"聚划算\"}]},\"real_point_fee\":0,\"received_payment\":\"0.00\",\"receiver_address\":\"上海市长宁区接口自动化\",\"receiver_city\":\"上海市\",\"receiver_country\":\"\",\"receiver_district\":\"长宁区\",\"receiver_mobile\":\"13100000000\",\"receiver_name\":\"" + TesterName() + "\",\"receiver_state\":\"上海\",\"receiver_town\":\"临虹路\",\"receiver_zip\":\"000000\",\"seller_alipay_no\":\"***ll@clarks.com\",\"seller_can_rate\":false,\"seller_cod_fee\":\"0.00\",\"seller_email\":\"tmall@clarks.com\",\"seller_memo\":\"\",\"seller_flag\":0,\"seller_mobile\":\"13100000000\",\"seller_name\":\"0\",\"seller_rate\":false,\"service_tags\":{\"logistics_tag\":[{\"logistic_service_tag_list\":{\"logistic_service_tag\":[{\"service_tag\":\"origAreaId=320302\",\"service_type\":\"100\"}]},\"order_id\":\"360570147701044029\"}]},\"shipping_type\":\"express\",\"sid\":\"360570147701044029\",\"snapshot_url\":\"o:360570147701044029_1\",\"title\":\"" + goods_name1 + "\",\"total_fee\":" + totalPrice + ",\"trade_from\":\"WAP,JHS,WAP\"}}}";
            String sqlOrder = "INSERT INTO jdp_tb_trade(tid,status,type,seller_nick,buyer_nick,created,modified,jdp_hashcode,jdp_response,jdp_created,jdp_modified)"
                    + "VALUES ('" + deal_code + "','WAIT_SELLER_SEND_GOODS','fixed',\"" + shopName() + "\",'" + TesterName() + "','" + NowTime + "','" + NowTime + "',2037739774,'" + jdp_response + "','" + NowTime + "','" + NowTime + "');";
            System.out.println(jdp_response);
            // 建立查询
            stm = connection.createStatement();
            // 执行插入语句
            int a = stm.executeUpdate(sqlOrder);
//            AutoLogger.log.info("天猫订单创建成功：" + a + "个订单多个oid" + " " + "" + "生成的平台订单号为：" + deal_code);
        } catch (SQLException e) {
            AutoLogger.log.info("SQL语句有误,  数据报错");
            e.printStackTrace();
        } finally {
            closeSSH();
        }

        // 插入oms_tb_trade表里
        try {
        	conn(dataBase());
            String sqlOrder = "INSERT INTO oms_tb_trade(tid,status,seller_nick,buyer_nick,pay_status,order_type,created,jdp_modified,transfer,add_time)VALUES ('" + deal_code + "','WAIT_SELLER_SEND_GOODS','" + shopName() + "','" + TesterName() + "','1','1','" + NowTime + "','" + NowTime + "',0," + timeStamp + ");";
            // 建立查询
            stm = connection.createStatement();
            // 执行插入语句
            int a = stm.executeUpdate(sqlOrder);
//            AutoLogger.log.info("天猫订单创建成功：" + a + "个订单" + " " + "" + "生成的平台订单号为：" + deal_code);
        } catch (SQLException e) {
            AutoLogger.log.info("SQL语句有误,  数据报错");
            e.printStackTrace();
        }

        // 一键转单  在获得"cron_id"的基础上进行开启定时任务"一键转单"
        HttpClientKw.executeTimeTask_orderTransfer();

        // 检查是否已转单至OMS得order_info表
        try {
            String sql = "select * from order_info where deal_code = " + deal_code + ";";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String deal_code = rs.getString("deal_code");
                if (deal_code.length() != 0) {
                    AutoLogger.log.info("接口 -->> 【OMS系统一键转单成功】 tid:" + deal_code);
                } else {
                    AutoLogger.log.info("接口 -->> 【OMS系统一键转单失败】 deal_code:" + deal_code);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 获取店铺id
        try {
            String sql = "select * from sys_shops where shop_code='" + shopCode() + "';";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                id = rs.getString("id");
                if (id.length() != 0) {
//                    AutoLogger.log.info("接口 -->> 【获取店铺id】 id:" + id);
                    writeProperties("id", id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 再一次点击一键转单

        // 查询出order_goods中的goods_barcode/id/
        try {
            String sqlQuery = "SELECT * FROM `order_goods` where tid =" + deal_code + " limit 0,1;";
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
                order_goods_id1 = rs.getString("id");
                orderLineId1 = rs.getString("id");
                outer_goods_id1 = rs.getString("outer_goods_id");
                goods_barcode1 = rs.getString("goods_barcode");
                String sqlResult = "获取到的" + "goods_barcode: " + goods_barcode1 + " " + "LineId1：" + " " + orderLineId1 + " "
                        + "outer_goods_id1:" + outer_goods_id1 + " " + "outer_order_id: " + outer_order_id;
//                AutoLogger.log.info(sqlResult);
                writeProperties("order_goods_id1", order_goods_id1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 查询出order_goods中的goods_barcode/id/
        try {
            String sqlQuery = "SELECT * FROM `order_goods` where tid =" + deal_code + " limit 1,2;";
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
                order_goods_id2 = rs.getString("id");
                orderLineId2 = rs.getString("id");
                outer_goods_id2 = rs.getString("outer_goods_id");
                goods_barcode2 = rs.getString("goods_barcode");
                String sqlResult = "获取到的" + "goods_barcode2: " + goods_barcode2 + " " + "LineId：" + " " + order_goods_id2 + " "
                        + "outer_goods_id2:" + outer_goods_id2;
//                AutoLogger.log.info(sqlResult);
                writeProperties("order_goods_id2", order_goods_id2);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 获取order_info中的order_id
        try {
            String sql = "select * from order_info where deal_code= " + deal_code + ";";
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sql);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
                order_id = rs.getString("order_id");
                String sqlResult = "在表中【order_info】获取到的" + "id: " + order_id;
//                AutoLogger.log.info(sqlResult);
                writeProperties("order_id", order_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 一键审核前,在order_info里获取order_id和sys_shops中的id
        HttpClientKw.clickButtonCheck();
        closeSSH();
        AutoLogger.log.info("------------------------------------------------------------------------------------------------------------------------------------------------");
    }

//
//
    /**
     * 场景三：拉取天猫退款单订单流程之【仅退款】   生产退款单的前提下保证oms已有此关联的订单
     */
    public void insertTmRefundOnly() throws Exception {
        // 创建退款单
        try {
            deal_code();
            refundSn();
            conn("sys_info_2");
            String jdp_respionse = "{\"refund_get_response\":{\"refund\":{\"refund_id\":\"" + refund_id + "\",\"status\":\"WAIT_SELLER_AGREE\",\"seller_nick\":\"" + shopName() + "\",\"buyer_nick\":\""+TesterName()+"\",\"tid\":" + deal_code + ",\"oid\":" + oid1() + ",\"created\":\"" + NowTime + "\",\"modified\":\"" + NowTime + "\",\"advance_status\":0,\"alipay_no\":\"2019031422001101000544245835\",\"attribute\":\";enfunddetail:1;ee_trace_id:0b0fa8ec15529958004425408e6491;bizCode:tmall.general.refund;lastOrder:0;disputeRequest:1;newRefund:rp2;tod:259200000;leavesCat:50012010;opRole:buyer;7d:1;apply_init_refund_fee:490000;itemBuyAmount:1;apply_text_id:500768;b2c:1;userCredit:5;seller_batch:true;restartForXiaoer:0;rootCat:50006842;tos:1;ol_tf:490000;sku:8806195806760|颜色分类#3B干邑色MWZ9SPA72CO001;sgr:1;bgmtc:2019-03-14 10#3B20#3B25;payMode:alipay;workflowName:refund;shop_name:MCM官方旗舰店;ttid:h5;old_reason_id:15;abnormal_dispute_status:0;seller_audit:0;itemPrice:490000;isVirtual:0;EXmrf:490000;\",\"cs_status\":1,\"desc\":\"我是"+TesterName()+",正在测试\",\"good_status\":\"BUYER_NOT_RECEIVED\",\"has_good_return\":false,\"num\":" + goods_number1() + ",\"num_iid\":585560583902,\"operation_contraint\":\"null\",\"order_status\":\"WAIT_SELLER_SEND_GOODS\",\"payment\":\"0.00\",\"price\":\"" + totalPrice1 + "\",\"reason\":\"\",\"refund_fee\":\"" + refund_fee1() + "\",\"refund_phase\":\"onsale\",\"refund_remind_timeout\":{\"exist_timeout\":true,\"remind_type\":3,\"timeout\":\"" + NowTime + "\"},\"refund_version\":1552995800503,\"sku\":\"" + goods_barcode1() + "\",\"title\":\"" + goods_name1 + "\",\"total_fee\":\""+totalPrice1+"\"}}}";
            String sql = "INSERT INTO `jdp_tb_refund`(`refund_id`, `seller_nick`, `buyer_nick`, `status`, `created`, `tid`, `oid`, `modified`, `jdp_hashcode`, `jdp_response`, `jdp_created`, `jdp_modified`) VALUES " +
                    "(" + refund_id + ", '" + shopName() + "', '"+TesterName()+"', 'WAIT_SELLER_AGREE', '" + NowTime + "', " + deal_code + ", " + deal_code + ", '" + NowTime + "', '14185203', '" + jdp_respionse + "', '" + NowTime + "', '" + NowTime + "');";
            stm = connection.createStatement();
            int a = stm.executeUpdate(sql);
            AutoLogger.log.info(sql);
            AutoLogger.log.info("天猫退款单创建成功：" + a + "个订单单个oid" + " " + " " + "生成的refund_deal_code：" + refund_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeSSH();
        }

        // 获取【拉取仅退款】 的cron_id
        try {
            conn(dataBase());
            String sqlQuery = "SELECT * FROM `sys_cron` where code = 'tmall:transfer_tmall_refund'";   // 拉取仅退款定时任务
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
                cron_id = rs.getString("id");
                String sqlResult = "在表中【sys_cron】获取到的" + "id: " + cron_id;
            }
            writeProperties("cron_id",cron_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //  执行定时任务一键拉取 【仅退款单】
        HttpClientKw.executeTimeTask_onlyRefund();
        
        // 查询order_refund表中是否已存在
        try {
            String sqlQuery = "SELECT * FROM `order_refund` where refund_deal_code = '"+refund_deal_code+"'";   // 拉取仅退款定时任务
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
            	refund_sn = rs.getString("refund_sn");
            	if(refund_sn != null) {
                    String sqlResult = "在表中【order_refund】获取到的" + "refund_sn: " + refund_sn;
                    AutoLogger.log.info(sqlResult);
                    writeProperties("refund_sn",refund_sn);
                    writeProperties("refund_sn1",refund_sn);

            	}
            	else {
                    String sqlResult = "在表中【order_refund】获取到的" + "refund_sn: " + refund_sn;
                    AutoLogger.log.info(sqlResult);
                    writeProperties("refund_sn",refund_sn);
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

//        // 获取 拦截退款单 的cron_id
//        try {
//            String sqlQuery = "SELECT * FROM `sys_cron` where code = 'tmall:transfer_tmall_order_intercept'";   // 拉取仅退款定时任务
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                cron_id = rs.getString("id");
//                String sqlResult = "在表中【sys_cron】获取到的" + "id: " + cron_id;
//            }
//            writeProperties("cron_id",cron_id);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            closeSSH();
//        }
//
//        HttpClientKw.executeTimeTask_intercept();

//        // 获取退款单号
//        try {
//            String sqlQuery = "SELECT * FROM `order_return` where deal_code =" + deal_code + ";";
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                refund_sn = rs.getString("refund_sn");
//                String refund_type = rs.getString("refund_type");
//                String sqlResult = "获取到的" + "refun_sn: " + refund_sn + " " + "refund_type：" + " " + refund_type + " "
//                        + "oid:" + oid;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 检查是否已转单至OMS得order_info表
//        try {
//            conn(dataBase());
//            String sql = "select * from order_info where deal_code = " + deal_code+ ";";
//            stm = connection.createStatement();
//            rs = stm.executeQuery(sql);
//            while (rs.next()) {
//                String deal_code = rs.getString("deal_code");
//                if (deal_code.length() != 0) {
//                    AutoLogger.log.info("接口 -->> 【OMS系统一键转单成功】 tid:" + deal_code);
//                } else {
//                    AutoLogger.log.info("接口 -->> 【OMS系统一键转单失败】 deal_code:" + deal_code);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 查询出order_goods中的goods_barcode/id/
//        try {
//            String sqlQuery = "SELECT * FROM `order_goods` where tid =" + deal_code + ";";
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                order_goods_id1 = rs.getString("id");
//                orderLineId1 = rs.getString("id");
//                outer_goods_id1 = rs.getString("outer_goods_id");
//                goods_barcode1 = rs.getString("goods_barcode");
//                String sqlResult = "获取到的" + "goods_barcode: " + goods_barcode1 + " " + "LineId：" + " " + order_goods_id1 + " "
//                        + "outer_goods_id:" + outer_goods_id1 + " " + "outer_order_id: " + outer_order_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 获取店铺id
//        try {
//            String sql = "select * from sys_shops where shop_code='" + shopCode() + "';";
//            Statement stm = connection.createStatement();
//            ResultSet rs = stm.executeQuery(sql);
//            while (rs.next()) {
//                id = rs.getString("id");
//                if (id.length() != 0) {
//                    AutoLogger.log.info("接口 -->> 【获取店铺id】 id:" + id);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 获取order_info中的order_id
//        try {
//            String sql = "select * from order_info where deal_code= " + deal_code + ";";
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sql);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                order_id = rs.getString("order_id");
//                String sqlResult = "在表中【order_info】获取到的" + "order_id: " + order_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 一键审核前,在order_info里获取order_id和sys_shops中的id
//        HttpClientKw.clickButtonCheck();
        closeSSH();
    }

    /**
     * 场景三：拉取天猫退款单订单流程之【退货退款】   生产退款单的前提下保证oms已有此关联的订单
     */
    public void insertTmReturnRefund() throws Exception {
        // 创建退款单
        try {
            deal_code();
            refundSn();
            conn("sys_info_2");
            String jdp_respionse = "{\"refund_get_response\":{\"refund\":{\"refund_id\":\"" + refund_id + "\",\"status\":\"WAIT_BUYER_RETURN_GOODS\",\"seller_nick\":\"" + shopName() + "\",\"buyer_nick\":\""+TesterName()+"\",\"tid\":" + deal_code + ",\"oid\":" + oid1() + ",\"created\":\"" + NowTime + "\",\"modified\":\"" + NowTime + "\",\"advance_status\":0,\"alipay_no\":\"2019031422001101000544245835\",\"attribute\":\";enfunddetail:1;ee_trace_id:0b0fa8ec15529958004425408e6491;bizCode:tmall.general.refund;lastOrder:0;disputeRequest:1;newRefund:rp2;tod:259200000;leavesCat:50012010;opRole:buyer;7d:1;apply_init_refund_fee:490000;itemBuyAmount:1;apply_text_id:500768;b2c:1;userCredit:5;seller_batch:true;restartForXiaoer:0;rootCat:50006842;tos:1;ol_tf:490000;sku:8806195806760|颜色分类#3B干邑色MWZ9SPA72CO001;sgr:1;bgmtc:2019-03-14 10#3B20#3B25;payMode:alipay;workflowName:refund;shop_name:MCM官方旗舰店;ttid:h5;old_reason_id:15;abnormal_dispute_status:0;seller_audit:0;itemPrice:490000;isVirtual:0;EXmrf:490000;\",\"cs_status\":1,\"desc\":\"我是"+TesterName()+",正在测试\",\"good_status\":\"BUYER_NOT_RECEIVED\",\"has_good_return\":true,\"num\":" + goods_number1() + ",\"num_iid\":585560583902,\"operation_contraint\":\"null\",\"order_status\":\"WAIT_SELLER_SEND_GOODS\",\"payment\":\"0.00\",\"price\":\"" + totalPrice1 + "\",\"reason\":\"七天无理由\",\"refund_fee\":\"" + refund_fee1() + "\",\"refund_phase\":\"onsale\",\"refund_remind_timeout\":{\"exist_timeout\":true,\"remind_type\":1,\"timeout\":\"" + NowTime + "\"},\"refund_version\":11111111,\"sku\":\"" + goods_barcode1() + "\",\"title\":\"" + goods_name1 + "\",\"total_fee\":\""+totalPrice1+"\"}}}";
            String sql = "INSERT INTO `jdp_tb_refund`(`refund_id`, `seller_nick`, `buyer_nick`, `status`, `created`, `tid`, `oid`, `modified`, `jdp_hashcode`, `jdp_response`, `jdp_created`, `jdp_modified`) VALUES " +
                    "(" + refund_id + ", '" + shopName() + "', '"+TesterName()+"', 'WAIT_BUYER_RETURN_GOODS', '" + NowTime + "', " + deal_code + ", " + oid1() + ", '" + NowTime + "', '17644905', '" + jdp_respionse + "', '" + NowTime + "', '" + NowTime + "');";
            stm = connection.createStatement();
            int a = stm.executeUpdate(sql);
//            AutoLogger.log.info(sql);
            AutoLogger.log.info("天猫退款单创建成功：" + a + "个订单单个oid" + " " + " " + "生成的refund_deal_code：" + refund_id);
        } catch (SQLException e) {
            AutoLogger.log.info("天猫退款单创建失败 ->> 生成的refund_deal_code：" + refund_id);
            e.printStackTrace();
        }finally {
            closeSSH();
        }

        // 获取【退货退款】 的cron_id
        try {
            conn(dataBase());
            String sqlQuery = "SELECT * FROM `sys_cron` where code = 'tmall:transfer_tmall_return'";   // 拉取退货退款定时任务
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
                cron_id = rs.getString("id");
                String sqlResult = "在表中【sys_cron】获取到的" + "id: " + cron_id;
            }
            writeProperties("cron_id",cron_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //  执行定时任务一键拉取 【退货退款单】
        HttpClientKw.executeTimeTask_ReturnRefund();

        // 查询order_refund表中是否已存在
        try {
            String sqlQuery = "SELECT * FROM `order_refund` where refund_deal_code = '"+refund_deal_code+"'";   // 拉取仅退款定时任务
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
            	refund_sn = rs.getString("refund_sn");
            }
            String sqlResult = "在表中【order_refund】获取到的" + "refund_sn: " + refund_sn;
            AutoLogger.log.info(sqlResult);
            writeProperties("refund_sn",refund_sn);
            writeProperties("refund_sn1",refund_sn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // 查询order_return表中的return_id
        try {
            String sqlQuery = "SELECT * FROM `order_return` where deal_code = '"+deal_code+"'";   // 拉取仅退款定时任务
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
            	return_id = rs.getString("return_id");
                return_sn = rs.getString("return_sn");
            }
            String sqlResult = "在表中【order_return】获取到的" + "return_id: " + return_id;
            String sqlResult2 = "在表中【order_return】获取到的" + "return_sn: " + return_sn;
            AutoLogger.log.info(sqlResult);
            AutoLogger.log.info(sqlResult2);
            writeProperties("return_id",return_id);
            writeProperties("return_sn",return_sn);
            writeProperties("return_sn1",return_sn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // 查询order_return_goods表中的第一个行的id                                                  like '%" + return_deal_code + "%'limit 0,1;
//        try {
//            String sqlQuery = "SELECT * FROM `order_return_goods` where order_sn = '"+ order_sn() +"' limit 0,1;";
//            AutoLogger.log.info(sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//            	order_return_goods_id = rs.getString("id");
//            }
//            String sqlResult = "在表中【order_return_goods1】获取到的" + "id: " + order_return_goods_id;
//            AutoLogger.log.info(sqlResult);
//            writeProperties("order_return_goods_id",order_return_goods_id);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            String sql = "select * from order_return_goods where oid = " + oid1() + ";";
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                order_return_goods_id = rs.getString("id");
            }
            AutoLogger.log.info("在order_return_goods查询到的第一行的Id：" + order_return_goods_id);
            writeProperties("order_return_goods_id",order_return_goods_id);
        } catch (SQLException e) {
            e.printStackTrace();
            AutoLogger.log.info("SQL语句有误,  数据报错");
        }

//        // 查询order_return_goods表中第二行的id                                                  like '%" + return_deal_code + "%'limit 1,2;
//        try { 
//            String sqlQuery = "SELECT * FROM `order_return_goods` where order_sn = '"+ order_sn() +"' limit 1,2;";  
//            AutoLogger.log.info(sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//            	order_return_goods_id2 = rs.getString("id");
//            }
//            String sqlResult = "在表中【order_return_goods2】获取到的" + "id: " + order_return_goods_id2;
//            if(order_return_goods_id2 != null) {
//                AutoLogger.log.info(sqlResult);
//                writeProperties("order_return_goods_id2",order_return_goods_id2);
//            }else {
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        closeSSH();
    }

    /** 
     * 【此方法暂未使用】场景三：拉取天猫退款单订单流程之【退货退款】   生产退款单的前提下保证oms已有此关联的订单  refund_id为唯一值！！！
     */
    public void insertTmReturnRefundS() throws Exception {
        // 创建退款单
        try {
            deal_code();
            refundSn();
            conn("sys_info_2");
            String jdp_respionse = "{\"refund_get_response\":{\"refund\":{\"refund_id\":\"" + refund_id + "\",\"status\":\"WAIT_BUYER_RETURN_GOODS\",\"seller_nick\":\"" + shopName() + "\",\"buyer_nick\":\""+TesterName()+"\",\"tid\":" + deal_code + ",\"oid\":" + oid2() + ",\"created\":\"" + NowTime + "\",\"modified\":\"" + NowTime + "\",\"advance_status\":0,\"alipay_no\":\"2019031422001101000544245835\",\"attribute\":\";enfunddetail:1;ee_trace_id:0b0fa8ec15529958004425408e6491;bizCode:tmall.general.refund;lastOrder:0;disputeRequest:1;newRefund:rp2;tod:259200000;leavesCat:50012010;opRole:buyer;7d:1;apply_init_refund_fee:490000;itemBuyAmount:1;apply_text_id:500768;b2c:1;userCredit:5;seller_batch:true;restartForXiaoer:0;rootCat:50006842;tos:1;ol_tf:490000;sku:8806195806760|颜色分类#3B干邑色MWZ9SPA72CO001;sgr:1;bgmtc:2019-03-14 10#3B20#3B25;payMode:alipay;workflowName:refund;shop_name:MCM官方旗舰店;ttid:h5;old_reason_id:15;abnormal_dispute_status:0;seller_audit:0;itemPrice:490000;isVirtual:0;EXmrf:490000;\",\"cs_status\":1,\"desc\":\""+TesterName()+",正在测试\",\"good_status\":\"BUYER_NOT_RECEIVED\",\"has_good_return\":true,\"num\":" + goods_number2() + ",\"num_iid\":585560583902,\"operation_contraint\":\"null\",\"order_status\":\"WAIT_SELLER_SEND_GOODS\",\"payment\":\"0.00\",\"price\":\"" + totalPrice2 + "\",\"reason\":\""+returnReason()+"\",\"refund_fee\":\"" + refund_fee2() + "\",\"refund_phase\":\"onsale\",\"refund_remind_timeout\":{\"exist_timeout\":true,\"remind_type\":1,\"timeout\":\"" + NowTime + "\"},\"refund_version\":11111111,\"sku\":\"" + goods_barcode2() + "\",\"title\":\"" + goods_name2 + "\",\"total_fee\":\""+totalPrice2+"\"}}}";
            String sql = "INSERT INTO `jdp_tb_refund`(`refund_id`, `seller_nick`, `buyer_nick`, `status`, `created`, `tid`, `oid`, `modified`, `jdp_hashcode`, `jdp_response`, `jdp_created`, `jdp_modified`) VALUES " +
                    "(" + refund_id + ", '" + shopName() + "', '"+TesterName()+"', 'WAIT_BUYER_RETURN_GOODS', '" + NowTime + "', " + deal_code + ", " + oid2() + ", '" + NowTime + "', '17644905', '" + jdp_respionse + "', '" + NowTime + "', '" + NowTime + "');";
            stm = connection.createStatement();
            int a = stm.executeUpdate(sql);
            AutoLogger.log.info(sql);
            AutoLogger.log.info("天猫退款单创建成功：" + a + "个订单单个oid" + " " + " " + "生成的refund_deal_code：" + refund_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeSSH();
        }

        // 获取【退货退款】 的cron_id
        try {
            conn(dataBase());
            String sqlQuery = "SELECT * FROM `sys_cron` where code = 'tmall:transfer_tmall_return'";   // 拉取退货退款定时任务
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
                cron_id = rs.getString("id");
                String sqlResult = "在表中【sys_cron】获取到的" + "id: " + cron_id;
            }
            writeProperties("cron_id",cron_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //  执行定时任务一键拉取 【退货退款单】
        HttpClientKw.executeTimeTask_ReturnRefund();

        // 查询order_refund表中是否已存在
        try {
            String sqlQuery = "SELECT * FROM `order_refund` where refund_deal_code = '"+refund_deal_code+"'";   // 拉取仅退款定时任务
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
            	refund_sn = rs.getString("refund_sn");
            }
            String sqlResult = "在表中【order_refund】获取到的" + "refund_sn: " + refund_sn;
            AutoLogger.log.info(sqlResult);
            writeProperties("refund_sn",refund_sn);
            writeProperties("refund_sn2",refund_sn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // 查询order_return表中的return_id
        try {
            String sqlQuery = "SELECT * FROM `order_return` where deal_code = '"+deal_code+"'";   // 拉取仅退款定时任务
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
            	return_id = rs.getString("return_id");
                return_sn = rs.getString("return_sn");
            }
            String sqlResult = "在表中【order_return】获取到的" + "return_id: " + return_id;
            String sqlResult2 = "在表中【order_return】获取到的" + "return_sn: " + return_sn;
            AutoLogger.log.info(sqlResult);
            AutoLogger.log.info(sqlResult2);
            writeProperties("return_id",return_id);
            writeProperties("return_sn",return_sn);
            writeProperties("return_sn2",return_sn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // 查询order_return_goods表中的第一个行的id                                                  like '%" + return_deal_code + "%'limit 0,1;
        try {
            String sql = "select * from order_return_goods where oid = " + oid2() + ";";
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                order_return_goods_id2 = rs.getString("id");
            }
            if(order_return_goods_id2 != null) {
                writeProperties("order_return_goods_id2",order_return_goods_id2);
                AutoLogger.log.info("在order_return_goods查询到的Id：" + order_return_goods_id2);
            }else {
                AutoLogger.log.info(sql);
                AutoLogger.log.info("在order_return_goods查询到的Id：" + order_return_goods_id2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AutoLogger.log.info("SQL语句有误,  数据报错");
        }
//        // 查询order_return_goods表中第二行的id                                                  like '%" + return_deal_code + "%'limit 1,2;
//        try { 
//            String sqlQuery = "SELECT * FROM `order_return_goods` where order_sn = '"+ order_sn() +"' limit 1,2;";  
//            AutoLogger.log.info(sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//            	order_return_goods_id2 = rs.getString("id");
//            }
//            String sqlResult = "在表中【order_return_goods2】获取到的" + "id: " + order_return_goods_id2;
//            if(order_return_goods_id2 != null) {
//                AutoLogger.log.info(sqlResult);
//                writeProperties("order_return_goods_id2",order_return_goods_id2);
//            }else {
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        closeSSH();
    }
    
    
    
//    /**
//     * 场景一：新建单订单   一个tid, 一个oid 如果config.properties的配置文件中的goods_number是2,则该场景为单订单多商品
//     */
//    public void insertOrder() throws SQLException {
//
//        /**
//         *  插入Order_info的SQL语句      注意：插入order_info中的这些价格字段的值均是实付金额
//         * goods_amount: 商品价格
//         * money_paid:   实付金额
//         * order_amount：应付款
//         * total_amount: 应付总额
//         */
//        try {
//            String sqlOrder = "INSERT INTO order_info(order_sn,deal_code,sd_id,order_status,shipping_status,pay_status,back_status,order_addtime,consignee,province,city,district,address,full_address,mobile,postscript,shipping_id,goods_amount,money_paid,order_amount,total_amount,confirm_time,add_time,pay_time,goods_count,user_nick,is_hand,shipping_store,pick_type,is_send,push_time,pay_way,update_time,omnichannel_param,outer_order_id) "
//                    + "VALUES (" + "'" + order_sn + "'" + "," + deal_code + "," + shopID() + ",1,3,1,0," + timeOld
//                    + ",'~raMMEgyXZpFvhhWGrW+10w==~-1~',25,321,2703,'上海','上海 上海市 长宁区 上海市长宁区接口自动化','13100000000','龙张立',17," + totalPrice1 + "," + totalPrice1 + "," + totalPrice1 + "," + totalPrice1 + ",'"
//                    + NowTime + "','" + NowTime + "','" + NowTime + "'," + goods_number + ",'~raMMEgyXZpFvhhWGrW+10w==~-1~',1,1,1,1,'" + NowTime
//                    + "',1,'" + NowTime + "',0,\"" + outer_order_id + "\");";
//
//            AutoLogger.log.info("正在向数据库插入新order订单 "); // + ": " + sqlOrder
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlOrder);
//            AutoLogger.log.info("新订单创建成功：" + a + "个订单" + " " + "" + "生成的订单号为：" + order_sn);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        /**
//         * 并获取到 order_id/id = orderLineId1
//         */
//        try {
//            String sqlQuery = "SELECT * FROM `order_info` where deal_code(=" + deal_code( + ";";
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                order_id = rs.getString("order_id");
//                outer_order_id = rs.getString("outer_order_id");
//                String sqlResult = "获取到的" + "order_id: " + order_id + " " + "order_sn：" + order_sn + " " + "deal_code(："
//                        + deal_code( + " " + "outer_order_id：" + outer_order_id;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//        /**
//         * 插入Order_Goods的SQL语句
//         * market_price: 市场价 =  单价
//         * goods_price:  商品单价
//         * money_paid：  实付金额 = 单价 * goods_number
//         */
//        String sqlGoods = "insert order_goods (order_id,tid,oid,goods_barcode,market_price,goods_number,goods_price,money_paid,updated_at,outer_goods_id) values ("
//                + order_id + "," + deal_code( + "," + oid + ",'" + goods_barcode1 + "'," + goods_price1 + "," + goods_number + "," + goods_price1 + "," + totalPrice1 + "," + nowTime
//                + ",\"" + outer_goods_id + "\");";
////		AutoLogger.log.info(sqlGoods);
//        // 建立statement查询
//        try {
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods); // 执行sql语句
//            AutoLogger.log.info("商品数据插入成功： " + a + "个订单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 查询出order_goods中的goods_barcode/id/
//        try {
//            String sqlQuery = "SELECT * FROM `order_goods` where tid =" + deal_code( + ";";
////            AutoLogger.log.info("输入查询goods_barcode和id的语句：" + sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                order_goods_id = rs.getString("id");
//                orderLineId1 = rs.getString("id");
//                outer_goods_id = rs.getString("outer_goods_id");
//                goods_barcode1 = rs.getString("goods_barcode");
//                String sqlResult = "获取到的" + "goods_barcode: " + goods_barcode1 + " " + "LineId：" + " " + order_goods_id + " "
//                        + "outer_goods_id:" + outer_goods_id + " " + "outer_order_id: " + outer_order_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 获取推送cron_id
//        try {
//            String sqlQuery = "SELECT * FROM `sys_cron` where code LIKE '%sales_account_data%'";
////            AutoLogger.log.info("输入查询goods_barcode和id的语句：" + sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                cron_id = rs.getString("id");
//                String sqlResult = "在表中【sys_cron】获取到的" + "id: " + cron_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 获取oms_synchronize_log中interface_deliver_data的shippingtime
//        try {
//            String sqlQuery = "select lasttime from oms_synchronize_log where sync_name='interface_deliver_data';";
////            AutoLogger.log.info("输入查询goods_barcode和id的语句：" + sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                lasttime = rs.getString("lasttime");
//                String sqlResult = "在表中【oms_synchronize_log的interface_deliver_data字段】获取到的" + "lasttime: " + lasttime;
////                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 获取oms_synchronize_log中interface_return_data的shippingtime
//        try {
//            String sqlQuery = "select lasttime from oms_synchronize_log where sync_name='interface_return_data';";
////            AutoLogger.log.info("输入查询goods_barcode和id的语句：" + sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                lasttime2 = rs.getString("lasttime");
//                String sqlResult = "在表中【oms_synchronize_log的interface_return_data字段】获取到的" + "lasttime: " + lasttime2;
////                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        //
//
//    }
//
//    /**
//     * 场景二： 新建多订单多商品   一个tid, 两个oid
//     */
//    public void insertOrders() throws SQLException {
//        // 插入Order_info的SQL语句
//        /**
//         * goods_amount: 商品价格
//         * money_paid:   实付金额
//         * order_amount：应付款
//         * total_amount: 应付总额
//         */
//        try {
//            String sqlOrder = "INSERT INTO order_info(order_sn,deal_code(,sd_id,order_status,shipping_status,pay_status,back_status,order_addtime,consignee,province,city,district,address,full_address,mobile,postscript,shipping_id,goods_amount,money_paid,order_amount,total_amount,confirm_time,add_time,pay_time,goods_count,user_nick,is_hand,shipping_store,pick_type,is_send,push_time,pay_way,update_time,omnichannel_param,outer_order_id) "
//                    + "VALUES (" + "'" + order_sn + "'" + "," + deal_code( + "," + shopID() + ",1,3,1,0," + timeOld
//                    + ",'~raMMEgyXZpFvhhWGrW+10w==~-1~',25,321,2703,'上海','上海 上海市 长宁区 上海市长宁区接口自动化','13100000000','龙张立',17,"
//                    + totalPrice + "," + totalPrice + "," + totalPrice + "," + totalPrice + ",'" + NowTime + "','"
//                    + NowTime + "','" + NowTime + "'," + totalNumber + ",'~raMMEgyXZpFvhhWGrW+10w==~-1~',1,1,1,1,'" + NowTime
//                    + "',1,'" + NowTime + "',0,\"" + outer_order_id + "\");";
////            AutoLogger.log.info("正在向数据库插入新order订单 "); // + ": " + sqlOrder
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlOrder); // 执行sql语句
//            AutoLogger.log.info("新订单创建成功：" + a + "个订单" + " " + "" + "生成的订单号为：" + order_sn);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        /**
//         * 查询并获取到 order_id/id/
//         */
//        try {
//            String sqlQuery = "SELECT * FROM `order_info` where deal_code(=" + deal_code( + ";";
////            AutoLogger.log.info("输入查询order_id语句："); // + sqlQuery
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                order_id = rs.getString("order_id");
//                outer_order_id = rs.getString("outer_order_id");
//                String sqlResult = "获取到的" + "order_id: " + order_id + " " + "order_sn：" + order_sn + " " + "deal_code(："
//                        + deal_code( + " " + "outer_order_id：" + outer_order_id;
////                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        /**
//         * 插入Order_Goods的SQL语句
//         * market_price: 市场价 =  单价
//         * goods_price:  商品单价
//         * money_paid：  实付金额 = 单价 * goods_number
//         */
//        String sqlGoods1 = "insert order_goods (order_id,tid,oid,goods_barcode,market_price,goods_number,goods_price,money_paid,updated_at,outer_goods_id) values ("
//                + order_id + "," + deal_code( + "," + oid1 + ",'" + goods_barcode1 + "'," + goods_price1 + "," + goods_number + "," + goods_price1 + "," + totalPrice1 + "," + nowTime + ",\"" + outer_goods_id + "\");";
//
//        String sqlGoods2 = "insert order_goods (order_id,tid,oid,goods_barcode,market_price,goods_number,goods_price,money_paid,updated_at,outer_goods_id) values ("
//                + order_id + "," + deal_code( + "," + oid2 + ",'" + goods_barcode2 + "'," + goods_price2 + "," + goods_number + "," + goods_price2 + "," + totalPrice2 + "," + nowTime + ",\"" + outer_goods_id + "\");";
//
//        AutoLogger.log.info("正在向 数据库插入新order_goods表 ");
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods1);     // 执行sql语句
//            AutoLogger.log.info("商品数据插入成功： " + "第一" + "个子订单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods2);    // 执行sql语句
//            AutoLogger.log.info("商品数据插入成功： " + "第二" + "个子订单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 查询出order_goods中的goods_barcode/id/outer_goods_id
//        try {
//            String sqlQuery = "SELECT * FROM `order_goods` where tid =" + deal_code( + " limit 0,1;";
////            AutoLogger.log.info("输入查询goods_barcode和id的语句：" + sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                goods_barcode1 = rs.getString("goods_barcode");
//                order_goods_id = rs.getString("id");
//                orderLineId1 = rs.getString("id");
//                outer_goods_id = rs.getString("outer_goods_id");
//                String sqlResult = "第一行商品获取到的" + "goods_barcode: " + goods_barcode1 + " " + "id：" + orderLineId1 + " "
//                        + "outer_goods_id:" + outer_goods_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 查询出order_goods中的goods_barcode/id/outer_goods_id
//        try {
//            String sqlQuery = "SELECT * FROM `order_goods` where tid =" + deal_code( + " limit 1,2;";
////            AutoLogger.log.info("输入查询goods_barcode和id的语句：" + sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                goods_barcode2 = rs.getString("goods_barcode");
//                orderLineId2 = rs.getString("id");
//                outer_goods_id2 = rs.getString("outer_goods_id");
//                String sqlResult = "第二行商品获取到的" + "goods_barcode: " + goods_barcode2 + " " + "id：" + orderLineId2 + " "
//                        + "outer_goods_id:" + outer_goods_id2;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 获取推送cron_id
//        try {
//            String sqlQuery = "SELECT * FROM `sys_cron` where code LIKE '%sales_account_data%'";
////            AutoLogger.log.info("输入查询goods_barcode和id的语句：" + sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                cron_id = rs.getString("id");
//                String sqlResult = "在表中【sys_cron】获取到的" + "id: " + cron_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 获取oms_synchronize_log中interface_deliver_data的shippingtime
//        try {
//            String sqlQuery = "select lasttime from oms_synchronize_log where sync_name='interface_deliver_data';";
////            AutoLogger.log.info("输入查询goods_barcode和id的语句：" + sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                lasttime = rs.getString("lasttime");
//                String sqlResult = "在表中【oms_synchronize_log的interface_deliver_data字段】获取到的" + "lasttime: " + lasttime;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 获取oms_synchronize_log中interface_return_data的shippingtime
//        try {
//            String sqlQuery = "select lasttime from oms_synchronize_log where sync_name='interface_return_data';";
////            AutoLogger.log.info("输入查询goods_barcode和id的语句：" + sqlQuery);
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                lasttime2 = rs.getString("lasttime");
//                String sqlResult = "在表中【oms_synchronize_log的interface_return_data字段】获取到的" + "lasttime: " + lasttime2;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    /**
//     * 场景一： 创建一个退货单带一个oid
//     * refund_fee:退还金额 = 商品行金额 + 运费金额     // 暂时未考虑运费, 运费为0
//     * total_fee: 交易总金额。精确到2位小数,单位:元。如:200.07，表示:200元7分'
//     * 未考虑到礼品卡、折扣减免、退运费的逻辑数据校验, 退款的钱为支付的钱
//     */
//    public void insertReturn() {
//        // 定义字符串插入退货单数据
//        String sqlInsert = "insert order_return (return_sn,return_deal_code,refund_sn,order_sn,deal_code(,return_type,sd_id,return_warehouse,created,reason,num,refund_fee,total_fee,\r\n"
//                + "invoice_no,shipping_name,buyer_nick,consignee,mobile,created_at,updated_at,return_source,return_push_status,service_type)"
//                + "values ('" + return_sn + "','" + deal_code( + "','" + refund_sn_tk + "','" + order_sn + "','" + deal_code(
//                + "',1," + shopID() + ",1," + nowTime + ",'七天无理由退换货',"
//                + "" + goods_number + "," + goods_price1 + "," + goods_price1 + ",984981184847,'顺丰特惠','~raMMEgyXZpFvhhWGrW+10w==~-1~','~raMMEgyXZpFvhhWGrW+10w==~-1~','$131$YlBhIwdN+Vy4pdYG+NrX1w==$-1$',"
//                + nowTime + "," + nowTime + ",2,2,1);";
//        AutoLogger.log.info("正在向数据库插入退货订单");
//
//        // 插入order_return的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlInsert); // 执行sql语句
//            AutoLogger.log.info("退货单创建成功：" + a + "个退货单" + " " + "" + "退货单号为：" + return_sn);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 查询order_return的return_id语句
//        String sqlQuery = "SELECT * FROM `order_return` where return_sn='" + return_sn + "';";
//        AutoLogger.log.info("输入查询sql语句：" + sqlQuery);
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                return_id = rs.getString("return_id");
//                String sqlResult = "获取到的数据为：" + "   return_id:" + return_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        String sqlQ = "insert order_return_goods (return_id,return_deal_code,oid,order_sn,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,lastchanged) values ("
//                + return_id + ",'" + deal_code( + "','" + oid + "','" + order_sn + "'," + order_goods_id + ",'"
//                + goods_barcode1 + "','" + shopCode + goods_name1 + "'," + goods_price1 + "," + goods_price1 + "," + goods_number + "," + goods_number + "," + nowTime + ");";
//        AutoLogger.log.info("正在向数据库插入退货商品订单");
//
//        // 插入order_return_goods的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlQ); // 执行sql语句
//            AutoLogger.log.info("退货商品单创建成功：" + a + "个退货商品单" + " " + " " + "退货商品单号为：" + return_sn + " " + "   return_id:"
//                    + return_id);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 获取order_return_goods中的lineId
//        try {
//            String sql = "select * from order_return_goods where return_id = " + return_id + " limit 0,1;";
//            stm = connection.createStatement();
//            rs = stm.executeQuery(sql);
//            while (rs.next()) {
//                returnLineId1 = rs.getString("id");
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//    }
//
//    /**
//     * 场景二： 创建一个退货单带两个oid
//     * refund_fee:退还金额 = 商品行金额 + 运费金额     // 暂时未考虑运费, 运费为0
//     * total_fee: 交易总金额。精确到2位小数,单位:元。如:200.07，表示:200元7分'
//     * return_source:  '退货来源，1-平台创建，2-手工创建',
//     * return_push_status:  推送状态，1-未推送仓库，2-已推送仓库',
//     * service_type:  '服务单类型，1-退货 2-换货 3-其他',
//     * 未考虑到礼品卡、折扣减免的逻辑数据校验, 退款的钱和支付的钱为同一个值, 待本人熟悉后再添加相关运算逻辑
//     * 第609行的2 代表为一个退货单有两个oid
//     */
//    public void insertReturns() {
//        // 定义字符串插入退货单数据
//        String sqlInsert = "insert order_return (return_sn,return_deal_code,refund_sn,order_sn,deal_code(,oid,return_type,sd_id,return_warehouse,created,reason,num,refund_fee,total_fee,\r\n"
//                + "invoice_no,shipping_name,buyer_nick,consignee,mobile,created_at,updated_at,return_source,return_push_status,service_type)\r\n"
//                + "values ('" + return_sn + "','" + deal_code( + "','" + refund_sn_tk + "','" + order_sn + "','" + deal_code(
//                + "','',1," + shopID() + ",1," + nowTime + ",'七天无理由退换货'," + totalNumber + ",'" + totalRefund + "','" + totalRefund
//                + "',984981184847,'顺丰特惠','~raMMEgyXZpFvhhWGrW+10w==~-1~','~raMMEgyXZpFvhhWGrW+10w==~-1~','$131$YlBhIwdN+Vy4pdYG+NrX1w==$-1$',"
//                + nowTime + "," + nowTime + ",2,2,1);";
//        AutoLogger.log.info("正在向数据库插入退货订单");
//
//        // 插入第一个order_return的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlInsert); // 执行sql语句
//            AutoLogger.log.info("退货单创建成功：" + a + "个退货单" + " " + "" + "退货单号为：" + return_sn);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 查询第一个order_return的return_id语句
//        String sqlQuery = "SELECT * FROM `order_return` where return_sn='" + return_sn + "';";
//        AutoLogger.log.info("输入查询sql语句：" + sqlQuery);
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                return_id = rs.getString("return_id");
//                String sqlResult = "获取到的第一个的：" + " return_id: " + return_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//        // 买多少退多少
//        AutoLogger.log.info("正在向数据库插入退货商品订单");
//        String sqlQ = "insert order_return_goods (return_id,return_deal_code,oid,order_sn,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,lastchanged) values ("
//                + return_id + ",'" + deal_code( + "','" + oid1 + "','" + order_sn + "'," + order_goods_id + ",'"
//                + goods_barcode1 + "','" + goods_name1 + "','" + goods_price1 + "','" + totalPrice1 + "','" + goods_number
//                + "','" + goods_number + "'," + nowTime + ");";
//
//        String sqlW = "insert order_return_goods (return_id,return_deal_code,oid,order_sn,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,lastchanged) values ("
//                + return_id + ",'" + deal_code( + "','" + oid2 + "','" + order_sn + "'," + order_goods_id + ",'"
//                + goods_barcode2 + "','" + goods_name2 + "','" + goods_price2 + "','" + totalPrice2 + "','" + goods_number
//                + "','" + goods_number + "'," + nowTime + ");";
//
//        // 插入第一个order_return_goods的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlQ); // 执行sql语句
//            AutoLogger.log.info("退货商品单创建成功：" + "第一" + "个退货商品单" + " " + " " + "退货sku为：" + goods_barcode1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 插入第二个order_return_goods的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlW); // 执行sql语句
//            AutoLogger.log.info("退货商品单创建成功：" + "第二" + "个退货商品单" + " " + " " + "退货sku为：" + goods_barcode2);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        try {
//            String sql = "select * from order_return_goods where return_id = " + return_id + " limit 0,1;";
//            stm = connection.createStatement();
//            rs = stm.executeQuery(sql);
//            while (rs.next()) {
//                returnLineId1 = rs.getString("id");
//            }
//            AutoLogger.log.info("在order_return_goods查询到的第一行的LineId：" + returnLineId1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        try {
//            String sql = "select * from order_return_goods where return_id = " + return_id + " limit 1,2;";
//            Statement stm = connection.createStatement();
//            ResultSet rs = stm.executeQuery(sql);
//            while (rs.next()) {
//                returnLineId2 = rs.getString("id");
//            }
//            AutoLogger.log.info("在order_return_goods查询到的第二行的LineId：" + returnLineId2);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//    }
//
//
//    /**
//     * 场景一：创建一个退款单<>未发货状态 仅退款</>  其中退款的金额与商品的金额一致, 暂未考虑折扣卡，礼品卡，运费之间的运算逻辑
//     * oid             子订单号。如果是单笔交易oid会等于tid
//     * refund_type     退款单类型，1-仅退款(未发货)，2-仅退款(已发货)，3-退货退款
//     * refund_from     退款单来源，1-天猫及其他平台，2-对接推送 3-新建退款单
//     * refund_status   退款单状态，0-待处理，1-处理中，2-退款成功，3-退款关闭
//     * refund_fee      退款金额
//     * total_fee       交易总金额。精确到2位小数,单位:元。如:200.07，表示:200元7分
//     */
//    public void insertRefund() {
//        String sqlInsert = "insert order_refund(refund_sn,refund_deal_code,return_sn,order_sn,deal_code(,oid,refund_type,refund_from,refund_status,sd_id,created,modified,reason,num,refund_fee,total_fee,consignee,mobile,return_warehouse,created_at,updated_at,auditor,audit_time)\r\n" +
//                "values ('" + refund_sn_tk + "','" + refund_deal_code + "','" + return_sn + "','" + order_sn + "','" + deal_code( + "','" + oid + "',1,3,1," + shopID() + ",\r\n" +
//                "'" + nowTime + "','" + nowTime + "','七天无理由'," + goods_number + "," + totalPrice1 + "," + totalPrice1 + ",'~YObyZjifiGiN7iOSV0HLlA==~-1~','$185$prMaLuLL1qzVf6Pplxu/aw==$-1$',1,'" + nowTime + "','" + nowTime + "','Stephen','" + nowTime + "');";
//
////		AutoLogger.log.info("正在向数据库插入退款单："+ sqlInsert);
//
//        // 插入order_return的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlInsert); // 执行sql语句
//            AutoLogger.log.info("退款单创建成功：" + a + "个退款单" + " " + "" + "退款单号为：" + refund_sn_tk + "交易订单号为：" + deal_code;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 获取refund_id
//        try { // 查询并获取到refund_id
//            String sqlQuery = "SELECT * FROM `order_refund` where refund_sn='" + refund_sn_tk + "';";
//            AutoLogger.log.info("准备查询refund_id："); // + sqlQuery
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                refund_id = rs.getString("refund_id");
//                String sqlResult = "获取到的" + "refund_id: " + refund_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 插入order_refund_goods的SQL语句
//        String sqlGoods = "insert order_refund_goods (refund_id,refund_deal_code,order_sn,oid,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,updated_at)\r\n" +
//                "values (" + refund_id + ",'" + refund_deal_code + "','" + order_sn + "','" + oid + "','" + order_goods_id + "','" + goods_barcode1 + "','" + goods_name1 + "'," + goods_price1 + "," + totalPrice1 + "," + goods_number + "," + goods_number + ",'" + nowTime + "');";
////		AutoLogger.log.info(sqlGoods);
//        AutoLogger.log.info("正在向数据库插入新order_refund_goods表 " + " " + "goods_barcode：" + goods_barcode1); // + ": " + sqlGoods
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods); // 执行sql语句
//            AutoLogger.log.info("退款商品数据插入成功： " + a + "个退款单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//    }
//
//    /**
//     * 场景二：创建一个退款单<>已发货状态 仅退款</>  其中退款的金额与商品的金额一致, 暂未考虑折扣卡，礼品卡，运费之间的运算逻辑
//     * oid             子订单号。如果是单笔交易oid会等于tid
//     * refund_type     退款单类型，1-仅退款(未发货)，2-仅退款(已发货)，3-退货退款
//     * refund_from     退款单来源，1-天猫及其他平台，2-对接推送 3-新建退款单
//     * refund_status   退款单状态，0-待处理，1-处理中，2-退款成功，3-退款关闭
//     * refund_fee      退款金额
//     * total_fee       交易总金额。精确到2位小数,单位:元。如:200.07，表示:200元7分
//     */
//    public void insertRefund_2() {
//        String sqlInsert = "insert order_refund(refund_sn,refund_deal_code,return_sn,order_sn,deal_code(,oid,refund_type,refund_from,refund_status,sd_id,created,modified,reason,num,refund_fee,total_fee,consignee,mobile,return_warehouse,created_at,updated_at,auditor,audit_time)\r\n" +
//                "values ('" + refund_sn_tk + "','" + refund_deal_code + "','" + return_sn + "','" + order_sn + "','" + deal_code( + "','" + oid + "',2,3,1," + shopID() + ",\r\n" +
//                "'" + nowTime + "','" + nowTime + "','七天无理由'," + goods_number + "," + totalPrice1 + "," + totalPrice1 + ",'~YObyZjifiGiN7iOSV0HLlA==~-1~','$185$prMaLuLL1qzVf6Pplxu/aw==$-1$',1,'" + nowTime + "','" + nowTime + "','Stephen','" + nowTime + "');";
//
//        AutoLogger.log.info("正在向数据库插入退款单：");
//
//        // 插入order_return的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlInsert); // 执行sql语句
//            AutoLogger.log.info("退款单创建成功：" + a + "个退款单" + " " + "" + "退款单号为：" + refund_sn_tk + "交易订单号为：" + deal_code;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 获取refund_id
//        try { // 查询并获取到refund_id
//            String sqlQuery = "SELECT * FROM `order_refund` where refund_sn='" + refund_sn_tk + "';";
//            AutoLogger.log.info("准备查询refund_id："); // + sqlQuery
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                refund_id = rs.getString("refund_id");
//                String sqlResult = "获取到的" + "refund_id: " + refund_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 插入order_refund_goods的SQL语句
//        String sqlGoods = "insert order_refund_goods (refund_id,refund_deal_code,order_sn,oid,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,updated_at)\r\n" +
//                "values (" + refund_id + ",'" + refund_deal_code + "','" + order_sn + "','" + oid + "','" + order_goods_id + "','" + goods_barcode1 + "','" + goods_name1 + "'," + goods_price1 + "," + totalPrice1 + "," + goods_number + "," + goods_number + ",'" + nowTime + "');";
//        AutoLogger.log.info(sqlGoods);
//        AutoLogger.log.info("正在向数据库插入新order_refund_goods表 " + " " + "goods_barcode：" + goods_barcode1); // + ": " + sqlGoods
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods); // 执行sql语句
//            AutoLogger.log.info("退款商品数据插入成功： " + a + "个退款单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//    }
//
//    /**
//     * 场景三：创建一个退款单<>已发货状态 退货退款</>  其中退款的金额与商品的金额一致, 暂未考虑折扣卡，礼品卡，运费之间的运算逻辑
//     * oid             子订单号。如果是单笔交易oid会等于tid
//     * refund_type     退款单类型，1-仅退款(未发货)，2-仅退款(已发货)，3-退货退款
//     * refund_from     退款单来源，1-天猫及其他平台，2-对接推送 3-新建退款单
//     * refund_status   退款单状态，0-待处理，1-处理中，2-退款成功，3-退款关闭
//     * refund_fee      退款金额
//     * total_fee       交易总金额。精确到2位小数,单位:元。如:200.07，表示:200元7分
//     */
//    public void insertRefund_3() {
//        String sqlInsert = "insert order_refund(refund_sn,refund_deal_code,return_sn,order_sn,deal_code(,oid,refund_type,refund_from,refund_status,sd_id,created,modified,reason,num,refund_fee,total_fee,consignee,mobile,return_warehouse,created_at,updated_at,auditor,audit_time)\r\n" +
//                "values ('" + refund_sn_tk + "','" + refund_deal_code + "','" + return_sn + "','" + order_sn + "','" + deal_code( + "','" + oid + "',3,3,1," + shopID() + ",\r\n" +
//                "'" + nowTime + "','" + nowTime + "','七天无理由'," + goods_number + "," + totalPrice1 + "," + totalPrice1 + ",'~YObyZjifiGiN7iOSV0HLlA==~-1~','$185$prMaLuLL1qzVf6Pplxu/aw==$-1$',1,'" + nowTime + "','" + nowTime + "','Stephen','" + nowTime + "');";
//
//        AutoLogger.log.info("正在向数据库插入退款单：");
//
//        // 插入order_return的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlInsert); // 执行sql语句
//            AutoLogger.log.info("退款单创建成功：" + a + "个退款单" + " " + "" + "退款单号为：" + refund_sn_tk + "交易订单号为：" + deal_code;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 获取refund_id
//        try { // 查询并获取到refund_id
//            String sqlQuery = "SELECT * FROM `order_refund` where refund_sn='" + refund_sn_tk + "';";
//            AutoLogger.log.info("准备查询refund_id："); // + sqlQuery
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                refund_id = rs.getString("refund_id");
//                String sqlResult = "获取到的" + "refund_id: " + refund_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 插入order_refund_goods的SQL语句
//        String sqlGoods = "insert order_refund_goods (refund_id,refund_deal_code,order_sn,oid,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,updated_at)\r\n" +
//                "values (" + refund_id + ",'" + refund_deal_code + "','" + order_sn + "','" + oid + "','" + order_goods_id + "','" + goods_barcode1 + "','" + goods_name1 + "'," + goods_price1 + "," + totalPrice1 + "," + goods_number + "," + goods_number + ",'" + nowTime + "');";
//        AutoLogger.log.info("正在向数据库插入新order_refund_goods表 " + " " + "goods_barcode：" + goods_barcode1); // + ": " + sqlGoods
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods); // 执行sql语句
//            AutoLogger.log.info("退款商品数据插入成功： " + a + "个退款单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//    }
//
//
//    /**
//     * 场景四：创建一个退款单多个sku <>未发货状态 仅退款</>  其中退款的金额与商品的金额一致, 暂未考虑折扣卡，礼品卡，运费之间的运算逻辑
//     * oid             子订单号。如果是单笔交易oid会等于tid
//     * refund_type     退款单类型，1-仅退款(未发货)，2-仅退款(已发货)，3-退货退款
//     * refund_from     退款单来源，1-天猫及其他平台，2-对接推送 3-新建退款单
//     * refund_status   退款单状态，0-待处理，1-处理中，2-退款成功，3-退款关闭
//     * refund_fee      退款金额
//     * total_fee       交易总金额。精确到2位小数,单位:元。如:200.07，表示:200元7分
//     */
//    public void insertRefunds() {
//        String sqlInsert = "insert order_refund(refund_sn,refund_deal_code,return_sn,order_sn,deal_code(,oid,refund_type,refund_from,refund_status,sd_id,created,modified,reason,num,refund_fee,total_fee,consignee,mobile,return_warehouse,created_at,updated_at,auditor,audit_time)\r\n" +
//                "values ('" + refund_sn_tk + "','" + refund_deal_code + "','" + return_sn + "','" + order_sn + "','" + deal_code( + "','" + oid + "',1,3,1," + shopID() + ",\r\n" +
//                "'" + nowTime + "','" + nowTime + "','七天无理由'," + totalNumber + "," + totalRefund + "," + totalPrice + ",'~YObyZjifiGiN7iOSV0HLlA==~-1~','13100000000',1,'" + nowTime + "','" + nowTime + "','Stephen','" + nowTime + "');";
//        AutoLogger.log.info("正在向数据库插入退款单：");
//
//        // 插入order_return的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlInsert); // 执行sql语句
//            AutoLogger.log.info("退款单创建成功：" + a + "个退款单" + " " + "" + "退款单号为：" + refund_sn_tk + "交易订单号为：" + deal_code;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 获取refund_id
//        try { // 查询并获取到refund_id
//            String sqlQuery = "SELECT * FROM `order_refund` where refund_sn='" + refund_sn_tk + "';";
//            AutoLogger.log.info("准备查询refund_id："); // + sqlQuery
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                refund_id = rs.getString("refund_id");
//                String sqlResult = "获取到的" + "refund_id: " + refund_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 插入order_refund_goods的SQL语句
//        String sqlGoods = "insert order_refund_goods (refund_id,refund_deal_code,order_sn,oid,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,updated_at)\r\n" +
//                "values (" + refund_id + ",'" + refund_deal_code + "','" + order_sn + "','" + oid1 + "','" + order_goods_id + "','" + goods_barcode1 + "','" + goods_name1 + "'," + totalPrice1 + "," + totalPrice1 + "," + goods_number + "," + goods_number + ",'" + nowTime + "');";
//
//        String sqlGoods2 = "insert order_refund_goods (refund_id,refund_deal_code,order_sn,oid,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,updated_at)\r\n" +
//                "values (" + refund_id + ",'" + refund_deal_code + "','" + order_sn + "','" + oid2 + "','" + order_goods_id + "','" + goods_barcode2 + "','" + goods_name2 + "'," + totalPrice2 + "," + totalPrice2 + "," + goods_number + "," + goods_number + ",'" + nowTime + "');";
//
//        AutoLogger.log.info("插入第一个退款单商品：" + oid1);
//        AutoLogger.log.info("插入第二个退款单商品：" + oid2);
//
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods); // 执行sql语句
//            AutoLogger.log.info("退款商品数据插入成功： " + a + "个退款单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods2); // 执行sql语句
//            AutoLogger.log.info("退款商品数据插入成功： " + a + "个退款单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//    }
//
//    /**
//     * 场景五：创建一个退款单多个sku <>已发货状态 仅退款</>  其中退款的金额与商品的金额一致, 暂未考虑折扣卡，礼品卡，运费之间的运算逻辑
//     * oid             子订单号。如果是单笔交易oid会等于tid
//     * refund_type     退款单类型，1-仅退款(未发货)，2-仅退款(已发货)，3-退货退款
//     * refund_from     退款单来源，1-天猫及其他平台，2-对接推送 3-新建退款单
//     * refund_status   退款单状态，0-待处理，1-处理中，2-退款成功，3-退款关闭
//     * refund_fee      退款金额
//     * total_fee       交易总金额。精确到2位小数,单位:元。如:200.07，表示:200元7分
//     */
//    public void insertRefunds_2() {
//        String sqlInsert = "insert order_refund(refund_sn,refund_deal_code,return_sn,order_sn,deal_code(,oid,refund_type,refund_from,refund_status,sd_id,created,modified,reason,num,refund_fee,total_fee,consignee,mobile,return_warehouse,created_at,updated_at,auditor,audit_time)\r\n" +
//                "values ('" + refund_sn_tk + "','" + refund_deal_code + "','" + return_sn + "','" + order_sn + "','" + deal_code( + "','" + oid + "',2,3,1," + shopID() + ",\r\n" +
//                "'" + nowTime + "','" + nowTime + "','七天无理由'," + totalNumber + "," + totalRefund + "," + totalPrice + ",'~YObyZjifiGiN7iOSV0HLlA==~-1~','13100000000',1,'" + nowTime + "','" + nowTime + "','Stephen','" + nowTime + "');";
//        AutoLogger.log.info("正在向数据库插入退款单：");
//
//        // 插入order_return的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlInsert); // 执行sql语句
//            AutoLogger.log.info("退款单创建成功：" + a + "个退款单" + " " + "" + "退款单号为：" + refund_sn_tk + "交易订单号为：" + deal_code;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 获取refund_id
//        try { // 查询并获取到refund_id
//            String sqlQuery = "SELECT * FROM `order_refund` where refund_sn='" + refund_sn_tk + "';";
//            AutoLogger.log.info("准备查询refund_id："); // + sqlQuery
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                refund_id = rs.getString("refund_id");
//                String sqlResult = "获取到的" + "refund_id: " + refund_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 插入order_refund_goods的SQL语句
//        String sqlGoods = "insert order_refund_goods (refund_id,refund_deal_code,order_sn,oid,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,updated_at)\r\n" +
//                "values (" + refund_id + ",'" + refund_deal_code + "','" + order_sn + "','" + oid1 + "','" + order_goods_id + "','" + goods_barcode1 + "','" + goods_name1 + "'," + totalPrice1 + "," + totalPrice1 + "," + goods_number + "," + goods_number + ",'" + nowTime + "');";
//
//        String sqlGoods2 = "insert order_refund_goods (refund_id,refund_deal_code,order_sn,oid,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,updated_at)\r\n" +
//                "values (" + refund_id + ",'" + refund_deal_code + "','" + order_sn + "','" + oid2 + "','" + order_goods_id + "','" + goods_barcode2 + "','" + goods_name2 + "'," + totalPrice2 + "," + totalPrice2 + "," + goods_number + "," + goods_number + ",'" + nowTime + "');";
//
//        AutoLogger.log.info("插入第一个退款单商品：" + oid1);
//        AutoLogger.log.info("插入第二个退款单商品：" + oid2);
//
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods); // 执行sql语句
//            AutoLogger.log.info("退款商品数据插入成功： " + a + "个退款单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods2); // 执行sql语句
//            AutoLogger.log.info("退款商品数据插入成功： " + a + "个退款单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//    }
//
//    /**
//     * 场景六：创建一个退款单多个sku <>已发货状态 退货退款</>  其中退款的金额与商品的金额一致, 暂未考虑折扣卡，礼品卡，运费之间的运算逻辑
//     * oid             子订单号。如果是单笔交易oid会等于tid
//     * refund_type     退款单类型，1-仅退款(未发货)，2-仅退款(已发货)，3-退货退款
//     * refund_from     退款单来源，1-天猫及其他平台，2-对接推送 3-新建退款单
//     * refund_status   退款单状态，0-待处理，1-处理中，2-退款成功，3-退款关闭
//     * refund_fee      退款金额
//     * total_fee       交易总金额。精确到2位小数,单位:元。如:200.07，表示:200元7分
//     */
//    public void insertRefunds_3() {
//        String sqlInsert = "insert order_refund(refund_sn,refund_deal_code,return_sn,order_sn,deal_code(,oid,refund_type,refund_from,refund_status,sd_id,created,modified,reason,num,refund_fee,total_fee,consignee,mobile,return_warehouse,created_at,updated_at,auditor,audit_time)\r\n" +
//                "values ('" + refund_sn_tk + "','" + refund_deal_code + "','" + return_sn + "','" + order_sn + "','" + deal_code( + "','" + oid + "',3,3,1," + shopID() + ",\r\n" +
//                "'" + nowTime + "','" + nowTime + "','七天无理由'," + totalNumber + "," + totalRefund + "," + totalPrice + ",'~YObyZjifiGiN7iOSV0HLlA==~-1~','13100000000',1,'" + nowTime + "','" + nowTime + "','Stephen','" + nowTime + "');";
//        AutoLogger.log.info("正在向数据库插入退款单：");
//
//        // 插入order_return的SQL语句
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlInsert); // 执行sql语句
//            AutoLogger.log.info("退款单创建成功：" + a + "个退款单" + " " + "" + "退款单号为：" + refund_sn_tk + "交易订单号为：" + deal_code;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        // 获取refund_id
//        try { // 查询并获取到refund_id
//            String sqlQuery = "SELECT * FROM `order_refund` where refund_sn='" + refund_sn_tk + "';";
//            AutoLogger.log.info("准备查询refund_id："); // + sqlQuery
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行sql语句
//            rs = stm.executeQuery(sqlQuery);
//            // 展开结果集
//            while (rs.next()) {
//                // 通过字段检索
//                refund_id = rs.getString("refund_id");
//                String sqlResult = "获取到的" + "refund_id: " + refund_id;
//                AutoLogger.log.info(sqlResult);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // 插入order_refund_goods的SQL语句
//        String sqlGoods = "insert order_refund_goods (refund_id,refund_deal_code,order_sn,oid,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,updated_at)\r\n" +
//                "values (" + refund_id + ",'" + refund_deal_code + "','" + order_sn + "','" + oid1 + "','" + order_goods_id + "','" + goods_barcode1 + "','" + goods_name1 + "'," + totalPrice1 + "," + totalPrice1 + "," + goods_number + "," + goods_number + ",'" + nowTime + "');";
//
//        String sqlGoods2 = "insert order_refund_goods (refund_id,refund_deal_code,order_sn,oid,order_goods_id,goods_barcode,title,goods_price,refund_fee,buy_goods_num,return_goods_num,updated_at)\r\n" +
//                "values (" + refund_id + ",'" + refund_deal_code + "','" + order_sn + "','" + oid2 + "','" + order_goods_id + "','" + goods_barcode2 + "','" + goods_name2 + "'," + totalPrice2 + "," + totalPrice2 + "," + goods_number + "," + goods_number + ",'" + nowTime + "');";
//
//        AutoLogger.log.info("插入第一个退款单商品：" + oid1);
//        AutoLogger.log.info("插入第二个退款单商品：" + oid2);
//
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods); // 执行sql语句
//            AutoLogger.log.info("退款商品数据插入成功： " + a + "个退款单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//
//        try {
//            // 建立查询
//            stm = connection.createStatement();
//            // 执行插入语句
//            int a = stm.executeUpdate(sqlGoods2); // 执行sql语句
//            AutoLogger.log.info("退款商品数据插入成功： " + a + "个退款单");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            AutoLogger.log.info("SQL语句有误,  数据报错");
//        }
//    }


    /**
     * 验证通知发货【单订单】  从 oms →> hub之间的数据传递，验证的对象： 通知发货请求体 与 hub_interface的delivery_order表
     * 验证多个字段： sku、order_sn、deal_code(、actualPrice、retailPrice、planQty、discountAmount、fullAddress
     *
     * @param body 接口请求体
     */
    public static void delivery_dataCheck(String body) {
        try {
            AutoLogger.log.info("----------------------------------------------获--取--HUB--数--据----------------------------------------------------------------");
            conn(hub_dataBase);
            hub_interface();
            String sql = "select * from " + hub_delivery_order + " where sourceOrderCode like '%" + deal_code + "%';";
            System.out.println(sql);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONArray array = new JSONArray();               // json对象
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
                array.add(jsonObject);
//                String hub_body = array.toString();
            }
            String hub_itemCode = jsonObject.getString("itemCode");
            String hub_deliveryOrderCode = jsonObject.getString("deliveryOrderCode");
            String hub_sourceOrderCode = jsonObject.getString("sourceOrderCode");
            String hub_planQty = jsonObject.getString("planQty");
            String hub_discountAmount = jsonObject.getString("discountAmount");
            String hub_actualPrice = jsonObject.getString("actualPrice");
            String hub_retailPrice = jsonObject.getString("retailPrice");
            String hub_detailAddress = jsonObject.getString("detailAddress");
            String hub_orderType = jsonObject.getString("orderType");
            String hub_warehouseCode = jsonObject.getString("warehouseCode");
            String hub_orderFlag = jsonObject.getString("orderFlag");
            String hub_sourcePlatformCode = jsonObject.getString("sourcePlatformCode");

            JSONObject json = new JSONObject();
            json.put("itemCode", hub_itemCode);
            json.put("deliveryOrderCode", hub_deliveryOrderCode);
            json.put("sourceOrderCode", hub_sourceOrderCode);
            json.put("planQty", hub_planQty);
            json.put("discountAmount", hub_discountAmount);
            json.put("actualPrice", hub_actualPrice);
            json.put("retailPrice", hub_retailPrice);
            json.put("detailAddress", hub_detailAddress);
            json.put("orderType", hub_orderType);
            json.put("warehouseCode", hub_warehouseCode);
            json.put("orderFlag", hub_orderFlag);
            json.put("sourcePlatformCode", hub_sourcePlatformCode);
            System.out.println(json.toJSONString());

            // 获取请求体里的关键字段
            String order_sn = JSONPath.read(body, "$.deliveryOrder.deliveryOrderCode").toString();
            String actualPrice = JSONPath.read(body, "$.orderLines.orderLine[0].actualPrice").toString();
            String retailPrice = JSONPath.read(body, "$.orderLines.orderLine[0].retailPrice").toString();
            String discountAmount = JSONPath.read(body, "$.deliveryOrder.discountAmount").toString();
            String fullAddress = JSONPath.read(body, "$.deliveryOrder.receiverInfo.detailAddress").toString();
            String deal_code = JSONPath.read(body, "$.orderLines.orderLine[0].sourceOrderCode").toString();
            String barCode = JSONPath.read(body, "$.orderLines.orderLine[0].itemCode").toString();
            String planQty = JSONPath.read(body, "$.orderLines.orderLine[0].planQty").toString();
            String orderType = JSONPath.read(body, "$.deliveryOrder.orderType").toString();
            String warehouseCode = JSONPath.read(body, "$.deliveryOrder.warehouseCode").toString();
            String orderFlag = JSONPath.read(body, "$.deliveryOrder.orderFlag").toString();
            String sourcePlatformCode = JSONPath.read(body, "$.deliveryOrder.sourcePlatformCode").toString();

            JSONObject json2 = new JSONObject();
            json2.put("order_sn", order_sn);
            json2.put("deal_code(", deal_code);
            json2.put("actualPrice", actualPrice);
            json2.put("retailPrice", retailPrice);
            json2.put("barCode", barCode);
            json2.put("planQty", planQty);
            json2.put("discountAmount", discountAmount);
            json2.put("fullAddress", fullAddress);
            json2.put("orderType", orderType);
            json2.put("warehouseCode", warehouseCode);
            json2.put("orderFlag", orderFlag);
            json2.put("sourcePlatformCode", sourcePlatformCode);

            AutoLogger.log.info("【OMS<请求数据>表中的数据为： →】" + json2.toJSONString());
            AutoLogger.log.info("【HUB<插入数据>表中的数据为： →】" + json.toJSONString());

            if (hub_deliveryOrderCode.equals(order_sn) && hub_itemCode.equals(barCode) && hub_sourceOrderCode.equals(deal_code)
                    && hub_planQty.equals(planQty) && hub_actualPrice.equals(actualPrice)
                    && hub_detailAddress.contains(fullAddress) && hub_orderType.equals(orderType)
                    && hub_warehouseCode.equals(warehouseCode) && hub_orderFlag.equals(orderFlag)
            ) {
                AutoLogger.log.info("-------------------------------------校验成功 ->> hub-interface的表与oms数据校验一致, 测试通过-------------------------------------");
            } else {
                AutoLogger.log.info("-------------------------------------校验失败 ->> hub-interface的表与oms数据校验不一致,测试失败-------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            AutoLogger.log.info("【注意事项： 1. 如果如上显示interface表没有查询到相关数据或为空, 则interface表无此数据】");
            AutoLogger.log.info("【注意事项： 2. 若控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口】");
        }
    }

    /**
     * 验证通知发货【多订单】 从 oms →> hub之间的数据传递，验证的对象： 通知发货请求体 与 hub_interface的delivery_order表
     * 验证多个字段： sku、order_sn、deal_code(、actualPrice、retailPrice、planQty、discountAmount、fullAddress
     *
     * @param body 接口请求体
     */
    public static void deliveryS_dataCheck(String body) {
        try {
            AutoLogger.log.info("----------------------------------------------获--取--HUB--数--据--------------------------------------------------------");
            conn(hub_dataBase);
            hub_interface();

            String sql1 = "select * from " + hub_delivery_order + " where sourceOrderCode='" + deal_code + "' limit 1,2;";
            AutoLogger.log.info(sql1);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql1);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
//                AutoLogger.log.info(jsonObject.toJSONString());
            }

            String sql2 = "select * from " + hub_delivery_order + " where sourceOrderCode='" + deal_code + "' limit 0,1;";
            AutoLogger.log.info(sql2);
            Statement stm2 = connection.createStatement();
            ResultSet rs2 = stm2.executeQuery(sql2);
            ResultSetMetaData metaData2 = rs2.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount2 = metaData2.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject2 = new JSONObject();        // json数组
            while (rs2.next()) {
                for (int i = 1; i <= columnCount2; i++) {
                    String columnName2 = metaData2.getColumnLabel(i);    // 得到列的别名
                    String Value2 = rs2.getString(columnName2);           // 得到列名
                    jsonObject2.fluentPut(columnName2, Value2);           // 遍历出interface的delivery表中所有的字段和值
                }
//                AutoLogger.log.info(jsonObject2.toJSONString());
            }

            String hub_itemCode1 = jsonObject.getString("itemCode");
            String hub_deliveryOrderCode1 = jsonObject.getString("deliveryOrderCode");
            String hub_sourceOrderCode1 = jsonObject.getString("sourceOrderCode");
            String hub_planQty1 = jsonObject.getString("planQty");
            String hub_actualPrice1 = jsonObject.getString("actualPrice");
            String hub_retailPrice1 = jsonObject.getString("retailPrice");
            String hub_detailAddress1 = jsonObject.getString("detailAddress");
            String hub_orderType1 = jsonObject.getString("orderType");
            String hub_warehouseCode1 = jsonObject.getString("warehouseCode");
            String hub_orderFlag1 = jsonObject.getString("orderFlag");
            String hub_sourcePlatformCode1 = jsonObject.getString("sourcePlatformCode");


            String hub_itemCode2 = jsonObject2.getString("itemCode");
            String hub_deliveryOrderCode2 = jsonObject2.getString("deliveryOrderCode");
            String hub_sourceOrderCode2 = jsonObject2.getString("sourceOrderCode");
            String hub_planQty2 = jsonObject2.getString("planQty");
            String hub_actualPrice2 = jsonObject2.getString("actualPrice");
            String hub_retailPrice2 = jsonObject2.getString("retailPrice");
            String hub_detailAddress2 = jsonObject2.getString("detailAddress");
            String hub_orderType2 = jsonObject2.getString("orderType");
            String hub_warehouseCode2 = jsonObject2.getString("warehouseCode");
            String hub_orderFlag2 = jsonObject2.getString("orderFlag");
            String hub_sourcePlatformCode2 = jsonObject2.getString("sourcePlatformCode");


            JSONObject json1 = new JSONObject();
            json1.put("itemCode", hub_itemCode1);
            json1.put("deliveryOrderCode", hub_deliveryOrderCode1);
            json1.put("sourceOrderCode", hub_sourceOrderCode1);
            json1.put("planQty", hub_planQty1);
            json1.put("detailAddress", hub_detailAddress1);
            json1.put("actualPrice", hub_actualPrice1);
            json1.put("retailPrice", hub_retailPrice1);
            json1.put("orderType", hub_orderType1);
            json1.put("warehouseCode", hub_warehouseCode1);
            json1.put("orderFlag", hub_orderFlag1);
            json1.put("sourcePlatformCode", hub_sourcePlatformCode1);

            JSONObject json2 = new JSONObject();
            json2.put("itemCode", hub_itemCode2);
            json2.put("deliveryOrderCode", hub_deliveryOrderCode2);
            json2.put("sourceOrderCode", hub_sourceOrderCode2);
            json2.put("planQty", hub_planQty2);
            json2.put("detailAddress", hub_detailAddress2);
            json2.put("actualPrice", hub_actualPrice2);
            json2.put("retailPrice", hub_retailPrice2);
            json2.put("orderType", hub_orderType2);
            json2.put("warehouseCode", hub_warehouseCode2);
            json2.put("orderFlag", hub_orderFlag2);
            json2.put("sourcePlatformCode", hub_sourcePlatformCode2);

            AutoLogger.log.info("【HUB<插入数据商品1>表中的数据为： →】" + json1.toJSONString());
            AutoLogger.log.info("【HUB<插入数据商品2>表中的数据为： →】" + json2.toJSONString());
            AutoLogger.log.info("----------------------------------------------获--取--OMS--数--据--------------------------------------------------------");

//            // 获取请求体里的关键字段
            String order_sn = JSONPath.read(body, "$.deliveryOrder.deliveryOrderCode").toString();
            String orderType = JSONPath.read(body, "$.deliveryOrder.orderType").toString();
            String warehouseCode = JSONPath.read(body, "$.deliveryOrder.warehouseCode").toString();
            String orderFlag = JSONPath.read(body, "$.deliveryOrder.orderFlag").toString();
            String sourcePlatformCode = JSONPath.read(body, "$.deliveryOrder.sourcePlatformCode").toString();
            String fullAddress = HttpClientKw.DeCode(JSONPath.read(body, "$.deliveryOrder.receiverInfo.detailAddress").toString());

//            System.out.println(hub_itemCode1);
//            System.out.println(hub_itemCode2);

            String sourceOrderCode1 = JSONPath.read(body, "$.orderLines.orderLine[0].sourceOrderCode").toString();
            String barCode1 = JSONPath.read(body, "$.orderLines.orderLine[0].itemCode").toString();
            String planQty1 = JSONPath.read(body, "$.orderLines.orderLine[0].planQty").toString();
            String actualPrice1 = JSONPath.read(body, "$.orderLines.orderLine[0].actualPrice").toString();


            String sourceOrderCode2 = JSONPath.read(body, "$.orderLines.orderLine[1].sourceOrderCode").toString();
            String barCode2 = JSONPath.read(body, "$.orderLines.orderLine[1].itemCode").toString();
            String planQty2 = JSONPath.read(body, "$.orderLines.orderLine[1].planQty").toString();
            String actualPrice2 = JSONPath.read(body, "$.orderLines.orderLine[1].actualPrice").toString();
//            System.out.println(barCode1);
//            System.out.println(barCode2);

            JSONObject jsonW = new JSONObject();
            jsonW.put("order_sn", order_sn);
            jsonW.put("orderType", orderType);
            jsonW.put("warehouseCode", warehouseCode);
            jsonW.put("orderFlag", orderFlag);
            jsonW.put("sourcePlatformCode", sourcePlatformCode);
            jsonW.put("fullAddress", fullAddress);

            jsonW.put("sourceOrderCode1", sourceOrderCode1);
            jsonW.put("sourceOrderCode2", sourceOrderCode2);
            jsonW.put("barCode1", barCode1);
            jsonW.put("barCode2", barCode2);

            jsonW.put("planQty1", planQty1);
            jsonW.put("planQty2", planQty2);

            jsonW.put("actualPrice1", actualPrice1);
            jsonW.put("actualPrice2", actualPrice2);


            AutoLogger.log.info("【OMS<请求数据>表中的数据为： →】" + jsonW.toJSONString());

            if (
                    hub_deliveryOrderCode1.equals(order_sn)
                            && hub_deliveryOrderCode2.equals(order_sn)
                            && hub_itemCode1.equals(barCode2)
                            && hub_itemCode2.equals(barCode1)
                            && hub_actualPrice1.equals(actualPrice2)
                            && hub_actualPrice2.equals(actualPrice1)
                            && hub_sourceOrderCode1.equals(sourceOrderCode1)
                            && hub_sourceOrderCode2.equals(sourceOrderCode2)
                            && hub_planQty1.equals(planQty1)
                            && hub_planQty2.equals(planQty2)
                            && hub_detailAddress1.equals(fullAddress)
                            && hub_detailAddress2.equals(fullAddress)
                            && hub_orderType1.equals(orderType)
                            && hub_orderType2.equals(orderType)
                            && hub_warehouseCode1.equals(warehouseCode)
                            && hub_warehouseCode2.equals(warehouseCode)
                            && hub_orderFlag1.equals(orderFlag)
                            && hub_orderFlag2.equals(orderFlag)
            ) {
                AutoLogger.log.info("-------------------------------------校验成功 ->> hub-interface的表与oms数据校验一致, 测试通过------------------------------");
            } else {
                AutoLogger.log.info("-------------------------------------校验失败 ->> hub-interface的表与oms数据校验不一致,测试失败-----------------------------");
            }
        } catch (SQLException e) {
//        	AutoLogger.log.info("【interface表没有查询到相关数据, 无法进行数据校验, 如果控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口】");
            e.printStackTrace();
        } finally {
            session.disconnect();
            AutoLogger.log.info("注意事项： 1. 如果interface表没有查询到相关数据或为空, 则无法进行数据校验");
            AutoLogger.log.info("注意事项： 2. 若控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口");
            AutoLogger.log.info("注意事项： 3. 要是hub的interface表找到了, 但是日志显示没有查询到相关数据,则可能是工具存在缺陷");
        }
    }

    /**
     * 验证订单取消 从oms →> hub之间的数据传递，验证的对象： 订单取消请求体 与 hub_interface的_order_cancel表
     *
     * @param body 接口请求体
     */
    public static void orderCancel_dataCheck(String body) {
        try {
            AutoLogger.log.info("----------------------------------------------获--取--HUB--数--据----------------------------------------------------------------");
            conn(hub_dataBase);
            hub_interface();
            String sql = "select * from " + hub_order_cancel + " where DealCode like '%" + deal_code + "%';";
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
            }
            String hub_id = jsonObject.getString("id");
            String hub_DealCode = jsonObject.getString("DealCode");
            String hub_ShopCode = jsonObject.getString("ShopCode");
            String hub_Canceler = jsonObject.getString("Canceler");
            String hub_CancelReason = jsonObject.getString("CancelReason"); //HttpClientKw.DeCode(
            String hub_interface_time = jsonObject.getString("interface_time");
            String hub_interface_status = jsonObject.getString("interface_status");
            String hub_orderId = jsonObject.getString("orderId");
            String hub_CancelDate = jsonObject.getString("CancelDate");

            JSONObject json = new JSONObject();
            json.put("id", hub_id);
            json.put("DealCode", hub_DealCode);
            json.put("ShopCode", hub_ShopCode);
            json.put("Canceler", hub_Canceler);
            json.put("CancelReason", hub_CancelReason);
            json.put("interface__time", hub_interface_time);
            json.put("orderId", hub_orderId);
            json.put("interface_status", hub_interface_status);
            json.put("CancelDate", hub_CancelDate);

            // 获取请求体里的关键字段
            String DealCode = JSONPath.read(body, "$.DealCode").toString();
            String CancelReason = HttpClientKw.DeCode(JSONPath.read(body, "$.CancelReason").toString());
            String shopCode = JSONPath.read(body, "$.shopCode").toString();
            String orderId = JSONPath.read(body, "$.orderId").toString();
            String CancelerW = JSONPath.read(body, "$.Canceler").toString();
            HttpClientKw kw = new HttpClientKw();
            String Canceler = HttpClientKw.DeCode(CancelerW);
            String CancelDate = JSONPath.read(body, "$.CancelDate").toString();

            JSONObject json2 = new JSONObject();
            json2.put("orderId", orderId);
            json2.put("CancelReason", CancelReason);
            json2.put("DealCode", DealCode);
            json2.put("shopCode", shopCode);
            json2.put("Canceler", Canceler);
            json2.put("CancelDate", CancelDate);

            AutoLogger.log.info("【OMS<请求数据>表中的数据为： →】" + json2.toJSONString());
            AutoLogger.log.info("【HUB<插入数据>表中的数据为： →】" + json.toJSONString());

            if (hub_DealCode.equals(DealCode) && hub_ShopCode.equals(shopCode) && hub_Canceler.equals(Canceler)
                    && hub_orderId.equals(orderId)
                    && hub_CancelReason.equals(CancelReason)
            ) {
                AutoLogger.log.info("-------------------------------------校验成功 ->> hub-interface的表与oms数据校验一致, 测试通过-------------------------------------");
            } else {
                AutoLogger.log.info("-------------------------------------校验失败 ->> hub-interface的表与oms数据校验不一致,测试失败-------------------------------------");
            }
        } catch (SQLException e) {
//        	AutoLogger.log.info("【interface表没有查询到相关数据, 无法进行数据校验, 如果控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口】");
            e.printStackTrace();
        } finally {
            session.disconnect();
            AutoLogger.log.info("注意事项： 1. 如果interface表没有查询到相关数据或为空, 则无法进行数据校验");
            AutoLogger.log.info("注意事项： 2. 若控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口");
            AutoLogger.log.info("注意事项： 3. 要是hub的interface表找到了, 但是日志显示没有查询到相关数据,则可能是工具存在缺陷");

        }
    }

    /**
     * 验证推送退货单【单订单】  从 oms →> hub之间的数据传递，验证的对象： 推送退货单请求体 与 hub_interface的_order_return表
     * 验证多个字段： returnOrderCode/orderType/warehouseCode/preDeliveryOrderCode/logisticsName/returnReason
     *
     * @param body 接口请求体
     */
    public static void returnOrder_data_Check(String body) {
        try {
            AutoLogger.log.info("----------------------------------------------获--取--HUB--数--据----------------------------------------------------------------");
            conn(hub_dataBase);
            hub_interface();
            String sql = "select * from " + hub_return_order + " where sourceOrderCode like '%" + deal_code() + "%';";
            System.out.println(sql);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
            }
            HttpClientKw kw = new HttpClientKw();
            String hub_returnOrderCode = jsonObject.getString("returnOrderCode");
            String hub_orderType = jsonObject.getString("orderType");
            String hub_warehouseCode = jsonObject.getString("warehouseCode");
            String hub_preDeliveryOrderCode = jsonObject.getString("preDeliveryOrderCode");
//            String hub_logisticsName = jsonObject.getString("logisticsName");
            String hub_returnReason = jsonObject.getString("returnReason");
            String hub_sourceOrderCode = jsonObject.getString("sourceOrderCode");
            String hub_subSourceOrderCode = jsonObject.getString("subSourceOrderCode");
            String hub_ownerCode = jsonObject.getString("ownerCode");
            String hub_itemCode = jsonObject.getString("itemCode");
            String hub_planQty = jsonObject.getString("planQty");
            String hub_detailAddress = jsonObject.getString("detailAddress");
            String hub_shopCode = jsonObject.getString("shopCode");
            String hub_ReceiptAmount = jsonObject.getString("ReceiptAmount");
            String hub_UnitPrice = jsonObject.getString("UnitPrice");

            JSONObject json = new JSONObject();
            json.put("returnOrderCode", hub_returnOrderCode);
            json.put("warehouseCode", hub_warehouseCode);
            json.put("orderType", hub_orderType);
            json.put("preDeliveryOrderCode", hub_preDeliveryOrderCode);
//            json.put("logisticsName", hub_logisticsName);
            json.put("returnReason", hub_returnReason);
            json.put("sourceOrderCode", hub_sourceOrderCode);
            json.put("subSourceOrderCode", hub_subSourceOrderCode);
            json.put("ownerCode", hub_ownerCode);
            json.put("itemCode", hub_itemCode);
            json.put("planQty", hub_planQty);
            json.put("detailAddress", hub_detailAddress);
            json.put("shopCode", hub_shopCode);
            json.put("ReceiptAmount", hub_ReceiptAmount);
            json.put("UnitPrice", hub_UnitPrice);

            // 获取请求体里的关键字段
            String returnOrderCode = JSONPath.read(body, "$.returnOrder.returnOrderCode").toString();
            String warehouseCode = JSONPath.read(body, "$.returnOrder.warehouseCode").toString();
            String orderType = JSONPath.read(body, "$.returnOrder.orderType").toString();
            String preDeliveryOrderCode = JSONPath.read(body, "$.returnOrder.preDeliveryOrderCode").toString();
//            String logisticsName = JSONPath.read(body, "$.returnOrder.logisticsName").toString();
            String returnReason = HttpClientKw.DeCode((String) JSONPath.read(body, "$.returnOrder.returnReason"));
            String detailAddress = HttpClientKw.DeCode((String) JSONPath.read(body, "$.returnOrder.senderInfo.detailAddress"));
            String sourceOrderCode = JSONPath.read(body, "$.orderLines.orderLine[0].sourceOrderCode").toString();
            String subSourceOrderCode = JSONPath.read(body, "$.orderLines.orderLine[0].subSourceOrderCode").toString();
            String ownerCode = JSONPath.read(body, "$.orderLines.orderLine[0].ownerCode").toString();
            String itemCode = JSONPath.read(body, "$.orderLines.orderLine[0].itemCode").toString();
            String planQty = JSONPath.read(body, "$.orderLines.orderLine[0].planQty").toString();
            String barCode = JSONPath.read(body, "$.orderLines.orderLine[0].extendProps.barCode").toString();
            String UnitPrice = JSONPath.read(body, "$.orderLines.orderLine[0].extendProps.UnitPrice").toString();
            String ReceiptAmount = JSONPath.read(body, "$.extendProps.ReceiptAmount").toString();

            JSONObject json2 = new JSONObject();
            json2.put("returnOrderCode", returnOrderCode);
            json2.put("warehouseCode", warehouseCode);
            json2.put("orderType", orderType);
            json2.put("preDeliveryOrderCode", preDeliveryOrderCode);
//            json2.put("logisticsName", logisticsName);
            json2.put("detailAddress", detailAddress);
            json2.put("returnReason", returnReason);
            json2.put("sourceOrderCode", sourceOrderCode);
            json2.put("subSourceOrderCode", subSourceOrderCode);
            json2.put("ownerCode", ownerCode);
            json2.put("itemCode", itemCode);
            json2.put("planQty", planQty);
            json2.put("barCode", barCode);
            json2.put("UnitPrice", UnitPrice);
            json2.put("ReceiptAmount", ReceiptAmount);

            AutoLogger.log.info("【OMS<请求数据>表中的数据为： →】" + json2.toJSONString());
            AutoLogger.log.info("【HUB<插入数据>表中的数据为： →】" + json.toJSONString());

            if (hub_returnOrderCode.equals(returnOrderCode) && hub_orderType.equals(orderType)
                    && hub_sourceOrderCode.equals(sourceOrderCode) && hub_warehouseCode.equals(warehouseCode)
                    && hub_preDeliveryOrderCode.equals(preDeliveryOrderCode)
                    && hub_returnReason.equals(returnReason) && hub_subSourceOrderCode.equals(subSourceOrderCode)
                    && hub_ownerCode.equals(ownerCode) && hub_itemCode.equals(itemCode) && hub_planQty.equals(planQty)
            ) {
                AutoLogger.log.info("-------------------------------------校验成功 ->> hub-interface的表与oms数据校验一致, 测试通过-------------------------------------");
            } else {
                AutoLogger.log.info("-------------------------------------校验失败 ->> hub-interface的表与oms数据校验不一致,测试失败-------------------------------------");
            }
        } catch (SQLException e) {
//        	AutoLogger.log.info("【interface表没有查询到相关数据, 无法进行数据校验, 如果控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口】");
            e.printStackTrace();
        } finally {
            session.disconnect();
            AutoLogger.log.info("注意事项： 1. 如果interface表没有查询到相关数据或为空, 则无法进行数据校验");
            AutoLogger.log.info("注意事项： 2. 若控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口");
            AutoLogger.log.info("注意事项： 3. 要是hub的interface表找到了, 但是日志显示没有查询到相关数据,则可能是工具存在缺陷");
        }
    }

    /**
     * 验证推送退货单【多订单】  从 oms →> hub之间的数据传递，验证的对象： 推送退货单的请求体 与 hub_interface的_order_return表
     * 验证多个字段： returnOrderCode/orderType/warehouseCode/preDeliveryOrderCode/logisticsName/returnReason
     */
    public static void returnOrderS_data_Check(String body) {
        try {
            AutoLogger.log.info("----------------------------------------------获--取--HUB--数--据----------------------------------------------------------------");
            conn(hub_dataBase);
            hub_interface();
            String sql = "select * from " + hub_return_order + " where sourceOrderCode like '%" + deal_code() + "%'limit 0,1;";
            System.out.println(sql);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
            }
            HttpClientKw kw = new HttpClientKw();
            String hub_returnOrderCode = jsonObject.getString("returnOrderCode");
            String hub_orderType = jsonObject.getString("orderType");
            String hub_warehouseCode = jsonObject.getString("warehouseCode");
            String hub_preDeliveryOrderCode = jsonObject.getString("preDeliveryOrderCode");
            String hub_logisticsName = jsonObject.getString("logisticsName");
            String hub_returnReason = jsonObject.getString("returnReason");
            String hub_sourceOrderCode = jsonObject.getString("sourceOrderCode");
            String hub_subSourceOrderCode = jsonObject.getString("subSourceOrderCode");
            String hub_ownerCode = jsonObject.getString("ownerCode");
            String hub_itemCode = jsonObject.getString("itemCode");
            String hub_barCode = jsonObject.getString("barCode");
            String hub_planQty = jsonObject.getString("planQty");
            String hub_detailAddress = jsonObject.getString("detailAddress");
            String hub_shopCode = jsonObject.getString("shopCode");
            String hub_ReceiptAmount = jsonObject.getString("ReceiptAmount");
            String hub_UnitPrice = jsonObject.getString("UnitPrice");
            String hub_interface_status = jsonObject.getString("interface_status");


            JSONObject json = new JSONObject();
            json.put("returnOrderCode", hub_returnOrderCode);
            json.put("warehouseCode", hub_warehouseCode);
            json.put("orderType", hub_orderType);
            json.put("preDeliveryOrderCode", hub_preDeliveryOrderCode);
            json.put("logisticsName", hub_logisticsName);
            json.put("returnReason", hub_returnReason);
            json.put("sourceOrderCode", hub_sourceOrderCode);
            json.put("subSourceOrderCode", hub_subSourceOrderCode);
            json.put("ownerCode", hub_ownerCode);
            json.put("itemCode", hub_itemCode);
            json.put("barCode", hub_barCode);
            json.put("planQty", hub_planQty);
            json.put("detailAddress", hub_detailAddress);
            json.put("shopCode", hub_shopCode);
            json.put("ReceiptAmount", hub_ReceiptAmount);
            json.put("UnitPrice", hub_UnitPrice);
            json.put("interface_status", hub_interface_status);

            String sql2 = "select * from " + hub_return_order + " where sourceOrderCode like '%" + deal_code() + "%'limit 1,2;";
            AutoLogger.log.info(sql2);
            Statement stm2 = connection.createStatement();
            ResultSet rs2 = stm2.executeQuery(sql2);
            ResultSetMetaData metaData2 = rs2.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount2 = metaData2.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject2 = new JSONObject();        // json对象
            while (rs2.next()) {
                for (int i = 1; i <= columnCount2; i++) {
                    String columnName2 = metaData2.getColumnLabel(i);    // 得到列的别名
                    String Value2 = rs2.getString(columnName2);           // 得到列名
                    jsonObject2.fluentPut(columnName2, Value2);           // 遍历出interface的delivery表中所有的字段和值
                }
            }

            String hub_returnOrderCode2 = jsonObject2.getString("returnOrderCode");
            String hub_orderType2 = jsonObject2.getString("orderType");
            String hub_warehouseCode2 = jsonObject2.getString("warehouseCode");
            String hub_preDeliveryOrderCode2 = jsonObject2.getString("preDeliveryOrderCode");
            String hub_logisticsName2 = jsonObject2.getString("logisticsName");
            String hub_returnReason2 = jsonObject2.getString("returnReason");
            String hub_sourceOrderCode2 = jsonObject2.getString("sourceOrderCode");
            String hub_subSourceOrderCode2 = jsonObject2.getString("subSourceOrderCode");
            String hub_ownerCode2 = jsonObject2.getString("ownerCode");
            String hub_itemCode2 = jsonObject2.getString("itemCode");
            String hub_planQty2 = jsonObject2.getString("planQty");
            String hub_detailAddress2 = jsonObject2.getString("detailAddress");
            String hub_shopCode2 = jsonObject2.getString("shopCode");
            String hub_ReceiptAmount2 = jsonObject2.getString("ReceiptAmount");
            String hub_UnitPrice2 = jsonObject2.getString("UnitPrice");
            String hub_interface_status2 = jsonObject2.getString("interface_status");

            JSONObject json1 = new JSONObject();        // json对象
            json1.put("returnOrderCode", hub_returnOrderCode2);
            json1.put("warehouseCode", hub_warehouseCode2);
            json1.put("orderType", hub_orderType2);
            json1.put("preDeliveryOrderCode", hub_preDeliveryOrderCode2);
            json1.put("logisticsName", hub_logisticsName2);
            json1.put("returnReason", hub_returnReason2);
            json1.put("sourceOrderCode", hub_sourceOrderCode2);
            json1.put("subSourceOrderCode", hub_subSourceOrderCode2);
            json1.put("ownerCode", hub_ownerCode2);
            json1.put("planQty", hub_planQty2);
            json1.put("detailAddress", hub_detailAddress2);
            json1.put("shopCode", hub_shopCode2);
            json1.put("ReceiptAmount", hub_ReceiptAmount2);
            json1.put("UnitPrice", hub_UnitPrice2);
            json1.put("itemCode", hub_itemCode2);
            json1.put("interface_status", hub_interface_status2);

            AutoLogger.log.info("【HUB<插入数据商品1>表中的数据为： →】" + json.toJSONString());
            AutoLogger.log.info("【HUB<插入数据商品2>表中的数据为： →】" + json1.toJSONString());

            // 获取请求体里的关键字段
            String returnOrderCode = JSONPath.read(body, "$.returnOrder.returnOrderCode").toString();
            String warehouseCode = JSONPath.read(body, "$.returnOrder.warehouseCode").toString();
            String orderType = JSONPath.read(body, "$.returnOrder.orderType").toString();
            String preDeliveryOrderCode = JSONPath.read(body, "$.returnOrder.preDeliveryOrderCode").toString();
            String logisticsName = JSONPath.read(body, "$.returnOrder.logisticsName").toString();
            String returnReason = HttpClientKw.DeCode((String) JSONPath.read(body, "$.returnOrder.returnReason"));
            String detailAddress = HttpClientKw.DeCode((String) JSONPath.read(body, "$.returnOrder.senderInfo.detailAddress"));

            String sourceOrderCode = JSONPath.read(body, "$.orderLines.orderLine[0].sourceOrderCode").toString();
            String subSourceOrderCode = JSONPath.read(body, "$.orderLines.orderLine[0].subSourceOrderCode").toString();
            String ownerCode = JSONPath.read(body, "$.orderLines.orderLine[0].ownerCode").toString();
            String itemCode = JSONPath.read(body, "$.orderLines.orderLine[0].itemCode").toString();
            String planQty = JSONPath.read(body, "$.orderLines.orderLine[0].planQty").toString();
            String barCode = JSONPath.read(body, "$.orderLines.orderLine[0].extendProps.barCode").toString();
            String UnitPrice = JSONPath.read(body, "$.orderLines.orderLine[0].extendProps.UnitPrice").toString();
            String ReceiptAmount = JSONPath.read(body, "$.extendProps.ReceiptAmount").toString();

            String sourceOrderCode2 = JSONPath.read(body, "$.orderLines.orderLine[1].sourceOrderCode").toString();
            String subSourceOrderCode2 = JSONPath.read(body, "$.orderLines.orderLine[1].subSourceOrderCode").toString();
            String ownerCode2 = JSONPath.read(body, "$.orderLines.orderLine[1].ownerCode").toString();
            String itemCode2 = JSONPath.read(body, "$.orderLines.orderLine[1].itemCode").toString();
            String planQty2 = JSONPath.read(body, "$.orderLines.orderLine[1].planQty").toString();
            String UnitPrice2 = JSONPath.read(body, "$.orderLines.orderLine[1].extendProps.UnitPrice").toString();


            JSONObject json2 = new JSONObject();
            json2.put("returnOrderCode", returnOrderCode);
            json2.put("warehouseCode", warehouseCode);
            json2.put("orderType", orderType);
            json2.put("preDeliveryOrderCode", preDeliveryOrderCode);
            json2.put("logisticsName", logisticsName);
            json2.put("detailAddress", detailAddress);
            json2.put("returnReason", returnReason);
            json2.put("sourceOrderCode", sourceOrderCode);
            json2.put("subSourceOrderCode", subSourceOrderCode);
            json2.put("ownerCode", ownerCode);
            json2.put("itemCode", itemCode);
            json2.put("planQty", planQty);
            json2.put("barCode", barCode);
            json2.put("UnitPrice", UnitPrice);
            json2.put("ReceiptAmount", ReceiptAmount);

            json2.put("sourceOrderCode2", sourceOrderCode2);
            json2.put("subSourceOrderCode2", subSourceOrderCode2);
            json2.put("ownerCode2", ownerCode2);
            json2.put("itemCode2", itemCode2);
            json2.put("planQty2", planQty2);
            json2.put("UnitPrice2", UnitPrice2);
            AutoLogger.log.info("【OMS<请求数据>表中的数据为： →】" + json2.toJSONString());

            if (hub_returnOrderCode.equals(returnOrderCode)
                    && hub_orderType.equals(orderType)
                    && hub_warehouseCode.equals(warehouseCode)
                    && hub_preDeliveryOrderCode.equals(preDeliveryOrderCode)
                    && hub_logisticsName.equals(logisticsName)
                    && hub_returnReason.equals(returnReason)
                    && hub_sourceOrderCode.equals(sourceOrderCode)
                    && hub_subSourceOrderCode.equals(subSourceOrderCode)
                    && hub_ownerCode.equals(ownerCode)
                    && hub_itemCode.equals(itemCode)
                    && hub_planQty.equals(planQty)
                    && hub_detailAddress.equals(detailAddress)
                    && hub_ReceiptAmount.equals(ReceiptAmount)
                    && hub_UnitPrice.equals(UnitPrice)

                    && hub_sourceOrderCode2.equals(sourceOrderCode2)
                    && hub_subSourceOrderCode2.equals(subSourceOrderCode2)
                    && hub_ownerCode2.equals(ownerCode2)
                    && hub_planQty2.equals(planQty2)
                    && hub_UnitPrice2.equals(UnitPrice2)
            ) {
                AutoLogger.log.info("-------------------------------------校验成功 ->> hub-interface的表与oms数据校验一致, 测试通过-------------------------------------");
            } else {
                AutoLogger.log.info("-------------------------------------校验失败 ->> hub-interface的表与oms数据校验不一致,测试失败-------------------------------------");
            }
        } catch (SQLException e) {
//        	AutoLogger.log.info("【interface表没有查询到相关数据, 无法进行数据校验, 如果控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口】");
            e.printStackTrace();
        } finally {
            AutoLogger.log.info("注意事项： 1. 如果interface表没有查询到相关数据或为空, 则无法进行数据校验");
            AutoLogger.log.info("注意事项： 2. 若控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口");
            AutoLogger.log.info("注意事项： 3. 要是hub的interface表找到了, 但是日志显示没有查询到相关数据,则可能是工具存在缺陷");
            session.disconnect();
        }
    }

    /**
     * 验证推送退货单作废【单订单】  从 oms →> hub之间的数据传递，验证的对象： 通知发货请求体 与 hub_interface的_order_return表
     * 验证多个字段： warehouseCode/ownerCode/orderCode/orderType/cancelReason
     */
    public static void returnRemove_Data_Check(String body) {
        try {
            AutoLogger.log.info("----------------------------------------------获--取--HUB--数--据----------------------------------------------------------------");
            conn(hub_dataBase);
            hub_interface();
            String sql = "select * from " + hub_order_remove + " where orderCode like '%" + return_sn() + "%' limit 0,1;";
            System.out.println(sql);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
            }
            HttpClientKw kw = new HttpClientKw();
            String hub_warehouseCode = jsonObject.getString("warehouseCode");
            String hub_ownerCode = jsonObject.getString("ownerCode");
            String hub_orderCode = jsonObject.getString("orderCode");
            String hub_orderId = jsonObject.getString("orderId");
            String hub_orderType = jsonObject.getString("orderType");
            String hub_cancelReason = jsonObject.getString("cancelReason");
            String hub_interface_status = jsonObject.getString("interface_status");

            JSONObject json = new JSONObject();
            json.put("warehouseCode", hub_warehouseCode);
            json.put("ownerCode", hub_ownerCode);
            json.put("orderCode", hub_orderCode);
            json.put("orderId", hub_orderId);
            json.put("orderType", hub_orderType);
            json.put("cancelReason", hub_cancelReason);
            json.put("interface_status", hub_interface_status);
            AutoLogger.log.info("【HUB<插入数据商品1>表中的数据为： →】" + json.toJSONString());

            // 获取请求体里的关键字段
            String warehouseCode = JSONPath.read(body, "$.warehouseCode").toString();
            String ownerCode = JSONPath.read(body, "$.ownerCode").toString();
            String orderCode = JSONPath.read(body, "$.orderCode").toString();
            String orderType = JSONPath.read(body, "$.orderType").toString();
            String cancelReason = JSONPath.read(body, "$.cancelReason").toString();

            JSONObject json2 = new JSONObject();
            json2.put("warehouseCode", warehouseCode);
            json2.put("ownerCode", ownerCode);
            json2.put("orderCode", orderCode);
            json2.put("orderType", orderType);
            json2.put("cancelReason", cancelReason);

            AutoLogger.log.info("【OMS<请求数据>表中的数据为： →】" + json2.toJSONString());

            if (hub_warehouseCode.equals(warehouseCode)
                    && hub_ownerCode.equals(ownerCode)
                    && hub_orderCode.equals(orderCode)
                    && hub_orderType.equals(orderType)
                    && hub_cancelReason.equals(cancelReason)
            ) {
                AutoLogger.log.info("-------------------------------------校验成功 ->> hub-interface的表与oms数据校验一致, 测试通过-------------------------------------");
            } else {
                AutoLogger.log.info("-------------------------------------校验失败 ->> hub-interface的表与oms数据校验不一致,测试失败-------------------------------------");
            }
        } catch (SQLException e) {
//        	AutoLogger.log.info("【interface表没有查询到相关数据, 无法进行数据校验, 如果控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口】");
            e.printStackTrace();
        } finally {
            session.disconnect();
            AutoLogger.log.info("注意事项： 1. 如果interface表没有查询到相关数据或为空, 则无法进行数据校验");
            AutoLogger.log.info("注意事项： 2. 若控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口");
            AutoLogger.log.info("注意事项： 3. 要是hub的interface表找到了, 但是日志显示没有查询到相关数据,则可能是工具存在缺陷");
        }
    }


    /**
     * 验证推送退款单【单订单】  从 oms →> hub之间的数据传递，验证的对象： 退款单请求体 与 hub_interface的_refund表
     * 验证多个字段： warehouseCode/ownerCode/orderCode/orderType/cancelReason
     */
    public static void refund_Data_Check(String body) {
        try {
            AutoLogger.log.info("----------------------------------------------获--取--HUB--数--据----------------------------------------------------------------");
            conn(hub_dataBase);
            String sql = "select * from " + hub_refund + " where refund_no like '%" + refund_sn + "%'limit 0,1;";
            System.out.println(sql);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
            }
            HttpClientKw kw = new HttpClientKw();
            String hub_storeCode = jsonObject.getString("storeCode");
            String hub_refund_id = jsonObject.getString("refund_id");
            String hub_refund_no = jsonObject.getString("refund_no");
            String hub_omsNo = jsonObject.getString("omsNo");
            String hub_tid = jsonObject.getString("tid");
            String hub_refund_fee = jsonObject.getString("refund_fee");
            String hub_status = jsonObject.getString("status");
            String hub_reason = jsonObject.getString("reason");
            String hub_LineId = jsonObject.getString("LineId");
            String hub_oid = jsonObject.getString("oid");
            String hub_barcode = jsonObject.getString("barcode");
            String hub_price = jsonObject.getString("price");
            String hub_num = jsonObject.getString("num");
            String hub_item_refund_fee = jsonObject.getString("item_refund_fee");
            String hub_interface_status = jsonObject.getString("interface_status");
            String hub_type = jsonObject.getString("type");

            JSONObject json = new JSONObject();
            json.put("storeCode", hub_storeCode);
            json.put("refund_id", hub_refund_id);
            json.put("refund_no", hub_refund_no);
            json.put("omsNo", hub_omsNo);
            json.put("tid", hub_tid);
            json.put("refund_fee", hub_refund_fee);
            json.put("status", hub_status);
            json.put("reason", hub_reason);
            json.put("LineId", hub_LineId);
            json.put("oid", hub_oid);
            json.put("barcode", hub_barcode);
            json.put("price", hub_price);
            json.put("num", hub_num);
            json.put("item_refund_fee", hub_item_refund_fee);
            json.put("interface_status", hub_interface_status);
            json.put("type", hub_type);
            AutoLogger.log.info("【HUB<插入数据商品1>表中的数据为： →】" + json.toJSONString());

            // 获取请求体里的关键字段
            String storeCode = JSONPath.read(body, "$.storeCode").toString();
            String refund_id = JSONPath.read(body, "$.refund_id").toString();
            String omsNo = JSONPath.read(body, "$.omsNo").toString();
            String refund_no = JSONPath.read(body, "$.refund_no").toString();
            String type = JSONPath.read(body, "$.type").toString();
            String tid = JSONPath.read(body, "$.tid").toString();
            String refund_fee = JSONPath.read(body, "$.refund_fee").toString();
            String reason = HttpClientKw.DeCode(JSONPath.read(body, "$.reason").toString());
            String status = JSONPath.read(body, "$.status").toString();
            String LineId = JSONPath.read(body, "$.Items[0].LineId").toString();
            String oid = JSONPath.read(body, "$.Items[0].oid").toString();
            String barcode = JSONPath.read(body, "$.Items[0].barcode").toString();
            String price = JSONPath.read(body, "$.Items[0].price").toString();
            String num = JSONPath.read(body, "$.Items[0].num").toString();
            String item_refund_fee = JSONPath.read(body, "$.Items[0].item_refund_fee").toString();

            JSONObject json2 = new JSONObject();
            json2.put("storeCode", storeCode);
            json2.put("refund_id", refund_id);
            json2.put("omsNo", omsNo);
            json2.put("refund_no", refund_no);
            json2.put("type", type);
            json2.put("tid", tid);
            json2.put("refund_fee", refund_fee);
            json2.put("reason", reason);
            json2.put("status", status);
            json2.put("oid", oid);
            json2.put("barcode", barcode);
            json2.put("price", price);
            json2.put("item_refund_fee", item_refund_fee);
            json2.put("num", num);
            json2.put("LineId", LineId);

            AutoLogger.log.info("【OMS<请求数据>表中的数据为： →】" + json2.toJSONString());

            if (hub_storeCode.equals(storeCode)
                    && hub_refund_id.equals(refund_id)
                    && hub_refund_no.equals(refund_no)
                    && hub_omsNo.equals(omsNo)
                    && hub_tid.equals(tid)
                    && hub_refund_fee.equals(refund_fee)
                    && hub_status.equals(status)
                    && hub_reason.equals(reason)
                    && hub_LineId.equals(LineId)
                    && hub_oid.equals(oid)
                    && hub_barcode.equals(barcode)
                    && hub_price.equals(price)
                    && hub_num.equals(num)
                    && hub_item_refund_fee.equals(item_refund_fee)
                    && hub_type.equals(type)
            ) {
                AutoLogger.log.info("-------------------------------------校验成功 ->> hub-interface的表与oms数据校验一致, 测试通过-------------------------------------");
            } else {
                AutoLogger.log.info("-------------------------------------校验失败 ->> hub-interface的表与oms数据校验不一致,测试失败-------------------------------------");
            }
        } catch (SQLException e) {
//        	AutoLogger.log.info("【interface表没有查询到相关数据, 无法进行数据校验, 如果控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口】");
            e.printStackTrace();
        } finally {
            session.disconnect();
            AutoLogger.log.info("注意事项： 1. 如果interface表没有查询到相关数据或为空, 则无法进行数据校验");
            AutoLogger.log.info("注意事项： 2. 若控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口");
            AutoLogger.log.info("注意事项： 3. 要是hub的interface表找到了, 但是日志显示没有查询到相关数据,则可能是工具存在缺陷");
        }
    }

    /**
     * 验证推送退款单【多订单】  从 oms →> hub之间的数据传递，验证的对象： 退款单请求体 与 hub_interface的_refund表
     * 验证多个字段： warehouseCode/ownerCode/orderCode/orderType/cancelReason
     *
     * @param body 接口请求体
     */
    public static void refundS_Data_Check(String body) {
        try {
            AutoLogger.log.info("----------------------------------------------获--取--HUB--数--据----------------------------------------------------------------");
            conn(hub_dataBase);
            String sql = "select * from " + hub_refund + " where refund_no like '%" + refund_sn + "%'limit 0,1;";
//            System.out.println(sql);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
            }
            HttpClientKw kw = new HttpClientKw();
            String hub_storeCode = jsonObject.getString("storeCode");
            String hub_refund_id = jsonObject.getString("refund_id");
            String hub_refund_no = jsonObject.getString("refund_no");
            String hub_omsNo = jsonObject.getString("omsNo");
            String hub_tid = jsonObject.getString("tid");
            String hub_refund_fee = jsonObject.getString("refund_fee");
            String hub_status = jsonObject.getString("status");
            String hub_reason = jsonObject.getString("reason");
            String hub_LineId = jsonObject.getString("LineId");
            String hub_oid = jsonObject.getString("oid");
            String hub_barcode = jsonObject.getString("barcode");
            String hub_price = jsonObject.getString("price");
            String hub_num = jsonObject.getString("num");
            String hub_item_refund_fee = jsonObject.getString("item_refund_fee");
            String hub_interface_status = jsonObject.getString("interface_status");
            String hub_type = jsonObject.getString("type");

            JSONObject json = new JSONObject();
            json.put("storeCode", hub_storeCode);
            json.put("refund_id", hub_refund_id);
            json.put("refund_no", hub_refund_no);
            json.put("omsNo", hub_omsNo);
            json.put("tid", hub_tid);
            json.put("refund_fee", hub_refund_fee);
            json.put("status", hub_status);
            json.put("reason", hub_reason);
            json.put("LineId", hub_LineId);
            json.put("oid", hub_oid);
            json.put("barcode", hub_barcode);
            json.put("price", hub_price);
            json.put("num", hub_num);
            json.put("item_refund_fee", hub_item_refund_fee);
            json.put("interface_status", hub_interface_status);
            json.put("type", hub_type);
            AutoLogger.log.info("【HUB<插入数据商品1>表中的数据为： →】" + json.toJSONString());

            String sql2 = "select * from " + hub_refund + " where refund_no like '%" + refund_sn + "%'limit 1,2;";
//            System.out.println(sql2);
            Statement stm2 = connection.createStatement();
            ResultSet rs2 = stm2.executeQuery(sql2);
            ResultSetMetaData metaData2 = rs2.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount2 = metaData2.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject2 = new JSONObject();        // json数组
            while (rs2.next()) {
                for (int i = 1; i <= columnCount2; i++) {
                    String columnName = metaData2.getColumnLabel(i);    // 得到列的别名
                    String Value = rs2.getString(columnName);           // 得到列名
                    jsonObject2.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
            }
            String hub_storeCode2 = jsonObject2.getString("storeCode");
            String hub_refund_id2 = jsonObject2.getString("refund_id");
            String hub_refund_no2 = jsonObject2.getString("refund_no");
            String hub_omsNo2 = jsonObject2.getString("omsNo");
            String hub_tid2 = jsonObject2.getString("tid");
            String hub_refund_fee2 = jsonObject2.getString("refund_fee");
            String hub_status2 = jsonObject2.getString("status");
            String hub_reason2 = jsonObject2.getString("reason");
            String hub_LineId2 = jsonObject2.getString("LineId");
            String hub_oid2 = jsonObject2.getString("oid");
            String hub_barcode2 = jsonObject2.getString("barcode");
            String hub_price2 = jsonObject2.getString("price");
            String hub_num2 = jsonObject2.getString("num");
            String hub_item_refund_fee2 = jsonObject2.getString("item_refund_fee");
            String hub_interface_status2 = jsonObject2.getString("interface_status");
            String hub_type2 = jsonObject.getString("type");

            JSONObject json2 = new JSONObject();
            json2.put("storeCode", hub_storeCode2);
            json2.put("refund_id", hub_refund_id2);
            json2.put("refund_no", hub_refund_no2);
            json2.put("omsNo", hub_omsNo2);
            json2.put("tid", hub_tid2);
            json2.put("refund_fee", hub_refund_fee2);
            json2.put("status", hub_status2);
            json2.put("reason", hub_reason2);
            json2.put("LineId", hub_LineId2);
            json2.put("oid", hub_oid2);
            json2.put("barcode", hub_barcode2);
            json2.put("price", hub_price2);
            json2.put("num", hub_num2);
            json2.put("item_refund_fee", hub_item_refund_fee2);
            json2.put("interface_status", hub_interface_status2);
            json2.put("type", hub_type2);
            AutoLogger.log.info("【HUB<插入数据商品2>表中的数据为： →】" + json2.toJSONString());

            // 获取请求体里的关键字段
            String storeCode = JSONPath.read(body, "$.storeCode").toString();
            String refund_id = JSONPath.read(body, "$.refund_id").toString();
            String omsNo = JSONPath.read(body, "$.omsNo").toString();
            String refund_no = JSONPath.read(body, "$.refund_no").toString();
            String type = JSONPath.read(body, "$.type").toString();
            String tid = JSONPath.read(body, "$.tid").toString();
            String refund_fee = JSONPath.read(body, "$.refund_fee").toString();
            String reason = HttpClientKw.DeCode(JSONPath.read(body, "$.reason").toString());
            String status = JSONPath.read(body, "$.status").toString();
            String LineId = JSONPath.read(body, "$.Items[0].LineId").toString();
            String oid = JSONPath.read(body, "$.Items[0].oid").toString();
            String barcode = JSONPath.read(body, "$.Items[0].barcode").toString();
            String price = JSONPath.read(body, "$.Items[0].price").toString();
            String num = JSONPath.read(body, "$.Items[0].num").toString();
            String item_refund_fee = JSONPath.read(body, "$.Items[0].item_refund_fee").toString();

            String LineId2 = JSONPath.read(body, "$.Items[1].LineId").toString();
            String oid2 = JSONPath.read(body, "$.Items[1].oid").toString();
            String barcode2 = JSONPath.read(body, "$.Items[1].barcode").toString();
            String price2 = JSONPath.read(body, "$.Items[1].price").toString();
            String num2 = JSONPath.read(body, "$.Items[1].num").toString();
            String item_refund_fee2 = JSONPath.read(body, "$.Items[1].item_refund_fee").toString();

            JSONObject jsonW = new JSONObject();
            jsonW.put("storeCode", storeCode);
            jsonW.put("omsNo", omsNo);
            jsonW.put("refund_no", refund_no);
            jsonW.put("type", type);
            jsonW.put("tid", tid);
            jsonW.put("refund_fee", refund_fee);
            jsonW.put("reason", reason);
            jsonW.put("status", status);

            jsonW.put("LineId", LineId);
            jsonW.put("oid", oid);
            jsonW.put("barcode", barcode);
            jsonW.put("item_refund_fee", item_refund_fee);
            jsonW.put("price", price);
            jsonW.put("num", num);

            jsonW.put("LineId2", LineId2);
            jsonW.put("oid2", oid2);
            jsonW.put("barcode2", barcode2);
            jsonW.put("item_refund_fee2", item_refund_fee2);
            jsonW.put("price2", price2);
            jsonW.put("num2", num2);
            AutoLogger.log.info("【OMS<请求数据>表中的数据为： →】" + jsonW.toJSONString());

            if (hub_status.equals(status)

                    && hub_LineId.equals(LineId)
                    && hub_oid.equals(oid)
                    && hub_barcode.equals(barcode)
                    && hub_price.equals(price)
                    && hub_num.equals(num)
                    && hub_item_refund_fee.equals(item_refund_fee)
                    && hub_type.equals(type)

                    && hub_LineId2.equals(LineId2)
                    && hub_oid2.equals(oid2)
                    && hub_barcode2.equals(barcode2)
                    && hub_price2.equals(price2)
                    && hub_num2.equals(num2)
                    && hub_item_refund_fee2.equals(item_refund_fee2)

            ) {
                AutoLogger.log.info("-------------------------------------校验成功 ->> hub-interface的表与oms数据校验一致, 测试通过-------------------------------------");
            } else {
                AutoLogger.log.info("-------------------------------------校验失败 ->> hub-interface的表与oms数据校验不一致,测试失败-------------------------------------");
            }
        } catch (SQLException e) {
//        	AutoLogger.log.info("【interface表没有查询到相关数据, 无法进行数据校验, 如果控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口】");
            e.printStackTrace();
        } finally {
            session.disconnect();
            AutoLogger.log.info("注意事项： 1. 如果interface表没有查询到相关数据或为空, 则无法进行数据校验");
            AutoLogger.log.info("注意事项： 2. 若控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口");
            AutoLogger.log.info("注意事项： 3. 要是hub的interface表找到了, 但是日志显示没有查询到相关数据,则可能是工具存在缺陷");

        }
    }

    /**
     * 验证OMS退货入库匹配后通知对接平台【单订单】  从 oms →> hub之间的数据传递，验证的对象： 退货入库匹配 与 hub_interface的return_confirm表
     * 验证多个字段： warehouseCode/ownerCode/orderCode/orderType/cancelReason
     *
     * @param body 接口请求体
     */
    public static void refunded_Data_Check(String body) {
        try {
            AutoLogger.log.info("----------------------------------------------获--取--HUB--数--据----------------------------------------------------------------");
            conn(hub_dataBase);
            String sql = "select * from " + hub_return_confirm + " where omsReturnNo like '%" + return_sn + "%'limit 0,1;";
            System.out.println(sql);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
            }
            HttpClientKw kw = new HttpClientKw();
            String hub_storeCode = jsonObject.getString("storeCode");
            String hub_omsReturnNo = jsonObject.getString("omsReturnNo");
            String hub_omsNo = jsonObject.getString("omsNo");
            String hub_tid = jsonObject.getString("tid");
            String hub_logisticsCode = jsonObject.getString("logisticsCode");
            String hub_logisticsName = jsonObject.getString("logisticsName");
            String hub_logisticsNo = jsonObject.getString("logisticsNo");
            String hub_backReason = jsonObject.getString("backReason");
            String hub_LineId = jsonObject.getString("LineId");
            String hub_oid = jsonObject.getString("oid");
            String hub_barcode = jsonObject.getString("barcode");
            String hub_num = jsonObject.getString("num");
            String hub_interface_status = jsonObject.getString("interface_status");

            JSONObject json = new JSONObject();
            json.put("storeCode", hub_storeCode);
            json.put("omsReturnNo", hub_omsReturnNo);
            json.put("omsNo", hub_omsNo);
            json.put("tid", hub_tid);
            json.put("logisticsCode", hub_logisticsCode);
            json.put("logisticsName", hub_logisticsName);
            json.put("logisticsNo", hub_logisticsNo);
            json.put("backReason", hub_backReason);
            json.put("LineId", hub_LineId);
            json.put("oid", hub_oid);
            json.put("barcode", hub_barcode);
            json.put("num", hub_num);
            json.put("interface_status", hub_interface_status);
            AutoLogger.log.info("【HUB<插入数据商品1>表中的数据为： →】" + json.toJSONString());

            //  获取请求体里的关键字段
            String storeCode = JSONPath.read(body, "$.storeCode").toString();
            String omsNo = JSONPath.read(body, "$.omsNo").toString();
            String omsReturnNo = JSONPath.read(body, "$.omsReturnNo").toString();
            String tid = JSONPath.read(body, "$.tid").toString();
            String logisticsCode = JSONPath.read(body, "$.logisticsCode").toString();
            String logisticsName = JSONPath.read(body, "$.logisticsName").toString();
            String logisticsNo = JSONPath.read(body, "$.logisticsNo").toString();
            String backReason = HttpClientKw.DeCode(JSONPath.read(body, "$.backReason").toString());
            String type = JSONPath.read(body, "$.type").toString();

//            String LineId = JSONPath.read(body, "$.Items[0].LineId").toString();
            String barcode = JSONPath.read(body, "$.Items[0].barcode").toString();
            String num = JSONPath.read(body, "$.Items[0].num").toString();
            String oid = JSONPath.read(body, "$.Items[0].oid").toString();

            JSONObject jsonW = new JSONObject();
            jsonW.put("storeCode", storeCode);
            jsonW.put("omsNo", omsNo);
            jsonW.put("omsReturnNo", omsReturnNo);
            jsonW.put("logisticsCode", logisticsCode);
            jsonW.put("logisticsName", logisticsName);
            jsonW.put("logisticsNo", logisticsNo);
            jsonW.put("backReason", backReason);
            jsonW.put("type", type);
//            jsonW.put("LineId", LineId);
            jsonW.put("barcode", barcode);
            jsonW.put("num", num);
            jsonW.put("oid", oid);
            AutoLogger.log.info("【OMS<请求数据>表中的数据为： →】" + jsonW.toJSONString());

            if (hub_storeCode.equals(storeCode)
                    && hub_omsReturnNo.equals(omsReturnNo)
                    && hub_omsNo.equals(omsNo)
                    && hub_tid.equals(tid)
                    && hub_logisticsCode.equals(logisticsCode)
                    && hub_logisticsName.equals(logisticsName)
                    && hub_logisticsNo.equals(logisticsNo)
                    && hub_backReason.equals(backReason)
//                    && hub_LineId.equals(LineId)
                    && hub_oid.equals(oid)
                    && hub_barcode.equals(barcode)
                    && hub_num.equals(num)
            ) {
                AutoLogger.log.info("-------------------------------------校验成功 ->> hub-interface的表与oms数据校验一致, 测试通过-------------------------------------");
            } else {
                AutoLogger.log.info("-------------------------------------校验失败 ->> hub-interface的表与oms数据校验不一致,测试失败-------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            AutoLogger.log.info("注意事项： 1. 如果interface表没有查询到相关数据或为空, 则无法进行数据校验");
            AutoLogger.log.info("注意事项： 2. 若控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口");
            AutoLogger.log.info("注意事项： 3. 要是hub的interface表找到了, 但是日志显示没有查询到相关数据,则可能是工具存在缺陷");
            session.disconnect();
        }
    }

    /**
     * 验证OMS退货入库匹配后通知对接平台【多订单】  从 oms →> hub之间的数据传递，验证的对象： 退货入库匹配 与 hub_interface的return_confirm表
     * 验证多个字段： warehouseCode/ownerCode/orderCode/orderType/cancelReason
     *
     * @param body 接口请求体
     */
    public static void refundedS_Data_Check(String body) {
        try {
            AutoLogger.log.info("----------------------------------------------获--取--HUB--数--据----------------------------------------------------------------");
            conn(hub_dataBase);
            String sql = "select * from " + hub_return_confirm + " where omsReturnNo like '%" + return_sn + "%'limit 0,1;";
            System.out.println(sql);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
            }
            HttpClientKw kw = new HttpClientKw();
            String hub_storeCode = jsonObject.getString("storeCode");
            String hub_omsReturnNo = jsonObject.getString("omsReturnNo");
            String hub_omsNo = jsonObject.getString("omsNo");
            String hub_tid = jsonObject.getString("tid");
            String hub_logisticsCode = jsonObject.getString("logisticsCode");
            String hub_logisticsName = jsonObject.getString("logisticsName");
            String hub_logisticsNo = jsonObject.getString("logisticsNo");
            String hub_backReason = jsonObject.getString("backReason");
            String hub_LineId = jsonObject.getString("LineId");
            String hub_oid = jsonObject.getString("oid");
            String hub_barcode = jsonObject.getString("barcode");
            String hub_num = jsonObject.getString("num");
            String hub_interface_status = jsonObject.getString("interface_status");

            JSONObject json = new JSONObject();
            json.put("storeCode", hub_storeCode);
            json.put("omsReturnNo", hub_omsReturnNo);
            json.put("omsNo", hub_omsNo);
            json.put("tid", hub_tid);
            json.put("logisticsCode", hub_logisticsCode);
            json.put("logisticsName", hub_logisticsName);
            json.put("logisticsNo", hub_logisticsNo);
            json.put("backReason", hub_backReason);
            json.put("LineId", hub_LineId);
            json.put("oid", hub_oid);
            json.put("barcode", hub_barcode);
            json.put("num", hub_num);
            json.put("interface_status", hub_interface_status);
            AutoLogger.log.info("【HUB<插入数据商品1>表中的数据为： →】" + json.toJSONString());

            String sql2 = "select * from " + hub_return_confirm + " where omsReturnNo like '%" + return_sn + "%'limit 1,2;";
            System.out.println(sql2);
            Statement stm2 = connection.createStatement();
            ResultSet rs2 = stm2.executeQuery(sql2);
            ResultSetMetaData metaData2 = rs2.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount2 = metaData2.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject2 = new JSONObject();        // json数组
            while (rs2.next()) {
                for (int i = 1; i <= columnCount2; i++) {
                    String columnName = metaData2.getColumnLabel(i);    // 得到列的别名
                    String Value = rs2.getString(columnName);           // 得到列名
                    jsonObject2.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
            }
            String hub_storeCode2 = jsonObject2.getString("storeCode");
            String hub_omsReturnNo2 = jsonObject2.getString("omsReturnNo");
            String hub_omsNo2 = jsonObject2.getString("omsNo");
            String hub_tid2 = jsonObject2.getString("tid");
            String hub_logisticsCode2 = jsonObject2.getString("logisticsCode");
            String hub_logisticsName2 = jsonObject2.getString("logisticsName");
            String hub_logisticsNo2 = jsonObject2.getString("logisticsNo");
            String hub_backReason2 = jsonObject2.getString("backReason");
            String hub_LineId2 = jsonObject2.getString("LineId");
            String hub_oid2 = jsonObject2.getString("oid");
            String hub_barcode2 = jsonObject2.getString("barcode");
            String hub_num2 = jsonObject2.getString("num");
            String hub_interface_status2 = jsonObject2.getString("interface_status");

            JSONObject json2 = new JSONObject();
            json2.put("storeCode", hub_storeCode2);
            json2.put("omsReturnNo", hub_omsReturnNo2);
            json2.put("omsNo", hub_omsNo2);
            json2.put("tid", hub_tid2);
            json2.put("logisticsCode", hub_logisticsCode2);
            json2.put("logisticsName", hub_logisticsName2);
            json2.put("logisticsNo", hub_logisticsNo2);
            json2.put("backReason", hub_backReason2);
            json2.put("LineId", hub_LineId2);
            json2.put("oid", hub_oid2);
            json2.put("barcode", hub_barcode2);
            json2.put("num", hub_num2);
            json2.put("interface_status", hub_interface_status2);
            AutoLogger.log.info("【HUB<插入数据商品2>表中的数据为： →】" + json2.toJSONString());

            //  获取请求体里的关键字段
            String storeCode = JSONPath.read(body, "$.storeCode").toString();
            String omsNo = JSONPath.read(body, "$.omsNo").toString();
            String omsReturnNo = JSONPath.read(body, "$.omsReturnNo").toString();
            String tid = JSONPath.read(body, "$.tid").toString();
            String logisticsCode = JSONPath.read(body, "$.logisticsCode").toString();
            String logisticsName = JSONPath.read(body, "$.logisticsName").toString();
            String logisticsNo = JSONPath.read(body, "$.logisticsNo").toString();
            String backReason = HttpClientKw.DeCode(JSONPath.read(body, "$.backReason").toString());
            String type = JSONPath.read(body, "$.type").toString();

            String LineId = JSONPath.read(body, "$.Items[0].LineId").toString();
            String barcode = JSONPath.read(body, "$.Items[0].barcode").toString();
            String num = JSONPath.read(body, "$.Items[0].num").toString();
            String oid = JSONPath.read(body, "$.Items[0].oid").toString();

            String LineId2 = JSONPath.read(body, "$.Items[1].LineId").toString();
            String barcode2 = JSONPath.read(body, "$.Items[1].barcode").toString();
            String num2 = JSONPath.read(body, "$.Items[1].num").toString();
            String oid2 = JSONPath.read(body, "$.Items[1].oid").toString();

            JSONObject jsonW = new JSONObject();
            jsonW.put("storeCode", storeCode);
            jsonW.put("omsNo", omsNo);
            jsonW.put("omsReturnNo", omsReturnNo);
            jsonW.put("logisticsCode", logisticsCode);
            jsonW.put("logisticsName", logisticsName);
            jsonW.put("logisticsNo", logisticsNo);
            jsonW.put("backReason", backReason);
            jsonW.put("type", type);
            jsonW.put("LineId", LineId);
            jsonW.put("barcode", barcode);
            jsonW.put("num", num);
            jsonW.put("oid", oid);

            jsonW.put("LineId2", LineId2);
            jsonW.put("barcode2", barcode2);
            jsonW.put("num2", num2);
            jsonW.put("oid2", oid2);
            AutoLogger.log.info("【OMS<请求数据>表中的数据为： →】" + jsonW.toJSONString());

            if (hub_storeCode.equals(storeCode)
                    && hub_omsReturnNo.equals(omsReturnNo)
                    && hub_omsNo.equals(omsNo)
                    && hub_tid.equals(tid)
                    && hub_logisticsCode.equals(logisticsCode)
                    && hub_logisticsName.equals(logisticsName)
                    && hub_logisticsNo.equals(logisticsNo)
                    && hub_backReason.equals(backReason)
                    && hub_LineId.equals(LineId)
                    && hub_oid.equals(oid)
                    && hub_barcode.equals(barcode)
                    && hub_num.equals(num)

                    && hub_LineId2.equals(LineId2)
                    && hub_oid2.equals(oid2)
                    && hub_barcode2.equals(barcode2)
                    && hub_num2.equals(num2)
            ) {
                AutoLogger.log.info("-------------------------------------校验成功 ->> hub-interface的表与oms数据校验一致, 测试通过-------------------------------------");
            } else {
                AutoLogger.log.info("-------------------------------------校验失败 ->> hub-interface的表与oms数据校验不一致,测试失败-------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            AutoLogger.log.info("注意事项： 1. 如果interface表没有查询到相关数据或为空, 则无法进行数据校验");
            AutoLogger.log.info("注意事项： 2. 若控制台日志显示接口执行正常, 请仔细检查该接口是否是走hub或该品牌是否支持该接口");
            AutoLogger.log.info("注意事项： 3. 要是hub的interface表找到了, 但是日志显示没有查询到相关数据,则可能是工具存在缺陷");
        }
    }


    /**
     * 发货数据推送(不包含对账)
     * bq_interface中生成的数据：只包含发货数据的话 push_type 应等于1
     * oms_synchronize_log表中的 interface_deliver_data , 表中的lasttime字段需关联
     */
    public static void sales_data_push(String deal_code) {
        try {
            conn(dataBase());
            AutoLogger.log.info("----------------------------------------------获--取--推送-发货--数--据---------------------------------------------------------------");
            String sql = "select oi.order_id,oi.order_sn,oi.sd_id,oi.order_type,og.tid,oi.add_time,oi.pay_time,oi.pay_final_time,FROM_UNIXTIME(oi.shipping_time) as shipping_time,CASE oi.pick_type WHEN 2 THEN oi.accept_time ELSE og.end_time END AS end_time,og.shipping_name,og.tax_amount,\n" +
                    "og.invoice_no,oi.shipping_fee,oi.consignee,oi.user_nick,oi.user_alipay,oi.alipay_no,oi.tel,oi.mobile,oi.email,oi.zipcode,oi.province,oi.city,oi.district,\n" +
                    "oi.address,oi.postscript,oi.to_buyer,oi.invoice_status,oi.total_amount,oi.money_paid,og.oid,og.deliver_number as goods_number,bgb.goods_sales_type,\n" +
                    "og.market_price,og.goods_price,bgb.goods_sn,bgb.goods_name,bgb.size_name,bgb.size_code,bgb.color_code,bgb.color_name,bgb.expand_color,bgb.barcode,1 as push_type,\n" +
                    "oi.pay_way_type as payment_method,oi.from_type,oi.is_hand,oi.exchange_sn,shop.shop_name,shop.platform,oi.forefather_djbh,ss.store_code,oi.pick_type,og.id as order_goods_id from order_info as oi\n" +
                    "inner join order_goods as og on oi.order_id = og.order_id\n" +
                    "inner join base_goods_barcodes as bgb on og.goods_barcode = bgb.barcode \n" +
                    "inner join sys_shops as shop on oi.sd_id = shop.id\n" +
                    "left join sys_stores as ss on ss.id = oi.shipping_store\n" +
                    "where oi.order_status=2 and og.deliver_number>0 and oi.pay_status=1 and deal_code=" + deal_code + " and oi.shipping_time>='" + lasttime + "' and oi.shipping_time< now();";
//            AutoLogger.log.info(sql);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
                if (jsonObject.toJSONString().contains(deal_code)) {
                    AutoLogger.log.info("在oms数据库中成功获取到推送发货数据 -->> " + jsonObject.toJSONString());
                } else {
                    AutoLogger.log.info("在oms数据库中获取推送退货数据 -->>  失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
        }
    }

    /**
     * 退货数据推送(不包含对账 ->>【退货推送节点为：匹配触发推送】)
     * bq_interface中生成的数据：只包含退货数据的话 push_type 应等于1
     * oms_synchronize_log表中的 interface_return_data , 表中的lasttime字段需关联
     */
    public static void returnMatch_data_push(String deal_code) {
        try {
            conn(dataBase());
            AutoLogger.log.info("----------------------------------------------获--取--推送-退货--数--据---------------------------------------------------------------");
            String sql = "select oi.order_id,oi.order_sn,ort.return_id,ort.return_sn,now() as interface_time,ort.sd_id,1 as return_types,oi.order_type,1 as push_type,\n" +
                    "og.tid,ort.created,ort.match_time,orw.reback_id,orw.reback_sn,orw.push_num,shop.shop_name,shop.platform,\n" +
                    "ort.match_time as completed_time,ort.shipping_name,ort.invoice_no,ort.shipping_fee,\n" +
                    "ort.consignee,ort.buyer_nick as user_nick,oi.user_alipay,oi.alipay_no,oi.tel,ort.mobile,oi.email,oi.zipcode,oi.province,\n" +
                    "oi.city,oi.district,oi.address,oi.postscript,oi.to_buyer,ort.reason,'' as payment_method,bgb.goods_sales_type,\n" +
                    "oi.total_amount,oi.money_paid,org.oid,bgb.goods_sn,bgb.goods_name,bgb.color_code,bgb.color_name,bgb.expand_color,bgb.size_name,bgb.size_code,bgb.barcode,\n" +
                    "org.return_goods_num,org.goods_price,og.market_price,og.money_paid as og_money_paid,org.refund_fee,ort.return_type,og.end_time,oi.is_hand,\n" +
                    "ss.warehouse_type,oi.pay_way_type,oi.forefather_djbh,ss.store_code,oi.exchange_sn,if(ort.return_type=2 || ort.service_type=2,4,3) as bz5,\n" +
                    "oi.pick_type,if(ort.status='SUCCESS',1,0) as is_refund,og.id as order_goods_id,orw.serial_no as bz11 from order_return as ort\n" +
                    "inner join order_return_goods as org on org.return_id = ort.return_id \n" +
                    "inner join order_reback_warehouse as orw on orw.return_sn = ort.return_sn and orw.order_return_goods_id = org.id\n" +
                    "inner join order_goods as og on og.id = org.order_goods_id\n" +
                    "inner join order_info as oi on oi.order_sn = org.order_sn\n" +
                    "inner join base_goods_barcodes as bgb on bgb.barcode = org.goods_barcode\n" +
                    "inner join sys_shops as shop on oi.sd_id = shop.id\n" +
                    "inner join sys_stores as ss on ss.id = orw.return_warehouse\n" +
                    "where ort.return_status!=4 and org.is_delete=0 and oi.order_status=2 and org.return_goods_num>0\n" +
                    "and ort.return_match=1 and oi.deal_code = '" + deal_code + "' and ort.match_time>= '" + lasttime2 + "' and ort.match_time< now();";
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
                if (jsonObject.toJSONString().contains(deal_code)) {
                    AutoLogger.log.info("在oms数据库中成功获取到推送退货数据 -->> " + jsonObject.toJSONString());
                } else {
                    AutoLogger.log.info("在oms数据库中获取推送退货数据 -->>  失败");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
        }
    }

    /**
     * 退货数据推送(不包含对账 ->>【退货推送节点为：完结触发推送】)
     * bq_interface中生成的数据：只包含退货数据的话 push_type 应等于1
     */
    public static void returnEnd_data_push(String deal_code) {
        // 获取order_return中的match_time
        try {
            conn(dataBase());
            String sqlQuery = "select match_time from order_return where deal_code(='" + deal_code + "';";
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            // 展开结果集
            while (rs.next()) {
                // 通过字段检索
                return_match_time = rs.getString("match_time");
                String sqlResult = "在表中【order_return】获取到的" + "match_time: " + return_match_time;
                AutoLogger.log.info(sqlResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            AutoLogger.log.info("----------------------------------------------获--取--推送-退货--数--据---------------------------------------------------------------");
            String sql = "select oi.order_id,oi.order_sn,ort.return_id,ort.return_sn,now() as interface_time,ort.sd_id,1 as return_types,oi.order_type,1 as push_type,\n" +
                    "og.tid,ort.created,ort.match_time,orw.reback_id,orw.reback_sn,orw.push_num,shop.shop_name,shop.platform,\n" +
                    "ort.match_time as completed_time,ort.shipping_name,ort.invoice_no,ort.shipping_fee,\n" +
                    "ort.consignee,ort.buyer_nick as user_nick,oi.user_alipay,oi.alipay_no,oi.tel,ort.mobile,oi.email,oi.zipcode,oi.province,\n" +
                    "oi.city,oi.district,oi.address,oi.postscript,oi.to_buyer,ort.reason,'' as payment_method,bgb.goods_sales_type,\n" +
                    "oi.total_amount,oi.money_paid,org.oid,bgb.goods_sn,bgb.goods_name,bgb.color_code,bgb.color_name,bgb.expand_color,bgb.size_name,bgb.size_code,bgb.barcode,\n" +
                    "org.return_goods_num,org.goods_price,og.market_price,og.money_paid as og_money_paid,org.refund_fee,ort.return_type,og.end_time,oi.is_hand,\n" +
                    "ss.warehouse_type,oi.pay_way_type,oi.forefather_djbh,ss.store_code,oi.exchange_sn,if(ort.return_type=2 || ort.service_type=2,4,3) as bz5,\n" +
                    "oi.pick_type,if(ort.status='SUCCESS',1,0) as is_refund,og.id as order_goods_id,orw.serial_no as bz11 from order_return as ort\n" +
                    "inner join order_return_goods as org on org.return_id = ort.return_id \n" +
                    "inner join order_reback_warehouse as orw on orw.return_sn = ort.return_sn and orw.order_return_goods_id = org.id\n" +
                    "inner join order_goods as og on og.id = org.order_goods_id\n" +
                    "inner join order_info as oi on oi.order_sn = org.order_sn\n" +
                    "inner join base_goods_barcodes as bgb on bgb.barcode = org.goods_barcode\n" +
                    "inner join sys_shops as shop on oi.sd_id = shop.id\n" +
                    "inner join sys_stores as ss on ss.id = orw.return_warehouse\n" +
                    "where ort.return_status!=4 and org.is_delete=0 and oi.order_status=2 and org.return_goods_num>0\n" +
                    "and ort.return_match=1 and ort.match_time>='" + return_match_time + "' and ort.match_time< now() and oi.deal_code='" + deal_code + "';";
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
                if (jsonObject.toJSONString().contains(deal_code)) {
                    AutoLogger.log.info("在oms数据库中成功获取到推送退货数据 -->> " + jsonObject.toJSONString());
                } else {
                    AutoLogger.log.info("在oms数据库中获取推送退货数据 -->>  失败");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
        }
    }

    /**
     * 发货数据推送(包含对账 ->> 【退款推送节点为：到账触发推送,退款单完结之后推！！！】 )
     * 推送对账数据的话，对于中间表会推送两条，push_type=1为发货，push_type=2为对账。
     * 在此基础上，修改order_goods内tm_status=‘TRADE_FINISHED’,且oid不能为空，end_time不能为空。
     */
    public static void refundAccount_data_push() {
        try {
            String tm_status;
            conn(dataBase());
            String sqlQuery = "update order_goods set end_time = now(), tm_status = 'TRADE_FINISHED' where tid = '" + deal_code + "';";
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            int rs = stm.executeUpdate(sqlQuery);
            AutoLogger.log.info("手工单中修改order_goods内tm_status=‘TRADE_FINISHED’");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            AutoLogger.log.info("----------------------------------------------获--取--推送-发货--数--据---------------------------------------------------------------");
            String sql = "select oi.order_id,oi.order_sn,orf.return_sn,now() as interface_time,oi.sd_id,1 as return_types,oi.order_type,2 as push_type,og.tid,orf.created,\n" +
                    "orf.completed_time,'' as shipping_name,'' as invoice_no,orf.shipping_fee,oi.consignee,oi.user_nick,oi.user_alipay,oi.alipay_no,oi.tel,\n" +
                    "oi.mobile,oi.email,oi.zipcode,oi.province,oi.city,oi.district,oi.address,oi.postscript,oi.to_buyer,orf.reason,oi.pay_way_type,bgb.goods_sales_type,\n" +
                    "oi.total_amount,oi.money_paid,org.oid,bgb.goods_sn,bgb.goods_name,bgb.color_code,bgb.color_name,bgb.expand_color,bgb.size_name,bgb.size_code,bgb.barcode,\n" +
                    "org.return_goods_num,org.goods_price,og.market_price,og.money_paid as og_money_paid,org.refund_fee,0 as return_type,ss.warehouse_type,ss.store_code,\n" +
                    "og.id as order_goods_id from order_refund as orf\n" +
                    "inner join order_refund_goods as org ON  org.refund_id = orf.refund_id\n" +
                    "inner join order_goods as og ON og.id = org.order_goods_id\n" +
                    "inner join order_info as oi ON oi.order_sn = org.order_sn\n" +
                    "inner join base_goods_barcodes as bgb ON bgb.barcode = org.goods_barcode\n" +
                    "inner join sys_shops as shop ON oi.sd_id = shop.id\n" +
                    "left join sys_stores as ss ON ss.id = orf.return_warehouse\n" +
                    "where shop.platform=0 and orf.refund_type=3 and orf.refund_status=2 and org.is_delete=0 and oi.order_status=2 and org.return_goods_num>0 and oi\n" +
                    ".deal_code(='" + deal_code + "';";
//            AutoLogger.log.info(sql);
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
                if (jsonObject.toJSONString().contains(deal_code)) {
                    AutoLogger.log.info("在oms数据库中成功获取到推送退款数据 -->> " + jsonObject.toJSONString());
                } else {
                    AutoLogger.log.info("在oms数据库中获取推送退货数据 -->>  失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
        }
    }

    /**
     * 发货数据推送后校验
     */
    public static void sales_data_check(String deal_code) {
        try {
            conn(hub_dataBase);
            AutoLogger.log.info("----------------------------------------------查-询--HUB--sales_data表--------------------------------------------------------------------");
            String sql = "select * from " + hub_sales_data + " where tid like '%" + deal_code + "%'limit 0,1;";
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();   // 描述结果集的元数据, 可以得到结果集中的基本信息: 结果集中有哪些列, 列名, 列的别名等.
            int columnCount = metaData.getColumnCount();     // 返回当前ResultSet对象中的列数
            JSONObject jsonObject = new JSONObject();        // json数组
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);    // 得到列的别名
                    String Value = rs.getString(columnName);           // 得到列名
                    jsonObject.fluentPut(columnName, Value);           // 遍历出interface的delivery表中所有的字段和值
                }
                AutoLogger.log.info(jsonObject.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
        }
    }

    /**
     * 退货数据推送后校验
     */
    public static void return_data_check(String deal_code) {
        AutoLogger.log.info("----------------------------------------------查-询--HUB--return_data表--------------------------------------------------------------------");
        try {
            conn(hub_dataBase);
            String sqlQuery = "SELECT * FROM `" + hub_return_data + "` where tid LIKE '%" + deal_code + "%'limit 0,1;";
            // 建立查询
            stm = connection.createStatement();
            // 执行sql语句
            rs = stm.executeQuery(sqlQuery);
            ResultSetMetaData setMeta = rs.getMetaData();
            JSONObject json = new JSONObject();
            int column = setMeta.getColumnCount();
            // 展开结果集
            while (rs.next()) {
                for (int i = 1; i < column; i++) {
                    String columnName = setMeta.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    json.fluentPut(columnName, value);
                }
                if (json.toJSONString().contains(deal_code)) {
                    AutoLogger.log.info("推送退货数据成功 ->>" + json.toJSONString());
                } else {
                    AutoLogger.log.info("推送退货数据失败 ->>" + json.toJSONString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
        }
    }


}