/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package box.domain;

import box.BachelorApp;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author emil
 */
public class GreenHouseManager extends Thread {

    private ProfileSettings settings;
    private GreenHouse greenHouse;
//    private static final Logger log = LoggerFactory.getLogger(BachelorApp.class);

    public GreenHouseManager(ProfileSettings settings, GreenHouse greenHouse) {
        this.settings = settings;
        this.greenHouse = greenHouse;
    }

    public GreenHouseManager() {
    }

    private void manageHumidity() {
        if (greenHouse.getHumidity().getSensorValue() < settings.getMinGroundHumidity()) {
            greenHouse.getHumidifier().turnOn();
        } else if (greenHouse.getHumidity().getSensorValue() > settings.getMaxGroundHumidity()) {
            greenHouse.getHumidifier().turnOff();
        }

    }

    private void managePumps() {
        boolean wattering = true;
        for (Plant plant : greenHouse.getPlants()) {
            if (plant.getHumidity().getSensorValue() < settings.getMinHumidity()) {
                wattering = true;
            } else if (plant.getHumidity().getSensorValue() > settings.getMaxHumidity()) {
                wattering = false;
            }
            if(wattering){
                greenHouse.getHumidifier().turnOn();
            }else{
                greenHouse.getHumidifier().turnOff();
            }

        }
    }
    
    private void manageLights(){
        DateTime time = new DateTime();
        boolean lightsOn = true;
        if(settings.getStartLight()[0] > time.getHourOfDay() && settings.getStartLight()[1] > time.getMinuteOfHour() &&
                settings.getEndLight()[0] < time.getHourOfDay() && settings.getEndLight()[1] < time.getMinuteOfHour()){
            lightsOn = true;
        }else {
            lightsOn = false;
        }
        
        for(OutSwitch light: greenHouse.getLights()){
            if(lightsOn){
                light.turnOn();
            } else {
                light.turnOff();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
//            log.error("manager started..............................................................................dasdasdsada:w");
            manageHumidity();
            managePumps();
            manageLights();
        }

    }

}
