package com.treadstone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.treadstone.domain.Semester;
import com.treadstone.repository.SemesterRepository;
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
 * REST controller for managing Semester.
 */
@RestController
@RequestMapping("/api")
public class SemesterResource {

    private final Logger log = LoggerFactory.getLogger(SemesterResource.class);

    @Inject
    private SemesterRepository semesterRepository;

    /**
     * POST  /semesters -> Create a new semester.
     */
    @RequestMapping(value = "/semesters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Semester> create(@Valid @RequestBody Semester semester) throws URISyntaxException {
        log.debug("REST request to save Semester : {}", semester);
        if (semester.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new semester cannot already have an ID").body(null);
        }
        Semester result = semesterRepository.save(semester);
        return ResponseEntity.created(new URI("/api/semesters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("semester", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /semesters -> Updates an existing semester.
     */
    @RequestMapping(value = "/semesters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Semester> update(@Valid @RequestBody Semester semester) throws URISyntaxException {
        log.debug("REST request to update Semester : {}", semester);
        if (semester.getId() == null) {
            return create(semester);
        }
        Semester result = semesterRepository.save(semester);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("semester", semester.getId().toString()))
            .body(result);
    }

    /**
     * GET  /semesters -> get all the semesters.
     */
    @RequestMapping(value = "/semesters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Semester> getAll() {
        log.debug("REST request to get all Semesters");
        return semesterRepository.findAll();
    }

    /**
     * GET  /semesters/:id -> get the "id" semester.
     */
    @RequestMapping(value = "/semesters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Semester> get(@PathVariable Long id) {
        log.debug("REST request to get Semester : {}", id);
        return Optional.ofNullable(semesterRepository.findOne(id))
            .map(semester -> new ResponseEntity<>(
                semester,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /semesters/:id -> delete the "id" semester.
     */
    @RequestMapping(value = "/semesters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Semester : {}", id);
        semesterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("semester", id.toString())).build();
    }
}
