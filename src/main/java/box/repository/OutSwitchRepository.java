package box.repository;

import box.domain.OutSwitch;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OutSwitch entity.
 */
@SuppressWarnings("unused")
public interface OutSwitchRepository extends JpaRepository<OutSwitch,Long> {

}
