package cbb.demo.utils;

import com.pi4j.io.gpio.*;

/**
 * @author cbb
 * @date 2021/5/5
 * 树莓派引脚工具类
 */
public class PiUtils {
    private final GpioController gpioController = GpioFactory.getInstance();
    private final GpioPinDigitalInput myButton = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);

    public GpioController getGpioController() {
        return gpioController;
    }

    public GpioPinDigitalInput getMyButton() {
        return myButton;
    }
}
