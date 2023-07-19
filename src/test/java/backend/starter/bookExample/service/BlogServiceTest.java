package backend.starter.bookExample.service;

import static org.junit.jupiter.api.Assertions.*;

import backend.starter.bookExample.domain.Article;
import backend.starter.bookExample.dto.ArticleUpdateRequest;
import backend.starter.bookExample.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogServiceTest {

  @Autowired
  private BlogRepository blogRepository;
  @Autowired
  private BlogService blogService;

  @BeforeEach
  public void makeArticleEntity() {

    blogRepository.deleteAll();

  }

  @DisplayName("update test")
  @Test
  public void updateMethodTest() {
    String oldTitle = "oldTitle";
    String oldContent = "oldContent";

    Article article = blogRepository.save(new Article(oldTitle, oldContent));

    String newTitle = "newTitle";
    String newContent = "newContent";

    ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest(newTitle, newContent);

    Article updateArticle = blogService.updateArticle(article.getId(), articleUpdateRequest);

    Assertions.assertEquals(updateArticle.getTitle(), newTitle);
    Assertions.assertEquals(updateArticle.getContent(), newContent);
    Assertions.assertEquals(updateArticle, article);
  }

}