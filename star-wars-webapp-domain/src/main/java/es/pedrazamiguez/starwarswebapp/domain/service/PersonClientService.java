package es.pedrazamiguez.starwarswebapp.domain.service;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;

import java.util.List;

public interface PersonClientService {

  List<Character> getAllCharacters(int page);

  Character getCharacterById(Long characterId);

}
