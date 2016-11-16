package box.repository;

import box.domain.Plant;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Plant entity.
 */
@SuppressWarnings("unused")
public interface PlantRepository extends JpaRepository<Plant,Long> {

}
