package rezepte.website.rezept_website.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.service.RezeptService;

import java.io.IOException;

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
    public String remove_page(Model model, @PathVariable int id) {
        if (service.getZubereitung(id) == null) return "redirect:/";
        model.addAttribute("rezeptForm", service.getZubereitung(id));
        return "speisen/zubereitung";
    }

    @GetMapping("/get/zubereitung/{id}/remove")
    public String zubereitung(Model model, @PathVariable int id) {
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
        return "add_rezept";
    }

    @PostMapping("/get/zubereitung/{id}/edit")
    public String edit(RedirectAttributes redirectAttributes, @PathVariable int id, @Valid @ModelAttribute("rezeptForm") RezeptForm rezept,
                       BindingResult bindingResult,
                       @RequestParam("bild") MultipartFile bild,
                       Model model) throws IOException {

        if(bindingResult.hasErrors()) {
            model.addAttribute("rezeptForm", rezept);
            model.addAttribute("kategorien", Kategorie.values());
            return "add_rezept";
        }
        rezept.setBild(bild);
        if(service.getZubereitung(id) == null) return "redirect:/";
        service.edit(id, rezept);
        redirectAttributes.addFlashAttribute("success_edit", true);
        return "redirect:/";
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
