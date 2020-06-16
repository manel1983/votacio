package cat.manel.votacio.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Answer.
 */
@Entity
@Table(name = "answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Answer implements Serializable {

	private static final long serialVersionUID = 1L;

	public Answer() {

	}

	public Answer(Long id) {
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
	@JsonIgnoreProperties(value = "answers", allowSetters = true)
	private AnswerStatus status;

	@ManyToOne
	@JoinColumn(columnDefinition = "question_id")
	@JsonIgnoreProperties(value = "answers", allowSetters = true)
	private Question question;

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

	public Answer text(String text) {
		this.text = text;
		return this;
	}

	public void setText(String text) {
		this.text = text;
	}

	public AnswerStatus getStatus() {
		return status;
	}

	public Answer status(AnswerStatus answerStatus) {
		this.status = answerStatus;
		return this;
	}

	public void setStatus(AnswerStatus answerStatus) {
		this.status = answerStatus;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	public Answer question(Question question) {
		this.question = question;
		return this;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Answer)) {
			return false;
		}
		return id != null && id.equals(((Answer) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Answer{" + "id=" + getId() + ", text='" + getText() + "'" + ", question=" + getQuestion() + ", status="
				+ getStatus() + "}";
	}
}
