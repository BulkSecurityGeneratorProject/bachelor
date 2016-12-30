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

    private static final int MAXTIMINGS = 85;
    private static int[] dht11Data = {0, 0, 0, 0, 0};

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

    private static boolean checkParity() {
        return (dht11Data[4] == ((dht11Data[0] + dht11Data[1] + dht11Data[2] + dht11Data[3]) & 0xFF));
    }

    public static double getHumidity(int pinNumber) {

        int laststate = Gpio.HIGH;
        int j = 0;
        dht11Data[0] = dht11Data[1] = dht11Data[2] = dht11Data[3] = dht11Data[4] = 0;

        Gpio.pinMode(pinNumber, Gpio.OUTPUT);
        Gpio.digitalWrite(pinNumber, Gpio.LOW);
        Gpio.delay(18);

        Gpio.digitalWrite(pinNumber, Gpio.HIGH);
        Gpio.pinMode(pinNumber, Gpio.INPUT);

        for (int i = 0; i < MAXTIMINGS; i++) {
            int counter = 0;
            while (Gpio.digitalRead(pinNumber) == laststate) {
                counter++;
                Gpio.delayMicroseconds(1);
                if (counter == 255) {
                    break;
                }
            }

            laststate = Gpio.digitalRead(pinNumber);

            if (counter == 255) {
                break;
            }

            /* ignore first 3 transitions */
            if ((i >= 4) && (i % 2 == 0)) {
                /* shove each bit into the storage bytes */
                dht11Data[j / 8] <<= 1;
                if (counter > 16) {
                    dht11Data[j / 8] |= 1;
                }
                j++;
            }
        }
     
        if ((j >= 40) && checkParity()) {
            float h = (float) ((dht11Data[0] << 8) + dht11Data[1]) / 10;
            if (h > 100) {
                h = dht11Data[0];   // for DHT11
            }
            return h;
        } else {
            return -999;
        }

    }

    public static double getTemperature(int pinNumber) {

        int laststate = Gpio.HIGH;
        int j = 0;
        dht11Data[0] = dht11Data[1] = dht11Data[2] = dht11Data[3] = dht11Data[4] = 0;

        Gpio.pinMode(pinNumber, Gpio.OUTPUT);
        Gpio.digitalWrite(pinNumber, Gpio.LOW);
        Gpio.delay(18);

        Gpio.digitalWrite(pinNumber, Gpio.HIGH);
        Gpio.pinMode(pinNumber, Gpio.INPUT);

        for (int i = 0; i < MAXTIMINGS; i++) {
            int counter = 0;
            while (Gpio.digitalRead(pinNumber) == laststate) {
                counter++;
                Gpio.delayMicroseconds(1);
                if (counter == 255) {
                    break;
                }
            }

            laststate = Gpio.digitalRead(pinNumber);

            if (counter == 255) {
                break;
            }

            /* ignore first 3 transitions */
            if ((i >= 4) && (i % 2 == 0)) {
                /* shove each bit into the storage bytes */
                dht11Data[j / 8] <<= 1;
                if (counter > 16) {
                    dht11Data[j / 8] |= 1;
                }
                j++;
            }
        }
       
        if ((j >= 40) && checkParity()) {
            float c = (float) (((dht11Data[2] & 0x7F) << 8) + dht11Data[3]) / 10;
            if (c > 125) {
                c = dht11Data[2];   
            }
            if ((dht11Data[2] & 0x80) != 0) {
                c = -c;
            }
            float f = c * 1.8f + 32;
            return f;
        } else {
            return -999;
        }

    }
}
