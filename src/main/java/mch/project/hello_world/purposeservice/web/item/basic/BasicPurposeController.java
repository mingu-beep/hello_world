package mch.project.hello_world.purposeservice.web.item.basic;

import lombok.RequiredArgsConstructor;
import mch.project.hello_world.purposeservice.domain.purpose.Purpose;
import mch.project.hello_world.purposeservice.domain.purpose.PurposeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
     * PathVariable 로 넘어온 목표 Id로 상품을 조회하고 모델에 담아둔다.
     * 그 후 뷰 템플릿을 호출한다.
     */
    @GetMapping("/{purposeId}")
    public String item(@PathVariable Long purposeId, Model model) {
        Purpose purpose = purposeRepository.findById(purposeId);
        model.addAttribute("purpose", purpose);
        return "basic/purpose";
    }


    /**
     * 등록 폼 렌더링 (등록 로직 X)
     */
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /**
     * RequestParam을 이용한 목표 등록 처리
     * - 메시지 바디에 쿼리 파라미터 형식으로 전달됨을 가정한다. (purposeName=purpose&....)
     */
    // @PostMapping("/add")
    public String addPurposeV2(@RequestParam String purposeName,
                               @RequestParam String purposeContent,
                               @RequestParam String purposeDue,
                               Model model) {

        // 1. @RequestParam 을 이용해 요청 파라미터 데이터를 변수에 저장시킨다.
        // 2. purpose 객체를 생성하고 purposeRepository에 저장한다.
        // 3. 저장된 purpose를 모델에 담아 뷰에 전달한다.
        Purpose purpose = new Purpose();
        purpose.setPurposeName(purposeName);
        purpose.setPurposeContent(purposeContent);
        purpose.setPurposeDue(purposeDue);

        purposeRepository.save(purpose);

        model.addAttribute("purpose", purpose); // 상세 화면에서 저장 결과를 보여주기 위함

        return "basic/purpose";
    }

    /**
     * @ModelAttribute를 활용한 목표 등록 처리 1
     * - RequestParam 으로 변수를 하나하나 받기에는 수가 늘어날 수록 불리하다.
     * - ModelAttribute 는 Purpose 객체를 생성하고 요청 파라미터의 값을 파라미터 접근법으로 입력해준다.
     * - ModelAttribute 로 지정된 객체는 모델에 자동을 저장한다. 이때 ModelAttribute 에 지정된 이름으로 저장되니 이점 유의
     */
//    @PostMapping("/add")
    public String addPurposeV2(@ModelAttribute("purpose") Purpose purpose, Model model) {
        purposeRepository.save(purpose);
        // model.addAttribute("purpose", purpose); // 자동 추가, 생략 가능

        return "basic/purpose";
    }

    /**
     * @ModelAttribute를 활용한 목표 등록 처리 2
     * - ModelAttribute 의 name 생략시 클래스명의 첫글자만 소문자로 등록 Purpose -> purpose
     */
//    @PostMapping("/add")
    public String addPurposeV3(@ModelAttribute Purpose purpose, Model model) {
        purposeRepository.save(purpose);
        // model.addAttribute("purpose", purpose); // 자동 추가, 생략 가능

        return "basic/purpose";
    }

    /**
     * @ModelAttribute를 활용한 목표 등록 처리 3
     * - ModelAttribute 전체 생략
     * -> String과 같은 단순 참조형은 RequestParam 을 사용하고
     * -> 직접 정의한 객체의 경우 ModelAttribute를 적용한다.
     */
    @PostMapping("/add")
    public String addPurposeV4(Purpose purpose) {
        purposeRepository.save(purpose);
        // model.addAttribute("purpose", purpose); // 자동 추가, 생략 가능

        return "basic/purpose";
    }


    @GetMapping ("/{purposeId}/edit")
    public String editForm(@PathVariable Long purposeId, Model model) {
        Purpose purpose = purposeRepository.findById(purposeId);
        model.addAttribute("purpose", purpose);
        return "basic/editForm";
    }

    @PostMapping("/{purposeId}/edit")
    public String edit(@PathVariable Long purposeId, @ModelAttribute Purpose purpose) {
        purposeRepository.update(purposeId, purpose);

        // 목표 상세화면으로 이동하도록 리다이렉트를 호출
        return "redirect:/basic/purposes/{purposeId}";
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
