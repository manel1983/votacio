package cat.manel.votacio.service;

import cat.manel.votacio.domain.AnswerStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AnswerStatus}.
 */
public interface AnswerStatusService {

    /**
     * Save a answerStatus.
     *
     * @param answerStatus the entity to save.
     * @return the persisted entity.
     */
    AnswerStatus save(AnswerStatus answerStatus);

    /**
     * Get all the answerStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnswerStatus> findAll(Pageable pageable);


    /**
     * Get the "id" answerStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerStatus> findOne(Long id);

    /**
     * Delete the "id" answerStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
