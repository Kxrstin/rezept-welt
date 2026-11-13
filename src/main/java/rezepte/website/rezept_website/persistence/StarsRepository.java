package rezepte.website.rezept_website.persistence;

import org.springframework.data.repository.CrudRepository;
import rezepte.website.rezept_website.controller.formulare.Stars;

import java.util.List;

public interface StarsRepository extends CrudRepository<Stars, String> {
    List<Stars> findAllByRezeptId(Integer rezeptId);
}
