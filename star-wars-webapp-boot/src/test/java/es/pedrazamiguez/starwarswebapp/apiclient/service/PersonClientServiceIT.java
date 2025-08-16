package es.pedrazamiguez.starwarswebapp.apiclient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonClientServiceIT {

  @Autowired private PersonClientServiceImpl personClientServiceImpl;

  @Test
  void givenPage_whenGetAllCharacters_thenCharactersReturned() {
    // GIVEN
    final int page = 1;

    // WHEN
    final List<Character> characters = this.personClientServiceImpl.getAllCharacters(page);

    // THEN
    assertFalse(characters.isEmpty(), "Character list should not be empty");
    assertEquals(
        "Luke Skywalker",
        characters.getFirst().getName(),
        "First character should be Luke Skywalker");
  }

  @Test
  void givenCharacterId_whenGetCharacterById_thenCharacterReturned() {
    // GIVEN
    final Long characterId = 1L;

    // WHEN
    final Character character = this.personClientServiceImpl.getCharacterById(characterId);

    // THEN
    assertEquals("Luke Skywalker", character.getName(), "Character name should be Luke Skywalker");
  }
}
