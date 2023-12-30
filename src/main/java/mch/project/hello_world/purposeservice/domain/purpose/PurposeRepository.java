package mch.project.hello_world.purposeservice.domain.purpose;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PurposeRepository {

    private static final Map<Long, Purpose> store = new HashMap<>(); // multi thread 환경에서 사용하면 안된다.
                                                                     // 실서비스에서는 concurrentHash 를 사용해 동시 접근시의 문제를 해결한다.
    private static long sequence = 0L;

    public Purpose save(Purpose purpose) {
        purpose.setId(++sequence);
        store.put(purpose.getId(), purpose);
        return purpose;
    }

    public Purpose findById(Long id) {
        return store.get(id);
    }

    public List<Purpose> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long purposeId, Purpose updateParam) {
        Purpose findPurpose = findById(purposeId);

        findPurpose.setPurposeName(updateParam.getPurposeName());
        findPurpose.setPurposeContent(updateParam.getPurposeContent());
        findPurpose.setPurposeDue(updateParam.getPurposeDue());
        findPurpose.setAchievement(updateParam.getAchievement());
    }

    public void clearStore() {
        store.clear();
    }
}
