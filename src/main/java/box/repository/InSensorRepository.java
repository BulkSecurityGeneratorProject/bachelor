package box.repository;

import box.domain.InSensor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InSensor entity.
 */
@SuppressWarnings("unused")
public interface InSensorRepository extends JpaRepository<InSensor,Long> {

}
