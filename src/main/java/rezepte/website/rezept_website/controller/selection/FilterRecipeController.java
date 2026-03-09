package rezepte.website.rezept_website.controller.selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rezepte.website.rezept_website.service.RezeptService;

@Controller
public class FilterRecipeController {
    final private RezeptService service;

    @Autowired
    public FilterRecipeController(RezeptService service) {
        this.service = service;
    }

    @GetMapping("/vorspeise/filter")
    public String filtered_vorspeisen(@RequestParam String filter, Model model) {
        model.addAttribute("vorspeisen", service.getFilteredVorspeisen(filter));
        return "speisen/vorspeise_page";
    }

    @GetMapping("/hauptspeise/filter")
    public String filtered_hauptspeisen(@RequestParam String filter, Model model) {
        model.addAttribute("hauptspeisen", service.getFilteredHauptspeisen(filter));
        return "speisen/hauptspeise_page";
    }

    @GetMapping("/nachspeise/filter")
    public String filtered_nachspeisen(@RequestParam String filter, Model model) {
        model.addAttribute("nachspeisen", service.getFilteredNachspeisen(filter));
        return "speisen/nachspeise_page";
    }
}
