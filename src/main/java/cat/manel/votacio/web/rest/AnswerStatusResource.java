package cat.manel.votacio.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cat.manel.votacio.domain.AnswerStatus;
import cat.manel.votacio.service.AnswerStatusService;
import cat.manel.votacio.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cat.manel.votacio.domain.AnswerStatus}.
 */
@RestController
@RequestMapping("/api")
public class AnswerStatusResource {

    private final Logger log = LoggerFactory.getLogger(AnswerStatusResource.class);

    private static final String ENTITY_NAME = "answerStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerStatusService answerStatusService;

    public AnswerStatusResource(AnswerStatusService answerStatusService) {
        this.answerStatusService = answerStatusService;
    }

    /**
     * {@code POST  /answer-statuses} : Create a new answerStatus.
     *
     * @param answerStatus the answerStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answerStatus, or with status {@code 400 (Bad Request)} if the answerStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/answer-statuses")
    public ResponseEntity<AnswerStatus> createAnswerStatus(@Valid @RequestBody AnswerStatus answerStatus) throws URISyntaxException {
        log.debug("REST request to save AnswerStatus : {}", answerStatus);
        if (answerStatus.getId() != null) {
            throw new BadRequestAlertException("A new answerStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnswerStatus result = answerStatusService.save(answerStatus);
        return ResponseEntity.created(new URI("/api/answer-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /answer-statuses} : Updates an existing answerStatus.
     *
     * @param answerStatus the answerStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerStatus,
     * or with status {@code 400 (Bad Request)} if the answerStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/answer-statuses")
    public ResponseEntity<AnswerStatus> updateAnswerStatus(@Valid @RequestBody AnswerStatus answerStatus) throws URISyntaxException {
        log.debug("REST request to update AnswerStatus : {}", answerStatus);
        if (answerStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnswerStatus result = answerStatusService.save(answerStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, answerStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /answer-statuses} : get all the answerStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answerStatuses in body.
     */
    @GetMapping("/answer-statuses")
    public ResponseEntity<List<AnswerStatus>> getAllAnswerStatuses(Pageable pageable) {
        log.debug("REST request to get a page of AnswerStatuses");
        Page<AnswerStatus> page = answerStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /answer-statuses/:id} : get the "id" answerStatus.
     *
     * @param id the id of the answerStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/answer-statuses/{id}")
    public ResponseEntity<AnswerStatus> getAnswerStatus(@PathVariable Long id) {
        log.debug("REST request to get AnswerStatus : {}", id);
        Optional<AnswerStatus> answerStatus = answerStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(answerStatus);
    }

    /**
     * {@code DELETE  /answer-statuses/:id} : delete the "id" answerStatus.
     *
     * @param id the id of the answerStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/answer-statuses/{id}")
    public ResponseEntity<Void> deleteAnswerStatus(@PathVariable Long id) {
        log.debug("REST request to delete AnswerStatus : {}", id);
        answerStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
