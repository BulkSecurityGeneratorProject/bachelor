package box.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.RaspiPin;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.RaspiPin;
/**
 * A OutSwitch.
 */
@Entity
@Table(name = "out_switch")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OutSwitch implements Serializable {

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
    private GpioPinDigitalOutput pin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public OutSwitch name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPinNumber() {
        return pinNumber;
    }

    public OutSwitch pinNumber(Integer pinNumber) {
        this.pinNumber = pinNumber;
        return this;
    }

    public void setPinNumber(Integer pinNumber) {
        pin = GpioFactory.getInstance().provisionDigitalOutputPin(
                                        RaspiPin.GPIO_00, "name", PinState.HIGH);
        pin.setShutdownOptions(true, PinState.LOW);
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
        OutSwitch outSwitch = (OutSwitch) o;
        if(outSwitch.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, outSwitch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OutSwitch{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", pinNumber='" + pinNumber + "'" +
            '}';
    }
}
