package es.pedrazamiguez.starwarswebapp.presentation.controller;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;
import es.pedrazamiguez.starwarswebapp.domain.usecase.SearchCharactersUseCase;
import es.pedrazamiguez.starwarswebapp.presentation.mapper.CharacterViewModelMapper;
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

  private static final String ATTRIBUTE_VIEW_MODEL = "viewModel";

  private final SearchCharactersUseCase searchCharactersUseCase;

  private final CharacterViewModelMapper characterViewModelMapper;

  @GetMapping
  public String searchCharacters(
      @RequestParam(value = "query", required = false, defaultValue = "") final String query,
      @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
      final Model model) {

    final PaginatedCharacters paginatedCharacters =
        this.searchCharactersUseCase.searchCharacters(query, page);

    model.addAttribute(
        ATTRIBUTE_VIEW_MODEL,
        this.characterViewModelMapper.toViewModel(paginatedCharacters, query, page));

    return TEMPLATE_NAME;
  }
}
