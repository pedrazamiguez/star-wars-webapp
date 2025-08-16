package es.pedrazamiguez.starwarswebapp.domain.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;

@FunctionalInterface
public interface ListCharactersUseCase {

  PaginatedCharacters listCharacters(int page);
}
