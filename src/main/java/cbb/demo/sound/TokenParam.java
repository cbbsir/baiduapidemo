package cbb.demo.sound;

/**
 * @author cbb
 * @date 2021/5/5
 */

/**
 * token参数
 */
public class TokenParam {
    //设置APPID/AK/SK
    public static String VIDEO_APP_ID = "23837796";
    public static String VIDEO_API_KEY = "SWKQtlXPKpUKLW02MnYGH9pq";
    public static String VEDIO_SECRET_KEY = "heKAUie9zklqBX56ikgMcR5Zcpg8mhVK";

    public static String TRANSLATE_APP_ID = "20210507000817564";
    public static String TRANSLATE_SECURITY_KEY = "4126TcVoQAHakrX9yfgZ";


    public static String tokenUrl = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" +
            "&client_id=" + VIDEO_API_KEY + "&client_secret=" + VEDIO_SECRET_KEY;

}
