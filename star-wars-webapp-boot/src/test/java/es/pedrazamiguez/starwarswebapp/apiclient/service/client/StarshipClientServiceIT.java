package es.pedrazamiguez.starwarswebapp.apiclient.service.client;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("it")
class StarshipClientServiceIT {

  @Autowired
  private StarshipClientServiceImpl starshipClientServiceImpl;

  @Test
  void givenStarshipId_whenGetStarshipById_thenReturnStarship() {
    // Given
    final Long starshipId = 12L;

    // When
    final Starship starship = this.starshipClientServiceImpl.getStarshipById(starshipId);

    // Then
    assertThat(starship).isNotNull()
        .hasFieldOrPropertyWithValue("starshipId", starshipId)
        .hasFieldOrPropertyWithValue("name", "X-wing")
        .hasFieldOrPropertyWithValue("model", "T-65 X-wing")
        .hasFieldOrPropertyWithValue("type", "Starfighter")
        .hasFieldOrPropertyWithValue("manufacturer", "Incom Corporation")
        .hasFieldOrPropertyWithValue("price", "149999")
        .hasFieldOrPropertyWithValue("length", "12.5")
        .hasFieldOrPropertyWithValue("crew", "1")
        .hasFieldOrPropertyWithValue("passengers", "0")
        .hasFieldOrPropertyWithValue("maxAtmospheringSpeed", "1050")
        .hasFieldOrPropertyWithValue("hyperdriveRating", "1.0")
        .hasFieldOrPropertyWithValue("mglt", "100")
        .hasFieldOrPropertyWithValue("cargoCapacity", "110")
        .hasFieldOrPropertyWithValue("consumables", "1 week");
  }

  @Test
  void givenInvalidStarshipId_whenGetStarshipById_thenReturnNull() {
    // Given
    final Long invalidStarshipId = 999L;

    // When
    final Starship starship = this.starshipClientServiceImpl.getStarshipById(invalidStarshipId);

    // Then
    assertThat(starship).isNull();
  }

  @Test
  void whenFetchAllStarships_thenReturnAllStarships() {
    // When
    final List<Starship> starships = this.starshipClientServiceImpl.fetchAllStarships();

    // Then
    assertThat(starships).isNotEmpty()
        .allSatisfy(starship -> assertThat(starship).hasFieldOrProperty("starshipId"))
        .hasSize(36);
  }

}
