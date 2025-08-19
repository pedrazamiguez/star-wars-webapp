package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.application.service.sorting.type.AbstractLocalDateTimeSortingService;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.SortingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
public class StarshipCreatedDateSortingServiceImpl extends AbstractLocalDateTimeSortingService<Starship>
    implements SortingService<Starship> {

  @Override
  protected Function<Starship, LocalDateTime> getSortFunction() {
    return Starship::getCreated;
  }

  @Override
  public String getSortBy() {
    return "created";
  }
}
