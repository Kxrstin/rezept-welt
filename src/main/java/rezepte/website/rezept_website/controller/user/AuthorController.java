package rezepte.website.rezept_website.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import rezepte.website.rezept_website.controller.formulare.Author;
import rezepte.website.rezept_website.service.user.AuthorService;

@Controller
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService s) {
        service = s;
    }

    @PostMapping("/api/author/add")
    public ResponseEntity<String> addUser(
            @RequestParam("username") String username,
            @RequestParam("description") String description,
            @RequestParam("color") String color,
            @RequestParam(value = "bildMultiPart", required = false) MultipartFile bildMultiPart) {
        try {
            boolean exists = service.exists(username);
            if(exists) return ResponseEntity.status(409).body("User existiert bereits");

            service.insert(new Author(username, description, bildMultiPart, color));
            return ResponseEntity.ok("Erfolgreich gespeichert");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Fehler beim Speichern");
        }
    }

    @PostMapping("/api/author/update")
    public ResponseEntity<String> updateUser(
            @RequestParam("username") String username,
            @RequestParam("description") String description,
            @RequestParam(value = "bildMultiPart", required = false) MultipartFile bildMultiPart) {
        try {
            Author author = service.findByUsername(username);
            if(author != null) {
                author.setDescription(description);
                if(bildMultiPart != null && !bildMultiPart.isEmpty()) {
                    author.setBildMultiPart(bildMultiPart);
                    author.setProfilePicture(bildMultiPart.getBytes());
                }
                service.save(author);
            } else {
                return ResponseEntity.status(404).body("User ist nicht registriert");
            }

            return ResponseEntity.ok("Erfolgreich gespeichert");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Fehler beim Speichern");
        }
    }
}
