package box.web.rest;

import com.codahale.metrics.annotation.Timed;
import box.domain.ProfileSettings;

import box.repository.ProfileSettingsRepository;
import box.service.GreenHouseManagerServiceService;
import box.service.impl.GreenHouseManagerServiceServiceImpl;
import box.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * REST controller for managing ProfileSettings.
 */
@RestController
@RequestMapping("/api")
public class ProfileSettingsResource {

    private final Logger log = LoggerFactory.getLogger(ProfileSettingsResource.class);

    @Inject
    private ProfileSettingsRepository profileSettingsRepository;

    @Inject
    private GreenHouseManagerServiceService greenHouseManagerSericeService;

    /**
     * POST /profile-settings : Create a new profileSettings.
     *
     * @param profileSettings the profileSettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new profileSettings, or with status 400 (Bad Request) if the
     * profileSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profile-settings")
    @Timed
    public ResponseEntity<ProfileSettings> createProfileSettings(@Valid @RequestBody ProfileSettings profileSettings) throws URISyntaxException {
        log.debug("REST request to save ProfileSettings : {}", profileSettings);
        if (profileSettings.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("profileSettings", "idexists", "A new profileSettings cannot already have an ID")).body(null);
        }
        ProfileSettings result = profileSettingsRepository.save(profileSettings);
        return ResponseEntity.created(new URI("/api/profile-settings/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("profileSettings", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /profile-settings : Updates an existing profileSettings.
     *
     * @param profileSettings the profileSettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * profileSettings, or with status 400 (Bad Request) if the profileSettings
     * is not valid, or with status 500 (Internal Server Error) if the
     * profileSettings couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profile-settings")
    @Timed
    public ResponseEntity<ProfileSettings> updateProfileSettings(@Valid @RequestBody ProfileSettings profileSettings) throws URISyntaxException {
        log.debug("REST request to update ProfileSettings : {}", profileSettings);
        if (profileSettings.getId() == null) {
            return createProfileSettings(profileSettings);
        }
        ProfileSettings result = profileSettingsRepository.save(profileSettings);
        greenHouseManagerSericeService.update(profileSettings);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("profileSettings", profileSettings.getId().toString()))
                .body(result);
    }

    /**
     * GET /profile-settings : get all the profileSettings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * profileSettings in body
     */
    @GetMapping("/profile-settings")
    @Timed
    public List<ProfileSettings> getAllProfileSettings() {
        log.debug("REST request to get all ProfileSettings");
        List<ProfileSettings> profileSettings = profileSettingsRepository.findAll();
        return profileSettings;
    }

    /**
     * GET /profile-settings/:id : get the "id" profileSettings.
     *
     * @param id the id of the profileSettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * profileSettings, or with status 404 (Not Found)
     */
    @GetMapping("/profile-settings/{id}")
    @Timed
    public ResponseEntity<ProfileSettings> getProfileSettings(@PathVariable Long id) {
        log.debug("REST request to get ProfileSettings : {}", id);
        ProfileSettings profileSettings = profileSettingsRepository.findOne(id);
        return Optional.ofNullable(profileSettings)
                .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /profile-settings/:id : delete the "id" profileSettings.
     *
     * @param id the id of the profileSettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profile-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfileSettings(@PathVariable Long id) {
        log.debug("REST request to delete ProfileSettings : {}", id);
        profileSettingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("profileSettings", id.toString())).build();
    }

}
