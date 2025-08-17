package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;
import es.pedrazamiguez.starwarswebapp.domain.service.search.CharacterSearchService;
import es.pedrazamiguez.starwarswebapp.domain.usecase.SearchCharactersUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchCharactersUseCaseImpl implements SearchCharactersUseCase {

  private final CharacterSearchService characterSearchService;

  @Override
  public PaginatedCharacters searchCharacters(
      final String searchTerm, final int page, final String sortBy, final String sortDirection) {

    log.info("Searching characters for term '{}' on page {}", searchTerm, page);
    return this.characterSearchService.searchCharacters(
        searchTerm.trim(), page, sortBy, sortDirection);
  }
}
