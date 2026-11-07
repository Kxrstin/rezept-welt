package rezepte.website.rezept_website.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rezepte.website.rezept_website.controller.formulare.Author;
import rezepte.website.rezept_website.service.user.AuthorService;
import rezepte.website.rezept_website.service.user.CommentService;

@Controller
public class CommentController {
    private final AuthorService authorService;
    private final CommentService commentService;

    @Autowired
    public CommentController(AuthorService a, CommentService c) {
        authorService = a;
        commentService = c;
    }

    @PostMapping("/api/comments")
    public String addComment(
            @RequestParam String username,
            @RequestParam String content,
            RedirectAttributes r) {

        try {
            Author author = authorService.findByUsername(username);

            if (author == null) {
                r.addFlashAttribute(
                        "error_message",
                        "Dein Username ist bei uns nicht hinterlegt."
                );
                return "redirect:/";
            }

            commentService.add(author, content);

            r.addFlashAttribute(
                    "success_message",
                    "Dein Kommentar wurde erfolgreich veröffentlicht!"
            );

        } catch (Exception e) {
            e.printStackTrace();

            r.addFlashAttribute(
                    "error_message",
                    "Fehler beim Speichern des Kommentars: " + e.getMessage()
            );
        }
        return "redirect:/";
    }
}
