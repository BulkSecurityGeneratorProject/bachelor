package box.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GreenHouseManager.
 */
@Entity
@Table(name = "green_house_manager")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GreenHouseManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private ProfileSettings settings;

    @OneToOne
    @JoinColumn(unique = true)
    private GreenHouse greenHouse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileSettings getSettings() {
        return settings;
    }

    public GreenHouseManager settings(ProfileSettings profileSettings) {
        this.settings = profileSettings;
        return this;
    }

    public void setSettings(ProfileSettings profileSettings) {
        this.settings = profileSettings;
    }

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public GreenHouseManager greenHouse(GreenHouse greenHouse) {
        this.greenHouse = greenHouse;
        return this;
    }

    public void setGreenHouse(GreenHouse greenHouse) {
        this.greenHouse = greenHouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GreenHouseManager greenHouseManager = (GreenHouseManager) o;
        if(greenHouseManager.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, greenHouseManager.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GreenHouseManager{" +
            "id=" + id +
            '}';
    }
}
