package backend.starter.bookExample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import backend.starter.bookExample.domain.Article;
import backend.starter.bookExample.dto.ArticleAddRequest;
import backend.starter.bookExample.dto.ArticleUpdateRequest;
import backend.starter.bookExample.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void deleteAllArticles() {
    blogRepository.deleteAll();
  }

  @DisplayName("save article :: 블로그 글 저장에 성공")
  @Test
  public void saveArticle() throws Exception {

    // given
    String url = "/api/articles";

    String title = "제목 테스트";
    String content = "내용 테스트";

    ArticleAddRequest request = new ArticleAddRequest(title, content);
    final String requestBody = objectMapper.writeValueAsString(request);

    //when
    ResultActions resultActions = mockMvc.perform(
        post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody)).andDo(print());

    //then
    resultActions.andExpect(status().isCreated()).andExpect(jsonPath("title").value(title))
                 .andExpect(jsonPath("content").value(content));


  }

  @DisplayName("findAllArticles :: 블로그 내 모든 글 조회")
  @Test
  public void getAllArticles() throws Exception {

    // given
    String url = "/api/articles";

    for (int i = 0; i < 10; i++) {
      ArticleAddRequest request = new ArticleAddRequest("title", "content");
      String requestBodyAsString = objectMapper.writeValueAsString(request);
      mockMvc.perform(
          post(url).contentType(MediaType.APPLICATION_JSON).content(requestBodyAsString));
    }

    // when
    ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
                                         .andDo(print());

    // then
    resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0]").isNotEmpty());
  }

  @DisplayName("findArticle :: 블로그 글 조회에 성공")
  @Test
  public void getArticle() throws Exception {

    // given
    String url = "/api/articles/{id}";

    String title = "특정 id 조회 title";
    String content = "특정 id 조회 content";

    Article article = new Article(title, content);
    Article savedArticle = blogRepository.save(article);

    // when
    ResultActions resultActions = mockMvc.perform(
        get(url, savedArticle.getId()).accept(MediaType.APPLICATION_JSON)).andDo(print());

    // then
    resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(savedArticle.getId()))
                 .andExpect(jsonPath("$.title").value(savedArticle.getTitle()))
                 .andExpect(jsonPath("$.content").value(savedArticle.getContent()));
  }

  @DisplayName("badRequetFindArticle :: 조회할 수 없는 글 조회 에러")
  @Test
  public void getArticleBadRequest() throws Exception {

    // given
    String url = "/api/articles/{id}";

    String title = "특정 id 조회 title";
    String content = "특정 id 조회 content";

    Article article = new Article(title, content);
    Article savedArticle = blogRepository.save(article);

    // when
    ResultActions notFoundResultActions = mockMvc.perform(
        get(url, 10000).accept(MediaType.APPLICATION_JSON)).andDo(print());

    // then
    notFoundResultActions.andExpect(status().isNotFound());
  }


  @DisplayName("deleteArticle :: 블로그 글 삭제")
  @Test
  public void deleteArticle() throws Exception {

    // given
    String url = "/api/articles/{id}";

    String title = "특정 id 삭제 title";
    String content = "특정 id 삭제 content";

    Article article = new Article(title, content);
    Article savedArticle = blogRepository.save(article);

    // when
    ResultActions notFoundResultActions = mockMvc.perform(delete(url, savedArticle.getId()))
                                                 .andDo(print());

    // then
    notFoundResultActions.andExpect(status().isOk());
  }

  @DisplayName("badRequestDeleteArticle :: 삭제할 수 없는 블로그 글 에러")
  @Test
  public void deleteArticleBadRequest() throws Exception {

    // given
    String url = "/api/articles/{id}";

    String title = "특정 id 삭제 title";
    String content = "특정 id 삭제 content";

    Article article = new Article(title, content);
    Article savedArticle = blogRepository.save(article);

    // when
    ResultActions notFoundResultActions = mockMvc.perform(delete(url, 10000)).andDo(print());

    // then
    notFoundResultActions.andExpect(status().isBadRequest());
  }

  @DisplayName("updateArticle :: 블로그 글 수정")
  @Test
  public void updateArticle() throws Exception {

    // given

    String url = "/api/articles/{id}";
    String oldTitle = "old title";
    String oldContent = "old content";

    Article article = blogRepository.save(new Article(oldTitle, oldContent));

    String newTitle = "new title";
    String newContent = "new content";

    ArticleUpdateRequest articleUpdateRequest = ArticleUpdateRequest.builder().title(newTitle)
                                                                    .content(newContent).build();

    String requestBody = objectMapper.writeValueAsString(articleUpdateRequest);

    // when
    ResultActions resultActions = mockMvc.perform(
        put(url, article.getId()).contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andDo(print());

    // then
    resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(article.getId()))
                 .andExpect(jsonPath("$.title").value(newTitle))
                 .andExpect(jsonPath("$.content").value(newContent));

  }

  @DisplayName("updateArticleTitle :: 블로그 글 제목만 수정")
  @Test
  public void updateArticleTitle() throws Exception {

    // given

    String url = "/api/articles/{id}";
    String oldTitle = "old title";
    String oldContent = "old content";

    Article article = blogRepository.save(new Article(oldTitle, oldContent));

    String newTitle = "new title";

    ArticleUpdateRequest articleUpdateRequest = ArticleUpdateRequest.builder().title(newTitle).build();

    String requestBody = objectMapper.writeValueAsString(articleUpdateRequest);

    // when
    ResultActions resultActions = mockMvc.perform(
                                             put(url, article.getId()).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                                         .andDo(print());

    // then
    resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(article.getId()))
                 .andExpect(jsonPath("$.title").value(newTitle))
                 .andExpect(jsonPath("$.content").value(oldContent));

  }

}