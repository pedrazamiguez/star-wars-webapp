package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.application.service.sorting.type.AbstractLocalDateTimeSortingService;
import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.SortingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
public class CharacterCreatedDateSortingServiceImpl extends AbstractLocalDateTimeSortingService<Character>
    implements SortingService<Character> {

  @Override
  protected Function<Character, LocalDateTime> getSortFunction() {
    return Character::getCreated;
  }

  @Override
  public String getSortBy() {
    return "created";
  }
}
