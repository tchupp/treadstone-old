package com.treadstone.web.rest;

import com.treadstone.Application;
import com.treadstone.domain.Registration;
import com.treadstone.repository.RegistrationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the RegistrationResource REST controller.
 *
 * @see RegistrationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RegistrationResourceTest {


    @Inject
    private RegistrationRepository registrationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restRegistrationMockMvc;

    private Registration registration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegistrationResource registrationResource = new RegistrationResource();
        ReflectionTestUtils.setField(registrationResource, "registrationRepository", registrationRepository);
        this.restRegistrationMockMvc = MockMvcBuilders.standaloneSetup(registrationResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        registration = new Registration();
    }

    @Test
    @Transactional
    public void createRegistration() throws Exception {
        int databaseSizeBeforeCreate = registrationRepository.findAll().size();

        // Create the Registration

        restRegistrationMockMvc.perform(post("/api/registrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(registration)))
                .andExpect(status().isCreated());

        // Validate the Registration in the database
        List<Registration> registrations = registrationRepository.findAll();
        assertThat(registrations).hasSize(databaseSizeBeforeCreate + 1);
        Registration testRegistration = registrations.get(registrations.size() - 1);
    }

    @Test
    @Transactional
    public void getAllRegistrations() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        // Get all the registrations
        restRegistrationMockMvc.perform(get("/api/registrations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(registration.getId().intValue())));
    }

    @Test
    @Transactional
    public void getRegistration() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        // Get the registration
        restRegistrationMockMvc.perform(get("/api/registrations/{id}", registration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(registration.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRegistration() throws Exception {
        // Get the registration
        restRegistrationMockMvc.perform(get("/api/registrations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistration() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

		int databaseSizeBeforeUpdate = registrationRepository.findAll().size();

        // Update the registration
        

        restRegistrationMockMvc.perform(put("/api/registrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(registration)))
                .andExpect(status().isOk());

        // Validate the Registration in the database
        List<Registration> registrations = registrationRepository.findAll();
        assertThat(registrations).hasSize(databaseSizeBeforeUpdate);
        Registration testRegistration = registrations.get(registrations.size() - 1);
    }

    @Test
    @Transactional
    public void deleteRegistration() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

		int databaseSizeBeforeDelete = registrationRepository.findAll().size();

        // Get the registration
        restRegistrationMockMvc.perform(delete("/api/registrations/{id}", registration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Registration> registrations = registrationRepository.findAll();
        assertThat(registrations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
