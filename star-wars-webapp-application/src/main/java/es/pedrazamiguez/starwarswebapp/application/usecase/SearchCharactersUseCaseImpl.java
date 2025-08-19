package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.service.search.SearchService;
import es.pedrazamiguez.starwarswebapp.domain.usecase.SearchCharactersUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchCharactersUseCaseImpl implements SearchCharactersUseCase {

  private final SearchService<Character> characterSearchService;

  @Override
  public Page<Character> searchCharacters(final String searchTerm, final int page, final String sortBy,
                                          final String sortDirection) {

    log.info("Searching characters for term '{}' on page {}", searchTerm, page);
    return this.characterSearchService.search(searchTerm.trim(), page, sortBy, sortDirection);
  }
}
