package rezepte.website.rezept_website.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rezepte.website.rezept_website.controller.formulare.Author;
import rezepte.website.rezept_website.persistence.AuthorRepository;

import java.util.Optional;

@Controller
public class AuthController {
    private static AuthorRepository repo;

    public AuthController(AuthorRepository r) {
        repo = r;
    }

    @PostMapping("/api/author/add")
    public ResponseEntity<String> addUser(@Validated @RequestBody Author author) {
        try {
            Optional<Author> authorOptional = repo.findByUsername(author.getUsername());
            if(authorOptional.isPresent()) {
                return ResponseEntity.status(409).body("User existiert bereits");
            }

            repo.save(author);
            return ResponseEntity.ok("Erfolgreich gespeichert");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Fehler beim Speichern");
        }
    }

    @PostMapping("/api/author/update")
    public ResponseEntity<String> updateUser(@Validated @RequestBody Author author) {
        try {
            repo.save(author);
            return ResponseEntity.ok("Erfolgreich gespeichert");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Fehler beim Speichern");
        }
    }
}
