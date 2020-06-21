package cat.manel.votacio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cat.manel.votacio.domain.Question;

/**
 * Service Interface for managing {@link Question}.
 */
public interface QuestionService {

    /**
     * Save a question.
     *
     * @param question the entity to save.
     * @return the persisted entity.
     */
    Question save(Question question);

    /**
     * Get all the questions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Question> findAll(Pageable pageable);

    List<Question> findBySection(Long sectionId);

    /**
     * Get the "id" question.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Question> findOne(Long id);

    /**
     * Delete the "id" question.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
