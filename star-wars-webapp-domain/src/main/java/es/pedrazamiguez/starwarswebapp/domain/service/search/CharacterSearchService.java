package es.pedrazamiguez.starwarswebapp.domain.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;

public interface CharacterSearchService {

  Page<Character> searchCharacters(String searchTerm, int page, String sortBy, String sortDirection);
}
