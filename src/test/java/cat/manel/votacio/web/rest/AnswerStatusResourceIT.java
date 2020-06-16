package cat.manel.votacio.web.rest;

import cat.manel.votacio.VotacioApp;
import cat.manel.votacio.config.TestSecurityConfiguration;
import cat.manel.votacio.domain.AnswerStatus;
import cat.manel.votacio.repository.AnswerStatusRepository;
import cat.manel.votacio.service.AnswerStatusService;

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
 * Integration tests for the {@link AnswerStatusResource} REST controller.
 */
@SpringBootTest(classes = { VotacioApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AnswerStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AnswerStatusRepository answerStatusRepository;

    @Autowired
    private AnswerStatusService answerStatusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnswerStatusMockMvc;

    private AnswerStatus answerStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerStatus createEntity(EntityManager em) {
        AnswerStatus answerStatus = new AnswerStatus()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return answerStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerStatus createUpdatedEntity(EntityManager em) {
        AnswerStatus answerStatus = new AnswerStatus()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return answerStatus;
    }

    @BeforeEach
    public void initTest() {
        answerStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnswerStatus() throws Exception {
        int databaseSizeBeforeCreate = answerStatusRepository.findAll().size();
        // Create the AnswerStatus
        restAnswerStatusMockMvc.perform(post("/api/answer-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerStatus)))
            .andExpect(status().isCreated());

        // Validate the AnswerStatus in the database
        List<AnswerStatus> answerStatusList = answerStatusRepository.findAll();
        assertThat(answerStatusList).hasSize(databaseSizeBeforeCreate + 1);
        AnswerStatus testAnswerStatus = answerStatusList.get(answerStatusList.size() - 1);
        assertThat(testAnswerStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnswerStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAnswerStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = answerStatusRepository.findAll().size();

        // Create the AnswerStatus with an existing ID
        answerStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerStatusMockMvc.perform(post("/api/answer-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerStatus)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerStatus in the database
        List<AnswerStatus> answerStatusList = answerStatusRepository.findAll();
        assertThat(answerStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = answerStatusRepository.findAll().size();
        // set the field null
        answerStatus.setName(null);

        // Create the AnswerStatus, which fails.


        restAnswerStatusMockMvc.perform(post("/api/answer-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerStatus)))
            .andExpect(status().isBadRequest());

        List<AnswerStatus> answerStatusList = answerStatusRepository.findAll();
        assertThat(answerStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnswerStatuses() throws Exception {
        // Initialize the database
        answerStatusRepository.saveAndFlush(answerStatus);

        // Get all the answerStatusList
        restAnswerStatusMockMvc.perform(get("/api/answer-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answerStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getAnswerStatus() throws Exception {
        // Initialize the database
        answerStatusRepository.saveAndFlush(answerStatus);

        // Get the answerStatus
        restAnswerStatusMockMvc.perform(get("/api/answer-statuses/{id}", answerStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(answerStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingAnswerStatus() throws Exception {
        // Get the answerStatus
        restAnswerStatusMockMvc.perform(get("/api/answer-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnswerStatus() throws Exception {
        // Initialize the database
        answerStatusService.save(answerStatus);

        int databaseSizeBeforeUpdate = answerStatusRepository.findAll().size();

        // Update the answerStatus
        AnswerStatus updatedAnswerStatus = answerStatusRepository.findById(answerStatus.getId()).get();
        // Disconnect from session so that the updates on updatedAnswerStatus are not directly saved in db
        em.detach(updatedAnswerStatus);
        updatedAnswerStatus
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restAnswerStatusMockMvc.perform(put("/api/answer-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnswerStatus)))
            .andExpect(status().isOk());

        // Validate the AnswerStatus in the database
        List<AnswerStatus> answerStatusList = answerStatusRepository.findAll();
        assertThat(answerStatusList).hasSize(databaseSizeBeforeUpdate);
        AnswerStatus testAnswerStatus = answerStatusList.get(answerStatusList.size() - 1);
        assertThat(testAnswerStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnswerStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAnswerStatus() throws Exception {
        int databaseSizeBeforeUpdate = answerStatusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerStatusMockMvc.perform(put("/api/answer-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerStatus)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerStatus in the database
        List<AnswerStatus> answerStatusList = answerStatusRepository.findAll();
        assertThat(answerStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnswerStatus() throws Exception {
        // Initialize the database
        answerStatusService.save(answerStatus);

        int databaseSizeBeforeDelete = answerStatusRepository.findAll().size();

        // Delete the answerStatus
        restAnswerStatusMockMvc.perform(delete("/api/answer-statuses/{id}", answerStatus.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnswerStatus> answerStatusList = answerStatusRepository.findAll();
        assertThat(answerStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
