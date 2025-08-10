package rezepte.website.rezept_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import java.util.LinkedList;
import java.util.List;

@Controller
public class MainPageController {

    final private List<RezeptForm> rezepte = new LinkedList<>();
    final private RezeptService service;

    public MainPageController(RezeptService s) {
        service = s;
    }

    @GetMapping("/")
    public String mainPage() {
        return "main_page";
    }

    @GetMapping("/add/rezept")
    public String addRezeptAnzeigen(Model model) {
        model.addAttribute("kategorien", Kategorie.values());
        return "add_rezept";
    }

    @PostMapping("/add/rezept")
    public String addRezept(@RequestParam Kategorie kategorie,
                            @RequestParam String name,
                            @RequestParam String zutaten,
                            @RequestParam String zubereitung,
                            @RequestParam MultipartFile bild,
                            RedirectAttributes redirectAttributes) throws IOException {

        service.addRezept(rezepte, kategorie, name, zutaten, zubereitung, bild);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }
}
