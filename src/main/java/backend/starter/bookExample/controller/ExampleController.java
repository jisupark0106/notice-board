package backend.starter.bookExample.controller;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {


  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  class Person{
    Long id;
    String name;
    Integer age;
    List<String> hobbies;
  }
  @GetMapping("/thymeleaf/example")
  public String thymeleafExample(Model model){
    Person person = new Person(1L, "박지수", 28,List.of("댄스","노래","운동"));

    model.addAttribute("person", person);
    model.addAttribute("today", LocalDate.now());

    return "example";
  }

}

