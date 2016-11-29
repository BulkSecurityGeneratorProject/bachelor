/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package box.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author emil
 */
public class ProfileSettings {

    private double maxHumidity;
    private double minHumidity;
    private double maxTemp;
    private double minTemp;
    private Integer[] startLight = new Integer[2];
    private Integer[] endLight = new Integer[2];
    private double minGroundHumidity;
    private double maxGroundHumidity;

    public double getMinGroundHumidity() {
        return minGroundHumidity;
    }

    public void setMinGroundHumidity(double minGroundHumidity) {
        this.minGroundHumidity = minGroundHumidity;
    }

    public double getMaxGroundHumidity() {
        return maxGroundHumidity;
    }

    public void setMaxGroundHumidity(double maxGroundHumidity) {
        this.maxGroundHumidity = maxGroundHumidity;
    }

    public ProfileSettings(double maxHumidity, double minHumidity, double maxTemp, double minTemp, Integer[] startLight, Integer[] endLight, double minGroundHumidity, double maxGroundHumidity) {
        this.maxHumidity = maxHumidity;
        this.minHumidity = minHumidity;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.startLight = startLight;
        this.endLight = endLight;
        this.minGroundHumidity = minGroundHumidity;
        this.maxGroundHumidity = maxGroundHumidity;
    }

    public ProfileSettings() {
    }

    public Integer[] getEndLight() {
        return endLight;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public Integer[] getStartLight() {
        return startLight;
    }

    public void setStartLight(Integer[] startLight) {
        this.startLight = startLight;
    }

    public void setEndLight(Integer[] endLight) {
        this.endLight = endLight;
    }

}
