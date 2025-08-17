package es.pedrazamiguez.starwarswebapp.application.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedStarships;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.service.client.StarshipClientService;
import es.pedrazamiguez.starwarswebapp.domain.service.search.StarshipSearchService;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.StarshipSortingService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class StarshipSearchServiceImpl implements StarshipSearchService {

  private final int pageSize;

  private final StarshipClientService starshipClientService;

  private final Map<String, StarshipSortingService> starshipSortingServices;

  public StarshipSearchServiceImpl(
      @Value("${swapi.default-page-size}") final int defaultPageSize,
      final StarshipClientService starshipClientService,
      final List<StarshipSortingService> starshipSortingServices) {

    this.pageSize = defaultPageSize;
    this.starshipClientService = starshipClientService;
    this.starshipSortingServices =
        starshipSortingServices.stream()
            .collect(Collectors.toMap(StarshipSortingService::getSortBy, Function.identity()));
  }

  @Override
  public PaginatedStarships searchStarships(
      final String searchTerm, final int page, final String sortBy, final String sortDirection) {

    log.debug(
        "Searching starships for term '{}' on page {}, sortBy: {}, sortDirection: {}",
        searchTerm,
        page,
        sortBy,
        sortDirection);

    final List<Starship> starships = this.starshipClientService.fetchAllStarships();
    final List<Starship> filteredStarships =
        starships.stream()
            .filter(
                starship ->
                    searchTerm.isEmpty()
                        || starship.getName().toLowerCase().contains(searchTerm.toLowerCase()))
            .collect(Collectors.toList());

    final StarshipSortingService starshipSortingService = this.starshipSortingServices.get(sortBy);

    if (!ObjectUtils.isEmpty(starshipSortingService)) {

      final Comparator<Starship> starshipComparator =
          starshipSortingService.getComparator(sortDirection);

      filteredStarships.sort(starshipComparator);
    }

    final int from = (page - 1) * this.pageSize;
    final int to = Math.min(from + this.pageSize, filteredStarships.size());

    final List<Starship> pageSlice =
        (from < filteredStarships.size())
            ? filteredStarships.subList(from, to)
            : Collections.emptyList();

    final boolean hasNext = to < filteredStarships.size();
    final boolean hasPrevious = page > 1;

    return PaginatedStarships.builder()
        .starships(pageSlice)
        .totalCount(filteredStarships.size())
        .hasNext(hasNext)
        .hasPrevious(hasPrevious)
        .build();
  }
}
