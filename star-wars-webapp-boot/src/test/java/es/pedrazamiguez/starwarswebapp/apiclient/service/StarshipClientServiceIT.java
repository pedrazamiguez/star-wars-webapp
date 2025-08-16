package es.pedrazamiguez.starwarswebapp.apiclient.service;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class StarshipClientServiceIT {

  @Autowired
  private StarshipClientServiceImpl starshipClientServiceImpl;

  @Test
  void givenPage_whenGetAllStarships_thenStarshipsReturned() {
    // GIVEN
    final int page = 1;

    // WHEN
    final List<Starship> starships = this.starshipClientServiceImpl.getAllStarships(page);

    // THEN
    assertFalse(starships.isEmpty(), "Starship list should not be empty");
    assertEquals(
        "CR90 corvette",
        starships.getFirst().getName(),
        "First starship should be CR90 corvette");
  }

  @Test
  void givenStarshipId_whenGetStarshipById_thenStarshipReturned() {
    // GIVEN
    final Long starshipId = 3L;

    // WHEN
    final Starship starship = this.starshipClientServiceImpl.getStarshipById(starshipId);

    // THEN
    assertEquals("Star Destroyer", starship.getName(), "Starship name should be Star Destroyer");
  }
  
}
