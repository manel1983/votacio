package cat.manel.votacio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cat.manel.votacio.domain.Vote;

/**
 * Spring Data  repository for the Vote entity.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	
	@Query(nativeQuery = false, value = "SELECT vot FROM Vote vot WHERE vot.userId = :userId AND vot.question.id = :questionId")
	Optional<Vote> findUserVote(@Param("questionId") Long questionId, @Param("userId") Long userId);

}
