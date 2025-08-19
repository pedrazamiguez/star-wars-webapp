package es.pedrazamiguez.starwarswebapp.application.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.service.client.StarshipClientService;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.SortingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StarshipSearchServiceImpl extends AbstractSearchService<Starship> {

  private final StarshipClientService starshipClientService;

  private final Map<String, SortingService<Starship>> starshipSortingServices;

  public StarshipSearchServiceImpl(@Value("${swapi.default-page-size}") final int pageSize,
                                   final StarshipClientService starshipClientService,
                                   final List<SortingService<Starship>> starshipSortingServices) {
    super(pageSize);

    this.starshipClientService = starshipClientService;
    this.starshipSortingServices = starshipSortingServices.stream()
        .collect(Collectors.toMap(SortingService<Starship>::getSortBy, Function.identity()));
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
    final SortingService<Starship> starshipSortingService = this.starshipSortingServices.get(sortBy);

    if (!ObjectUtils.isEmpty(starshipSortingService)) {
      return starshipSortingService.getComparator(sortDirection);
    }

    return Comparator.comparing(Starship::getStarshipId);
  }

}
