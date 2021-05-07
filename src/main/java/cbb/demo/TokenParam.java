package cbb.demo;

/**
 * @author cbb
 * @date 2021/5/5
 */
public class TokenParam {
    //设置APPID/AK/SK
    public static final String APP_ID = "23837796";
    public static final String API_KEY = "SWKQtlXPKpUKLW02MnYGH9pq";
    public static final String SECRET_KEY = "heKAUie9zklqBX56ikgMcR5Zcpg8mhVK";

    public static final String tokenUrl = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" +
            "&client_id=" + API_KEY + "&client_secret=" + SECRET_KEY;

}
