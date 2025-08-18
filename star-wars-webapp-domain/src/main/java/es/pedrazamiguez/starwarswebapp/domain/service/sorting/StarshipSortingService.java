package es.pedrazamiguez.starwarswebapp.domain.service.sorting;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;

import java.util.Comparator;

public interface StarshipSortingService {

  Comparator<Starship> getComparator(String sortDirection);

  String getSortBy();
}
