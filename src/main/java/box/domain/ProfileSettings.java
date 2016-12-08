package box.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProfileSettings.
 */
@Entity
@Table(name = "profile_settings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProfileSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "max_ground_humidity", nullable = false)
    private Double maxGroundHumidity;

    @NotNull
    @Column(name = "min_groun_humidity", nullable = false)
    private Double minGrounHumidity;

    @NotNull
    @Column(name = "min_humidity", nullable = false)
    private Double minHumidity;

    @NotNull
    @Column(name = "max_humidity", nullable = false)
    private Double maxHumidity;

    @Column(name = "max_temperature")
    private Double maxTemperature;

    @Column(name = "min_temperature")
    private Double minTemperature;

    @Column(name = "start_hour")
    private Integer startHour;

    @Column(name = "start_minute")
    private Integer startMinute;

    @Column(name = "end_minute")
    private Integer endMinute;

    @Column(name = "end_hour")
    private Integer endHour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMaxGroundHumidity() {
        return maxGroundHumidity;
    }

    public ProfileSettings maxGroundHumidity(Double maxGroundHumidity) {
        this.maxGroundHumidity = maxGroundHumidity;
        return this;
    }

    public void setMaxGroundHumidity(Double maxGroundHumidity) {
        this.maxGroundHumidity = maxGroundHumidity;
    }

    public Double getMinGrounHumidity() {
        return minGrounHumidity;
    }

    public ProfileSettings minGrounHumidity(Double minGrounHumidity) {
        this.minGrounHumidity = minGrounHumidity;
        return this;
    }

    public void setMinGrounHumidity(Double minGrounHumidity) {
        this.minGrounHumidity = minGrounHumidity;
    }

    public Double getMinHumidity() {
        return minHumidity;
    }

    public ProfileSettings minHumidity(Double minHumidity) {
        this.minHumidity = minHumidity;
        return this;
    }

    public void setMinHumidity(Double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public Double getMaxHumidity() {
        return maxHumidity;
    }

    public ProfileSettings maxHumidity(Double maxHumidity) {
        this.maxHumidity = maxHumidity;
        return this;
    }

    public void setMaxHumidity(Double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public ProfileSettings maxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
        return this;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public ProfileSettings minTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
        return this;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public ProfileSettings startHour(Integer startHour) {
        this.startHour = startHour;
        return this;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public ProfileSettings startMinute(Integer startMinute) {
        this.startMinute = startMinute;
        return this;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public ProfileSettings endMinute(Integer endMinute) {
        this.endMinute = endMinute;
        return this;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public ProfileSettings endHour(Integer endHour) {
        this.endHour = endHour;
        return this;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfileSettings profileSettings = (ProfileSettings) o;
        if(profileSettings.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, profileSettings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProfileSettings{" +
            "id=" + id +
            ", maxGroundHumidity='" + maxGroundHumidity + "'" +
            ", minGrounHumidity='" + minGrounHumidity + "'" +
            ", minHumidity='" + minHumidity + "'" +
            ", maxHumidity='" + maxHumidity + "'" +
            ", maxTemperature='" + maxTemperature + "'" +
            ", minTemperature='" + minTemperature + "'" +
            ", startHour='" + startHour + "'" +
            ", startMinute='" + startMinute + "'" +
            ", endMinute='" + endMinute + "'" +
            ", endHour='" + endHour + "'" +
            '}';
    }
}
