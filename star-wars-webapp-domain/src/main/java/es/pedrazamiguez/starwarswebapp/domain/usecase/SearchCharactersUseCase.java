package es.pedrazamiguez.starwarswebapp.domain.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;

@FunctionalInterface
public interface SearchCharactersUseCase {

  Page<Character> searchCharacters(String searchTerm, int page, String sortBy, String sortDirection);
}
