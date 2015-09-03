package com.treadstone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.treadstone.domain.Registration;
import com.treadstone.repository.RegistrationRepository;
import com.treadstone.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Registration.
 */
@RestController
@RequestMapping("/api")
public class RegistrationResource {

    private final Logger log = LoggerFactory.getLogger(RegistrationResource.class);

    @Inject
    private RegistrationRepository registrationRepository;

    /**
     * POST  /registrations -> Create a new registration.
     */
    @RequestMapping(value = "/registrations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Registration> create(@RequestBody Registration registration) throws URISyntaxException {
        log.debug("REST request to save Registration : {}", registration);
        if (registration.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new registration cannot already have an ID").body(null);
        }
        Registration result = registrationRepository.save(registration);
        return ResponseEntity.created(new URI("/api/registrations/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("registration", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /registrations -> Updates an existing registration.
     */
    @RequestMapping(value = "/registrations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Registration> update(@RequestBody Registration registration) throws URISyntaxException {
        log.debug("REST request to update Registration : {}", registration);
        if (registration.getId() == null) {
            return create(registration);
        }
        Registration result = registrationRepository.save(registration);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("registration", registration.getId().toString()))
                .body(result);
    }

    /**
     * GET  /registrations -> get all the registrations.
     */
    @RequestMapping(value = "/registrations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Registration> getAll() {
        log.debug("REST request to get all Registrations");
        return registrationRepository.findAll();
    }

    /**
     * GET  /registrations/:id -> get the "id" registration.
     */
    @RequestMapping(value = "/registrations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Registration> get(@PathVariable Long id) {
        log.debug("REST request to get Registration : {}", id);
        return Optional.ofNullable(registrationRepository.findOne(id))
            .map(registration -> new ResponseEntity<>(
                registration,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /registrations/:id -> delete the "id" registration.
     */
    @RequestMapping(value = "/registrations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Registration : {}", id);
        registrationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("registration", id.toString())).build();
    }
}
