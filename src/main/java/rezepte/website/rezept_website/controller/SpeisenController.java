package rezepte.website.rezept_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rezepte.website.rezept_website.service.RezeptService;

@Controller
public class SpeisenController {

    final private RezeptService service;

    public SpeisenController(RezeptService s) {
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

    @GetMapping("/get/zubereitung/{id}")
    public String getZubereitung(Model model, @PathVariable int id) {
        if(service.getZubereitung(id) == null) return "redirect:/";
        model.addAttribute("rezeptForm", service.getZubereitung(id));
        return "/speisen/zubereitung";
    }
}
