package cat.manel.votacio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cat.manel.votacio.domain.Answer;

/**
 * Spring Data  repository for the Answer entity.
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

	@Query(nativeQuery = false, value = "SELECT ans FROM Answer ans WHERE ans.question.id = :questionId AND ans.status.id = 2")
	List<Answer> findByQuestion(@Param("questionId") Long questionId);

}
