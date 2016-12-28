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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GreenHouseManagerServiceServiceImpl implements GreenHouseManagerServiceService {

    private final long START_PROFILE_SETTINGS = 1011L;
    private final Logger log = LoggerFactory.getLogger(GreenHouseManagerServiceServiceImpl.class);
    @Inject
    private GreenHouseManagerRepository greenHouseManagerRepository;

    private GreenHouseManager manager;

    @PostConstruct
    public void initIt() {
        manager = greenHouseManagerRepository.findOne(START_PROFILE_SETTINGS);

    }
    //tmp

    private void manageHumidity() {
        log.debug("HUMIDITY Start" + manager.getGreenHouse().getHumidity().getSensorValue() + ", " + manager.getSettings().getMinHumidity());

        if (manager.getGreenHouse().getHumidity().getSensorValue() < manager.getSettings().getMinHumidity()) {
            log.debug("HUMIDITY ON " + manager.getGreenHouse().getHumidity().getSensorValue() + ", " + manager.getSettings().getMinHumidity());
            manager.getGreenHouse().getHumidifier().turnOn();
        } else if (manager.getGreenHouse().getHumidity().getSensorValue() >= manager.getSettings().getMaxHumidity()) {
            log.debug("HUMIDITY OFF");

            manager.getGreenHouse().getHumidifier().turnOff();
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    private void managePumps() {
        boolean wattering = true;
        for (Plant plant : manager.getGreenHouse().getPlants()) {
            if (plant.getHumidity().getSensorValue() < manager.getSettings().getMinGrounHumidity()) {
                wattering = true;
            } else if (plant.getHumidity().getSensorValue() > manager.getSettings().getMaxGroundHumidity()) {
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
        log.debug(manager.getSettings().getStartHour() + "   " + time.getHourOfDay());
        if (manager.getSettings().getStartHour() > time.getHourOfDay() && manager.getSettings().getStartMinute() >= time.getMinuteOfHour()
                && manager.getSettings().getEndHour() < time.getHourOfDay() && manager.getSettings().getEndMinute() <= time.getMinuteOfHour()) {
            lightsOn = true;
        } else {
            lightsOn = false;
        }

        for (OutSwitch light : manager.getGreenHouse().getLights()) {
            if (lightsOn) {
                log.debug("Lights ON");

                light.turnOn();
            } else {
                log.debug("Lights off");

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

    @Override
    public void update(Long id) {
        manager = greenHouseManagerRepository.findOne(id);
    }

}
