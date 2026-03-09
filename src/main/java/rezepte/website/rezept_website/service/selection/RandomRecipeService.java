package rezepte.website.rezept_website.service.selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;
import rezepte.website.rezept_website.persistence.RezeptRepository;
import rezepte.website.rezept_website.service.helper.Speisen;

import java.util.List;

@Service
public class RandomRecipeService {
    private final Speisen speisen;

    @Autowired
    public RandomRecipeService(RezeptRepository repo){
        this.speisen = new Speisen(repo);
    }

    public long getRandomVorspeiseId() {
        return getRandomSpeiseId(Kategorie.Vorspeise);
    }

    public long getRandomHauptspeiseId() {
        return getRandomSpeiseId(Kategorie.Hauptspeise);
    }

    public long getRandomNachspeiseId() {
        return getRandomSpeiseId(Kategorie.Nachspeise);
    }

    private long getRandomSpeiseId(Kategorie kategorie) {
        List<RezeptForm> rezepte = speisen.getSpeise(kategorie);
        int random = (int) (Math.random() * rezepte.size());
        return rezepte.get(random).getId();
    }
}
