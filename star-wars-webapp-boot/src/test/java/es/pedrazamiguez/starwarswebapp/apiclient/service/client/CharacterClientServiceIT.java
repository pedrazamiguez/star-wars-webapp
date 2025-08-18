package es.pedrazamiguez.starwarswebapp.apiclient.service.client;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("it")
class CharacterClientServiceIT {

  @Autowired
  private CharacterClientServiceImpl characterClientServiceImpl;

  @Test
  void givenCharacterId_whenGetCharacterById_thenReturnCharacter() {
    // Given
    final Long characterId = 1L;

    // When
    final Character character = this.characterClientServiceImpl.getCharacterById(characterId);

    // Then
    assertThat(character).isNotNull()
        .hasFieldOrPropertyWithValue("characterId", characterId)
        .hasFieldOrPropertyWithValue("name", "Luke Skywalker")
        .hasFieldOrPropertyWithValue("birthYear", "19BBY")
        .hasFieldOrPropertyWithValue("eyeColor", "blue")
        .hasFieldOrPropertyWithValue("gender", "male")
        .hasFieldOrPropertyWithValue("hairColor", "blond")
        .hasFieldOrPropertyWithValue("height", "172")
        .hasFieldOrPropertyWithValue("mass", "77")
        .hasFieldOrPropertyWithValue("skinColor", "fair");
  }

  @Test
  void givenInvalidCharacterId_whenGetCharacterById_thenReturnNull() {
    // Given
    final Long invalidCharacterId = 999L;

    // When
    final Character character = this.characterClientServiceImpl.getCharacterById(invalidCharacterId);

    // Then
    assertThat(character).isNull();
  }

  @Test
  void whenFetchAllCharacters_thenReturnAllCharacters() {
    // When
    final List<Character> characters = this.characterClientServiceImpl.fetchAllCharacters();

    // Then
    assertThat(characters).isNotEmpty()
        .allSatisfy(character -> assertThat(character).hasFieldOrProperty("characterId"))
        .hasSize(82);
  }

}
