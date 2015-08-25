package com.treadstone.web.rest;

import com.treadstone.Application;
import com.treadstone.domain.Semester;
import com.treadstone.repository.SemesterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SemesterResource REST controller.
 *
 * @see SemesterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SemesterResourceTest {

    private static final String DEFAULT_SEMESTER_ID = "SAMPLE_TEXT";
    private static final String UPDATED_SEMESTER_ID = "UPDATED_TEXT";
    private static final String DEFAULT_SEMESTER_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_SEMESTER_NAME = "UPDATED_TEXT";

    @Inject
    private SemesterRepository semesterRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restSemesterMockMvc;

    private Semester semester;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SemesterResource semesterResource = new SemesterResource();
        ReflectionTestUtils.setField(semesterResource, "semesterRepository", semesterRepository);
        this.restSemesterMockMvc = MockMvcBuilders.standaloneSetup(semesterResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        semester = new Semester();
        semester.setSemesterId(DEFAULT_SEMESTER_ID);
        semester.setSemesterName(DEFAULT_SEMESTER_NAME);
    }

    @Test
    @Transactional
    public void createSemester() throws Exception {
        int databaseSizeBeforeCreate = semesterRepository.findAll().size();

        // Create the Semester

        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(semester)))
            .andExpect(status().isCreated());

        // Validate the Semester in the database
        List<Semester> semesters = semesterRepository.findAll();
        assertThat(semesters).hasSize(databaseSizeBeforeCreate + 1);
        Semester testSemester = semesters.get(semesters.size() - 1);
        assertThat(testSemester.getSemesterId()).isEqualTo(DEFAULT_SEMESTER_ID);
        assertThat(testSemester.getSemesterName()).isEqualTo(DEFAULT_SEMESTER_NAME);
    }

    @Test
    @Transactional
    public void checkSemesterIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = semesterRepository.findAll().size();
        // set the field null
        semester.setSemesterId(null);

        // Create the Semester, which fails.

        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(semester)))
            .andExpect(status().isBadRequest());

        List<Semester> semesters = semesterRepository.findAll();
        assertThat(semesters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSemesterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = semesterRepository.findAll().size();
        // set the field null
        semester.setSemesterName(null);

        // Create the Semester, which fails.

        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(semester)))
            .andExpect(status().isBadRequest());

        List<Semester> semesters = semesterRepository.findAll();
        assertThat(semesters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSemesters() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesters
        restSemesterMockMvc.perform(get("/api/semesters"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semester.getId().intValue())))
            .andExpect(jsonPath("$.[*].semesterId").value(hasItem(DEFAULT_SEMESTER_ID)))
            .andExpect(jsonPath("$.[*].semesterName").value(hasItem(DEFAULT_SEMESTER_NAME)));
    }

    @Test
    @Transactional
    public void getSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get the semester
        restSemesterMockMvc.perform(get("/api/semesters/{id}", semester.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(semester.getId().intValue()))
            .andExpect(jsonPath("$.semesterId").value(DEFAULT_SEMESTER_ID))
            .andExpect(jsonPath("$.semesterName").value(DEFAULT_SEMESTER_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingSemester() throws Exception {
        // Get the semester
        restSemesterMockMvc.perform(get("/api/semesters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        int databaseSizeBeforeUpdate = semesterRepository.findAll().size();

        // Update the semester
        semester.setSemesterId(UPDATED_SEMESTER_ID);
        semester.setSemesterName(UPDATED_SEMESTER_NAME);


        restSemesterMockMvc.perform(put("/api/semesters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(semester)))
            .andExpect(status().isOk());

        // Validate the Semester in the database
        List<Semester> semesters = semesterRepository.findAll();
        assertThat(semesters).hasSize(databaseSizeBeforeUpdate);
        Semester testSemester = semesters.get(semesters.size() - 1);
        assertThat(testSemester.getSemesterId()).isEqualTo(UPDATED_SEMESTER_ID);
        assertThat(testSemester.getSemesterName()).isEqualTo(UPDATED_SEMESTER_NAME);
    }

    @Test
    @Transactional
    public void deleteSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        int databaseSizeBeforeDelete = semesterRepository.findAll().size();

        // Get the semester
        restSemesterMockMvc.perform(delete("/api/semesters/{id}", semester.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Semester> semesters = semesterRepository.findAll();
        assertThat(semesters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
