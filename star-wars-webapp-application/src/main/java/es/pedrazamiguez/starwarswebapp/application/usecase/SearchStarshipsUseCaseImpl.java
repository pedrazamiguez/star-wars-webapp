package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.service.search.StarshipSearchService;
import es.pedrazamiguez.starwarswebapp.domain.usecase.SearchStarshipsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchStarshipsUseCaseImpl implements SearchStarshipsUseCase {

  private final StarshipSearchService starshipSearchService;

  @Override
  public Page<Starship> searchStarships(
      final String searchTerm, final int page, final String sortBy, final String sortDirection) {

    log.info("Searching starships for term '{}' on page {}", searchTerm, page);
    return this.starshipSearchService.searchStarships(
        searchTerm.trim(), page, sortBy, sortDirection);
  }
}
