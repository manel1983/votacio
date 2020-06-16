package cat.manel.votacio.service.impl;

import cat.manel.votacio.service.SectionStatusService;
import cat.manel.votacio.domain.SectionStatus;
import cat.manel.votacio.repository.SectionStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SectionStatus}.
 */
@Service
@Transactional
public class SectionStatusServiceImpl implements SectionStatusService {

    private final Logger log = LoggerFactory.getLogger(SectionStatusServiceImpl.class);

    private final SectionStatusRepository sectionStatusRepository;

    public SectionStatusServiceImpl(SectionStatusRepository sectionStatusRepository) {
        this.sectionStatusRepository = sectionStatusRepository;
    }

    /**
     * Save a sectionStatus.
     *
     * @param sectionStatus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SectionStatus save(SectionStatus sectionStatus) {
        log.debug("Request to save SectionStatus : {}", sectionStatus);
        return sectionStatusRepository.save(sectionStatus);
    }

    /**
     * Get all the sectionStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SectionStatus> findAll(Pageable pageable) {
        log.debug("Request to get all SectionStatuses");
        return sectionStatusRepository.findAll(pageable);
    }


    /**
     * Get one sectionStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SectionStatus> findOne(Long id) {
        log.debug("Request to get SectionStatus : {}", id);
        return sectionStatusRepository.findById(id);
    }

    /**
     * Delete the sectionStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SectionStatus : {}", id);
        sectionStatusRepository.deleteById(id);
    }
}
