package cat.manel.votacio.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.manel.votacio.domain.Section;
import cat.manel.votacio.repository.SectionRepository;
import cat.manel.votacio.service.QuizService;
import cat.manel.votacio.service.dto.QuizDTO;

/**
 * Service Implementation for managing {@link Quiz}.
 */
@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    private final Logger log = LoggerFactory.getLogger(QuizServiceImpl.class);

    private final SectionRepository sectionRepository;

    public QuizServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    /**
     * Get one vote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuizDTO> findOne(Long id) {
        log.debug("Request to get Quiz: {}", id);
        QuizDTO quizDTO = new QuizDTO();
        List<Section> sections = sectionRepository.findAll();
        if (sections != null && !sections.isEmpty()) {
        	quizDTO.setSections(sections);
            quizDTO.setId(Long.valueOf(1));
            return Optional.of(quizDTO);
        } else {
        	return Optional.empty();
        }
    }

}
