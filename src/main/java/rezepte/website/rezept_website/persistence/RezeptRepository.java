package rezepte.website.rezept_website.persistence;

import org.springframework.data.repository.CrudRepository;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;

import java.util.List;

public interface RezeptRepository extends CrudRepository<RezeptForm, Integer> {
    List<RezeptForm> findAll();

    RezeptForm findRezeptFormByName(String name);
}