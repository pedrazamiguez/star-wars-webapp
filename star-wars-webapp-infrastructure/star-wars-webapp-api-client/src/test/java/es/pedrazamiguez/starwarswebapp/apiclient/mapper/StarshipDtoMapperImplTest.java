package es.pedrazamiguez.starwarswebapp.apiclient.mapper;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.StarshipDto;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ExtendWith(MockitoExtension.class)
class StarshipDtoMapperImplTest {

  @InjectMocks
  private StarshipDtoMapperImpl starshipDtoMapperImpl;

  @Test
  void givenStarshipDto_whenMapToStarship_thenReturnStarshipWithParsedId() {
    // Given
    final StarshipDto starshipDto = Instancio.of(StarshipDto.class)
        .set(field(StarshipDto::getUrl), "https://swapi.dev/api/starships/2/")
        .set(field(StarshipDto::getCreated), LocalDateTime.now()
            .format(DateTimeFormatter.ISO_DATE_TIME))
        .set(field(StarshipDto::getEdited), LocalDateTime.now()
            .format(DateTimeFormatter.ISO_DATE_TIME))
        .create();

    // When
    final Starship starship = this.starshipDtoMapperImpl.toStarship(starshipDto);

    // Then
    assertThat(starship).isNotNull()
        .hasFieldOrPropertyWithValue("starshipId", 2L);
  }

}