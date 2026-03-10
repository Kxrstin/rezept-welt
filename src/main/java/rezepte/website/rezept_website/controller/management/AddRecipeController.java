package rezepte.website.rezept_website.controller.management;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.service.management.AddRecipeService;

import java.io.IOException;

@Controller
public class AddRecipeController {
    final private AddRecipeService service;

    @Autowired
    public AddRecipeController(AddRecipeService s) {
        service = s;
    }

    @GetMapping("/add/rezept")
    public String addRezeptAnzeigen(Model model) {
        model.addAttribute("rezeptForm", new RezeptForm());
        model.addAttribute("kategorien", Kategorie.values());
        model.addAttribute("edit", false);
        return "change/add_rezept";
    }

    @PostMapping("/add/rezept")
    public String addRezept(@Valid @ModelAttribute("rezeptForm") RezeptForm rezept,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors() || rezept.getBildMultiPart().isEmpty()) {
            model.addAttribute("rezeptForm", rezept);
            model.addAttribute("kategorien", Kategorie.values());
            return "change/add_rezept";
        }

        try {
            service.addRezept(rezept);
        } catch(IOException e) {
            redirectAttributes.addFlashAttribute("error_message", true);
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }
}
