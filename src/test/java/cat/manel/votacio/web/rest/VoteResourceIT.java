package cat.manel.votacio.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import cat.manel.votacio.VotacioApp;
import cat.manel.votacio.config.TestSecurityConfiguration;
import cat.manel.votacio.domain.Answer;
import cat.manel.votacio.domain.Question;
import cat.manel.votacio.domain.Vote;
import cat.manel.votacio.repository.VoteRepository;
import cat.manel.votacio.service.VoteService;

/**
 * Integration tests for the {@link VoteResource} REST controller.
 */
@SpringBootTest(classes = { VotacioApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class VoteResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Question DEFAULT_QUESTION = new Question(1L);
    private static final Question UPDATED_QUESTION = new Question(2L);

    private static final Answer DEFAULT_ANSWER = new Answer(1L);
    private static final Answer UPDATED_ANSWER = new Answer(2L);

    private static final Long DEFAULT_STATUS = 1L;
    private static final Long UPDATED_STATUS = 2L;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoteMockMvc;

    private Vote vote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vote createEntity(EntityManager em) {
        Vote vote = new Vote()
            .userId(DEFAULT_USER_ID)
            .question(DEFAULT_QUESTION)
            .answer(DEFAULT_ANSWER)
            .status(DEFAULT_STATUS);
        return vote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vote createUpdatedEntity(EntityManager em) {
        Vote vote = new Vote()
            .userId(UPDATED_USER_ID)
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER)
            .status(UPDATED_STATUS);
        return vote;
    }

    @BeforeEach
    public void initTest() {
        vote = createEntity(em);
    }

    @Test
    @Transactional
    public void createVote() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();
        // Create the Vote
        restVoteMockMvc.perform(post("/api/votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vote)))
            .andExpect(status().isCreated());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate + 1);
        Vote testVote = voteList.get(voteList.size() - 1);
        assertThat(testVote.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testVote.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testVote.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testVote.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createVoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();

        // Create the Vote with an existing ID
        vote.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoteMockMvc.perform(post("/api/votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vote)))
            .andExpect(status().isBadRequest());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteRepository.findAll().size();
        // set the field null
        vote.setUserId(null);

        // Create the Vote, which fails.


        restVoteMockMvc.perform(post("/api/votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vote)))
            .andExpect(status().isBadRequest());

        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuestionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteRepository.findAll().size();
        // set the field null
        vote.setQuestion(null);

        // Create the Vote, which fails.


        restVoteMockMvc.perform(post("/api/votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vote)))
            .andExpect(status().isBadRequest());

        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnswerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteRepository.findAll().size();
        // set the field null
        vote.setAnswer(null);

        // Create the Vote, which fails.


        restVoteMockMvc.perform(post("/api/votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vote)))
            .andExpect(status().isBadRequest());

        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = voteRepository.findAll().size();
        // set the field null
        vote.setStatus(null);

        // Create the Vote, which fails.


        restVoteMockMvc.perform(post("/api/votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vote)))
            .andExpect(status().isBadRequest());

        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVotes() throws Exception {
        // Initialize the database
        voteRepository.saveAndFlush(vote);

        // Get all the voteList
        restVoteMockMvc.perform(get("/api/votes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vote.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].questionId").value(hasItem(DEFAULT_QUESTION.getId().intValue())))
            .andExpect(jsonPath("$.[*].answerId").value(hasItem(DEFAULT_ANSWER.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.intValue())));
    }
    
    @Test
    @Transactional
    public void getVote() throws Exception {
        // Initialize the database
        voteRepository.saveAndFlush(vote);

        // Get the vote
        restVoteMockMvc.perform(get("/api/votes/{id}", vote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vote.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.questionId").value(DEFAULT_QUESTION.getId().intValue()))
            .andExpect(jsonPath("$.answerId").value(DEFAULT_ANSWER.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingVote() throws Exception {
        // Get the vote
        restVoteMockMvc.perform(get("/api/votes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVote() throws Exception {
        // Initialize the database
        voteService.save(vote);

        int databaseSizeBeforeUpdate = voteRepository.findAll().size();

        // Update the vote
        Vote updatedVote = voteRepository.findById(vote.getId()).get();
        // Disconnect from session so that the updates on updatedVote are not directly saved in db
        em.detach(updatedVote);
        updatedVote
            .userId(UPDATED_USER_ID)
            .question(UPDATED_QUESTION)
            .answer(UPDATED_ANSWER)
            .status(UPDATED_STATUS);

        restVoteMockMvc.perform(put("/api/votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVote)))
            .andExpect(status().isOk());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeUpdate);
        Vote testVote = voteList.get(voteList.size() - 1);
        assertThat(testVote.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testVote.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testVote.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testVote.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingVote() throws Exception {
        int databaseSizeBeforeUpdate = voteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoteMockMvc.perform(put("/api/votes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vote)))
            .andExpect(status().isBadRequest());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVote() throws Exception {
        // Initialize the database
        voteService.save(vote);

        int databaseSizeBeforeDelete = voteRepository.findAll().size();

        // Delete the vote
        restVoteMockMvc.perform(delete("/api/votes/{id}", vote.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
