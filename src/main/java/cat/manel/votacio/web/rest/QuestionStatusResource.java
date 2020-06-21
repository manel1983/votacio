package cat.manel.votacio.web.rest;

import cat.manel.votacio.domain.QuestionStatus;
import cat.manel.votacio.service.QuestionStatusService;
import cat.manel.votacio.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link cat.manel.votacio.domain.QuestionStatus}.
 */
@RestController
@RequestMapping("/api")
public class QuestionStatusResource {

    private final Logger log = LoggerFactory.getLogger(QuestionStatusResource.class);

    private static final String ENTITY_NAME = "questionStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionStatusService questionStatusService;

    public QuestionStatusResource(QuestionStatusService questionStatusService) {
        this.questionStatusService = questionStatusService;
    }

    /**
     * {@code POST  /question-statuses} : Create a new questionStatus.
     *
     * @param questionStatus the questionStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionStatus, or with status {@code 400 (Bad Request)} if the questionStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-statuses")
    public ResponseEntity<QuestionStatus> createQuestionStatus(@Valid @RequestBody QuestionStatus questionStatus) throws URISyntaxException {
        log.debug("REST request to save QuestionStatus : {}", questionStatus);
        if (questionStatus.getId() != null) {
            throw new BadRequestAlertException("A new questionStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionStatus result = questionStatusService.save(questionStatus);
        return ResponseEntity.created(new URI("/api/question-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-statuses} : Updates an existing questionStatus.
     *
     * @param questionStatus the questionStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionStatus,
     * or with status {@code 400 (Bad Request)} if the questionStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-statuses")
    public ResponseEntity<QuestionStatus> updateQuestionStatus(@Valid @RequestBody QuestionStatus questionStatus) throws URISyntaxException {
        log.debug("REST request to update QuestionStatus : {}", questionStatus);
        if (questionStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionStatus result = questionStatusService.save(questionStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questionStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-statuses} : get all the questionStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionStatuses in body.
     */
    @GetMapping("/question-statuses")
    public ResponseEntity<List<QuestionStatus>> getAllQuestionStatuses(Pageable pageable) {
        log.debug("REST request to get a page of QuestionStatuses");
        Page<QuestionStatus> page = questionStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /question-statuses/:id} : get the "id" questionStatus.
     *
     * @param id the id of the questionStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-statuses/{id}")
    public ResponseEntity<QuestionStatus> getQuestionStatus(@PathVariable Long id) {
        log.debug("REST request to get QuestionStatus : {}", id);
        Optional<QuestionStatus> questionStatus = questionStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionStatus);
    }

    /**
     * {@code DELETE  /question-statuses/:id} : delete the "id" questionStatus.
     *
     * @param id the id of the questionStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-statuses/{id}")
    public ResponseEntity<Void> deleteQuestionStatus(@PathVariable Long id) {
        log.debug("REST request to delete QuestionStatus : {}", id);
        questionStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
