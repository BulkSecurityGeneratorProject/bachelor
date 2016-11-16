package box.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.RaspiPin;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.sound.midi.SysexMessage;

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

    
    
    @Transient
    @JsonSerialize
    @JsonDeserialize
    private GpioPinAnalogInput pin;
    
    
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
        //TMP SOLUTION
       // pin = GpioFactory.getInstance().provisionAnalogInputPin(RaspiPin.getPinByAddress(17));
        this.pinNumber = pinNumber;
        System.err.println(pin.getPin().getAddress());
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
        if (inSensor.id == null || id == null) {
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
        return "InSensor{"
                + "id=" + id
                + ", name='" + name + "'"
                + ", pinNumber='" + pinNumber + "'"
                + '}';
    }
}
