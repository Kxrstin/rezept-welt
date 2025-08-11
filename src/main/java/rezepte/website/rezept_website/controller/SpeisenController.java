package rezepte.website.rezept_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import rezepte.website.rezept_website.service.RezeptService;

@Controller
public class SpeisenController {

    final private RezeptService service;

    public SpeisenController(RezeptService s) {
        service = s;
    }

    @GetMapping("/vorspeise")
    public String vorspeise() {
        return "speisen/vorspeise_page";
    }

    @GetMapping("/hauptspeise")
    public String hauptspeise() {
        return "speisen/hauptspeise_page";
    }

    @GetMapping("/nachspeise")
    public String nachspeise() {
        return "speisen/nachspeise_page";
    }
}
