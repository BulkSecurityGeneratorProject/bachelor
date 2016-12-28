package box.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GreenHouse.
 */
@Entity
@Table(name = "green_house")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GreenHouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private InSensor humidity;

    @OneToOne
    @JoinColumn(unique = true)
    private InSensor temperature;

    @OneToOne
    @JoinColumn(unique = true)
    private OutSwitch humidifier;

    @ManyToMany
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "green_house_plants",
               joinColumns = @JoinColumn(name="green_houses_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="plants_id", referencedColumnName="ID"))
    private Set<Plant> plants = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "green_house_pumps",
               joinColumns = @JoinColumn(name="green_houses_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="pumps_id", referencedColumnName="ID"))
    private Set<OutSwitch> pumps = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "green_house_lights",
               joinColumns = @JoinColumn(name="green_houses_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="lights_id", referencedColumnName="ID"))
    private Set<OutSwitch> lights = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "green_house_fans",
               joinColumns = @JoinColumn(name="green_houses_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="fans_id", referencedColumnName="ID"))
    private Set<OutSwitch> fans = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InSensor getHumidity() {
        return humidity;
    }

    public GreenHouse humidity(InSensor inSensor) {
        this.humidity = inSensor;
        return this;
    }

    public void setHumidity(InSensor inSensor) {
        this.humidity = inSensor;
    }

    public InSensor getTemperature() {
        return temperature;
    }

    public GreenHouse temperature(InSensor inSensor) {
        this.temperature = inSensor;
        return this;
    }

    public void setTemperature(InSensor inSensor) {
        this.temperature = inSensor;
    }

    public OutSwitch getHumidifier() {
        return humidifier;
    }

    public GreenHouse humidifier(OutSwitch outSwitch) {
        this.humidifier = outSwitch;
        return this;
    }

    public void setHumidifier(OutSwitch outSwitch) {
        this.humidifier = outSwitch;
    }

    public Set<Plant> getPlants() {
        return plants;
    }

    public GreenHouse plants(Set<Plant> plants) {
        this.plants = plants;
        return this;
    }

    public GreenHouse addPlants(Plant plant) {
        plants.add(plant);
        return this;
    }

    public GreenHouse removePlants(Plant plant) {
        plants.remove(plant);
        return this;
    }

    public void setPlants(Set<Plant> plants) {
        this.plants = plants;
    }

    public Set<OutSwitch> getPumps() {
        return pumps;
    }

    public GreenHouse pumps(Set<OutSwitch> outSwitches) {
        this.pumps = outSwitches;
        return this;
    }

    public GreenHouse addPumps(OutSwitch outSwitch) {
        pumps.add(outSwitch);
        return this;
    }

    public GreenHouse removePumps(OutSwitch outSwitch) {
        pumps.remove(outSwitch);
        return this;
    }

    public void setPumps(Set<OutSwitch> outSwitches) {
        this.pumps = outSwitches;
    }

    public Set<OutSwitch> getLights() {
        return lights;
    }

    public GreenHouse lights(Set<OutSwitch> outSwitches) {
        this.lights = outSwitches;
        return this;
    }

    public GreenHouse addLights(OutSwitch outSwitch) {
        lights.add(outSwitch);
        return this;
    }

    public GreenHouse removeLights(OutSwitch outSwitch) {
        lights.remove(outSwitch);
        return this;
    }

    public void setLights(Set<OutSwitch> outSwitches) {
        this.lights = outSwitches;
    }

    public Set<OutSwitch> getFans() {
        return fans;
    }

    public GreenHouse fans(Set<OutSwitch> outSwitches) {
        this.fans = outSwitches;
        return this;
    }

    public GreenHouse addFans(OutSwitch outSwitch) {
        fans.add(outSwitch);
        return this;
    }

    public GreenHouse removeFans(OutSwitch outSwitch) {
        fans.remove(outSwitch);
        return this;
    }

    public void setFans(Set<OutSwitch> outSwitches) {
        this.fans = outSwitches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GreenHouse greenHouse = (GreenHouse) o;
        if(greenHouse.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, greenHouse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GreenHouse{" +
            "id=" + id +
            '}';
    }
}
