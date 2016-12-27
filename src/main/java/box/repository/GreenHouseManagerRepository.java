package box.repository;

import box.domain.GreenHouseManager;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GreenHouseManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GreenHouseManagerRepository extends JpaRepository<GreenHouseManager,Long> {

}
