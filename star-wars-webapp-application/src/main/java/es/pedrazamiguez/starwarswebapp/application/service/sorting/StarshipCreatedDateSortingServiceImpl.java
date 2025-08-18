package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.StarshipSortingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Slf4j
@Service
public class StarshipCreatedDateSortingServiceImpl implements StarshipSortingService {
  @Override
  public Comparator<Starship> getComparator(final String sortDirection) {
    log.debug("Sorting starships by {} in {} order", this.getSortBy(), sortDirection);

    Comparator<Starship> comparator =
        Comparator.comparing(Starship::getCreated, Comparator.nullsLast(Comparator.naturalOrder()));

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
