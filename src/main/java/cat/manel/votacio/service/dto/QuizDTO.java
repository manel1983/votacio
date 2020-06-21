package cat.manel.votacio.service.dto;

import java.util.List;

import cat.manel.votacio.domain.Section;

/**
 * A DTO representing a quiz, with his authorities.
 */
public class QuizDTO {

	private Long id;

	private List<Section> sections;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

}
