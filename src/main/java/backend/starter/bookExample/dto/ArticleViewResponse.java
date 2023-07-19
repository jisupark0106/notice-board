package backend.starter.bookExample.dto;

import backend.starter.bookExample.domain.Article;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
  private Long id;
  private String title;
  private String content;

  private LocalDateTime createdDate;

  public ArticleViewResponse(Article article) {
    this.id = article.getId();
    this.title = article.getTitle();
    this.content = article.getContent();
    this.createdDate = article.getCreatedDate();
  }
}
