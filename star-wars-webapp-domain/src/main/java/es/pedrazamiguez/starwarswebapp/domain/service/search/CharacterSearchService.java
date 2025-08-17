package es.pedrazamiguez.starwarswebapp.domain.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;

public interface CharacterSearchService {

  PaginatedCharacters searchCharacters(
      String searchTerm, int page, String sortBy, String sortDirection);
}
