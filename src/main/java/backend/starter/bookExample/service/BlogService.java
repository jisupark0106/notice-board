package backend.starter.bookExample.service;

import backend.starter.bookExample.domain.Article;
import backend.starter.bookExample.dto.ArticleAddRequest;
import backend.starter.bookExample.dto.ArticleUpdateRequest;
import backend.starter.bookExample.repository.BlogRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(ArticleAddRequest request){
        return blogRepository.save(request.toEntity());
    }

    public List<Article> getAllArticles(){
        return blogRepository.findAll();
    }

    public Article getArticle(Long id){

        Optional<Article> optional = blogRepository.findById(id);
        if(optional.isEmpty()){
            throw new IllegalArgumentException();
        }
        return optional.get();
    }

    public void deleteArticle(Long id){
        if(!blogRepository.existsById(id)){
            throw new IllegalArgumentException();
        }
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article updateArticle(Long id, ArticleUpdateRequest articleUpdateRequest){

        Optional<Article> optional = blogRepository.findById(id);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Article article = optional.get();
        article.update(articleUpdateRequest);

        return article;
    }
}
