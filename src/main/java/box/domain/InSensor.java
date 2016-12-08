package box.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A InSensor.
 */
@Entity
@Table(name = "in_sensor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InSensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "pin_number", nullable = false)
    private Integer pinNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public InSensor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPinNumber() {
        return pinNumber;
    }

    public InSensor pinNumber(Integer pinNumber) {
        this.pinNumber = pinNumber;
        return this;
    }

    public void setPinNumber(Integer pinNumber) {
        this.pinNumber = pinNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InSensor inSensor = (InSensor) o;
        if(inSensor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, inSensor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InSensor{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", pinNumber='" + pinNumber + "'" +
            '}';
    }
}
