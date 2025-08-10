package rezepte.website.rezept_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @GetMapping("/")
    public String mainPage() {
        return "main_page";
    }
}
