package com.treadstone.web.rest;

import com.treadstone.Application;
import com.treadstone.domain.Major;
import com.treadstone.repository.MajorRepository;
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
 * Test class for the MajorResource REST controller.
 *
 * @see MajorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MajorResourceTest {

    private static final String DEFAULT_MAJOR_ID = "SAMPLE_TEXT";
    private static final String UPDATED_MAJOR_ID = "UPDATED_TEXT";
    private static final String DEFAULT_MAJOR_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_MAJOR_NAME = "UPDATED_TEXT";

    @Inject
    private MajorRepository majorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restMajorMockMvc;

    private Major major;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MajorResource majorResource = new MajorResource();
        ReflectionTestUtils.setField(majorResource, "majorRepository", majorRepository);
        this.restMajorMockMvc = MockMvcBuilders.standaloneSetup(majorResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        major = new Major();
        major.setMajorId(DEFAULT_MAJOR_ID);
        major.setMajorName(DEFAULT_MAJOR_NAME);
    }

    @Test
    @Transactional
    public void createMajor() throws Exception {
        int databaseSizeBeforeCreate = majorRepository.findAll().size();

        // Create the Major

        restMajorMockMvc.perform(post("/api/majors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(major)))
            .andExpect(status().isCreated());

        // Validate the Major in the database
        List<Major> majors = majorRepository.findAll();
        assertThat(majors).hasSize(databaseSizeBeforeCreate + 1);
        Major testMajor = majors.get(majors.size() - 1);
        assertThat(testMajor.getMajorId()).isEqualTo(DEFAULT_MAJOR_ID);
        assertThat(testMajor.getMajorName()).isEqualTo(DEFAULT_MAJOR_NAME);
    }

    @Test
    @Transactional
    public void checkMajorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = majorRepository.findAll().size();
        // set the field null
        major.setMajorId(null);

        // Create the Major, which fails.

        restMajorMockMvc.perform(post("/api/majors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(major)))
            .andExpect(status().isBadRequest());

        List<Major> majors = majorRepository.findAll();
        assertThat(majors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMajorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = majorRepository.findAll().size();
        // set the field null
        major.setMajorName(null);

        // Create the Major, which fails.

        restMajorMockMvc.perform(post("/api/majors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(major)))
            .andExpect(status().isBadRequest());

        List<Major> majors = majorRepository.findAll();
        assertThat(majors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMajors() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

        // Get all the majors
        restMajorMockMvc.perform(get("/api/majors"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(major.getId().intValue())))
            .andExpect(jsonPath("$.[*].majorId").value(hasItem(DEFAULT_MAJOR_ID)))
            .andExpect(jsonPath("$.[*].majorName").value(hasItem(DEFAULT_MAJOR_NAME)));
    }

    @Test
    @Transactional
    public void getMajor() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

        // Get the major
        restMajorMockMvc.perform(get("/api/majors/{id}", major.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(major.getId().intValue()))
            .andExpect(jsonPath("$.majorId").value(DEFAULT_MAJOR_ID))
            .andExpect(jsonPath("$.majorName").value(DEFAULT_MAJOR_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingMajor() throws Exception {
        // Get the major
        restMajorMockMvc.perform(get("/api/majors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMajor() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

        int databaseSizeBeforeUpdate = majorRepository.findAll().size();

        // Update the major
        major.setMajorId(UPDATED_MAJOR_ID);
        major.setMajorName(UPDATED_MAJOR_NAME);


        restMajorMockMvc.perform(put("/api/majors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(major)))
            .andExpect(status().isOk());

        // Validate the Major in the database
        List<Major> majors = majorRepository.findAll();
        assertThat(majors).hasSize(databaseSizeBeforeUpdate);
        Major testMajor = majors.get(majors.size() - 1);
        assertThat(testMajor.getMajorId()).isEqualTo(UPDATED_MAJOR_ID);
        assertThat(testMajor.getMajorName()).isEqualTo(UPDATED_MAJOR_NAME);
    }

    @Test
    @Transactional
    public void deleteMajor() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

        int databaseSizeBeforeDelete = majorRepository.findAll().size();

        // Get the major
        restMajorMockMvc.perform(delete("/api/majors/{id}", major.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Major> majors = majorRepository.findAll();
        assertThat(majors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
