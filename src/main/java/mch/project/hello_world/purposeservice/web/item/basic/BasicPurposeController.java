package mch.project.hello_world.purposeservice.web.item.basic;

import lombok.RequiredArgsConstructor;
import mch.project.hello_world.purposeservice.domain.purpose.Purpose;
import mch.project.hello_world.purposeservice.domain.purpose.PurposeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/purposes")
@RequiredArgsConstructor // final이 붙은 멤버 변수만 사용해서 생성자를 자동으로 만들어준다.
                         // 생성자가 딱 1개일 경우 스프링이 해당 생성자에 Autowired로 의존관계를 주입해준다.
public class BasicPurposeController {

    private final PurposeRepository purposeRepository; // final 키워드를 제거할 경우 의존관계 주입이 진행되지 않으므로 주의

    @GetMapping
    public String purposes(Model model) {
        List<Purpose> purposes = purposeRepository.findAll();
        model.addAttribute("purposes", purposes);
        return "basic/purposes";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        purposeRepository.save(new Purpose("purpose A", "content A", "2023-12-31"));
        purposeRepository.save(new Purpose("purpose B", "content B", "2024-01-01"));
    }
}
