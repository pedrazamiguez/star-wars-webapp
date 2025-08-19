package es.pedrazamiguez.starwarswebapp.apiclient.service.client;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.PeopleResponseDto;
import es.pedrazamiguez.starwarswebapp.apiclient.dto.PersonDto;
import es.pedrazamiguez.starwarswebapp.apiclient.mapper.PersonDtoMapper;
import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterClientServiceImplTest {

  private CharacterClientServiceImpl characterClientServiceImpl;

  @Mock
  private RestClient restClient;

  @Mock
  private RestClient.Builder restClientBuilder;

  @Mock
  private PersonDtoMapper personDtoMapper;

  @Mock
  private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

  @Mock
  private RestClient.RequestHeadersSpec requestHeadersSpec;

  @Mock
  private RestClient.ResponseSpec responseSpec;

  @BeforeEach
  void setUp() {
    when(this.restClientBuilder.baseUrl(any(String.class))).thenReturn(this.restClientBuilder);
    when(this.restClientBuilder.build()).thenReturn(this.restClient);
    when(this.restClient.get()).thenReturn(this.requestHeadersUriSpec);
    when(this.requestHeadersUriSpec.uri(any(String.class))).thenReturn(this.requestHeadersSpec);
    when(this.requestHeadersSpec.retrieve()).thenReturn(this.responseSpec);

    this.characterClientServiceImpl = new CharacterClientServiceImpl("https://swapi.dev/api", this.restClientBuilder, this.personDtoMapper);
  }

  @Test
  void givenValidCharacterId_whenGetCharacterById_thenReturnCharacter() {
    // Given
    final Long characterId = 1L;
    final PersonDto personDto = Instancio.of(PersonDto.class)
        .set(field(PersonDto::getName), "Luke Skywalker")
        .create();
    final Character expectedCharacter = Instancio.of(Character.class)
        .set(field(Character::getName), "Luke Skywalker")
        .create();

    when(this.responseSpec.body(PersonDto.class)).thenReturn(personDto);
    when(this.personDtoMapper.toCharacter(personDto)).thenReturn(expectedCharacter);

    // When
    final Character result = this.characterClientServiceImpl.getCharacterById(characterId);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("Luke Skywalker");
  }

  @Test
  void givenInvalidCharacterId_whenGetCharacterById_thenReturnNull() {
    // Given
    final Long characterId = 999L;
    when(this.responseSpec.body(PersonDto.class)).thenThrow(new RuntimeException("API error"));

    // When
    final Character result = this.characterClientServiceImpl.getCharacterById(characterId);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void givenValidPage_whenGetPaginatedCharacters_thenReturnPaginatedResults() {
    // Given
    final int page = 1;
    final PeopleResponseDto responseDto = Instancio.create(PeopleResponseDto.class);
    final Page<Character> expectedPage = Page.<Character>builder()
        .items(Instancio.ofList(Character.class)
            .size(5)
            .create())
        .totalCount(5)
        .hasNext(false)
        .hasPrevious(false)
        .build();

    when(this.responseSpec.body(PeopleResponseDto.class)).thenReturn(responseDto);
    when(this.personDtoMapper.toPaginatedCharacters(responseDto)).thenReturn(expectedPage);

    // When
    final Page<Character> result = this.characterClientServiceImpl.getPaginatedCharacters(page);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getItems()).hasSize(5);
    assertThat(result.isHasNext()).isFalse();
  }

  @Test
  void givenInvalidPage_whenGetPaginatedCharacters_thenReturnEmptyPage() {
    // Given
    final int page = 1;
    when(this.responseSpec.body(PeopleResponseDto.class)).thenThrow(new RuntimeException("API error"));

    // When
    final Page<Character> result = this.characterClientServiceImpl.getPaginatedCharacters(page);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getItems()).isEmpty();
    assertThat(result.isHasNext()).isFalse();
    assertThat(result.isHasPrevious()).isFalse();
  }

  @Test
  void givenMultiplePages_whenFetchAllCharacters_thenReturnAllCharacters() {
    // Given
    final Page<Character> page1 = Page.<Character>builder()
        .items(Instancio.ofList(Character.class)
            .size(5)
            .create())
        .totalCount(5)
        .hasNext(true)
        .hasPrevious(false)
        .build();
    final Page<Character> page2 = Page.<Character>builder()
        .items(Instancio.ofList(Character.class)
            .size(3)
            .create())
        .totalCount(3)
        .hasNext(false)
        .hasPrevious(true)
        .build();
    final PeopleResponseDto responseDto1 = Instancio.create(PeopleResponseDto.class);
    final PeopleResponseDto responseDto2 = Instancio.create(PeopleResponseDto.class);

    when(this.responseSpec.body(PeopleResponseDto.class)).thenReturn(responseDto1)
        .thenReturn(responseDto2);
    when(this.personDtoMapper.toPaginatedCharacters(responseDto1)).thenReturn(page1);
    when(this.personDtoMapper.toPaginatedCharacters(responseDto2)).thenReturn(page2);

    // When
    final List<Character> result = this.characterClientServiceImpl.fetchAllCharacters();

    // Then
    assertThat(result).isNotNull()
        .hasSize(8);
  }

  @Test
  void givenNoPagesAvailable_whenFetchAllCharacters_thenReturnEmptyList() {
    // Given
    final Page<Character> emptyPage = Page.empty();
    final PeopleResponseDto responseDto = Instancio.create(PeopleResponseDto.class);

    when(this.responseSpec.body(PeopleResponseDto.class)).thenReturn(responseDto);
    when(this.personDtoMapper.toPaginatedCharacters(responseDto)).thenReturn(emptyPage);

    // When
    final List<Character> result = this.characterClientServiceImpl.fetchAllCharacters();

    // Then
    assertThat(result).isNotNull()
        .isEmpty();
  }
}
