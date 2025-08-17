package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.CharacterSortingService;
import java.util.Comparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CharacterCreatedDateSortingServiceImpl implements CharacterSortingService {

  @Override
  public Comparator<Character> getComparator(final String sortDirection) {
    log.debug("Sorting characters by {} in {} order", this.getSortBy(), sortDirection);

    Comparator<Character> comparator =
        Comparator.comparing(
            Character::getCreated, Comparator.nullsLast(Comparator.naturalOrder()));

    if ("desc".equalsIgnoreCase(sortDirection)) {
      comparator = comparator.reversed();
    }

    return comparator;
  }

  @Override
  public String getSortBy() {
    return "created";
  }
}
