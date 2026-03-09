package rezepte.website.rezept_website.service.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class ManageRecipeService {
    final private RezeptRepository repo;

    @Autowired
    public ManageRecipeService(RezeptRepository repo) {
        this.repo = repo;
    }

    public RezeptForm getZubereitung(int id) {
        Optional<RezeptForm> opt = repo.findById(id);
        return opt.orElse(null);
    }

    public void removeRezept(int id) {
        Optional<RezeptForm> r = repo.findById(id);
        r.ifPresent(repo::delete);
    }

    public void edit(int id, RezeptForm rezeptForm) throws IOException {
        RezeptForm old = getZubereitung(id);
        if(old == null) return;
        old.setName(rezeptForm.getName());
        old.setKategorie(rezeptForm.getKategorie());
        old.setName(rezeptForm.getName());
        old.setZubereitung(rezeptForm.getZubereitung());
        old.setZutaten(rezeptForm.getZutaten());
        old.setBildMultiPart(rezeptForm.getBildMultiPart());
        old.setBild(rezeptForm.getBildMultiPart().getBytes());
        repo.save(old);
    }
}
