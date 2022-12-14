package br.com.engrenantorres.questionmanager.controller;

import br.com.engrenantorres.questionmanager.dto.NewQuestionDTO;
import br.com.engrenantorres.questionmanager.model.Banca;
import br.com.engrenantorres.questionmanager.model.Question;
import br.com.engrenantorres.questionmanager.model.SubjectArea;
import br.com.engrenantorres.questionmanager.model.User;
import br.com.engrenantorres.questionmanager.repository.BancaRepository;
import br.com.engrenantorres.questionmanager.repository.QuestionRepository;
import br.com.engrenantorres.questionmanager.repository.SubjectAreaRepository;
import br.com.engrenantorres.questionmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/question-form")
public class QuestionFormController {
  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private BancaRepository bancaRepository;
  @Autowired
  private SubjectAreaRepository areaRepository;
  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public String form(Model model,
                     NewQuestionDTO newQuestionDTO,
                     Principal principal
  ) {
    model.addAttribute("userName", principal.getName());
    injectAttributesFromBD(model);
    return "question-form";
  }
  @PostMapping
  public String insert(@Valid NewQuestionDTO newQuestionDTO, BindingResult bindingResult, Model model){
    if(bindingResult.hasErrors()) {
      injectAttributesFromBD(model);
      return "question-form";
    }

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username);
    saveQuestionInDB(newQuestionDTO, user);
    return "redirect:/questions-list";
  }

  private void saveQuestionInDB(NewQuestionDTO newQuestionDTO, User author) {
    Question question = newQuestionDTO.toQuestion(author);
    questionRepository.save(question);
  }

  private void injectAttributesFromBD(Model model) {
    List<SubjectArea> areas = areaRepository.findAll();
    List<Banca> bancas = bancaRepository.findAll();
    model.addAttribute("areas",areas);
    model.addAttribute("bancas",bancas);
  }
}
