package rezepte.website.rezept_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.model.IModel;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.service.RezeptService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Controller
public class MainPageController {

    private List<RezeptForm> rezepte = new LinkedList<>();
    private RezeptService service = new RezeptService();

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
                            @RequestParam MultipartFile bild) throws IOException {

        service.addRezept(rezepte, kategorie, name, zutaten, zubereitung, bild);
        return "redirect:/";
    }
}
