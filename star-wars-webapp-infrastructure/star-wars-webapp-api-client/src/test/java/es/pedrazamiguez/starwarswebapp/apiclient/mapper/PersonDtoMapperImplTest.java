package es.pedrazamiguez.starwarswebapp.apiclient.mapper;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.PersonDto;
import es.pedrazamiguez.starwarswebapp.domain.model.Character;
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
class PersonDtoMapperImplTest {

  @InjectMocks
  private PersonDtoMapperImpl personDtoMapperImpl;

  @Test
  void givenPersonDto_whenMapToCharacter_thenReturnCharacterWithParsedId() {
    // Given
    final PersonDto personDto = Instancio.of(PersonDto.class)
        .set(field(PersonDto::getUrl), "https://swapi.dev/api/people/1/")
        .set(field(PersonDto::getCreated), LocalDateTime.now()
            .format(DateTimeFormatter.ISO_DATE_TIME))
        .set(field(PersonDto::getEdited), LocalDateTime.now()
            .format(DateTimeFormatter.ISO_DATE_TIME))
        .create();

    // When
    final Character character = this.personDtoMapperImpl.toCharacter(personDto);

    // Then
    assertThat(character).isNotNull()
        .hasFieldOrPropertyWithValue("characterId", 1L);
  }

}