package cat.manel.votacio.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.manel.votacio.domain.User;
import cat.manel.votacio.domain.Vote;
import cat.manel.votacio.repository.UserRepository;
import cat.manel.votacio.repository.VoteRepository;
import cat.manel.votacio.security.SecurityUtils;
import cat.manel.votacio.service.VoteService;

/**
 * Service Implementation for managing {@link Vote}.
 */
@Service
@Transactional
public class VoteServiceImpl implements VoteService {

    private final Logger log = LoggerFactory.getLogger(VoteServiceImpl.class);

    private final VoteRepository voteRepository;

    private final UserRepository userRepository;

    public VoteServiceImpl(VoteRepository voteRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a vote.
     *
     * @param vote the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Vote save(Vote vote) {
        log.debug("Request to save Vote : {}", vote);
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
        	Optional<User> user = userRepository.findOneByLogin(login.get());
        	if (user.isPresent()) {
        		vote.setUserId(user.get().getId());
        		
        		Optional<Vote> currentVoteOpt = this.voteRepository.findUserVote(vote.getQuestion().getId(), user.get().getId());
        		if (currentVoteOpt.isPresent()) {
        			Vote currentVote = currentVoteOpt.get();
        			currentVote.setAnswer(vote.getAnswer());
        			return voteRepository.save(currentVote);
        		} else {
        			return voteRepository.save(vote);
        		}
        		
        	}
        }
        return null;
    }

    /**
     * Get all the votes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vote> findAll(Pageable pageable) {
        log.debug("Request to get all Votes");
        return voteRepository.findAll(pageable);
    }


    /**
     * Get one vote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vote> findOne(Long id) {
        log.debug("Request to get Vote : {}", id);
        return voteRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vote> findUserVote(Long questionId, Long userId) {
    	log.debug("Request to get a User Vote : question {}, user {}", questionId, userId);
    	Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
        	Optional<User> user = userRepository.findOneByLogin(login.get());
        	if (user.isPresent() && user.get().getId().equals(userId)) {
        		return voteRepository.findUserVote(questionId, userId);
        	} else {
        		return null;
        	}
        } else {
        	return null;
        }
    }

    /**
     * Delete the vote by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vote : {}", id);
        voteRepository.deleteById(id);
    }
}
