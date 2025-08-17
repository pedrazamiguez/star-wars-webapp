package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.CharacterSortingService;
import java.util.Comparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CharacterNameSortingServiceImpl implements CharacterSortingService {

  @Override
  public Comparator<Character> getComparator(final String sortDirection) {
    log.debug("Sorting characters by {} in {} order", this.getSortBy(), sortDirection);

    Comparator<Character> comparator =
        Comparator.comparing(Character::getName, String.CASE_INSENSITIVE_ORDER);

    if ("desc".equalsIgnoreCase(sortDirection)) {
      comparator = comparator.reversed();
    }

    return comparator;
  }

  @Override
  public String getSortBy() {
    return "name";
  }
}
