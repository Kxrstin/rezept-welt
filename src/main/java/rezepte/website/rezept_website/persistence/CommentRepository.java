package rezepte.website.rezept_website.persistence;

import org.springframework.data.repository.CrudRepository;
import rezepte.website.rezept_website.controller.formulare.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, String> { ;
}
