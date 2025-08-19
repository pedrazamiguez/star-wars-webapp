package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.application.service.sorting.type.AbstractStringSortingService;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StarshipNameSortingServiceImpl extends AbstractStringSortingService<Starship> {

  @Override
  protected Function<Starship, String> getSortFunction() {
    return Starship::getName;
  }

  @Override
  public String getSortBy() {
    return "name";
  }
}
