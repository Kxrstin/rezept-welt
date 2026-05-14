package rezepte.website.rezept_website.controller.management;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.service.management.ManageRecipeService;

import java.io.IOException;

@Controller
public class ManageRecipeController {
    private final ManageRecipeService service;

    @Autowired
    public ManageRecipeController(ManageRecipeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        return "main_page";
    }

    @GetMapping("/get/zubereitung/{id}")
    public String get_zubereitung(Model model, @PathVariable int id) {
        if (service.getZubereitung(id) == null) return "redirect:/";
        model.addAttribute("rezeptForm", service.getZubereitung(id));
        return "speisen/zubereitung";
    }


    @GetMapping("/get/zubereitung/{id}/remove")
    public String remove_page(Model model, @PathVariable int id) {
        if (service.getZubereitung(id) == null) return "redirect:/";
        model.addAttribute("rezeptForm", service.getZubereitung(id));
        return "change/remove";
    }

    @PostMapping("/get/zubereitung/{id}/remove")
    public String remove(RedirectAttributes redirectAttributes, @PathVariable int id) {
        if(service.getZubereitung(id) == null) return "redirect:/";
        service.removeRezept(id);
        redirectAttributes.addFlashAttribute("success_remove", true);
        return "redirect:/";
    }


    @GetMapping("/get/zubereitung/{id}/edit")
    public String edit_page(Model model, @PathVariable int id) {
        if(service.getZubereitung(id) == null) return "redirect:/";
        model.addAttribute("rezeptForm", service.getZubereitung(id));
        model.addAttribute("edit", true);
        model.addAttribute("kategorien", Kategorie.values());
        return "change/add_rezept";
    }

    @PostMapping("/get/zubereitung/{id}/edit")
    public String edit(RedirectAttributes redirectAttributes,
                       @PathVariable int id,
                       @Valid @ModelAttribute("rezeptForm") RezeptForm rezept,
                       BindingResult bindingResult,
                       Model model) {

        boolean keepOldImg = false;
        if(service.getZubereitung(id) == null) return "redirect:/";

        if(bindingResult.hasErrors()) {
            if(bindingResult.getErrorCount() == 1) {
                if (rezept.getBildMultiPart() == null || rezept.getBildMultiPart().isEmpty()) {
                    // Keep old image if no new image is set
                    keepOldImg = true;
                } else {
                    model.addAttribute("rezeptForm", rezept);
                    model.addAttribute("kategorien", Kategorie.values());
                    return "change/add_rezept";
                }
            }
        }

        try {
            if(keepOldImg) {
                service.editWithoutImg(id, rezept);
            } else {
                service.edit(id, rezept);
            }
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error_message",
                    "Das hat leider nicht geklappt.");
        }
        redirectAttributes.addFlashAttribute("success_edit", true);

        return "redirect:/";
    }
}
