package es.pedrazamiguez.starwarswebapp.domain.service.client;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;

import java.util.List;

public interface CharacterClientService {

  List<Character> fetchAllCharacters();

  Character getCharacterById(Long characterId);
}
