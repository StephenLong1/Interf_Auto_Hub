package source;

import com.alibaba.fastjson.JSONPath;
import common.AutoLogger;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.testng.Reporter;

import javax.crypto.Cipher;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientKw {

    /**
     * http://47.92.244.122:30004/index.php?  【请求HUB的app_key是21534044/23013186】
     * http://39.98.231.61:30003/api/fromduijie?  【请求OMS的app_key是21534044】
     */
    public static String password = "@oms2019oms";
    public static String token;       // _token
    public static String X_CSRF_TOKEN; // 代转订单列表的接口返回体里
    public static String X_CSRF_TOKEN_1; // OMS登录接口返回体里
    public static String XSRF_TOKEN; // 登录页面的返回头里
    public static String prePubkey;
    public static String pubkeyF;
    public static String pubkey;      // 在返回体中获取到的公钥
    public static String ciphertext; // 被RSA加密后的登录密码
    public static BasicCookieStore cookieStore = new BasicCookieStore();  // cookieStore对象，httpclient用它来记录得到的cookie值
    public static CloseableHttpClient client;
    public static CloseableHttpResponse responseGet;
    public static HttpGet doGet;
    public static String key;
    public static String value;
    private boolean useCookie = true;    // 是否使用cookie标志位, 默认使用cookie
    public static final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");    // 匹配unicode编码格式的正则表达式。
    public static String result;    // result为最终返回结果
    public static int code;
    public static int code2;
    public static int code3;
    public static SSHService ssh = new SSHService();

    /**
     * 通过saveCookie来使用成员变量的cookieStore通过一个成员变量标志位useCookie来进行判断
     */
    public void saveCookie() {
        // 设置useCookie的状态为真
        useCookie = true;
    }


    /**
     * 清理掉成员变量cookieStore池中所有的cookie
     * 并且通过指定成员变量标志位useCookie为false，不让创建client调用已有的cookieStore
     */
    public void clearCookie() {
        // 设置不使用已有的cookie
        useCookie = false;
        // 重新实例化, 将cookieStore清空
        cookieStore = new BasicCookieStore();
    }


    /**
     * 查找字符串中的unicode编码并转换为中文
     *
     * @param u
     * @return
     */
    public static String DeCode(String u) {
        try {
            Matcher m = reUnicode.matcher(u);
            StringBuffer sb = new StringBuffer(u.length());
            while (m.find()) {
                m.appendReplacement(sb, Character.toString((char) Integer.parseInt(m.group(1), 16)));
            }
            m.appendTail(sb);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return u;
        }
    }


    /**
     * 查找字符串中的中文编码并转换为unicode
     *
     * @param gbString
     * @return
     */
    public static String Encode(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }


    /**
     * 通过jsonpath表达式解析返回中的内容，基于解析结果断言
     *
     * @param expect   预期结果
     * @param jsonPath jsonpath表达式
     * @return
     */
    public static void assertContains(String expect, String jsonPath) {
        try {
            String actual = JSONPath.read(result, jsonPath).toString();
            if (actual != null && actual.contains(expect)) {
                AutoLogger.log.info("《测试通过!》");
            } else {
                AutoLogger.log.info("《测试失败!》");
            }
        } catch (Exception e) {
            AutoLogger.log.error("解析失败，请检查jsonPath表达式");
            AutoLogger.log.error(e, e.fillInStackTrace());
        }
    }


    /**
     * 断言2 TestNG页面元素断言(Reporter,Assert)
     *
     * @param expect   期望的接口返回的值
     * @param jsonPath json表达式如：通过$.来获取key值
     * @param Para     标注为：某某接口
     */
    public Boolean assertExpectContains(String expect, String jsonPath, String Para) {
        try {
            String actual = JSONPath.read(result, jsonPath).toString();
            if (actual != null && actual.contains(expect)) {
                Reporter.log(expect + "=> 《测试通过!》");
                AutoLogger.log.info(Para + " → " + "断言验证成功, 结果与预期结果匹配");
                return true;
            } else {
                Reporter.log(expect + "=> 《测试失败!》");
                AutoLogger.log.info(Para + " → " + "断言验证失败, 请查看返回体,检查你所测试的品牌商是否支持该接口,");
                return false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Reporter.log("断言验证失败,结果与预期结果不一致,   请检查日志！");
            AutoLogger.log.info(Para + " → " + "断言失败, 请检查日志！");
            AutoLogger.log.error(e, e.fillInStackTrace());
            return false;
        }

    }


    /**
     * SSLcontext用于绕过ssl验证，使发包的方法能够对https的接口进行请求。
     */
    private static SSLContext createIgnoreVerifySSL() {
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSLv3");
            sc.init(null, new TrustManager[]{trustManager}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sc;
    }


    /**
     * 用于完成httpclient的创建
     *
     * @return 返回创建好的httpclient对象用于发包。
     */
    private static CloseableHttpClient createClient() {
        // 采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 创建自定义的httpclient对象
        client = HttpClients.custom().setConnectionManager(connManager).setDefaultCookieStore(cookieStore).build();
        // 当需要进行代理抓包时，启用下面的代码。
        // 设置代理地址，适用于需要用fiddler抓包时使用，不用时切记注释掉这句！
//		HttpHost proxy = new HttpHost("localhost", 8888, "http");
//		CloseableHttpClient client = HttpClients.custom().setProxy(proxy).setConnectionManager(connManager).build();
        return client;
    }


    /**
     * 通过httpclient实现get方法
     *
     * @param url   接口的url地址后缀如：  /admin/login
     * @param param 接口的参数列表。
     */
    public static String doGet(String interName, String url, String param,String header,String value,String header2,String value2) {
        // 创建httpclient对象
        client = createClient();
        try {
            // 拼接url和param，形成最终请求的url格式。
            String urlWithParam = "";
            if (param.length() > 0) {
                urlWithParam = url + param;
            } else {
                urlWithParam = url;
            }
            // 创建get方式请求对象
            doGet = new HttpGet(urlWithParam);
            // 设置连接的超时时间
            // setsocketTImeout指定收发包过程中的超时上线是15秒，connectTime指定和服务器建立连接，还没有发包时的超时上限为10秒。
            RequestConfig config = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(10000).build();
            doGet.setConfig(config);
            // httpclient执行httpget方法
            responseGet = client.execute(doGet);
            // 添加请求头
            doGet.addHeader(header,value);
            doGet.addHeader(header2,value2);
            // 获取结果实体
            HttpEntity entity = responseGet.getEntity();
            if (entity != null) {
                // 按指定编码转换返回实体为String类型
                result = EntityUtils.toString(entity, "UTF-8");
            }
            // 判断返回体是否为html格式
            if (result.contains("html") || result.contains("����")  || result.contains("div")) {
                AutoLogger.log.info("接口 -->> 【" + interName + "】" + " 的返回体是html格式 ->> 状态码：" + responseGet.getStatusLine().getStatusCode());
            } else {
                AutoLogger.log.info("接口 -->> 【" + interName + "】" + " 的返回体是：" + DeCode(result));
            }
            // 对结果中可能出现的unicode编码进行转换，转成中文
            result = DeCode(result);
            // 在代转订单页面获取X-CSRF-TOKEN   'X-CSRF-TOKEN': 'C5u8FaVx5eCzavLdy8XvUoRe9p70iYqi4PdveUDQ'
            Pattern p = Pattern.compile("'X-CSRF-TOKEN': '(.+?)'");
            Matcher m = p.matcher(result);
            while (m.find()) {
                X_CSRF_TOKEN = m.group(1);
            }
//            AutoLogger.log.info("X_CSRF_TOKEN："+X_CSRF_TOKEN);

//            // 打印请求头
//            for(Header head:doGet.getAllHeaders()){
//                AutoLogger.log.info(head);
//            }
            // 释放返回实体
            EntityUtils.consume(entity);
            // 关闭返回包
            responseGet.close();
        } catch (Exception e) {
            // 当出现报错时，将报错信息记录作为结果
            AutoLogger.log.error(result);
            result = e.fillInStackTrace().toString();
        } finally {
            // 关闭httpclient对象，释放资源
            try {
                client.close();
            } catch (IOException e) {
                AutoLogger.log.error(e, e.fillInStackTrace());
            }
        }
        return result;
    }


    /**
     * 通过httpclient实现的以x-www-form-urlencoded格式传参的post方法
     *
     * @param url   接口的url地址后缀如：  /admin/login
     * @param param 接口的参数列表。
     */
    public static String doPost(String interName, String url, String param, String key, String value) {
        // 创建httpclient对象
        client = createClient();
        try {
            // 创建post方式请求对象
            HttpPost post = new HttpPost(url);
//            AutoLogger.log.info(url+"/"+param);
            // 设置连接的超时时间
            // setsocketTImeout指定收发包过程中的超时上线是15秒，connectTime指定和服务器建立连接，还没有发包时的超时上限为10秒。
            RequestConfig config = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(30000).build();
            post.setConfig(config);
            // 创建urlencoded格式的请求实体，设置编码为utf8
            StringEntity postParams = new StringEntity(param);
            postParams.setContentType("application/x-www-form-urlencoded");
            postParams.setContentEncoding("UTF-8");
//            post.addHeader("X-CSRF-TOKEN", X_CSRF_TOKEN);
            post.addHeader(key,value);
//            AutoLogger.log.info(token);
            // 添加请求体到post请求中
            post.setEntity(postParams);
//
            // 执行请求操作，并获取返回包
            CloseableHttpResponse response = client.execute(post);
            // 获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 按指定编码转换返回实体为String类型
                result = EntityUtils.toString(entity, "UTF-8");
            }
            // 对结果中可能出现的unicode编码进行转换，转成中文
            result = DeCode(result);
            if (result.contains("html") || result.contains("����")) {
                AutoLogger.log.info("接口 -->> 【" + interName + "】" + " 的返回体是html格式 ->> 状态码：" + response.getStatusLine().getStatusCode());
            } else {
                AutoLogger.log.info("接口 -->> 【" + interName + "】" + " 的返回体是：" + result + "");
            }
            // 打印请求体
//            for(Header head:post.getAllHeaders()){
//                AutoLogger.log.info(head);
//            }
            // 释放返回实体
            EntityUtils.consume(entity);
            // 关闭返回包
            response.close();
        } catch (Exception e) {
            // 当出现报错时，将报错信息记录作为结果。
            AutoLogger.log.error(e, e.fillInStackTrace());
            result = e.fillInStackTrace().toString();
        } finally {
            // 关闭httpclient对象，释放资源
            try {
                client.close();
            } catch (IOException e) {
                AutoLogger.log.error(e, e.fillInStackTrace());
            }
        }
        return result;
    }


    /**
     * 通过httpclient实现的以Json格式传参的post方法
     *
     * @param url   接口的url地址
     * @param param 接口的参数列表。
     * @param param 接口的描述
     */
    public static String doPostJson(String url, String param, String interName) {
        // 创建httpclient对象
        CloseableHttpClient client = createClient();

        try {
            // 创建post方式请求对象
            HttpPost post = new HttpPost(url);
            // 设置连接的超时时间
            // setsocketTImeout指定收发包过程中的超时上线是15秒，connectTime指定和服务器建立连接，还没有发包时的超时上限为10秒。
            RequestConfig config = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(10000).build();
            post.setConfig(config);
            // 创建urlencoded格式的请求实体，设置编码为utf8
            StringEntity postParams = new StringEntity(param);
            postParams.setContentType("application/json");
            postParams.setContentEncoding("UTF-8");
            // 添加请求体到post请求中
            post.setEntity(postParams);
            // 执行请求操作，并获取返回包
            CloseableHttpResponse response = client.execute(post);
            // 获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 按指定编码转换返回实体为String类型
                result = EntityUtils.toString(entity, "UTF-8");
            }
            // 对结果中可能出现的unicode编码进行转换，转成中文
            result = DeCode(result);
            AutoLogger.log.info("接口：【" + interName + "】" + " 的返回体是：" + result);
            // 释放返回实体
            EntityUtils.consume(entity);
            // 关闭返回包
            response.close();
        } catch (Exception e) {
            // 当出现报错时，将报错信息记录作为结果。
            AutoLogger.log.error(e, e.fillInStackTrace());
            result = e.fillInStackTrace().toString();
        } finally {
            // 关闭httpclient对象，释放资源
            try {
                client.close();
            } catch (IOException e) {
                AutoLogger.log.error(e, e.fillInStackTrace());
            }
        }
        return result;
    }


    /**
     * 通过httpclient实现的以XML格式传参的post方法
     *
     * @param url   接口的url地址
     * @param param 接口的参数列表。
     */
    public static String doPostXml(String interName, String url, String param) {
        // 创建httpclient对象
        client = createClient();
        try {
            // 创建post方式请求对象
            HttpPost post = new HttpPost(url);
            // 设置连接的超时时间
            // setsocketTImeout指定收发包过程中的超时上线是15秒，connectTime指定和服务器建立连接，还没有发包时的超时上限为10秒。
            RequestConfig config = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(30000).build();
            post.setConfig(config);
            // 创建urlencoded格式的请求实体，设置编码为utf8
            StringEntity postParams = new StringEntity(param);
            postParams.setContentType("text/html");
            postParams.setContentEncoding("UTF-8");
            // 添加请求体到post请求中
            post.setEntity(postParams);
            // 执行请求操作，并获取返回包
            CloseableHttpResponse response = client.execute(post);
//            AutoLogger.log.info(response);
            // 获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 按指定编码转换返回实体为String类型
                result = EntityUtils.toString(entity, "UTF-8");
            }
            // 对结果中可能出现的unicode编码进行转换，转成中文
            result = DeCode(result);
            AutoLogger.log.info(result);
            if (result.contains("html") || result.contains("����")) {
                AutoLogger.log.info("接口 -->> 【" + interName + "】" + " 的返回体是html格式 ->> 状态码：" + response.getStatusLine().getStatusCode());
            } else {
                AutoLogger.log.info("接口 -->> 【" + interName + "】" + " 的返回体是：" + result);
            }
            // 释放返回实体
            EntityUtils.consume(entity);
            // 关闭返回包
            response.close();
        } catch (Exception e) {
            // 当出现报错时，将报错信息记录作为结果。
            AutoLogger.log.error(e, e.fillInStackTrace());
            result = e.fillInStackTrace().toString();
        } finally {
            // 关闭httpclient对象，释放资源
            try {
                client.close();
            } catch (IOException e) {
                AutoLogger.log.error(e, e.fillInStackTrace());
            }
        }
        return result;
    }


    /**
     * @param plainText 将必要的请求参数拼接成字符串,然后通过方法"stringToMD5" 生成sign签名
     */
    public static String stringToMD5(String plainText) { // MD5加密
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        String sign = md5code.toUpperCase();
        return sign;
    }


    /**
     * 该URL传递的接口不是奇门接口， 没有包含customerId
     * ①String url OMS接口签名:  拼接字符串(7个请求参数) → ②拼接字符串(secret+url的参数+body+secret) →// ③进行md5加密并获取sign → 拼装URL请求
     */
    public static String urlJoint(String url, String body, String method, String format, String v, String sales_brand, String app_key) { // ① String url OMS接口签名拼接字符串(7个请求参数) → ② 拼接字符串(secret+url的参数+body+secret) →// ③ 进行md5加密并获取sign → 拼装URL请求
        //定义app_secret变量
        String app_secret = "4a389e054f22b97bcb781d2d0546639c";
        String sign_method = "md5";
        // 获取当前时间
        String timestamp = String.valueOf(System.currentTimeMillis());
        // 进行接口签名拼接字符串  根据post参数顺序由小到大排列
        String strToMd5 = app_secret + "app_key" + app_key + "format" + format + "method" + method + "sales_brand" + sales_brand + "sign_method" + sign_method + "timestamp" + timestamp + "v" + v + body + app_secret;
        // 将已经拼接好的字符串丢到MD5方法里, 生成 sign(接口签名)
//		System.out.println("MD5字符串是： "+strToMd5);
        String sign = stringToMD5(strToMd5);
//		System.out.println("方法1"+sign);
        // 将所有参数值全部转换为UTF-8                                   //clarks_bq的pos门店(clarks_gz)的261392954065商品库存查询
        String RequestUrl = url + "method=" + method + "&timestamp=" + timestamp + "&format=" + format + "&app_key=" + app_key + "&v=" + v + "&sign_method=md5&sales_brand=" + sales_brand + "&sign=" + sign;
        AutoLogger.log.info("发送URL请求  →  " + RequestUrl);
        return RequestUrl;
    }

    /**
     * 该URL传递的接口不是奇门接口， 没有包含customerId
     * ①String url OMS接口签名:  拼接字符串(7个请求参数) → ②拼接字符串(secret+url的参数+body+secret) →// ③进行md5加密并获取sign → 拼装URL请求
     */
    public static String urlJoint2(String url, String body, String method, String format, String v, String sales_brand, String app_key) { // ① String url OMS接口签名拼接字符串(7个请求参数) → ② 拼接字符串(secret+url的参数+body+secret) →// ③ 进行md5加密并获取sign → 拼装URL请求
        //定义app_secret变量
        String app_secret = "4a389e054f22b97bcb781d2d0546639c";
        String sign_method = "md5";
        // 获取当前时间
        String timestamp = String.valueOf(System.currentTimeMillis());
        // 进行接口签名拼接字符串  根据post参数顺序由小到大排列
        String strToMd5 = app_secret + "app_key" + app_key + "format" + format + "method" + method + "sales_brand" + sales_brand + "sign_method" + sign_method + "timestamp" + timestamp + "v" + v + body + app_secret;
        // 将已经拼接好的字符串丢到MD5方法里, 生成 sign(接口签名)
//		System.out.println("MD5字符串是： "+strToMd5);
        String sign = stringToMD5(strToMd5);
//		System.out.println("方法1"+sign);
        // 将所有参数值全部转换为UTF-8                                   //clarks_bq的pos门店(clarks_gz)的261392954065商品库存查询
        String RequestUrl = url + "method=" + method + "&timestamp=" + timestamp + "&format=" + format + "&app_key=" + app_key + "&v=" + v + "&sign_method=md5&sales_brand=" + sales_brand + "&sign=" + sign;
        AutoLogger.log.info("发送URL请求  →  " + RequestUrl);
        return RequestUrl;
    }

    // 奇门接口, 包含customerId
    public static String urlQimen1(String url, String body, String method, String format, String v, String sales_brand, String app_key, String customerId) {
        //定义app_secret变量
        String app_secret = "4a389e054f22b97bcb781d2d0546639c";
//		String app_key = "21534044";
//		String format = "json";
//		String method = "pos.inventory.find";
//		String sales_brand = "mulberry";
        String sign_method = "md5";
        // 获取当前时间
        String timestamp = String.valueOf(System.currentTimeMillis());
//		String timestamp ="1591256968743";
//		String v = "1.0";

        // 进行接口签名拼接字符串  根据post参数顺序由小到大排列
        String strToMd5 = app_secret + "app_key" + app_key + "customerId" + customerId + "format" + format + "" + "method" + method + "sales_brand" + sales_brand + "sign_method" + sign_method + "timestamp" + timestamp + "v" + v + body + app_secret;
        // 将已经拼接好的字符串丢到MD5方法里, 生成 sign(接口签名)
        String sign = stringToMD5(strToMd5);
        // 将所有参数值全部转换为UTF-8                                   //clarks_bq的pos门店(clarks_gz)的261392954065商品库存查询
        String RequestUrl = url + "method=" + method + "&timestamp=" + timestamp + "&format=" + format + "&app_key=" + app_key + "&v=" + v + "&sign_method=md5&sales_brand=" + sales_brand + "&sign=" + sign + "&customerId=" + customerId;
        AutoLogger.log.info("发送URL请求  →  " + RequestUrl);
        return RequestUrl;
    }


    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] a = cipher.doFinal(bytes);
        String outStr = Base64.encodeBase64String(a);
        return outStr;
    }


    /**
     * 登录OMS
     *
     * @param brand 品牌商名
     * @throws Exception
     */
    public static void loginOms(String interName, String url, String brand) throws Exception {
        getPassword();
        loginOMS(url, brand);
    }


    /**
     * 在获取加密的密码的前提下请求OMS的接口
     *
     * @return
     * @throws IOException
     */
    public static String getPassword() throws Exception {
//		System.out.println("----------------------------------------------第一次发包进入页面<获取token和加密后的密码>----------------------------------");
//        HttpHost proxy =new HttpHost("127.0.0.1",8888,"http");
//        client = HttpClients.custom().setDefaultCookieStore(cookieStore).setProxy(proxy).build();
        // 进入页面, 在response中获取到token
        String url = "http://39.98.231.61:30003/admin/login";
        client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = client.execute(get);
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        String resultF = EntityUtils.toString(entity);
        // 在返回头的" set-Cookies "中 先获取头部,再获取到 XSRF-TOKEN
        Header cookieHeader = response.getFirstHeader("Set-Cookie");
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("XSRF-TOKEN")) {
                XSRF_TOKEN = cookie.getValue();
            }
        }
//        AutoLogger.log.info("XSRF-TOKEN值："+ XSRF_TOKEN);

        // 在登录页面获取token
        Pattern p = Pattern.compile("name=\"_token\" content=\"(.+?)\"/>");
        Matcher m = p.matcher(resultF);
        while (m.find()) {
            token = m.group(1);
        }
//		AutoLogger.log.info("token值为：" + token);

        // 在登录页面获取pubkey
        Pattern p2 = Pattern.compile("<input type=\"hidden\" id=\"pubkey\" value=([\\d\\D]*?)>");
        Matcher m2 = p2.matcher(resultF);
        while (m2.find()) {
            prePubkey = m2.group(1);
        }
        pubkeyF = prePubkey.replaceAll("\r|\n", "");

        // 获取pubkey 公钥
        Pattern p3 = Pattern.compile("KEY-----(.*?)-----END");
        Matcher m3 = p3.matcher(pubkeyF);
        while (m3.find()) {
            pubkey = m3.group(1);
        }
//        AutoLogger.log.info("获取的公钥pubkey：" + pubkey);

        // 调用加密方法,生成加密后的password
        ciphertext = RSAEncrypt.encrypt(password, pubkey);
//		AutoLogger.log.info("Urlencode前的password：" + ciphertext);
        ciphertext = URLEncoder.encode(ciphertext);
//		AutoLogger.log.info("Urlencode后的password：" + ciphertext);
        return ciphertext;
    }


    /**
     * 登录OMS
     *
     * @param brand 品牌商名
     * @throws Exception
     */
    private static String loginOMS(String url, String brand) throws IOException {
//		System.out.println("----------------------------------------------第二次发包登录账号密码----------------------------------------------------");884980082140  884980082140
        HttpPost post = new HttpPost(url);
        //设置请求体,直接用原始的字符串形式，再指定content-type格式就可以
        StringEntity entity = new StringEntity("username=admin&password=" + ciphertext + "&locale=zh_cn&_ati=884980082140&brand=" + brand + "&_token=" + token + "&device_id=1", "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        // 添加请求头到post请求中
        post.setEntity(entity);
        post.addHeader("Cookies","_ati=884980082140");
        // 获取返回体
        CloseableHttpResponse response = client.execute(post);
        // 判断是否重定向
        int code = response.getStatusLine().getStatusCode();
        // 在登录页面获取X-CSRF-TOKEN   'X-CSRF-TOKEN': 'C5u8FaVx5eCzavLdy8XvUoRe9p70iYqi4PdveUDQ'
//        Pattern p = Pattern.compile("'X-CSRF-TOKEN': '(.+?)'");
//        Matcher m = p.matcher(result);
//        while (m.find()) {
//            X_CSRF_TOKEN_1 = m.group(1);
//        }
//        AutoLogger.log.info(X);
        //添加cookie
        BasicClientCookie cookie = new BasicClientCookie("_ati", "884980082140");
        cookie.setDomain("");
        cookie.setPath("");
        //放入cookiestore
        cookieStore.addCookie(cookie);

        // 获取结果实体,  按指定编码转换返回实体为String类型
        HttpEntity Entity2 = response.getEntity();
        String result2 = EntityUtils.toString(Entity2, "UTF-8");
        String decodeResult = HttpClientKw.DeCode(result2);
        if (decodeResult.contains("10000")) {
            AutoLogger.log.info("接口 -->> 【登录OMS系统成功】" + decodeResult);
        } else {
            AutoLogger.log.info("接口 -->> 【登录OMS系统失败】" + decodeResult);
        }
        return decodeResult;
    }


    /**
     * 登录HUB
     *
     * @throws Exception
     */
    public static void loginHub() throws IOException {
//      System.out.println("--------------------------------------第一次发包,  请求HUB首页---------------------------------------------");
        client = createClient();
        HttpGet get = new HttpGet("http://47.92.244.122:30001/login");
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, "utf-8");
        code = response.getStatusLine().getStatusCode();

        Pattern p = Pattern.compile("<input type=\"hidden\" name=\"_token\" value=\"(.+?)\">");
        Matcher m = p.matcher(result);
        while (m.find()) {
            token = m.group(1);
        }

//      System.out.println("--------------------------------------第二次发包, 登录HUB---------------------------------------------");
        HttpPost post = new HttpPost("http://47.92.244.122:30001/login");
        String email = URLEncoder.encode("stones.zhou@ibaiqiu.com");
        StringEntity entity2 = new StringEntity("_token=" + token + "&email=" + email + "&password=666888");
        entity2.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        entity2.setContentEncoding("UTF-8");
        post.setEntity(entity2);
        HttpResponse response2 = client.execute(post);
        code2 = response2.getStatusLine().getStatusCode();
        HttpEntity entity1 = response2.getEntity();
        result = EntityUtils.toString(entity1);

//      System.out.println("--------------------------------------第三次发包, 请求重定向后的URL---------------------------------------------");
        if (code2 == 302) {
            Header header = response2.getFirstHeader("location");
            String newUrl = header.getValue();  // 跳转后的地址, 再向这个地址发处新的申请, 明白跳转后的信息是什么
            AutoLogger.log.info(newUrl);
            HttpGet get2 = new HttpGet(newUrl);
            HttpResponse response3 = client.execute(get2);
            code3 = response3.getStatusLine().getStatusCode();
            HttpEntity entity3 = response3.getEntity();
            result = EntityUtils.toString(entity3, "UTF-8");
            if (code == 200) {
                if (result.contains("html")) {
                    AutoLogger.log.info("接口 -->> 【登录HUB系统成功】");
                } else {
                    AutoLogger.log.info("接口 -->> 【登录HUB系统异常】");
                }
            } else {
                AutoLogger.log.info("接口 -->> 【登录HUB系统重定向失败】");
            }
        }
    }


    /**
     * 执行OMS推送发货退货对账数据的定时任务
     *
     * @param ownerCode
     * @throws Exception
     */
    public static void executeTimedTask(String ownerCode) throws Exception {
        AutoLogger.log.info("----------------------------------------------执-行--OMS--定-时-任-务-----------------------------------------------------------------");
        loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", ownerCode);
        doGet("进入定时任务界面", "http://39.98.231.61:30003/admin/cron/list?", "page=1&limit=20&keyword=sales_account&type=-1&status=-1","","","","");
        doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id() + "&type=run","","","","");
        try {
            if (result.contains("未启用无法执行")) {
                doGet("点击启用", "http://39.98.231.61:30003/lib/layui/css/modules/layer/default/icon.png", "","","","","");
                doGet("点击确定", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=start","","","","");
                doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=run","","","","");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+token+"","_ati","884980082140");

        }
    }


    /**
     * 定时任务页面, 执行OMS一键转单的定时任务  tmall:transfer_tmall_order  注意：一键审单功能在定时任务界面运行不成功！！！
     *
     * @throws Exception
     */
    public static void executeTimeTask2() throws Exception {
        AutoLogger.log.info("----------------------------------------------执-行--OMS-【一键转单】-定-时-任-务-------------------------------------------------");
        loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
//        AutoLogger.log.info(SSHService.ownerCode());
//        doGet("进入定时任务界面", "http://39.98.231.61:30003/admin/cron/list?", "page=1&limit=20&keyword=&type=-1&status=-1");  // %E8%BD%AC%E5%8D%95 转单
        doGet("搜索定时任务之转单", "http://39.98.231.61:30003/admin/cron/list?", "page=1&limit=20&keyword=%E8%BD%AC%E5%8D%95&type=-1&status=-1","","","","");  // %E8%BD%AC%E5%8D%95 转单
        try {
            doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=run","","","","");
            if (result.contains("未启用无法执行")) {
                doGet("点击启用", "http://39.98.231.61:30003/lib/layui/css/modules/layer/default/icon.png", "","","","","");
                doGet("点击确定", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=start","","","","");
                doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=run","","","","");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 定时任务页面, 拉取天猫仅退款单的定时任务  tmall:transfer_tmall_refund
     *
     * @throws Exception
     */
    public static void executeTimeTask_onlyRefund() throws Exception {
        AutoLogger.log.info("----------------------------------------------执-行--OMS-【拉取天猫仅退款单】-定-时-任-务-------------------------------------------------");
        loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
        doGet("搜索定时任务之拉取【仅退款】单", "http://39.98.231.61:30003/admin/cron/list?", "page=1&limit=20&keyword=%E6%8B%89%E5%8F%96&type=-1&status=-1","","","","");  // %E8%BD%AC%E5%8D%95 转单
        doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id() + "&type=run","","","","");
        doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+token+"","_ati","884980082140");
        try {
            if (result.contains("未启用无法执行")) {
                doGet("点击启用", "http://39.98.231.61:30003/lib/layui/css/modules/layer/default/icon.png", "","","","","");
                doGet("点击确定", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=start","","","","");
                doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=run","","","","");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 定时任务页面, 拉取天猫退货退款单的定时任务  tmall:transfer_tmall_return
     *
     * @throws Exception
     */
    public static void executeTimeTask_ReturnRefund() throws Exception {
        AutoLogger.log.info("----------------------------------------------执-行--OMS-【拉取天猫退货退款单】-定-时-任-务-------------------------------------------------");
        loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
        String returnRefund = "%E6%8B%89%E5%8F%96%E5%A4%A9%E7%8C%AB%E9%80%80%E8%B4%A7%E5%8D%95%EF%BC%88%E9%80%80%E8%B4%A7%E9%80%80%E6%AC%BE%EF%BC%89";
        doGet("搜索定时任务之拉取-退货退款-单", "http://39.98.231.61:30003/admin/cron/list?", "page=1&limit=20&keyword="+returnRefund+"&type=-1&status=-1","","","","");
        doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id() + "&type=run","","","","");
        doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+token+"","_ati","884980082140");
        try {
            if (result.contains("未启用无法执行")) {
                doGet("点击启用", "http://39.98.231.61:30003/lib/layui/css/modules/layer/default/icon.png", "","","","","");
                doGet("点击确定", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=start","","","","");
                doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=run","","","","");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 定时任务页面, 拦截天猫退款单的定时任务  tmall:transfer_tmall_order_intercept
     *
     * @throws Exception
     */
    public static void executeTimeTask_intercept() throws Exception {
        AutoLogger.log.info("----------------------------------------------执-行--OMS-【拦截退款单】-定-时-任-务-------------------------------------------------");
//        HttpClientKw.loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
        doGet("搜索定时任务之-拦截退款单-", "http://39.98.231.61:30003/admin/cron/list?", "page=1&limit=20&keyword=%E6%8B%A6&type=-1&status=-1","","","","");
        doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id() + "&type=run","","","","");
        doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+token+"","_ati","884980082140");
        try {
            if (result.contains("未启用无法执行")) {
                doGet("点击启用", "http://39.98.231.61:30003/lib/layui/css/modules/layer/default/icon.png", "","","","","");
                doGet("点击确定", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=start","","","","");
                doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=run","","","","");
            }
        } catch (Exception e) {
            doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+token+"","_ati","884980082140");
            e.printStackTrace();
        }
    }


    /**
     * 代转页面, 执行OMS一键转单的定时任务  tmall:transfer_tmall_order
     *
     * @throws Exception
     */
    public static void executeTimeTask_orderTransfer() throws Exception {
        AutoLogger.log.info("----------------------------------------------执-行--OMS-【一键转单】-定-时-任-务---------------------------------------------------");
        loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
        doGet("进入代转订单页面", "http://39.98.231.61:30003/admin/api_order/orders_list?", "page=1&limit=12&sd_id=" + SSHService.id + "&tid=&order_type=0&pay_status=0&start_time=&end_time=&tab=1", "", "", "", "");
        doGet("刷新代转页面", "http://39.98.231.61:30003/admin/api_order/orders_list?", "page=1&limit=12&sd_id=" + SSHService.id + "&tid=&order_type=0&pay_status=0&start_time=&end_time=&tab=1", "", "", "", "");
        doPost("点击一键转单", "http://39.98.231.61:30003/admin/api_order/orders_transfer", "sd_id=" + SSHService.id + "&tid=&order_type=0&pay_status=0&start_time=&end_time=&transfer_type=0", "X-CSRF-TOKEN", X_CSRF_TOKEN);
//        doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+token+"","_ati","884980082140");
        //        if(result.equals("没有可转订单")){
//            doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+token+"","_ati","884980082140");
//            AutoLogger.log.info("第一次执行失败,重新转单");
//            AutoLogger.log.info("----------------------------------------------执-行--OMS-【一键转单】-定-时-任-务---------------------------------------------------");
//            loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
//            doGet("进入代转订单页面", "http://39.98.231.61:30003/admin/api_order/orders_list?", "page=1&limit=12&sd_id="+SSHService.id+"&tid=&order_type=0&pay_status=0&start_time=&end_time=&tab=1","","","","");
//            doPost("点击一键转单", "http://39.98.231.61:30003/admin/api_order/orders_transfer", "sd_id="+SSHService.id+"&tid=&order_type=0&pay_status=0&start_time=&end_time=&transfer_type=0","X-CSRF-TOKEN",X_CSRF_TOKEN);
//            if(result.equals("没有可转订单")){
//                AutoLogger.log.info("第二次执行失败,请手动转单");
//            }}
        try {
            if (result.contains("未启用无法执行")) {
                doGet("点击启用", "http://39.98.231.61:30003/lib/layui/css/modules/layer/default/icon.png", "","","","","");
                doGet("点击确定", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=start","","","","");
                doGet("点击执行", "http://39.98.231.61:30003/admin/cron/operate?", "cron_id=" + SSHService.cron_id + "&type=run","","","","");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  一键审核, 并退出OMS
     */
    public static void clickButtonCheck() throws Exception {
        AutoLogger.log.info("----------------------------------------------执-行--OMS-【一键转审核】---------------------------------------------------------------------------");
        try {
//            loginOms("登录OMS系统", "http://39.98.231.61:30003/admin/login", SSHService.ownerCode());
            doGet("点击销售订单","http://39.98.231.61:30003/admin/order/list","","","","","");
            doGet("进入订单详情页","http://39.98.231.61:30003/admin/order/detail?","page=1&limit=12&order_id="+ SSHService.order_id+"","","","","");
//            doGet("刷新订单详情页","http://39.98.231.61:30003/admin/order/detail?","page=1&limit=12&order_id="+ SSHService.order_id+"","","","","");
            doPost("点击一键审核","http://39.98.231.61:30003/admin/order/check","orders%5B%5D="+ SSHService.order_id+"&platform=0&sd_id="+ SSHService.id+"","X-CSRF-TOKEN", X_CSRF_TOKEN);
//            AutoLogger.log.info(SSHService.order_id+" || "+SSHService.id+" || " +X_CSRF_TOKEN);
            doPost("点击退出OMS","http://39.98.231.61:30003/admin/logout","_token="+token+"","_ati","884980082140");
            AutoLogger.log.info("天猫平台订单号："+SSHService.deal_code);
            if(result.contains("failure")){
                AutoLogger.log.info("审核失败,  订单显示异常, 可能是商品缺货导致");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}