package cbb.demo.sound;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author cbb
 * @date 2021/5/5
 */

/**
 * 获取token、上传音频文件
 */
public class Token {

    //token
    private String token = "";
    //音频文件路径
    private static String path = System.getProperty("user.dir") + "/record.wav";

    public void setToken(String token) {
        this.token = token;
    }

    public static String getPath() {
        return path;
    }

    //获取access_token
    public void getToken() throws Exception {
        String getTokenURL = TokenParam.tokenUrl ;
        HttpURLConnection conn = (HttpURLConnection) new URL(getTokenURL).openConnection();
        token = new JSONObject(printResponse(conn)).getString("access_token");
//        System.out.println(token);
    }

    public String printResponse(HttpURLConnection conn) throws Exception {
        if (conn.getResponseCode() != 200) {
            // request error
//            System.out.println("conn.getResponseCode() = " + conn.getResponseCode());
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
//        System.out.println(new JSONObject(response.toString()).toString(4));
        return response.toString();
    }



}
