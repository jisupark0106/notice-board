package backend.starter.bookExample.dto;

import backend.starter.bookExample.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListViewResponse {

  private Long id;
  private String title;
  private String content;

  public ArticleListViewResponse(Article article) {
    this.id = article.getId();
    this.title = article.getTitle();
    this.content = article.getContent();
  }

  public ArticleListViewResponse of(Article article) {
   return new ArticleListViewResponse(article);
  }
}
