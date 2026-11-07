package rezepte.website.rezept_website.service.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.Author;
import rezepte.website.rezept_website.persistence.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository repo;
    private final JdbcTemplate jdbcTemplate;

    public AuthorService(AuthorRepository r, JdbcTemplate jdbcTemplate) {
        this.repo = r;
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean exists(String username) {
        return repo.existsByUsername(username);
    }

    public Author findByUsername(String username) {
        Optional<Author> a = repo.findByUsername(username);
        return a.orElse(null);
    }

    public void save(Author author) {
        repo.save(author);
    }

    public void insert(Author author) {
        jdbcTemplate.update(
                "INSERT INTO author (username, description, profile_picture, color) VALUES (?, ?, ?, ?)",
                author.getUsername(),
                author.getDescription(),
                author.getProfilePicture(),
                author.getColor()
        );
    }

    public List<Author> findAllByUsernameIn(List<String> usernames) {
        return repo.findAllByUsernameIn(usernames);
    }
}
