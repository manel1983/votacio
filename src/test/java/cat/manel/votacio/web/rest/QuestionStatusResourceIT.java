package cat.manel.votacio.web.rest;

import cat.manel.votacio.VotacioApp;
import cat.manel.votacio.config.TestSecurityConfiguration;
import cat.manel.votacio.domain.QuestionStatus;
import cat.manel.votacio.repository.QuestionStatusRepository;
import cat.manel.votacio.service.QuestionStatusService;

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
 * Integration tests for the {@link QuestionStatusResource} REST controller.
 */
@SpringBootTest(classes = { VotacioApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class QuestionStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private QuestionStatusRepository questionStatusRepository;

    @Autowired
    private QuestionStatusService questionStatusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionStatusMockMvc;

    private QuestionStatus questionStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionStatus createEntity(EntityManager em) {
        QuestionStatus questionStatus = new QuestionStatus()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return questionStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionStatus createUpdatedEntity(EntityManager em) {
        QuestionStatus questionStatus = new QuestionStatus()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return questionStatus;
    }

    @BeforeEach
    public void initTest() {
        questionStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionStatus() throws Exception {
        int databaseSizeBeforeCreate = questionStatusRepository.findAll().size();
        // Create the QuestionStatus
        restQuestionStatusMockMvc.perform(post("/api/question-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionStatus)))
            .andExpect(status().isCreated());

        // Validate the QuestionStatus in the database
        List<QuestionStatus> questionStatusList = questionStatusRepository.findAll();
        assertThat(questionStatusList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionStatus testQuestionStatus = questionStatusList.get(questionStatusList.size() - 1);
        assertThat(testQuestionStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testQuestionStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createQuestionStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionStatusRepository.findAll().size();

        // Create the QuestionStatus with an existing ID
        questionStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionStatusMockMvc.perform(post("/api/question-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionStatus)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionStatus in the database
        List<QuestionStatus> questionStatusList = questionStatusRepository.findAll();
        assertThat(questionStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionStatusRepository.findAll().size();
        // set the field null
        questionStatus.setName(null);

        // Create the QuestionStatus, which fails.


        restQuestionStatusMockMvc.perform(post("/api/question-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionStatus)))
            .andExpect(status().isBadRequest());

        List<QuestionStatus> questionStatusList = questionStatusRepository.findAll();
        assertThat(questionStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionStatuses() throws Exception {
        // Initialize the database
        questionStatusRepository.saveAndFlush(questionStatus);

        // Get all the questionStatusList
        restQuestionStatusMockMvc.perform(get("/api/question-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getQuestionStatus() throws Exception {
        // Initialize the database
        questionStatusRepository.saveAndFlush(questionStatus);

        // Get the questionStatus
        restQuestionStatusMockMvc.perform(get("/api/question-statuses/{id}", questionStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingQuestionStatus() throws Exception {
        // Get the questionStatus
        restQuestionStatusMockMvc.perform(get("/api/question-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionStatus() throws Exception {
        // Initialize the database
        questionStatusService.save(questionStatus);

        int databaseSizeBeforeUpdate = questionStatusRepository.findAll().size();

        // Update the questionStatus
        QuestionStatus updatedQuestionStatus = questionStatusRepository.findById(questionStatus.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionStatus are not directly saved in db
        em.detach(updatedQuestionStatus);
        updatedQuestionStatus
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restQuestionStatusMockMvc.perform(put("/api/question-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionStatus)))
            .andExpect(status().isOk());

        // Validate the QuestionStatus in the database
        List<QuestionStatus> questionStatusList = questionStatusRepository.findAll();
        assertThat(questionStatusList).hasSize(databaseSizeBeforeUpdate);
        QuestionStatus testQuestionStatus = questionStatusList.get(questionStatusList.size() - 1);
        assertThat(testQuestionStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testQuestionStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionStatus() throws Exception {
        int databaseSizeBeforeUpdate = questionStatusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionStatusMockMvc.perform(put("/api/question-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionStatus)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionStatus in the database
        List<QuestionStatus> questionStatusList = questionStatusRepository.findAll();
        assertThat(questionStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionStatus() throws Exception {
        // Initialize the database
        questionStatusService.save(questionStatus);

        int databaseSizeBeforeDelete = questionStatusRepository.findAll().size();

        // Delete the questionStatus
        restQuestionStatusMockMvc.perform(delete("/api/question-statuses/{id}", questionStatus.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionStatus> questionStatusList = questionStatusRepository.findAll();
        assertThat(questionStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
