package cat.manel.votacio.service.impl;

import cat.manel.votacio.service.QuestionStatusService;
import cat.manel.votacio.domain.QuestionStatus;
import cat.manel.votacio.repository.QuestionStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionStatus}.
 */
@Service
@Transactional
public class QuestionStatusServiceImpl implements QuestionStatusService {

    private final Logger log = LoggerFactory.getLogger(QuestionStatusServiceImpl.class);

    private final QuestionStatusRepository questionStatusRepository;

    public QuestionStatusServiceImpl(QuestionStatusRepository questionStatusRepository) {
        this.questionStatusRepository = questionStatusRepository;
    }

    /**
     * Save a questionStatus.
     *
     * @param questionStatus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QuestionStatus save(QuestionStatus questionStatus) {
        log.debug("Request to save QuestionStatus : {}", questionStatus);
        return questionStatusRepository.save(questionStatus);
    }

    /**
     * Get all the questionStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionStatus> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionStatuses");
        return questionStatusRepository.findAll(pageable);
    }


    /**
     * Get one questionStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionStatus> findOne(Long id) {
        log.debug("Request to get QuestionStatus : {}", id);
        return questionStatusRepository.findById(id);
    }

    /**
     * Delete the questionStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionStatus : {}", id);
        questionStatusRepository.deleteById(id);
    }
}
