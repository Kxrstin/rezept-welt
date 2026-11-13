package rezepte.website.rezept_website.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rezepte.website.rezept_website.controller.formulare.Stars;
import rezepte.website.rezept_website.persistence.StarsRepository;
import rezepte.website.rezept_website.service.user.StarsService;

import java.util.List;

@Controller
public class StarController {
    private final StarsRepository repo;
    private final StarsService starsService;

    public StarController(StarsRepository r, StarsService starsService) {
        repo = r;
        this.starsService = starsService;
    }

    @GetMapping("/get/zubereitung/{rezeptId}/comments")
    public String getCommentsForId(
            Model model,
            @PathVariable Integer rezeptId) {

        model.addAttribute("rezeptId", "rezeptId");

        List<Stars> stars = starsService.findAllByRezeptId(rezeptId);
        model.addAttribute("comments", stars);
        model.addAttribute("rezeptId", rezeptId);
        model.addAttribute("avgRating", starsService.avgRanking(rezeptId));

        return "stars/stars";
    }

    @PostMapping("/get/zubereitung/{rezeptId}/comments")
    public String postCommentsForId(
            @PathVariable Integer rezeptId,
            @RequestParam Integer ranking,
            @RequestParam String content,
            @RequestParam String username) {

        starsService.save(rezeptId, ranking, content, username);

        return "redirect:/get/zubereitung/" + rezeptId + "/comments";
    }
}
