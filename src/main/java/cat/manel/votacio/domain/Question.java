package cat.manel.votacio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	public Question() {

	}

	public Question(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "text", nullable = false)
	private String text;

	@ManyToOne
	@JoinColumn(columnDefinition = "status_id")
	@JsonIgnoreProperties(value = "questions", allowSetters = true)
	private QuestionStatus status;

	@ManyToOne
	@JoinColumn(columnDefinition = "section_id")
	@JsonIgnoreProperties(value = "questions", allowSetters = true)
	private Section section;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public Question text(String text) {
		this.text = text;
		return this;
	}

	public void setText(String text) {
		this.text = text;
	}

	public QuestionStatus getStatus() {
		return status;
	}

	public Question status(QuestionStatus questionStatus) {
		this.status = questionStatus;
		return this;
	}

	public void setStatus(QuestionStatus questionStatus) {
		this.status = questionStatus;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Question section(Section section) {
		this.section = section;
		return this;
	}

	public Section getSection() {
		return section;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Question)) {
			return false;
		}
		return id != null && id.equals(((Question) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Question{" + "id=" + getId() + ", text='" + getText() + "'" + ", section=" + getSection() + ", status="
				+ getStatus() + "}";
	}
}
