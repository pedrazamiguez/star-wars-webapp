package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("it")
class SearchStarshipsUseCaseIT {

  @Value("${swapi.default-page-size}")
  private int pageSize;

  @Autowired
  private SearchStarshipsUseCaseImpl searchStarshipsUseCaseImpl;

  @Test
  void givenValidSearchCriteria_whenSearchStarships_thenReturnMatchingStarships() {
    // Given
    final String searchTerm = "falcon";
    final int page = 1;
    final String sortBy = "name";
    final String sortDirection = "asc";

    // When
    final Page<Starship> result = this.searchStarshipsUseCaseImpl.searchStarships(searchTerm, page, sortBy, sortDirection);

    // Then
    assertThat(result).isNotNull()
        .hasFieldOrProperty("items")
        .isNotNull()
        .hasFieldOrPropertyWithValue("totalCount", 1)
        .hasFieldOrPropertyWithValue("hasNext", false)
        .hasFieldOrPropertyWithValue("hasPrevious", false);

    assertThat(result.getItems()).isNotEmpty()
        .hasSize(1)
        .allSatisfy(starship -> {
          assertThat(starship).isNotNull();
          assertThat(starship.getName()).containsIgnoringCase("falcon");
        });
  }

  @Test
  void givenNotValidSearchCriteria_whenSearchStarships_thenReturnPageOffset() {
    // Given
    final String searchTerm = "x-wing";
    final int page = 2;
    final String sortBy = "name";
    final String sortDirection = "asc";

    // When
    final Page<Starship> result = this.searchStarshipsUseCaseImpl.searchStarships(searchTerm, page, sortBy, sortDirection);

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
  void givenNoSearchCriteria_whenSearchStarships_thenAllStarshipsReturned() {
    // Given
    final String searchTerm = "";
    final int page = 1;
    final String sortBy = "created";
    final String sortDirection = "desc";

    // When
    final Page<Starship> result = this.searchStarshipsUseCaseImpl.searchStarships(searchTerm, page, sortBy, sortDirection);

    // Then
    assertThat(result).isNotNull()
        .hasFieldOrProperty("items")
        .isNotNull()
        .hasFieldOrPropertyWithValue("totalCount", 36)
        .hasFieldOrPropertyWithValue("hasNext", true)
        .hasFieldOrPropertyWithValue("hasPrevious", false);

    assertThat(result.getItems()).isNotEmpty()
        .hasSize(this.pageSize);

    assertThat(result.getItems()
        .getFirst()).isNotNull()
        .hasFieldOrPropertyWithValue("starshipId", 75L)
        .hasFieldOrPropertyWithValue("name", "V-wing")
        .hasFieldOrPropertyWithValue("model", "Alpha-3 Nimbus-class V-wing starfighter")
        .hasFieldOrPropertyWithValue("type", "starfighter")
        .hasFieldOrPropertyWithValue("length", "7.9")
        .hasFieldOrPropertyWithValue("crew", "1")
        .hasFieldOrPropertyWithValue("passengers", "0");
  }
}
