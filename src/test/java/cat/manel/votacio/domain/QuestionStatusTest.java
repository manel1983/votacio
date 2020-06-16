package cat.manel.votacio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cat.manel.votacio.web.rest.TestUtil;

public class QuestionStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionStatus.class);
        QuestionStatus questionStatus1 = new QuestionStatus();
        questionStatus1.setId(1L);
        QuestionStatus questionStatus2 = new QuestionStatus();
        questionStatus2.setId(questionStatus1.getId());
        assertThat(questionStatus1).isEqualTo(questionStatus2);
        questionStatus2.setId(2L);
        assertThat(questionStatus1).isNotEqualTo(questionStatus2);
        questionStatus1.setId(null);
        assertThat(questionStatus1).isNotEqualTo(questionStatus2);
    }
}
