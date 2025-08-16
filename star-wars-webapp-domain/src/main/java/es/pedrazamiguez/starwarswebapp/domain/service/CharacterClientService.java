package es.pedrazamiguez.starwarswebapp.domain.service;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import java.util.List;

public interface CharacterClientService {

  List<Character> getAllCharacters(int page);

  List<Character> searchCharacters(String searchTerm, int page);

  Character getCharacterById(Long characterId);

}
