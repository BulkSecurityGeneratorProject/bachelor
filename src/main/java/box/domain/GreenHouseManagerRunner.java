package box.domain;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import box.repository.GreenHouseManagerRepository;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Component
public class GreenHouseManagerRunner extends Thread {

    @Inject
    private GreenHouseManagerRepository greenHouseManagerRepository;

    private GreenHouseManager manager;

    @PostConstruct
    public void initIt() {
        manager = greenHouseManagerRepository.findOne(1011L);

    }
    //tmp

    private void manageHumidity() {
        /*       if (manager.getGreenHouse().getHumidity().getSensorValue() < manager.getSettings().getMinGrounHumidity()) {
	        	manager.getGreenHouse().getHumidifier().turnOn();
	        } else if (manager.getGreenHouse().getHumidity().getSensorValue() > manager.getSettings().getMaxGroundHumidity()) {
	        	manager.getGreenHouse().getHumidifier().turnOff();
	        }
         */
    }

    private void managePumps() {
        /*	        boolean wattering = true;
	        for (Plant plant : manager.getGreenHouse().getPlants()) {
	            if (plant.getHumidity().getSensorValue() < manager.getSettings().getMinHumidity()) {
	                wattering = true;
	            } else if (plant.getHumidity().getSensorValue() > manager.getSettings().getMaxHumidity()) {
	                wattering = false;
	            }
	            if(wattering){
	            	manager.getGreenHouse().getHumidifier().turnOn();
	            }else{
	            	manager.getGreenHouse().getHumidifier().turnOff();
	            }

	        }*/
    }

    private void manageLights() {
        /*   DateTime time = new DateTime();
	        boolean lightsOn = true;
	        if(manager.getSettings().getStartHour() > time.getHourOfDay() && manager.getSettings().getStartMinute() > time.getMinuteOfHour() &&
	        		manager.getSettings().getEndHour() < time.getHourOfDay() && manager.getSettings().getEndMinute() < time.getMinuteOfHour()){
	            lightsOn = true;
	        }else {
	            lightsOn = false;
	        }
	        
	        for(OutSwitch light: manager.getGreenHouse().getLights()){
	            if(lightsOn){
	                light.turnOn();
	            } else {
	                light.turnOff();
	            }
	        }*/
    }

    @Override
    public void run() {
        while (true) {
//	            log.error("manager started..............................................................................dasdasdsada:w");
            manageHumidity();
            managePumps();
            manageLights();
        }

    }

}
