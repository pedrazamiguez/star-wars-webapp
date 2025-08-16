package es.pedrazamiguez.starwarswebapp.domain.service;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;

public interface CharacterClientService {

  PaginatedCharacters getAllCharacters(int page);

  PaginatedCharacters searchCharacters(String searchTerm, int page);

  Character getCharacterById(Long characterId);
}
