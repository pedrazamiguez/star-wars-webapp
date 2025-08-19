package es.pedrazamiguez.starwarswebapp.domain.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;

@FunctionalInterface
public interface SearchStarshipsUseCase {

  Page<Starship> searchStarships(String searchTerm, int page, String sortBy, String sortDirection);
}
