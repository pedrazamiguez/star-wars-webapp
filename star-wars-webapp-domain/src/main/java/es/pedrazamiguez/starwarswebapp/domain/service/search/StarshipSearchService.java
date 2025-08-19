package es.pedrazamiguez.starwarswebapp.domain.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;

public interface StarshipSearchService {

  Page<Starship> searchStarships(String searchTerm, int page, String sortBy, String sortDirection);
}
