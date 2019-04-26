package com.mt.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	private static final String prefix_url="http://izf4gj.natappfree.cc/";
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092400589357";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCU9/O/xWdrXW+QKTlby6cXoEH90KksY/eD87f2AdCJ+zIskfyxuvKUW7p4s6ZHZmoKLyZ1Xsb3nBVG/xCMOqpgx1wMn7hlzacZ6OwRc32pdYBfkxAS1T45aSpsQ7tbg8EOXt+2QfOzF9l2G1B9BfyiF9gK6UE8sBVdacF7qZEJyuxeARzQmwpcQMqGWvuXWfvnAJBjl8Ky0djlBIXF4nD9HhjoOFAKFNFzuAjwNWcmHhxFAk97ptuCKb4iXpjFyeGXtDt6ZiF/3s98aFnOuUz0Fh0CmZmcYxIfdCVRo6uau6ad06rwzrG25VxlvsBuh2N1hfLHMcgyxlKihB3xiQiBAgMBAAECggEAF4tQ1QBoJgnwdgJt9FotoIhNpcXy1vc9yXcdnh4PTZ+MAItaOuTnLheJU0Zhg2tmWxG0rFw8aiobGst5XBBxmTYpED22MTDdtjSgCuoL9FqD3ZcqtS9K1FwQvme+Fuqt+o14IiAuWiHs1sPeZsQpd5z5IlBXZTYXSlpKoDbem4NfMvtwHtcHih/espetmBJSypyvbR7OsCjO36V8P4+nAdm5TQ1H13IEc+CfK9EVS2kQRFgEBbIG8WNcjdu+VHgj5kO52LtKPjMi1UJCnpKssuXRgdXKR49FYW9crHGaNXXV30sEMCGXfQdKa6tdqBCWq5Uobi9nzUxWJSx6MBoluQKBgQDI+Pce1p5EPb9D1iMPaPwKZWtgv7B427kY127XsXubOIJ8sf4I/OWNBkiFfKJ0nqKf8QqyL11WoL8knfiTWzi4d6IcS39OhYGwZm/gM0zGveSWKs7N74WuK6FyIplz2RZZs0CLkUXdXzOM+e1MF1NeWcLUf+4U3m3EDaTwqQmRUwKBgQC9wc3bhEPlFWA9OS5Hb5FUZK3NLgGXE3axHSV5wahdnXDftu4VTnxU8JjpBSPmNXfvRK+vJkYkW4B/rzKoiAQTwudcnScb5oIR4M5QXJDfpE/Eo7W8RN/Wtlza7jrXV0U5abANWibq0Wwgg0DjINLe/k5QXhr4XcihIUnDvCggWwKBgGK++WUcnKz5DeY97AIhWl2dUXI7HXF0vHC5QYwXd51f2bV9G029POja4n08LPJSE6O0osXEQf9TgjdyqvV0R91TYsK2Ubn6HSvYWTX23s1z9kHWjMBg5PUcGPpkh7OFQdcXHSq2WXaY1VJ6Zp8RgvQD9JfGt+tnEtXWwmlBFSNjAoGAM2ETbTmt7IxSJ6Gg70S9M/2itaicU5y4iZJbwFD4Voi3tEtiwbAjqBgRPx7eLooRtF6Dt0gEu/lunKQCcnGJsRQX8xK2MS7DzzVwh3/Y1kU2OAcfwR9hAFEWaU0R75a4rqf4PFVJrlEQFumTGyybDDgNWmNwqAk0trQ9tdrvfnsCgYBz5Wq3Mz3SKE0/ACDfD/x4k3R92UY+gSN0AyXPWv96VYnyIEGQDLtiWcoEq3Q61HeD16myFsHJRH9iBkP90LR04X49QXNKUhr2vPNkZT/WCYYREIMOAOz6YegaCscxz+ZyM0zrRWdF7mXMVBf0IEDh/sc7qdlGwDMUVrDwpwXl6g==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq9Ku6V3XPya5EgEimds8L2RLH9OdLPtgl0KZlor9vbOhEvgT4lCXbk8tk2CoMGTP6N3BNrs0l7NugsJ/2TJVMWhxNqwmL7AEVHpR3aFwBBf1qOrZvKtdkiEok5g3QN2oZ+1hyyIyo0CUJWzZJ/L2Dzuj6+C7yYpAbbbpljeg335+eqyhkttrdAQax34MZ+SJfw+VUnvsLwVJQNFVhUx+kUaOnZtY95WQUCjg+KLtc18R8D9C0ObzBTNpAEeZra1RO7swtFCixRsm/anyB2LlMsc7wzgD1a5vGh6MOYCVEGP+8zMVYIdPVz9EopoKvJGULGXTlyoND9BOJw3eJ6LCxQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = prefix_url+"BF2/order/alipayreturn.action";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = prefix_url+"BF2/user.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 日志位置
//	public static String log_path = "C:\\";
	public static String log_path = "D:\\MyFoodProject\\test\\alilog";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
          * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

