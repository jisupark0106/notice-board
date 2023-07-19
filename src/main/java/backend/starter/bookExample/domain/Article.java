package backend.starter.bookExample.domain;

import backend.starter.bookExample.dto.ArticleUpdateRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String title;

  @NotNull
  private String content;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime modifiedDate;


  @Builder
  public Article(String title, String content) {
    this.title = title;
    this.content = content;
  }


  public void update(ArticleUpdateRequest request) {

    if (StringUtils.isNotEmpty(request.getTitle()) && !request.getTitle().equals(this.title)) {
      this.title = request.getTitle();
    }
    if (StringUtils.isNotEmpty(request.getContent()) && !request.getContent()
                                                                .equals(this.content)) {
      this.content = request.getContent();
    }
  }
}
