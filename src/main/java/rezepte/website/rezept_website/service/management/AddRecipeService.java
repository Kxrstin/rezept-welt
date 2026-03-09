package rezepte.website.rezept_website.service.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;

import java.io.IOException;

@Service
public class AddRecipeService {
    final private RezeptRepository repo;

    @Autowired
    public AddRecipeService(RezeptRepository repo) {
        this.repo = repo;
    }

    public RezeptForm addRezept(RezeptForm rezept) throws IOException {
        if (rezept.getBildMultiPart() != null && !rezept.getBildMultiPart().isEmpty()) {
            rezept.setBild(rezept.getBildMultiPart().getBytes());
        }

        rezept.setId(null);
        return repo.save(rezept);
    }
}
