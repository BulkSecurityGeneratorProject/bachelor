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
import box.domain.ProfileSettings;
import box.utils.RaspiPinTools;

@Service
@Transactional
public class GreenHouseManagerServiceServiceImpl implements GreenHouseManagerServiceService {

    private static final long START_PROFILE_SETTINGS = 1011L;
    private static final int WRONG_VALUE = -999;
    private final Logger log = LoggerFactory.getLogger(GreenHouseManagerServiceServiceImpl.class);
    @Inject
    private GreenHouseManagerRepository greenHouseManagerRepository;

    private GreenHouseManager manager;

    @PostConstruct
    public void initIt() {
        manager = greenHouseManagerRepository.findOne(START_PROFILE_SETTINGS);

    }
    //tmp

    @Transactional(propagation = Propagation.SUPPORTS)
    private void manageHumidity() {
        double humidity = RaspiPinTools.getHumidity(manager.getGreenHouse().getTemperature().getPinNumber());
        log.debug("Humidity read: " + humidity);
        if (humidity != WRONG_VALUE) {
            if (humidity < manager.getSettings().getMinHumidity()) {
                manager.getGreenHouse().getHumidifier().turnOn();
            } else if (humidity >= manager.getSettings().getMaxHumidity()) {
                log.debug("HUMIDITY OFF");

                manager.getGreenHouse().getHumidifier().turnOff();
            }
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
            //TODO: like lights
//            if (wattering) {
//                log.debug("PUMPS:\t" + manager.getGreenHouse().getPumps().turnOn());
//            } else {
//                log.debug("PUMPS:\t" + manager.getGreenHouse().getHumidifier().turnOff());
//            }

        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    private void manageLights() {
        DateTime time = new DateTime();
        boolean lightsOn = true;
        //TODO repair checking conditional
        if (manager.getSettings().getStartHour() < time.getHourOfDay()
                && manager.getSettings().getEndHour() > time.getHourOfDay()) {
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

        manageHumidity();
        managePumps();
        manageLights();
//        }

    }

    @Override
    public void update(ProfileSettings profileSettings) {
        manager.setSettings(profileSettings);
    }

}
