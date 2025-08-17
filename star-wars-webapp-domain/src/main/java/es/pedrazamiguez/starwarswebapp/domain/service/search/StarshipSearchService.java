package es.pedrazamiguez.starwarswebapp.domain.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedStarships;

public interface StarshipSearchService {

  PaginatedStarships searchStarships(
      String searchTerm, int page, String sortBy, String sortDirection);
}
