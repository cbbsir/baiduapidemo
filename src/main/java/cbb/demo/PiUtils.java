package cbb.demo;

import com.pi4j.io.gpio.*;

/**
 * @author cbb
 * @date 2021/5/5
 */
public class PiUtils {
    private static final GpioController gpioController = GpioFactory.getInstance();
    private static final GpioPinDigitalInput myButton = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);

    public GpioController getGpioController() {
        return gpioController;
    }

    public GpioPinDigitalInput getMyButton() {
        return myButton;
    }
}
