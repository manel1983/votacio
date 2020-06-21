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
 * A Vote.
 */
@Entity
@Table(name = "vote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vote implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "status_id", nullable = false)
	private Long status;

	@ManyToOne
	@JoinColumn(columnDefinition = "question_id")
	@JsonIgnoreProperties(value = "votes", allowSetters = true)
	private Question question;

	@ManyToOne
	@JoinColumn(columnDefinition = "answer_id")
	@JsonIgnoreProperties(value = "votes", allowSetters = true)
	private Answer answer;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public Vote userId(Long userId) {
		this.userId = userId;
		return this;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	public Vote status(Long status) {
		this.status = status;
		return this;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Vote question(Question question) {
		this.question = question;
		return this;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Vote answer(Answer answer) {
		this.answer = answer;
		return this;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Vote)) {
			return false;
		}
		return id != null && id.equals(((Vote) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Vote{" + "id=" + getId() + ", userId=" + getUserId() + ", question=" + getQuestion() + ", answer="
				+ getAnswer() + ", status=" + getStatus() + "}";
	}
}
