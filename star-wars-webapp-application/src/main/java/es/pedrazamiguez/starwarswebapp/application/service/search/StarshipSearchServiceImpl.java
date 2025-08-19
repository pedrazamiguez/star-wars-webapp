package es.pedrazamiguez.starwarswebapp.application.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.service.client.StarshipClientService;
import es.pedrazamiguez.starwarswebapp.domain.service.search.SearchService;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.StarshipSortingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StarshipSearchServiceImpl extends AbstractSearchService<Starship> implements SearchService<Starship> {

  private final StarshipClientService starshipClientService;

  private final Map<String, StarshipSortingService> starshipSortingServices;

  public StarshipSearchServiceImpl(@Value("${swapi.default-page-size}") final int pageSize,
                                   final StarshipClientService starshipClientService,
                                   final List<StarshipSortingService> starshipSortingServices) {
    super(pageSize);

    this.starshipClientService = starshipClientService;
    this.starshipSortingServices = starshipSortingServices.stream()
        .collect(Collectors.toMap(StarshipSortingService::getSortBy, Function.identity()));
  }

  @Override
  protected List<Starship> fetchAllItems() {
    return this.starshipClientService.fetchAllStarships();
  }

  @Override
  protected boolean qualifiesForSearch(final Starship item, final String searchTerm) {
    return item.getName()
        .toLowerCase()
        .contains(searchTerm) || item.getModel()
        .toLowerCase()
        .contains(searchTerm);
  }

  @Override
  protected Comparator<Starship> getComparator(final String sortBy, final String sortDirection) {
    final StarshipSortingService starshipSortingService = this.starshipSortingServices.get(sortBy);

    if (!ObjectUtils.isEmpty(starshipSortingService)) {
      return starshipSortingService.getComparator(sortDirection);
    }

    return Comparator.comparing(Starship::getStarshipId);
  }

}
