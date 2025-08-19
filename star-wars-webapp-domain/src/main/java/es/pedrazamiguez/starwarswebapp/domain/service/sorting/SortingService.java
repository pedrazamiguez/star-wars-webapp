package es.pedrazamiguez.starwarswebapp.domain.service.sorting;

import java.util.Comparator;

public interface SortingService<T> {

  Comparator<T> getComparator(String sortDirection);

  String getSortBy();
}
