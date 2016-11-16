package box.web.rest;

import com.codahale.metrics.annotation.Timed;
import box.domain.InSensor;

import box.repository.InSensorRepository;
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
 * REST controller for managing InSensor.
 */
@RestController
@RequestMapping("/api")
public class InSensorResource {

    private final Logger log = LoggerFactory.getLogger(InSensorResource.class);
        
    @Inject
    private InSensorRepository inSensorRepository;

    /**
     * POST  /in-sensors : Create a new inSensor.
     *
     * @param inSensor the inSensor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inSensor, or with status 400 (Bad Request) if the inSensor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/in-sensors")
    @Timed
    public ResponseEntity<InSensor> createInSensor(@Valid @RequestBody InSensor inSensor) throws URISyntaxException {
        log.debug("REST request to save InSensor : {}", inSensor);
        if (inSensor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("inSensor", "idexists", "A new inSensor cannot already have an ID")).body(null);
        }
        InSensor result = inSensorRepository.save(inSensor);
        return ResponseEntity.created(new URI("/api/in-sensors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("inSensor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /in-sensors : Updates an existing inSensor.
     *
     * @param inSensor the inSensor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inSensor,
     * or with status 400 (Bad Request) if the inSensor is not valid,
     * or with status 500 (Internal Server Error) if the inSensor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/in-sensors")
    @Timed
    public ResponseEntity<InSensor> updateInSensor(@Valid @RequestBody InSensor inSensor) throws URISyntaxException {
        log.debug("REST request to update InSensor : {}", inSensor);
        if (inSensor.getId() == null) {
            return createInSensor(inSensor);
        }
        InSensor result = inSensorRepository.save(inSensor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("inSensor", inSensor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /in-sensors : get all the inSensors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inSensors in body
     */
    @GetMapping("/in-sensors")
    @Timed
    public List<InSensor> getAllInSensors() {
        log.debug("REST request to get all InSensors");
        List<InSensor> inSensors = inSensorRepository.findAll();
        return inSensors;
    }

    /**
     * GET  /in-sensors/:id : get the "id" inSensor.
     *
     * @param id the id of the inSensor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inSensor, or with status 404 (Not Found)
     */
    @GetMapping("/in-sensors/{id}")
    @Timed
    public ResponseEntity<InSensor> getInSensor(@PathVariable Long id) {
        log.debug("REST request to get InSensor : {}", id);
        InSensor inSensor = inSensorRepository.findOne(id);
        return Optional.ofNullable(inSensor)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /in-sensors/:id : delete the "id" inSensor.
     *
     * @param id the id of the inSensor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/in-sensors/{id}")
    @Timed
    public ResponseEntity<Void> deleteInSensor(@PathVariable Long id) {
        log.debug("REST request to delete InSensor : {}", id);
        inSensorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("inSensor", id.toString())).build();
    }

}
