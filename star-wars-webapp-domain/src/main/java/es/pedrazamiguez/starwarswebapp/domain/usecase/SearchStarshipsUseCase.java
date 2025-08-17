package es.pedrazamiguez.starwarswebapp.domain.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedStarships;

@FunctionalInterface
public interface SearchStarshipsUseCase {

  PaginatedStarships searchStarships(
      String searchTerm, int page, String sortBy, String sortDirection);
}
