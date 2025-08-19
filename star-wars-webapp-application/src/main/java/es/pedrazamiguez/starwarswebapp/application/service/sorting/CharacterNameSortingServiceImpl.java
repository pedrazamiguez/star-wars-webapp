package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.application.service.sorting.type.AbstractStringSortingService;
import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.SortingService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CharacterNameSortingServiceImpl extends AbstractStringSortingService<Character>
    implements SortingService<Character> {

  @Override
  protected Function<Character, String> getSortFunction() {
    return Character::getName;
  }

  @Override
  public String getSortBy() {
    return "name";
  }
}
