package com.treadstone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.treadstone.domain.Major;
import com.treadstone.repository.MajorRepository;
import com.treadstone.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Major.
 */
@RestController
@RequestMapping("/api")
public class MajorResource {

    private final Logger log = LoggerFactory.getLogger(MajorResource.class);

    @Inject
    private MajorRepository majorRepository;

    /**
     * POST  /majors -> Create a new major.
     */
    @RequestMapping(value = "/majors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Major> create(@Valid @RequestBody Major major) throws URISyntaxException {
        log.debug("REST request to save Major : {}", major);
        if (major.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new major cannot already have an ID").body(null);
        }
        Major result = majorRepository.save(major);
        return ResponseEntity.created(new URI("/api/majors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("major", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /majors -> Updates an existing major.
     */
    @RequestMapping(value = "/majors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Major> update(@Valid @RequestBody Major major) throws URISyntaxException {
        log.debug("REST request to update Major : {}", major);
        if (major.getId() == null) {
            return create(major);
        }
        Major result = majorRepository.save(major);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("major", major.getId().toString()))
            .body(result);
    }

    /**
     * GET  /majors -> get all the majors.
     */
    @RequestMapping(value = "/majors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Major> getAll() {
        log.debug("REST request to get all Majors");
        return majorRepository.findAll();
    }

    /**
     * GET  /majors/:id -> get the "id" major.
     */
    @RequestMapping(value = "/majors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Major> get(@PathVariable Long id) {
        log.debug("REST request to get Major : {}", id);
        return Optional.ofNullable(majorRepository.findOne(id))
            .map(major -> new ResponseEntity<>(
                major,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /majors/:id -> delete the "id" major.
     */
    @RequestMapping(value = "/majors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Major : {}", id);
        majorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("major", id.toString())).build();
    }
}
