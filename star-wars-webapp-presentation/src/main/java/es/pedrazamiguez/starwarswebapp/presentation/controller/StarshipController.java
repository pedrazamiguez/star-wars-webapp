package es.pedrazamiguez.starwarswebapp.presentation.controller;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedStarships;
import es.pedrazamiguez.starwarswebapp.domain.usecase.SearchStarshipsUseCase;
import es.pedrazamiguez.starwarswebapp.presentation.mapper.StarshipViewModelMapper;
import es.pedrazamiguez.starwarswebapp.presentation.view.StarshipViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/starships")
@RequiredArgsConstructor
public class StarshipController {

  private static final String TEMPLATE_NAME = "starships";

  private static final String ATTRIBUTE_VIEW_MODEL = "viewModel";

  private final SearchStarshipsUseCase searchStarshipsUseCase;

  private final StarshipViewModelMapper starshipViewModelMapper;

  @GetMapping
  public String searchStarships(
      @RequestParam(value = "query", required = false, defaultValue = "") final String query,
      @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
      @RequestParam(value = "sortBy", required = false, defaultValue = "") final String sortBy,
      @RequestParam(value = "sortDirection", required = false, defaultValue = "")
          final String sortDirection,
      final Model model) {

    final PaginatedStarships paginatedStarships =
        this.searchStarshipsUseCase.searchStarships(query, page, sortBy, sortDirection);

    final StarshipViewModel starshipViewModel =
        this.starshipViewModelMapper.toViewModel(
            paginatedStarships, query, page, sortBy, sortDirection);

    model.addAttribute(ATTRIBUTE_VIEW_MODEL, starshipViewModel);

    return TEMPLATE_NAME;
  }
}
