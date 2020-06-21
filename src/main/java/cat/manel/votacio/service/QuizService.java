package cat.manel.votacio.service;

import java.util.Optional;

import cat.manel.votacio.service.dto.QuizDTO;

/**
 * Service Interface for managing {@link Quiz}.
 */
public interface QuizService {

    /**
     * Get the "id" quiz.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuizDTO> findOne(Long id);

}
