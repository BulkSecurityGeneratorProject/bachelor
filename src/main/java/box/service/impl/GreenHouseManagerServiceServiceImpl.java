package box.service.impl;

import box.domain.GreenHouseManager;
import box.domain.OutSwitch;
import box.domain.Plant;
import box.repository.GreenHouseManagerRepository;
import box.service.GreenHouseManagerServiceService;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GreenHouseManagerServiceServiceImpl implements GreenHouseManagerServiceService {

    private final Logger log = LoggerFactory.getLogger(GreenHouseManagerServiceServiceImpl.class);
    @Inject
    private GreenHouseManagerRepository greenHouseManagerRepository;

    private GreenHouseManager manager;

    @PostConstruct
    public void initIt() {
        manager = greenHouseManagerRepository.findOne(1011L);

    }
    //tmp

    private void manageHumidity() {
        if (manager.getGreenHouse().getHumidity().getSensorValue() < manager.getSettings().getMinGrounHumidity()) {
            manager.getGreenHouse().getHumidifier().turnOn();
        } else if (manager.getGreenHouse().getHumidity().getSensorValue() > manager.getSettings().getMaxGroundHumidity()) {
            manager.getGreenHouse().getHumidifier().turnOff();
        }

    }

    private void managePumps() {
        boolean wattering = true;
        for (Plant plant : manager.getGreenHouse().getPlants()) {
            if (plant.getHumidity().getSensorValue() < manager.getSettings().getMinHumidity()) {
                wattering = true;
            } else if (plant.getHumidity().getSensorValue() > manager.getSettings().getMaxHumidity()) {
                wattering = false;
            }
            if (wattering) {
                manager.getGreenHouse().getHumidifier().turnOn();
            } else {
                manager.getGreenHouse().getHumidifier().turnOff();
            }

        }
    }

    private void manageLights() {
        DateTime time = new DateTime();
        boolean lightsOn = true;
        if (manager.getSettings().getStartHour() > time.getHourOfDay() && manager.getSettings().getStartMinute() > time.getMinuteOfHour()
                && manager.getSettings().getEndHour() < time.getHourOfDay() && manager.getSettings().getEndMinute() < time.getMinuteOfHour()) {
            lightsOn = true;
        } else {
            lightsOn = false;
        }

        for (OutSwitch light : manager.getGreenHouse().getLights()) {
            if (lightsOn) {
                light.turnOn();
            } else {
                light.turnOff();
            }
        }
    }

    @Override
    @Scheduled(fixedDelay = 1000)
    public void run() {
//        while (true) {
//	            log.error("manager started..............................................................................dasdasdsada:w");
        manageHumidity();
        managePumps();
        manageLights();
//        }

    }

}
