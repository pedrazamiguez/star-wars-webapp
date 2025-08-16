package es.pedrazamiguez.starwarswebapp.presentation.controller;

import es.pedrazamiguez.starwarswebapp.domain.usecase.ListCharactersUseCase;
import es.pedrazamiguez.starwarswebapp.domain.usecase.SearchCharactersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

  private static final String TEMPLATE_NAME = "characters";

  private static final String ATTRIBUTE_CHARACTERS = "characters";

  private static final String ATTRIBUTE_CURRENT_PAGE = "currentPage";

  private static final String ATTRIBUTE_QUERY = "query";

  private final ListCharactersUseCase listCharactersUseCase;

  private final SearchCharactersUseCase searchCharactersUseCase;

  @GetMapping
  public String listCharacters(
      @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
      final Model model) {
    model.addAttribute(ATTRIBUTE_CHARACTERS, this.listCharactersUseCase.listCharacters(page));
    model.addAttribute(ATTRIBUTE_CURRENT_PAGE, page);
    model.addAttribute(ATTRIBUTE_QUERY, "");
    return TEMPLATE_NAME;
  }

  @GetMapping("/search")
  public String searchCharacters(
      @RequestParam(value = "query", required = false, defaultValue = "") final String query,
      @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
      final Model model) {
    model.addAttribute(
        ATTRIBUTE_CHARACTERS, this.searchCharactersUseCase.searchCharacters(query, page));
    model.addAttribute(ATTRIBUTE_CURRENT_PAGE, page);
    model.addAttribute(ATTRIBUTE_QUERY, query);
    return TEMPLATE_NAME;
  }
}
