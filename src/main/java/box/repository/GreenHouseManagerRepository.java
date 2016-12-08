package box.repository;

import box.domain.GreenHouseManager;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GreenHouseManager entity.
 */
@SuppressWarnings("unused")
public interface GreenHouseManagerRepository extends JpaRepository<GreenHouseManager,Long> {

}
