package rezepte.website.rezept_website.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.service.RezeptService;

import java.io.IOException;

@Controller
public class MainPageController {
    final private RezeptService service;

    public MainPageController(RezeptService s) {
        service = s;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        return "main_page";
    }

    @GetMapping("/add/rezept")
    public String addRezeptAnzeigen(Model model) {
        model.addAttribute("rezeptForm", new RezeptForm());
        model.addAttribute("kategorien", Kategorie.values());
        model.addAttribute("edit", false);
        return "add_rezept";
    }

    @PostMapping("/add/rezept")
    public String addRezept(@Valid @ModelAttribute("rezeptForm") RezeptForm rezept,
                            BindingResult bindingResult,
                            Model model) {

        if(bindingResult.hasErrors() || rezept.getBild().isEmpty()) {
            model.addAttribute("rezeptForm", rezept);
            model.addAttribute("kategorien", Kategorie.values());
            return "add_rezept";
        }
        service.addRezept(rezept);

        return "redirect:/?success=true";
    }
}
