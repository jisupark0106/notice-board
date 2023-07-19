package backend.starter.bookExample.controller;

import backend.starter.bookExample.domain.Article;
import backend.starter.bookExample.dto.ArticleListViewResponse;
import backend.starter.bookExample.dto.ArticleResponse;
import backend.starter.bookExample.dto.ArticleViewResponse;
import backend.starter.bookExample.service.BlogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

  private final BlogService blogService;

  @GetMapping("/articles")
  public String getArticles(Model model) {

    List<ArticleListViewResponse> articleListViewResponses = blogService.getAllArticles().stream()
                                                                        .map(
                                                                            ArticleListViewResponse::new)
                                                                        .toList();

    model.addAttribute("articles", articleListViewResponses);
    return "articleList";
  }

  @GetMapping("/articles/{id}")
  public String getArticle(@PathVariable Long id, Model model){

    Article article = blogService.getArticle(id);
    model.addAttribute("article", article);

    return "article";

  }

  @GetMapping("/new-article")
  public String newArticle(@RequestParam(required = false) Long id, Model model){
    if(ObjectUtils.isEmpty(id)){
      model.addAttribute("article", new ArticleViewResponse());
    } else{
      Article article = blogService.getArticle(id);
      model.addAttribute("article", new ArticleViewResponse(article));
    }

    return "newArticle";
  }

}
