package cbb.demo;

import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

import static cbb.demo.Sample.*;

/**
 * @author cbb
 * @date 2021/5/5
 */
public class Application {
    public static void main(String[] args) throws Exception {
        //录音
        VoiceRecorder voiceRecorder = new VoiceRecorder();
        voiceRecorder.voiceRecorderInConsole();

        //使用百度翻译Api
        Token token = new Token();
        token.getToken();
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "log4j.properties");

        // 调用接口
        JSONObject res = client.asr(token.getPath(), "wav", 16000, null);
        System.out.println(res.getJSONArray("result").toString());
    }

}
