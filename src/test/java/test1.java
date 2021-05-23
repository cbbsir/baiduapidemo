import cbb.demo.example.VoiceRecorder1;
import cbb.demo.sound.Token;
import cbb.demo.translate.TransApi;
import cbb.demo.utils.VoiceRecorder;
import com.alibaba.fastjson.JSON;
import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static cbb.demo.sound.TokenParam.*;

/**
 * @author cbb
 * @date 2021/5/5
 */
public class test1 {

    public static void main(String[] args) throws Exception {

        //录音、翻译
        VoiceRecorder1 voiceRecorder = new VoiceRecorder1();
        voiceRecorder.voiceRecorderInConsole();

        //使用百度翻译Api，获取token
        Token token = new Token();
        token.getToken();

        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(VIDEO_APP_ID, VIDEO_API_KEY, VEDIO_SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        Map<String,Object> hashMap = new HashMap<String,Object>();
        // 设置音频转换语言类型 1637 粤语
        hashMap.put("dev_pid",1637);

        //语音转文字 通用的 HTTP 接口。 上传完整录音文件，录音文件时长不超过60s。
        JSONObject res = client.asr(token.getPath(), "wav", 16000, (HashMap<String, Object>) hashMap);
        //取json中需要的内容
//        String english = res.getJSONArray("result").get(0).toString();
//        System.out.println("english: " + english);
        String text = res.toString();
        System.out.println(text);
        //调用翻译接口 英文转中文
//        TransApi api = new TransApi(TRANSLATE_APP_ID, TRANSLATE_SECURITY_KEY);
//        String result = api.getTransResult(english, "en", "zh");
//        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
//        //取json中需要的内容
//        String chinese = JSON.parseObject(jsonObject.getJSONArray("trans_result").get(0).toString()).get("dst").toString();
//        System.out.println("chinese: " + chinese);

    }
}
