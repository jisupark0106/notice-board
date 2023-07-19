package backend.starter.bookExample.repository;

import backend.starter.bookExample.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
