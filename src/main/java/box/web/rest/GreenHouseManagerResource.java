package box.web.rest;

import com.codahale.metrics.annotation.Timed;
import box.domain.GreenHouseManager;

import box.repository.GreenHouseManagerRepository;
import box.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GreenHouseManager.
 */
@RestController
@RequestMapping("/api")
public class GreenHouseManagerResource {

    private final Logger log = LoggerFactory.getLogger(GreenHouseManagerResource.class);
        
    @Inject
    private GreenHouseManagerRepository greenHouseManagerRepository;

    /**
     * POST  /green-house-managers : Create a new greenHouseManager.
     *
     * @param greenHouseManager the greenHouseManager to create
     * @return the ResponseEntity with status 201 (Created) and with body the new greenHouseManager, or with status 400 (Bad Request) if the greenHouseManager has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/green-house-managers")
    @Timed
    public ResponseEntity<GreenHouseManager> createGreenHouseManager(@RequestBody GreenHouseManager greenHouseManager) throws URISyntaxException {
        log.debug("REST request to save GreenHouseManager : {}", greenHouseManager);
        if (greenHouseManager.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("greenHouseManager", "idexists", "A new greenHouseManager cannot already have an ID")).body(null);
        }
        GreenHouseManager result = greenHouseManagerRepository.save(greenHouseManager);
        return ResponseEntity.created(new URI("/api/green-house-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("greenHouseManager", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /green-house-managers : Updates an existing greenHouseManager.
     *
     * @param greenHouseManager the greenHouseManager to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated greenHouseManager,
     * or with status 400 (Bad Request) if the greenHouseManager is not valid,
     * or with status 500 (Internal Server Error) if the greenHouseManager couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/green-house-managers")
    @Timed
    public ResponseEntity<GreenHouseManager> updateGreenHouseManager(@RequestBody GreenHouseManager greenHouseManager) throws URISyntaxException {
        log.debug("REST request to update GreenHouseManager : {}", greenHouseManager);
        if (greenHouseManager.getId() == null) {
            return createGreenHouseManager(greenHouseManager);
        }
        GreenHouseManager result = greenHouseManagerRepository.save(greenHouseManager);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("greenHouseManager", greenHouseManager.getId().toString()))
            .body(result);
    }

    /**
     * GET  /green-house-managers : get all the greenHouseManagers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of greenHouseManagers in body
     */
    @GetMapping("/green-house-managers")
    @Timed
    public List<GreenHouseManager> getAllGreenHouseManagers() {
        log.debug("REST request to get all GreenHouseManagers");
        List<GreenHouseManager> greenHouseManagers = greenHouseManagerRepository.findAll();
        return greenHouseManagers;
    }

    /**
     * GET  /green-house-managers/:id : get the "id" greenHouseManager.
     *
     * @param id the id of the greenHouseManager to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the greenHouseManager, or with status 404 (Not Found)
     */
    @GetMapping("/green-house-managers/{id}")
    @Timed
    public ResponseEntity<GreenHouseManager> getGreenHouseManager(@PathVariable Long id) {
        log.debug("REST request to get GreenHouseManager : {}", id);
        GreenHouseManager greenHouseManager = greenHouseManagerRepository.findOne(id);
        return Optional.ofNullable(greenHouseManager)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /green-house-managers/:id : delete the "id" greenHouseManager.
     *
     * @param id the id of the greenHouseManager to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/green-house-managers/{id}")
    @Timed
    public ResponseEntity<Void> deleteGreenHouseManager(@PathVariable Long id) {
        log.debug("REST request to delete GreenHouseManager : {}", id);
        greenHouseManagerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("greenHouseManager", id.toString())).build();
    }

}
