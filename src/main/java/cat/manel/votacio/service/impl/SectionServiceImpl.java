package cat.manel.votacio.service.impl;

import cat.manel.votacio.service.SectionService;
import cat.manel.votacio.domain.Section;
import cat.manel.votacio.repository.SectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Section}.
 */
@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final Logger log = LoggerFactory.getLogger(SectionServiceImpl.class);

    private final SectionRepository sectionRepository;

    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    /**
     * Save a section.
     *
     * @param section the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Section save(Section section) {
        log.debug("Request to save Section : {}", section);
        return sectionRepository.save(section);
    }

    /**
     * Get all the sections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Section> findAll(Pageable pageable) {
        log.debug("Request to get all Sections");
        return sectionRepository.findAll(pageable);
    }


    /**
     * Get one section by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Section> findOne(Long id) {
        log.debug("Request to get Section : {}", id);
        return sectionRepository.findById(id);
    }

    /**
     * Delete the section by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Section : {}", id);
        sectionRepository.deleteById(id);
    }
}
