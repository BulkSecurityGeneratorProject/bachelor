package box.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Plant.
 */
@Entity
@Table(name = "plant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "min_humidity", nullable = false)
    private Double minHumidity;

    @OneToOne
    @JoinColumn(unique = true)
    private InSensor humidity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Plant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinHumidity() {
        return minHumidity;
    }

    public Plant minHumidity(Double minHumidity) {
        this.minHumidity = minHumidity;
        return this;
    }

    public void setMinHumidity(Double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public InSensor getHumidity() {
        return humidity;
    }

    public Plant humidity(InSensor inSensor) {
        this.humidity = inSensor;
        return this;
    }

    public void setHumidity(InSensor inSensor) {
        this.humidity = inSensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plant plant = (Plant) o;
        if(plant.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, plant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Plant{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", minHumidity='" + minHumidity + "'" +
            '}';
    }
}
