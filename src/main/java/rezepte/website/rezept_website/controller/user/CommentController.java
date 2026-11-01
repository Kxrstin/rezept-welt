package rezepte.website.rezept_website.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rezepte.website.rezept_website.controller.formulare.Author;
import rezepte.website.rezept_website.controller.formulare.Comment;
import rezepte.website.rezept_website.persistence.AuthorRepository;
import rezepte.website.rezept_website.persistence.CommentRepository;

import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDate.now;

@Controller
public class CommentController {
    private final AuthorRepository authorRepo;
    private final CommentRepository commentRepo;

    @Autowired
    public CommentController(AuthorRepository authorR, CommentRepository commentR, CommentRepository commentRepository) {
        authorRepo = authorR;
        commentRepo = commentR;
    }

    @PostMapping("api/comments")
    public String addComment(@RequestBody String username, @RequestBody String content, RedirectAttributes r) {
        Optional<Author> author = authorRepo.findByUsername(username);
        if(author.isEmpty()) {
            r.addFlashAttribute("error_message", "Dein Username ist bei uns nicht hinterlegt.");
            return "redirect:/";
        }

        Comment comment = new Comment(
                UUID.randomUUID(),
                author.get(),
                content,
                now()
        );

        commentRepo.save(comment);
        r.addFlashAttribute("success_message", "Dein Kommentar wurde erfolgreich veröffentlicht!");

        return "redirect:/";
    }
}
