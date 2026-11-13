package rezepte.website.rezept_website.service.user;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import rezepte.website.rezept_website.controller.formulare.Author;
import rezepte.website.rezept_website.controller.formulare.Comment;
import rezepte.website.rezept_website.controller.formulare.Stars;
import rezepte.website.rezept_website.persistence.StarsRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StarsService {
    private final StarsRepository repo;
    private final AuthorService authorService;
    private final JdbcTemplate jdbcTemplate;

    public StarsService(StarsRepository repository, AuthorService authorService, JdbcTemplate jdbcTemplate) {
        repo = repository;
        this.authorService = authorService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Stars> findAllByRezeptId(Integer rezeptId) {
        List<Stars> stars = Streamable.of(repo.findAllByRezeptId(rezeptId)).toList();
        stars = stars.stream().sorted(Comparator.comparing(Stars::getCreatedAt).reversed()).toList();
        List<String> neededAuthors = stars.stream()
                .map(Stars::getUsername)
                .distinct()
                .toList();

        Map<String, Author> authors = authorService
                .findAllByUsernameIn(neededAuthors)
                .stream()
                .collect(Collectors.toMap(
                        Author::getUsername,
                        Function.identity()
                ));
        
        return stars.stream()
                .peek(s -> s.setAuthor(authors.get(s.getUsername())))
                .toList();
    }

    public Double avgRanking(Integer rezeptId) {
        List<Stars> stars = repo.findAllByRezeptId(rezeptId);
        int size = stars.size();
        if(size == 0) return 5.0;
        return stars.stream()
                .map(Stars::getRanking)
                .reduce(Double::sum)
                .orElse(0.0) / stars.size();
    }

    public void save(Integer rezeptId, Integer ranking, String content, String username) {
        jdbcTemplate.update(
                "INSERT INTO stars (id, content, ranking, username, created_at, rezept_id) VALUES (?, ?, ?, ?, ?, ?)",
                UUID.randomUUID().toString(),
                content,
                ranking,
                username,
                LocalDateTime.now(ZoneId.of("Europe/Berlin")),
                rezeptId
        );
    }
}
