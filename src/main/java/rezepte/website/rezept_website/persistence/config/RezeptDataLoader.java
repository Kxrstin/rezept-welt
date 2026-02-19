package rezepte.website.rezept_website.persistence.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;

import java.io.IOException;

@Component
@Profile("!test")
public class RezeptDataLoader implements CommandLineRunner {

    private final RezeptRepository repository;

    public RezeptDataLoader(RezeptRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        saveInitialRezept("Frische Guacamole mit Nachos", "static/img/recipe_images/guacamole.jpg");
        saveInitialRezept("Frischer Linsensalat mit Petersilie", "static/img/recipe_images/linsensalat.jpg");
        saveInitialRezept("Caesar-Salat", "static/img/recipe_images/caesar-salat.jpg");
    }

    private void saveInitialRezept(String name, String pfad) throws IOException {
        ClassPathResource imgFile = new ClassPathResource(pfad);
        byte[] imageBytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        RezeptForm rezept = repository.findRezeptFormByName(name);
        if(rezept.getBild() != null) {
            rezept.setBild(imageBytes);
            repository.save(rezept);
        }
    }
}
