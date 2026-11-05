package rezepte.website.rezept_website.service.user;

import org.springframework.data.util.Streamable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import rezepte.website.rezept_website.controller.formulare.Author;
import rezepte.website.rezept_website.controller.formulare.Comment;
import rezepte.website.rezept_website.persistence.CommentRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository repo;
    private final JdbcTemplate jdbcTemplate;
    private final AuthorService authorService;

    public CommentService(CommentRepository repo, JdbcTemplate jdbcTemplate, AuthorService authorService) {
        this.repo = repo;
        this.jdbcTemplate = jdbcTemplate;
        this.authorService = authorService;
    }

    public void add(Author author, String content) {
        String id = UUID.randomUUID().toString();

        jdbcTemplate.update(
                "INSERT INTO comment (id, content, created, author) VALUES (?, ?, ?, ?)",
                id,
                content,
                LocalDateTime.now(ZoneId.of("Europe/Berlin")),
                author.getUsername()
        );
    }

    public List<Comment> getAll() {
        List<Comment> comments = Streamable.of(repo.findAll()).toList();
        comments = comments.stream().sorted(Comparator.comparing(Comment::getCreated).reversed()).toList();

        List<String> usernames = comments.stream()
                .map(Comment::getUsername)
                .distinct()
                .toList();

        if (usernames.isEmpty()) {
            return comments;
        }

        Map<String, Author> authors = authorService.findAllByUsernameIn(usernames)
                .stream()
                .collect(Collectors.toMap(
                        Author::getUsername,
                        Function.identity()
                ));

        comments.forEach(comment ->
                comment.setAuthor(authors.get(comment.getUsername()))
        );

        return comments;
    }
}