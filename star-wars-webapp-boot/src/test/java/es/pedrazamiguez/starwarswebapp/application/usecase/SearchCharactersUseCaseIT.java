package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("it")
class SearchCharactersUseCaseIT {

  @Value("${swapi.default-page-size}")
  private int pageSize;

  @Autowired
  private SearchCharactersUseCaseImpl searchCharactersUseCaseImpl;

  @Test
  void givenValidSearchCriteria_whenSearchCharacters_thenReturnMatchingCharacters() {
    // Given
    final String searchTerm = "sky";
    final int page = 1;
    final String sortBy = "name";
    final String sortDirection = "asc";

    // When
    final Page<Character> result = this.searchCharactersUseCaseImpl.searchCharacters(searchTerm, page, sortBy, sortDirection);

    // Then
    assertThat(result).isNotNull()
        .hasFieldOrProperty("items")
        .isNotNull()
        .hasFieldOrPropertyWithValue("totalCount", 3)
        .hasFieldOrPropertyWithValue("hasNext", false)
        .hasFieldOrPropertyWithValue("hasPrevious", false);

    assertThat(result.getItems()).isNotEmpty()
        .hasSize(3)
        .allSatisfy(character -> {
          assertThat(character).isNotNull();
          assertThat(character.getName()).containsIgnoringCase("sky");
        });
  }

  @Test
  void givenNotValidSearchCriteria_whenSearchCharacters_thenReturnPageOffset() {
    // Given
    final String searchTerm = "anakin";
    final int page = 2;
    final String sortBy = "name";
    final String sortDirection = "asc";

    // When
    final Page<Character> result = this.searchCharactersUseCaseImpl.searchCharacters(searchTerm, page, sortBy, sortDirection);

    // Then
    assertThat(result).isNotNull()
        .hasFieldOrProperty("items")
        .isNotNull()
        .hasFieldOrPropertyWithValue("totalCount", 1)
        .hasFieldOrPropertyWithValue("hasNext", false)
        .hasFieldOrPropertyWithValue("hasPrevious", true);

    assertThat(result.getItems()).isEmpty();
  }

  @Test
  void givenNoSearchCriteria_whenSearchCharacters_thenAllCharactersReturned() {
    // Given
    final String searchTerm = "";
    final int page = 1;
    final String sortBy = "created";
    final String sortDirection = "desc";

    // When
    final Page<Character> result = this.searchCharactersUseCaseImpl.searchCharacters(searchTerm, page, sortBy, sortDirection);

    // Then
    assertThat(result).isNotNull()
        .hasFieldOrProperty("items")
        .isNotNull()
        .hasFieldOrPropertyWithValue("totalCount", 82)
        .hasFieldOrPropertyWithValue("hasNext", true)
        .hasFieldOrPropertyWithValue("hasPrevious", false);

    assertThat(result.getItems()).isNotEmpty()
        .hasSize(this.pageSize);

    assertThat(result.getItems()
        .getFirst()).isNotNull()
        .hasFieldOrPropertyWithValue("characterId", 83L)
        .hasFieldOrPropertyWithValue("name", "Tion Medon")
        .hasFieldOrPropertyWithValue("birthYear", "unknown")
        .hasFieldOrPropertyWithValue("eyeColor", "black")
        .hasFieldOrPropertyWithValue("gender", "male")
        .hasFieldOrPropertyWithValue("hairColor", "none")
        .hasFieldOrPropertyWithValue("height", "206")
        .hasFieldOrPropertyWithValue("mass", "80")
        .hasFieldOrPropertyWithValue("skinColor", "grey");
  }
}
