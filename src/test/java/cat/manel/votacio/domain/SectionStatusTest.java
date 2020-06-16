package cat.manel.votacio.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cat.manel.votacio.web.rest.TestUtil;

public class SectionStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectionStatus.class);
        SectionStatus sectionStatus1 = new SectionStatus();
        sectionStatus1.setId(1L);
        SectionStatus sectionStatus2 = new SectionStatus();
        sectionStatus2.setId(sectionStatus1.getId());
        assertThat(sectionStatus1).isEqualTo(sectionStatus2);
        sectionStatus2.setId(2L);
        assertThat(sectionStatus1).isNotEqualTo(sectionStatus2);
        sectionStatus1.setId(null);
        assertThat(sectionStatus1).isNotEqualTo(sectionStatus2);
    }
}
