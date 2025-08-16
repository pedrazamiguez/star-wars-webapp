package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;
import es.pedrazamiguez.starwarswebapp.domain.service.CharacterClientService;
import es.pedrazamiguez.starwarswebapp.domain.usecase.ListCharactersUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ListCharactersUseCaseImpl implements ListCharactersUseCase {

  private final CharacterClientService characterClientService;

  @Override
  public PaginatedCharacters listCharacters(final int page) {
    log.info("Listing characters for page {}", page);
    return this.characterClientService.getAllCharacters(page);
  }
}
