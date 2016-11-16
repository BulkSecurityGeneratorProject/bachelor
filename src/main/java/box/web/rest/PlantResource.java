package box.web.rest;

import com.codahale.metrics.annotation.Timed;
import box.domain.Plant;

import box.repository.PlantRepository;
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

/**
 * REST controller for managing Plant.
 */
@RestController
@RequestMapping("/api")
public class PlantResource {

    private final Logger log = LoggerFactory.getLogger(PlantResource.class);
        
    @Inject
    private PlantRepository plantRepository;

    /**
     * POST  /plants : Create a new plant.
     *
     * @param plant the plant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plant, or with status 400 (Bad Request) if the plant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plants")
    @Timed
    public ResponseEntity<Plant> createPlant(@Valid @RequestBody Plant plant) throws URISyntaxException {
        log.debug("REST request to save Plant : {}", plant);
        if (plant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("plant", "idexists", "A new plant cannot already have an ID")).body(null);
        }
        Plant result = plantRepository.save(plant);
        return ResponseEntity.created(new URI("/api/plants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("plant", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plants : Updates an existing plant.
     *
     * @param plant the plant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plant,
     * or with status 400 (Bad Request) if the plant is not valid,
     * or with status 500 (Internal Server Error) if the plant couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plants")
    @Timed
    public ResponseEntity<Plant> updatePlant(@Valid @RequestBody Plant plant) throws URISyntaxException {
        log.debug("REST request to update Plant : {}", plant);
        if (plant.getId() == null) {
            return createPlant(plant);
        }
        Plant result = plantRepository.save(plant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("plant", plant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plants : get all the plants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plants in body
     */
    @GetMapping("/plants")
    @Timed
    public List<Plant> getAllPlants() {
        log.debug("REST request to get all Plants");
        List<Plant> plants = plantRepository.findAll();
        return plants;
    }

    /**
     * GET  /plants/:id : get the "id" plant.
     *
     * @param id the id of the plant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plant, or with status 404 (Not Found)
     */
    @GetMapping("/plants/{id}")
    @Timed
    public ResponseEntity<Plant> getPlant(@PathVariable Long id) {
        log.debug("REST request to get Plant : {}", id);
        Plant plant = plantRepository.findOne(id);
        return Optional.ofNullable(plant)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /plants/:id : delete the "id" plant.
     *
     * @param id the id of the plant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plants/{id}")
    @Timed
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        log.debug("REST request to delete Plant : {}", id);
        plantRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("plant", id.toString())).build();
    }

}
