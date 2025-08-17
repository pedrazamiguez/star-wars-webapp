package es.pedrazamiguez.starwarswebapp.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

  @GetMapping
  public String handleError() {
    return "error";
  }
}
