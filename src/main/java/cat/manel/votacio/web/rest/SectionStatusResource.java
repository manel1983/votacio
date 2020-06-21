package cat.manel.votacio.web.rest;

import cat.manel.votacio.domain.SectionStatus;
import cat.manel.votacio.service.SectionStatusService;
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
 * REST controller for managing {@link cat.manel.votacio.domain.SectionStatus}.
 */
@RestController
@RequestMapping("/api")
public class SectionStatusResource {

    private final Logger log = LoggerFactory.getLogger(SectionStatusResource.class);

    private static final String ENTITY_NAME = "sectionStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SectionStatusService sectionStatusService;

    public SectionStatusResource(SectionStatusService sectionStatusService) {
        this.sectionStatusService = sectionStatusService;
    }

    /**
     * {@code POST  /section-statuses} : Create a new sectionStatus.
     *
     * @param sectionStatus the sectionStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sectionStatus, or with status {@code 400 (Bad Request)} if the sectionStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/section-statuses")
    public ResponseEntity<SectionStatus> createSectionStatus(@Valid @RequestBody SectionStatus sectionStatus) throws URISyntaxException {
        log.debug("REST request to save SectionStatus : {}", sectionStatus);
        if (sectionStatus.getId() != null) {
            throw new BadRequestAlertException("A new sectionStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SectionStatus result = sectionStatusService.save(sectionStatus);
        return ResponseEntity.created(new URI("/api/section-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /section-statuses} : Updates an existing sectionStatus.
     *
     * @param sectionStatus the sectionStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sectionStatus,
     * or with status {@code 400 (Bad Request)} if the sectionStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sectionStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/section-statuses")
    public ResponseEntity<SectionStatus> updateSectionStatus(@Valid @RequestBody SectionStatus sectionStatus) throws URISyntaxException {
        log.debug("REST request to update SectionStatus : {}", sectionStatus);
        if (sectionStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SectionStatus result = sectionStatusService.save(sectionStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sectionStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /section-statuses} : get all the sectionStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sectionStatuses in body.
     */
    @GetMapping("/section-statuses")
    public ResponseEntity<List<SectionStatus>> getAllSectionStatuses(Pageable pageable) {
        log.debug("REST request to get a page of SectionStatuses");
        Page<SectionStatus> page = sectionStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /section-statuses/:id} : get the "id" sectionStatus.
     *
     * @param id the id of the sectionStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sectionStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/section-statuses/{id}")
    public ResponseEntity<SectionStatus> getSectionStatus(@PathVariable Long id) {
        log.debug("REST request to get SectionStatus : {}", id);
        Optional<SectionStatus> sectionStatus = sectionStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sectionStatus);
    }

    /**
     * {@code DELETE  /section-statuses/:id} : delete the "id" sectionStatus.
     *
     * @param id the id of the sectionStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/section-statuses/{id}")
    public ResponseEntity<Void> deleteSectionStatus(@PathVariable Long id) {
        log.debug("REST request to delete SectionStatus : {}", id);
        sectionStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
