package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.service.PersonClientService;
import es.pedrazamiguez.starwarswebapp.domain.usecase.ListCharactersUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ListCharactersUseCaseImpl implements ListCharactersUseCase {

  private final PersonClientService personClientService;

  @Override
  public List<Character> listCharacters(final int page) {
    log.info("Listing characters for page {}", page);
    return this.personClientService.getAllCharacters(page);
  }
}
