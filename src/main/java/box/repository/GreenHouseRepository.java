package box.repository;

import box.domain.GreenHouse;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the GreenHouse entity.
 */
@SuppressWarnings("unused")
public interface GreenHouseRepository extends JpaRepository<GreenHouse,Long> {

    @Query("select distinct greenHouse from GreenHouse greenHouse left join fetch greenHouse.plants left join fetch greenHouse.pumps left join fetch greenHouse.lights left join fetch greenHouse.fans")
    List<GreenHouse> findAllWithEagerRelationships();

    @Query("select greenHouse from GreenHouse greenHouse left join fetch greenHouse.plants left join fetch greenHouse.pumps left join fetch greenHouse.lights left join fetch greenHouse.fans where greenHouse.id =:id")
    GreenHouse findOneWithEagerRelationships(@Param("id") Long id);

}
