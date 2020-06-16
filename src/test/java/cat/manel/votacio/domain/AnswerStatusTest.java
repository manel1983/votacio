package cat.manel.votacio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cat.manel.votacio.web.rest.TestUtil;

public class AnswerStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerStatus.class);
        AnswerStatus answerStatus1 = new AnswerStatus();
        answerStatus1.setId(1L);
        AnswerStatus answerStatus2 = new AnswerStatus();
        answerStatus2.setId(answerStatus1.getId());
        assertThat(answerStatus1).isEqualTo(answerStatus2);
        answerStatus2.setId(2L);
        assertThat(answerStatus1).isNotEqualTo(answerStatus2);
        answerStatus1.setId(null);
        assertThat(answerStatus1).isNotEqualTo(answerStatus2);
    }
}
