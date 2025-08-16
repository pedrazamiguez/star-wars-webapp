package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.service.CharacterClientService;
import es.pedrazamiguez.starwarswebapp.domain.usecase.SearchCharactersUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchCharactersUseCaseImpl implements SearchCharactersUseCase {

  private final CharacterClientService characterClientService;

  @Override
  public List<Character> searchCharacters(final String searchTerm, final int page) {
    log.info("Searching characters with term '{}' for page {}", searchTerm, page);
    if (ObjectUtils.isEmpty(searchTerm)) {
      log.warn("Search term is empty, listing all characters for page {}", page);
      return this.characterClientService.getAllCharacters(page);
    } else {
      return this.characterClientService.searchCharacters(searchTerm, page);
    }
  }
}
