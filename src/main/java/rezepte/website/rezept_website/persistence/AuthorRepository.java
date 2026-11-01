package rezepte.website.rezept_website.persistence;

import org.springframework.data.repository.CrudRepository;
import rezepte.website.rezept_website.controller.formulare.Author;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
    Optional<Author> findByUsername(String username);
}
