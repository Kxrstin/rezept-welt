package rezepte.website.rezept_website.controller.selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import rezepte.website.rezept_website.service.selection.RandomRecipeService;

@Controller
public class RandomRecipeController {
    final private RandomRecipeService service;

    @Autowired
    public RandomRecipeController(RandomRecipeService service) {
        this.service = service;
    }

    @GetMapping("/rezept/random/Vorspeise")
    public String getRandomVorspeise() {
        long randomId = service.getRandomVorspeiseId();
        return "redirect:/get/zubereitung/" + randomId;
    }

    @GetMapping("/rezept/random/Hauptspeise")
    public String getRandomHauptspeise() {
        long randomId = service.getRandomHauptspeiseId();
        return "redirect:/get/zubereitung/" + randomId;
    }

    @GetMapping("/rezept/random/Nachspeise")
    public String getRandomNachspeise() {
        long randomId = service.getRandomNachspeiseId();
        return "redirect:/get/zubereitung/" + randomId;
    }
}
