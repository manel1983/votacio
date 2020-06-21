package cat.manel.votacio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cat.manel.votacio.domain.Question;

/**
 * Spring Data  repository for the Question entity.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	@Query(nativeQuery = false, value = "SELECT que FROM Question que WHERE que.section.id = :sectionId AND que.status.id = 2")
	List<Question> findBySection(@Param("sectionId") Long sectionId);

}
