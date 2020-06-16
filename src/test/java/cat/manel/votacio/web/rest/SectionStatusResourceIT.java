package cat.manel.votacio.web.rest;

import cat.manel.votacio.VotacioApp;
import cat.manel.votacio.config.TestSecurityConfiguration;
import cat.manel.votacio.domain.SectionStatus;
import cat.manel.votacio.repository.SectionStatusRepository;
import cat.manel.votacio.service.SectionStatusService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SectionStatusResource} REST controller.
 */
@SpringBootTest(classes = { VotacioApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SectionStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SectionStatusRepository sectionStatusRepository;

    @Autowired
    private SectionStatusService sectionStatusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSectionStatusMockMvc;

    private SectionStatus sectionStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SectionStatus createEntity(EntityManager em) {
        SectionStatus sectionStatus = new SectionStatus()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return sectionStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SectionStatus createUpdatedEntity(EntityManager em) {
        SectionStatus sectionStatus = new SectionStatus()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return sectionStatus;
    }

    @BeforeEach
    public void initTest() {
        sectionStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createSectionStatus() throws Exception {
        int databaseSizeBeforeCreate = sectionStatusRepository.findAll().size();
        // Create the SectionStatus
        restSectionStatusMockMvc.perform(post("/api/section-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sectionStatus)))
            .andExpect(status().isCreated());

        // Validate the SectionStatus in the database
        List<SectionStatus> sectionStatusList = sectionStatusRepository.findAll();
        assertThat(sectionStatusList).hasSize(databaseSizeBeforeCreate + 1);
        SectionStatus testSectionStatus = sectionStatusList.get(sectionStatusList.size() - 1);
        assertThat(testSectionStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSectionStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSectionStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectionStatusRepository.findAll().size();

        // Create the SectionStatus with an existing ID
        sectionStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionStatusMockMvc.perform(post("/api/section-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sectionStatus)))
            .andExpect(status().isBadRequest());

        // Validate the SectionStatus in the database
        List<SectionStatus> sectionStatusList = sectionStatusRepository.findAll();
        assertThat(sectionStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionStatusRepository.findAll().size();
        // set the field null
        sectionStatus.setName(null);

        // Create the SectionStatus, which fails.


        restSectionStatusMockMvc.perform(post("/api/section-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sectionStatus)))
            .andExpect(status().isBadRequest());

        List<SectionStatus> sectionStatusList = sectionStatusRepository.findAll();
        assertThat(sectionStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSectionStatuses() throws Exception {
        // Initialize the database
        sectionStatusRepository.saveAndFlush(sectionStatus);

        // Get all the sectionStatusList
        restSectionStatusMockMvc.perform(get("/api/section-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sectionStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getSectionStatus() throws Exception {
        // Initialize the database
        sectionStatusRepository.saveAndFlush(sectionStatus);

        // Get the sectionStatus
        restSectionStatusMockMvc.perform(get("/api/section-statuses/{id}", sectionStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sectionStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingSectionStatus() throws Exception {
        // Get the sectionStatus
        restSectionStatusMockMvc.perform(get("/api/section-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSectionStatus() throws Exception {
        // Initialize the database
        sectionStatusService.save(sectionStatus);

        int databaseSizeBeforeUpdate = sectionStatusRepository.findAll().size();

        // Update the sectionStatus
        SectionStatus updatedSectionStatus = sectionStatusRepository.findById(sectionStatus.getId()).get();
        // Disconnect from session so that the updates on updatedSectionStatus are not directly saved in db
        em.detach(updatedSectionStatus);
        updatedSectionStatus
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restSectionStatusMockMvc.perform(put("/api/section-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSectionStatus)))
            .andExpect(status().isOk());

        // Validate the SectionStatus in the database
        List<SectionStatus> sectionStatusList = sectionStatusRepository.findAll();
        assertThat(sectionStatusList).hasSize(databaseSizeBeforeUpdate);
        SectionStatus testSectionStatus = sectionStatusList.get(sectionStatusList.size() - 1);
        assertThat(testSectionStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSectionStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSectionStatus() throws Exception {
        int databaseSizeBeforeUpdate = sectionStatusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSectionStatusMockMvc.perform(put("/api/section-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sectionStatus)))
            .andExpect(status().isBadRequest());

        // Validate the SectionStatus in the database
        List<SectionStatus> sectionStatusList = sectionStatusRepository.findAll();
        assertThat(sectionStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSectionStatus() throws Exception {
        // Initialize the database
        sectionStatusService.save(sectionStatus);

        int databaseSizeBeforeDelete = sectionStatusRepository.findAll().size();

        // Delete the sectionStatus
        restSectionStatusMockMvc.perform(delete("/api/section-statuses/{id}", sectionStatus.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SectionStatus> sectionStatusList = sectionStatusRepository.findAll();
        assertThat(sectionStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
