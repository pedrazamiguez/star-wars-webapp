package es.pedrazamiguez.starwarswebapp.apiclient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CharacterClientServiceIT {

  @Autowired private CharacterClientServiceImpl characterClientServiceImpl;

  @Test
  void givenPage_whenGetAllCharacters_thenCharactersReturned() {
    // GIVEN
    final int page = 1;

    // WHEN
    final PaginatedCharacters paginatedCharacters =
        this.characterClientServiceImpl.getAllCharacters(page);

    // THEN
    assertTrue(paginatedCharacters.getTotalCount() > 0, "Total count should be greater than 0");
    assertFalse(
        paginatedCharacters.getCharacters().isEmpty(), "Character list should not be empty");
    assertEquals(
        "Luke Skywalker",
        paginatedCharacters.getCharacters().getFirst().getName(),
        "First character should be Luke Skywalker");
  }

  @Test
  void givenSearchTermAndPage_whenSearchCharacters_thenCharactersReturned() {
    // GIVEN
    final String searchTerm = "Anakin";
    final int page = 1;

    // WHEN
    final PaginatedCharacters paginatedCharacters =
        this.characterClientServiceImpl.searchCharacters(searchTerm, page);

    // THEN
    assertTrue(paginatedCharacters.getTotalCount() > 0, "Total count should be greater than 0");
    assertFalse(
        paginatedCharacters.getCharacters().isEmpty(), "Character list should not be empty");
    final List<Character> characters = paginatedCharacters.getCharacters();
    assertTrue(
        characters.stream().anyMatch(c -> c.getName().contains(searchTerm)),
        "At least one character should match the search term");
    assertEquals(
        "Anakin Skywalker",
        characters.getFirst().getName(),
        "First character should be Anakin Skywalker");
  }

  @Test
  void givenCharacterId_whenGetCharacterById_thenCharacterReturned() {
    // GIVEN
    final Long characterId = 1L;

    // WHEN
    final Character character = this.characterClientServiceImpl.getCharacterById(characterId);

    // THEN
    assertEquals("Luke Skywalker", character.getName(), "Character name should be Luke Skywalker");
  }
}
