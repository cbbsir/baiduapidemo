package cbb.demo.utils;

//import com.pi4j.io.gpio.*;
//import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
//import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import cbb.demo.sound.Token;
import cbb.demo.translate.TransApi;
import com.alibaba.fastjson.JSON;
import com.baidu.aip.speech.AipSpeech;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.json.JSONObject;

import javax.sound.sampled.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static cbb.demo.sound.TokenParam.*;

/**
 * @author cbb
 * @date 2021/3/21
 * 录音工具类
 */
public class VoiceRecorder {

    private static AudioFormat audioFormat;
    private static TargetDataLine targetDataLine;

    private static final GpioController gpioController = GpioFactory.getInstance();
    private static final GpioPinDigitalInput myButton = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);

    int type = 0;

//    public static void main(String[] args) {
//        while (i < 20){
//            System.out.println("y开始n结束");
//            Scanner input = new Scanner(System.in);
//            String Sinput = input.next();
//            long testtime = System.currentTimeMillis();
//            if(Sinput.equals("y")){
//                captureAudio();// 调用录音方法
//            }
//            Scanner input_2 = new Scanner(System.in);
//            String Sinput_2 = input_2.next();
//            if(Sinput_2.equals("n")){
//                closeCaptureAudio();
//            }
//            System.out.println("录音了"+(System.currentTimeMillis()-testtime)/1000+"秒！");
//            System.out.println("i = " + i);
//            i++;
//        }
//    }

    public void voiceRecorderInConsole(final int type) {
//        System.out.println("y开始n结束");
//        Scanner input = new Scanner(System.in);
//        String Sinput = input.next();
//        long testtime = System.currentTimeMillis();
//        if(Sinput.equals("y")){
//            captureAudio();// 调用录音方法
//        }
//        Scanner input_2 = new Scanner(System.in);
//        String Sinput_2 = input_2.next();
//        if(Sinput_2.equals("n")){
//            closeCaptureAudio();
//        }
//        System.out.println("录音了"+(System.currentTimeMillis()-testtime)/1000+"秒！");

        final Map<String,Object> hashMap = new HashMap<String,Object>();

        switch (type){
            case 1:
                //普通话
                hashMap.put("dev_pid",1537);
                break;
            case 2:
                //英文
                hashMap.put("dev_pid",1737);
                break;
            case 3:
                //粤语
                hashMap.put("dev_pid",1637);
                break;
            case 4:
                //四川话
                hashMap.put("dev_pid",1837);
                break;
            default:
                //默认为普通话转文本
                break;

        }

        System.out.println("按下按键开始录音，松开按键结束录音：");
        myButton.addListener(new GpioPinListenerDigital() {

            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

                //按下录音
                if (event.getState().isLow()){
                    System.out.println("开始录音,录音中...");
                    // 调用录音方法
                    captureAudio();
                }

                //松开结束录音
                if (event.getState().isHigh()){
                    closeCaptureAudio();
                    System.out.println("录音结束...");

                    // 初始化一个AipSpeech
                    AipSpeech client = new AipSpeech(VIDEO_APP_ID, VIDEO_API_KEY, VEDIO_SECRET_KEY);

                    // 可选：设置网络连接参数
                    client.setConnectionTimeoutInMillis(2000);
                    client.setSocketTimeoutInMillis(60000);

                    // 也可以直接通过jvm启动参数设置此环境变量
                    //        System.setProperty("aip.log4j.conf", "log4j.properties");


                    // 设置音频转换语言类型 1737 英文
//                    hashMap.put("dev_pid",1737);

                    //语音转文字 通用的 HTTP 接口。 上传完整录音文件，录音文件时长不超过60s。
                    JSONObject res = client.asr(Token.getPath(), "wav", 16000, (HashMap<String, Object>) hashMap);
                    //取json中需要的内容
                    String text = res.getJSONArray("result").get(0).toString();
//                    String text = res.toString();

                    //英汉翻译
                    if (type == 2){
                        System.out.println("english: " + text);
                        //调用翻译接口 英文转中文
                        TransApi api = new TransApi(TRANSLATE_APP_ID, TRANSLATE_SECURITY_KEY);
                        String result = null;
                        try {
                            result = api.getTransResult(text, "en", "zh");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
                        //取json中需要的内容
                        String chinese = JSON.parseObject(jsonObject.getJSONArray("trans_result").get(0).toString()).get("dst").toString();
                        System.out.println("翻译结果: " + chinese + "\n");
                    }else  {
                        System.out.println("识别结果: " + text);
                    }

                }
            }
        });
    }

    public static void closeCaptureAudio(){
        targetDataLine.stop();
        targetDataLine.close();
    }

    public static void captureAudio(){
        try {
            // 构造具有线性 PCM 编码和给定参数的 AudioFormat。
            audioFormat = getAudioFormat();
            // 根据指定信息构造数据行的信息对象，这些信息包括单个音频格式。此构造方法通常由应用程序用于描述所需的行。
            // lineClass - 该信息对象所描述的数据行的类
            // format - 所需的格式
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            // 如果请求 DataLine，且 info 是 DataLine.Info 的实例（至少指定一种完全限定的音频格式），
            // 上一个数据行将用作返回的 DataLine 的默认格式。
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            // 开启线程
            new CaptureThread().start();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static AudioFormat getAudioFormat() {
        // 8000,11025,16000,22050,44100 采样率
        float sampleRate = 16000F;
        // 8,16 每个样本中的位数
        int sampleSizeInBits = 16;
        // 1,2 信道数（单声道为 1，立体声为 2，等等）
        int channels = 1;
        // true,false
        boolean signed = true;
        // true,false 指示是以 big-endian 顺序还是以 little-endian 顺序存储音频数据。
        boolean bigEndian = false;
        // 构造具有线性 PCM 编码和给定参数的 AudioFormat。
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
                bigEndian);
    }

    static class CaptureThread extends Thread {
        public void run() {
            // 指定的文件类型
            AudioFileFormat.Type fileType = null;
            // 设置文件类型和文件扩展名
            File audioFile = null;
            fileType = AudioFileFormat.Type.WAVE;
            audioFile = new File("record.wav");
            try {
                // format - 所需音频格式
                targetDataLine.open(audioFormat);
                // 当开始音频捕获或回放时，生成 START 事件。
                targetDataLine.start();
                // new AudioInputStream(TargetDataLine line):构造从指示的目标数据行读取数据的音频输入流。该流的格式与目标数据行的格式相同,line - 此流从中获得数据的目标数据行。
                // stream - 包含要写入文件的音频数据的音频输入流
                // fileType - 要写入的音频文件的种类
                // out - 应将文件数据写入其中的外部文件
                AudioSystem.write(new AudioInputStream(targetDataLine),fileType, audioFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
