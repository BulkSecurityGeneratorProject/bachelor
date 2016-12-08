package box.repository;

import box.domain.ProfileSettings;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProfileSettings entity.
 */
@SuppressWarnings("unused")
public interface ProfileSettingsRepository extends JpaRepository<ProfileSettings,Long> {

}
