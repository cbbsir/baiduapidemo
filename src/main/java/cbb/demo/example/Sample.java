package cbb.demo.example;
import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * @author cbb
 * @date 2021/3/21
 */
public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "23837796";
    public static final String API_KEY = "SWKQtlXPKpUKLW02MnYGH9pq";
    public static final String SECRET_KEY = "heKAUie9zklqBX56ikgMcR5Zcpg8mhVK";

    public static final String tokenUrl = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" +
        "&client_id=" + API_KEY + "&client_secret=" + SECRET_KEY;
    private static String token = "";
//    private static String path = "D:\\chenB\\大四\\baiduapi\\baiduapiapidemo\\record.wav";
    private static String path = System.getProperty("user.dir") + "/record.wav";

    public static void main(String[] args) throws Exception {
        getToken();
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "log4j.properties");

        HashMap hashMap = new HashMap();
        //设置语言
        hashMap.put("dev_pid",1737);
        // 调用接口
        JSONObject res = client.asr(path, "wav", 16000, hashMap);
        System.out.println(res.getJSONArray("result").get(0).toString());
    }

    private static String printResponse(HttpURLConnection conn) throws Exception {
        if (conn.getResponseCode() != 200) {
            // request error
            System.out.println("conn.getResponseCode() = " + conn.getResponseCode());
            return "";
        }
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        System.out.println(new JSONObject(response.toString()).toString(4));
        return response.toString();
    }

    //获取access_token
    private static void getToken() throws Exception {
        String getTokenURL = tokenUrl ;
        HttpURLConnection conn = (HttpURLConnection) new URL(getTokenURL).openConnection();
        token = new JSONObject(printResponse(conn)).getString("access_token");
        System.out.println(token);
    }
}
