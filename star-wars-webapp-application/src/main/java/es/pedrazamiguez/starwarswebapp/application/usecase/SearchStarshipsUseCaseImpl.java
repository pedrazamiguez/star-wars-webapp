package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedStarships;
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
  public PaginatedStarships searchStarships(
      final String searchTerm, final int page, final String sortBy, final String sortDirection) {

    log.info("Searching characters for term '{}' on page {}", searchTerm, page);
    return this.starshipSearchService.searchStarships(
        searchTerm.trim(), page, sortBy, sortDirection);
  }
}
