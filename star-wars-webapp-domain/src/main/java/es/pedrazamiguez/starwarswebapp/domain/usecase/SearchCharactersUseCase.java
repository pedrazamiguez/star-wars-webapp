package es.pedrazamiguez.starwarswebapp.domain.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;

@FunctionalInterface
public interface SearchCharactersUseCase {

  PaginatedCharacters searchCharacters(String searchTerm, int page);
}
