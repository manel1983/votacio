package cat.manel.votacio.service.impl;

import cat.manel.votacio.service.AnswerStatusService;
import cat.manel.votacio.domain.AnswerStatus;
import cat.manel.votacio.repository.AnswerStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnswerStatus}.
 */
@Service
@Transactional
public class AnswerStatusServiceImpl implements AnswerStatusService {

    private final Logger log = LoggerFactory.getLogger(AnswerStatusServiceImpl.class);

    private final AnswerStatusRepository answerStatusRepository;

    public AnswerStatusServiceImpl(AnswerStatusRepository answerStatusRepository) {
        this.answerStatusRepository = answerStatusRepository;
    }

    /**
     * Save a answerStatus.
     *
     * @param answerStatus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnswerStatus save(AnswerStatus answerStatus) {
        log.debug("Request to save AnswerStatus : {}", answerStatus);
        return answerStatusRepository.save(answerStatus);
    }

    /**
     * Get all the answerStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnswerStatus> findAll(Pageable pageable) {
        log.debug("Request to get all AnswerStatuses");
        return answerStatusRepository.findAll(pageable);
    }


    /**
     * Get one answerStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerStatus> findOne(Long id) {
        log.debug("Request to get AnswerStatus : {}", id);
        return answerStatusRepository.findById(id);
    }

    /**
     * Delete the answerStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnswerStatus : {}", id);
        answerStatusRepository.deleteById(id);
    }
}
