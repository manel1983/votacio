package cat.manel.votacio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cat.manel.votacio.domain.Answer;

/**
 * Service Interface for managing {@link Answer}.
 */
public interface AnswerService {

    /**
     * Save a answer.
     *
     * @param answer the entity to save.
     * @return the persisted entity.
     */
    Answer save(Answer answer);

    /**
     * Get all the answers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Answer> findAll(Pageable pageable);

    List<Answer> findByQuestion(Long questionId);

    /**
     * Get the "id" answer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Answer> findOne(Long id);

    /**
     * Delete the "id" answer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
