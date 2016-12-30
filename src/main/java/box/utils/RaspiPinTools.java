/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package box.utils;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;

/**
 *
 * @author emil
 */
public class RaspiPinTools {

    public static native float[] readData(int pin);

    static {
        System.loadLibrary("dht11sensor");
    }

    public static Pin getEnumFromInt(int pinNumber) {

        switch (pinNumber) {
            case 0:
                return RaspiPin.GPIO_00;
            case 1:
                return RaspiPin.GPIO_01;
            case 2:
                return RaspiPin.GPIO_02;
            case 3:
                return RaspiPin.GPIO_03;
            case 4:
                return RaspiPin.GPIO_04;
            case 5:
                return RaspiPin.GPIO_05;
            case 6:
                return RaspiPin.GPIO_06;
            case 7:
                return RaspiPin.GPIO_07;
            default:
                return null;
        }
    }

    public static double getHumidity(int pinNumber) {
        float[] data = RaspiPinTools.readData(pinNumber);
        return data[0];
    }
}
