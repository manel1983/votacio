package cat.manel.votacio.web.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.manel.votacio.service.QuizService;
import cat.manel.votacio.service.dto.QuizDTO;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cat.manel.votacio.domain.Quiz}.
 */
@RestController
@RequestMapping("/api")
public class QuizResource {

    private final Logger log = LoggerFactory.getLogger(QuizResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuizService quizService;

    public QuizResource(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * {@code GET  /quiz/:id} : get the "id" vote.
     *
     * @param id the id of the quiz to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quiz, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quiz/{id}")
    public ResponseEntity<QuizDTO> getQuiz(@PathVariable Long id) {
        log.debug("REST request to get Quiz: {}", id);
        Optional<QuizDTO> quiz = quizService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quiz);
    }

}
