package backend.starter.bookExample.controller;

import backend.starter.bookExample.domain.Article;
import backend.starter.bookExample.dto.ArticleAddRequest;
import backend.starter.bookExample.dto.ArticleResponse;
import backend.starter.bookExample.dto.ArticleUpdateRequest;
import backend.starter.bookExample.service.BlogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

  private final BlogService blogService;

  @PostMapping("/api/articles")
  public ResponseEntity<Article> saveArticle(@RequestBody ArticleAddRequest request) {

    Article article = blogService.save(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(article);
  }

  @GetMapping("/api/articles")
  public ResponseEntity<List<ArticleResponse>> getAllArticles() {

    List<ArticleResponse> articles = blogService.getAllArticles().stream().map(ArticleResponse::new)
                                                .toList();

    return ResponseEntity.status(HttpStatus.OK).body(articles);
  }

  @GetMapping("/api/articles/{id}")
  public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) {

    try {
      Article article = blogService.getArticle(id);
      return ResponseEntity.ok().body(ArticleResponse.of(article));

    } catch (IllegalArgumentException e){
      return ResponseEntity.notFound().build();
    }

  }

  @DeleteMapping("/api/articles/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
    try {
      blogService.deleteArticle(id);
      return ResponseEntity.ok().build();

    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/api/articles/{id}")
  public ResponseEntity<ArticleResponse> updateArticle(@PathVariable Long id,
                                                       @RequestBody ArticleUpdateRequest request) {

    try {
      Article article = blogService.updateArticle(id, request);
      return ResponseEntity.ok().body(ArticleResponse.of(article));

    } catch (IllegalArgumentException e){
      return ResponseEntity.badRequest().build();
    }

  }
}