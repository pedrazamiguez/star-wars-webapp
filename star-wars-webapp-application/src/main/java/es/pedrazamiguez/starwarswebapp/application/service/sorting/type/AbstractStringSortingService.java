package es.pedrazamiguez.starwarswebapp.application.service.sorting.type;

import es.pedrazamiguez.starwarswebapp.domain.service.sorting.SortingService;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.function.Function;

@Slf4j
public abstract class AbstractStringSortingService<T> implements SortingService<T> {

  @Override
  public Comparator<T> getComparator(final String sortDirection) {
    log.debug("Sorting by {} in {} order", this.getSortBy(), sortDirection);

    Comparator<T> comparator = Comparator.comparing(this.getSortFunction(), String.CASE_INSENSITIVE_ORDER);

    if ("desc".equalsIgnoreCase(sortDirection)) {
      comparator = comparator.reversed();
    }

    return comparator;
  }

  protected abstract Function<T, String> getSortFunction();
}
