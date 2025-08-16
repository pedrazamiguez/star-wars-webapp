package es.pedrazamiguez.starwarswebapp.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/starships")
public class StarshipController {

  @RequestMapping
  public String listStarships(
      @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
    return "starships";
  }
}
