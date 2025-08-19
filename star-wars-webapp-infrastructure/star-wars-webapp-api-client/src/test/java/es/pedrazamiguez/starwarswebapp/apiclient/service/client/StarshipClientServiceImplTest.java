package es.pedrazamiguez.starwarswebapp.apiclient.service.client;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.StarshipDto;
import es.pedrazamiguez.starwarswebapp.apiclient.dto.StarshipsResponseDto;
import es.pedrazamiguez.starwarswebapp.apiclient.mapper.StarshipDtoMapper;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
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
class StarshipClientServiceImplTest {

  private StarshipClientServiceImpl starshipClientServiceImpl;

  @Mock
  private RestClient restClient;

  @Mock
  private RestClient.Builder restClientBuilder;

  @Mock
  private StarshipDtoMapper starshipDtoMapper;

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

    this.starshipClientServiceImpl = new StarshipClientServiceImpl("https://swapi.dev/api", this.restClientBuilder, this.starshipDtoMapper);
  }

  @Test
  void givenValidStarshipId_whenGetStarshipById_thenReturnStarship() {
    // Given
    final Long starshipId = 1L;
    final StarshipDto starshipDto = Instancio.of(StarshipDto.class)
        .set(field(StarshipDto::getName), "X-Wing")
        .create();
    final Starship expectedStarship = Instancio.of(Starship.class)
        .set(field(Starship::getName), "X-Wing")
        .create();

    when(this.responseSpec.body(StarshipDto.class)).thenReturn(starshipDto);
    when(this.starshipDtoMapper.toStarship(starshipDto)).thenReturn(expectedStarship);

    // When
    final Starship result = this.starshipClientServiceImpl.getStarshipById(starshipId);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("X-Wing");
  }

  @Test
  void givenInvalidStarshipId_whenGetStarshipById_thenReturnNull() {
    // Given
    final Long starshipId = 999L;
    when(this.responseSpec.body(StarshipDto.class)).thenThrow(new RuntimeException("API error"));

    // When
    final Starship result = this.starshipClientServiceImpl.getStarshipById(starshipId);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void givenValidPage_whenGetPaginatedStarships_thenReturnPaginatedResults() {
    // Given
    final int page = 1;
    final StarshipsResponseDto responseDto = Instancio.create(StarshipsResponseDto.class);
    final Page<Starship> expectedPage = Page.<Starship>builder()
        .items(Instancio.ofList(Starship.class)
            .size(5)
            .create())
        .totalCount(5)
        .hasNext(false)
        .hasPrevious(false)
        .build();

    when(this.responseSpec.body(StarshipsResponseDto.class)).thenReturn(responseDto);
    when(this.starshipDtoMapper.toPaginatedStarships(responseDto)).thenReturn(expectedPage);

    // When
    final Page<Starship> result = this.starshipClientServiceImpl.getPaginatedStarships(page);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getItems()).hasSize(5);
    assertThat(result.isHasNext()).isFalse();
  }

  @Test
  void givenInvalidPage_whenGetPaginatedStarships_thenReturnEmptyPage() {
    // Given
    final int page = 1;
    when(this.responseSpec.body(StarshipsResponseDto.class)).thenThrow(new RuntimeException("API error"));

    // When
    final Page<Starship> result = this.starshipClientServiceImpl.getPaginatedStarships(page);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getItems()).isEmpty();
    assertThat(result.isHasNext()).isFalse();
    assertThat(result.isHasPrevious()).isFalse();
  }

  @Test
  void givenMultiplePages_whenFetchAllStarships_thenReturnAllStarships() {
    // Given
    final Page<Starship> page1 = Page.<Starship>builder()
        .items(Instancio.ofList(Starship.class)
            .size(5)
            .create())
        .totalCount(5)
        .hasNext(true)
        .hasPrevious(false)
        .build();
    final Page<Starship> page2 = Page.<Starship>builder()
        .items(Instancio.ofList(Starship.class)
            .size(3)
            .create())
        .totalCount(3)
        .hasNext(false)
        .hasPrevious(true)
        .build();
    final StarshipsResponseDto responseDto1 = Instancio.create(StarshipsResponseDto.class);
    final StarshipsResponseDto responseDto2 = Instancio.create(StarshipsResponseDto.class);

    when(this.responseSpec.body(StarshipsResponseDto.class)).thenReturn(responseDto1)
        .thenReturn(responseDto2);
    when(this.starshipDtoMapper.toPaginatedStarships(responseDto1)).thenReturn(page1);
    when(this.starshipDtoMapper.toPaginatedStarships(responseDto2)).thenReturn(page2);

    // When
    final List<Starship> result = this.starshipClientServiceImpl.fetchAllStarships();

    // Then
    assertThat(result).isNotNull()
        .hasSize(8);
  }

  @Test
  void givenNoPagesAvailable_whenFetchAllStarships_thenReturnEmptyList() {
    // Given
    final Page<Starship> emptyPage = Page.empty();
    final StarshipsResponseDto responseDto = Instancio.create(StarshipsResponseDto.class);

    when(this.responseSpec.body(StarshipsResponseDto.class)).thenReturn(responseDto);
    when(this.starshipDtoMapper.toPaginatedStarships(responseDto)).thenReturn(emptyPage);

    // When
    final List<Starship> result = this.starshipClientServiceImpl.fetchAllStarships();

    // Then
    assertThat(result).isNotNull()
        .isEmpty();
  }
}