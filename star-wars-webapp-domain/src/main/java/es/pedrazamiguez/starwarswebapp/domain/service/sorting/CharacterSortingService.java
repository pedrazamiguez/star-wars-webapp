package es.pedrazamiguez.starwarswebapp.domain.service.sorting;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import java.util.Comparator;

public interface CharacterSortingService {

  Comparator<Character> getComparator(String sortDirection);

  String getSortBy();
}
