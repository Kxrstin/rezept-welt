package rezepte.website.rezept_website.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rezepte.website.rezept_website.service.management.SpeisenPagesService;

@Controller
public class SpeisenPagesController {

    final private SpeisenPagesService service;

    @Autowired
    public SpeisenPagesController(SpeisenPagesService s) {
        service = s;
    }

    @GetMapping("/vorspeise")
    public String vorspeise(Model model) {
        model.addAttribute("vorspeisen", service.getVorspeisen());
        return "speisen/vorspeise_page";
    }

    @GetMapping("/hauptspeise")
    public String hauptspeise(Model model) {
        model.addAttribute("hauptspeisen", service.getHauptspeisen());
        return "speisen/hauptspeise_page";
    }

    @GetMapping("/nachspeise")
    public String nachspeise(Model model) {
        model.addAttribute("nachspeisen", service.getNachspeisen());
        return "speisen/nachspeise_page";
    }
}
