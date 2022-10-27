package br.com.engrenantorres.questionmanager.controller;

import br.com.engrenantorres.questionmanager.model.Question;
import br.com.engrenantorres.questionmanager.model.SubjectArea;
import br.com.engrenantorres.questionmanager.repository.QuestionRepository;
import br.com.engrenantorres.questionmanager.repository.SubjectAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/questions-list")
public class IndexController {
  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private SubjectAreaRepository areaRepository;
  private Integer paginationSize = 5;

  @GetMapping
  public String index(Model model,
                      @RequestParam(name= "page",required = false, defaultValue = "0")Integer page) {

    List<SubjectArea> areas = areaRepository.findAll();
    model.addAttribute("areas",areas);
    HandlePagination(model, page, 0L);

    return "index";
  }


  @GetMapping("/{areaId}")
  public String filter(
    @PathVariable("areaId") Long areaId ,
    Model model,
    @RequestParam(name= "page",required = false, defaultValue = "0")Integer page) {
    Optional<SubjectArea> optionalAreas = areaRepository.findById(areaId);

    optionalAreas.ifPresent(area -> {
      HandlePagination(model, page, areaId);
      }
    );

    return "index";
  }
  @ExceptionHandler(IllegalArgumentException.class)
  public String onError(){
    return "redirect:/questions-list";
  }
  private void HandlePagination(Model model, Integer page, Long areaId) {
    Page<Question> questions = questionRepository.findAll(PageRequest.of(page,paginationSize));
    Integer nextPage = (page >= (questions.getTotalElements() - 1)/paginationSize) ? page : page + 1;
    Integer previousPage = (page <= 0) ? 0 : page - 1;

    model.addAttribute("questions", questions);
    model.addAttribute("nextPage", nextPage);
    model.addAttribute("previousPage", previousPage);
    model.addAttribute("isFirst",questions.isFirst());
    model.addAttribute("isLast",questions.isLast());
    if(areaId == 0)  model.addAttribute("areaId", "nulo");
      else model.addAttribute("areaId", areaId);
  }
}
