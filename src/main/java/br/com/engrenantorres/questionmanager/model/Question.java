package br.com.engrenantorres.questionmanager.model;

import br.com.engrenantorres.questionmanager.repository.QuestionRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Banca banca = new Banca();
  @ManyToOne
  private SubjectArea cargo = new SubjectArea();
  private String enunciado = "";

  private String alternativa1 = "";

  private String alternativa2 = "";

  private String alternativa3 = "";

  private String alternativa4 = "";

  private String alternativa5 = "";

  private Integer ano = 2022;
  private String concurso = "";
  private String assunto = "";
  @Enumerated(EnumType.STRING)
  private Answers resposta = Answers.a;
  private String observacao = "";
  private LocalDateTime date = LocalDateTime.now();

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private User author;

  public Question(User author) {
    this.author = author;
  }
  public Question() {
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Banca getBanca() {
    return banca;
  }

  public void setBanca(Banca banca) {
    this.banca = banca;
  }

  public SubjectArea getCargo() {
    return cargo;
  }

  public void setCargo(SubjectArea cargo) {
    this.cargo = cargo;
  }

  public String getEnunciado() {
    return enunciado;
  }

  public void setEnunciado(String enunciado) {
    this.enunciado = enunciado;
  }

  public String getAlternativa1() {
    return alternativa1;
  }

  public void setAlternativa1(String alternativa) {
    this.alternativa1 = alternativa;
  }

  public String getAlternativa2() {
    return alternativa2;
  }

  public void setAlternativa2(String alternativa2) {
    this.alternativa2 = alternativa2;
  }

  public String getAlternativa3() {
    return alternativa3;
  }

  public void setAlternativa3(String alternativa3) {
    this.alternativa3 = alternativa3;
  }

  public String getAlternativa4() {
    return alternativa4;
  }

  public void setAlternativa4(String alternativa4) {
    this.alternativa4 = alternativa4;
  }

  public String getAlternativa5() {
    return alternativa5;
  }

  public void setAlternativa5(String alternativa5) {
    this.alternativa5 = alternativa5;
  }

  public Integer getAno() {
    return ano;
  }

  public void setAno(Integer ano) {
    this.ano = ano;
  }

  public String getConcurso() {
    return concurso;
  }

  public void setConcurso(String concurso) {
    this.concurso = concurso;
  }

  public String getAssunto() {
    return assunto;
  }

  public void setAssunto(String assunto) {
    this.assunto = assunto;
  }

  public Answers getResposta() {
    return resposta;
  }

  public void setResposta(Answers resposta) {
    this.resposta = resposta;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  @Autowired
  private static QuestionRepository questionRepository;

  public static List<Question> getAll(){
    return questionRepository.findAll();
  }
}
