package rezepte.website.rezept_website.persistence;

import org.springframework.data.repository.CrudRepository;
import rezepte.website.rezept_website.controller.formulare.Author;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, String> {
    boolean existsByUsername(String username);
    Optional<Author> findByUsername(String username);
    List<Author> findAllByUsernameIn(Collection<String> usernames);
}
