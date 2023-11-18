package mch.project.hello_world.purposeservice.domain.purpose;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PurposeRepositoryTest {

    PurposeRepository purposeRepository = new PurposeRepository();

    @AfterEach
    void afterEach() {
        purposeRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Purpose purpose = new Purpose("purposeA", "contentA", "20231231");

        // when
        Purpose savedPurpose = purposeRepository.save(purpose);

        // then
        Purpose findPurpose = purposeRepository.findById(purpose.getId());
        assertThat(findPurpose).isEqualTo(savedPurpose);
    }

    @Test
    void findAll() {
        //given
        Purpose purposeA = new Purpose("purposeA", "contentA", "20231230");
        Purpose purposeB = new Purpose("purposeB", "contentB", "20231231");

        purposeRepository.save(purposeA);
        purposeRepository.save(purposeB);

        //when
        List<Purpose> result = purposeRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(purposeA, purposeB);

    }

    @Test
    void updateItem() {
        //given
        Purpose purpose = new Purpose("purposeA", "contentA", "20231231");

        Purpose savedPurpose = purposeRepository.save(purpose);
        Long purposeId = savedPurpose.getId();

        //when
        Purpose updateParam = new Purpose("purposeB", "contentB", "20231231");
        purposeRepository.update(purposeId, updateParam);

        Purpose findPurpose = purposeRepository.findById(purposeId);

        //then
        assertThat(findPurpose.getPurposeName()).isEqualTo(updateParam.getPurposeName());
        assertThat(findPurpose.getPurposeContent()).isEqualTo(updateParam.getPurposeContent());
        assertThat(findPurpose.getPurposeDate()).isEqualTo(updateParam.getPurposeDate());
        assertThat(findPurpose.getAchievement()).isEqualTo(updateParam.getAchievement());

    }
}
