package mch.project.hello_world.purposeservice.web.form;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mch.project.hello_world.purposeservice.domain.purpose.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/form/purposes")
@RequiredArgsConstructor
public class FormItemController {

    private final PurposeRepository purposeRepository;

    @ModelAttribute("categories")
    public Map<String, String> categories() {
        Map<String, String> categories = new LinkedHashMap<>();

        categories.put("EXERCISE", "운동");
        categories.put("STUDY", "공부");
        categories.put("RELATIONSHIP", "인간 관계");

        return categories;
    }

    @ModelAttribute("purposeTypes")
    public PurposeType[] purposeTypes() {
        return PurposeType.values();
    }

    @ModelAttribute("importances")
    public List<Importance> importances() {
        List<Importance> importances = new ArrayList<>();
        importances.add(new Importance("HIGH", "높음"));
        importances.add(new Importance("NORMAL", "보통"));
        importances.add(new Importance("LOW", "낮음"));
        return importances;
    }

    @GetMapping
    public String purposes(Model model) {
        List<Purpose> purposes = purposeRepository.findAll();
        model.addAttribute("purposes", purposes);
        return "form/purposes";
    }

    @GetMapping("/{purposeId}")
    public String item(@PathVariable long purposeId, Model model) {
        Purpose purpose = purposeRepository.findById(purposeId);
        model.addAttribute("purpose", purpose);
        return "form/purpose";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("purpose", new Purpose());
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Purpose purpose, RedirectAttributes redirectAttributes) {

        log.info("purpose.achievement={}", purpose.getAchievement());
        log.info("purpose.category={}", purpose.getCategories());

        log.info("purpose.open={}", purpose.getOpen());

        log.info("purpose.categories={}", purpose.getCategories());

        log.info("purpose.purposeType={}", purpose.getPurposeType());

        Purpose savedPurpose = purposeRepository.save(purpose);
        redirectAttributes.addAttribute("purposeId", savedPurpose.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/purposes/{purposeId}";
    }

    @GetMapping("/{purposeId}/edit")
    public String editForm(@PathVariable Long purposeId, Model model) {
        Purpose purpose = purposeRepository.findById(purposeId);
        model.addAttribute("purpose", purpose);
        return "form/editForm";
    }

    @PostMapping("/{purposeId}/edit")
    public String edit(@PathVariable Long purposeId, @ModelAttribute Purpose purpose) {
        purposeRepository.update(purposeId, purpose);
        return "redirect:/form/purposes/{purposeId}";
    }

}

