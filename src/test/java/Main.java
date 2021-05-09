import cbb.demo.TransApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20210507000817564";
    private static final String SECURITY_KEY = "4126TcVoQAHakrX9yfgZ";

    public static void main(String[] args) throws UnsupportedEncodingException {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = "just so so";
        String result = api.getTransResult(query, "en", "zh");
        JSONObject jsonObject = JSON.parseObject(result);
        String jsonResult = JSON.parseObject(jsonObject.getJSONArray("trans_result").get(0).toString()).get("dst").toString();
        System.out.println(jsonResult);
    }

}
