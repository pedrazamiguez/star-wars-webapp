package es.pedrazamiguez.starwarswebapp.application.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.service.client.StarshipClientService;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.SortingService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StarshipSearchServiceImplTest {

  private StarshipSearchServiceImpl starshipSearchServiceImpl;

  @Mock
  private StarshipClientService starshipClientService;

  @Mock
  private SortingService<Starship> sortingService;

  @BeforeEach
  void setUp() {
    final int pageSize = 10;
    final List<SortingService<Starship>> sortingServices = Collections.singletonList(this.sortingService);
    this.starshipSearchServiceImpl = new StarshipSearchServiceImpl(pageSize, this.starshipClientService, sortingServices);
  }

  @Test
  void givenValidQueryAndSortParameters_whenSearchStarships_thenReturnPaginatedResults() {
    // Given
    final String query = "Wing";
    final int page = 1;
    final String sortBy = "name";
    final String sortDirection = "asc";
    final List<Starship> starships = Instancio.ofList(Starship.class)
        .size(8)
        .set(field(Starship::getName), "X-Wing")
        .create();

    when(this.starshipClientService.fetchAllStarships()).thenReturn(starships);

    // When
    final Page<Starship> result = this.starshipSearchServiceImpl.search(query, page, sortBy, sortDirection);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getItems()).hasSize(starships.size());
    assertThat(result.getTotalCount()).isEqualTo(starships.size());
    assertThat(result.isHasNext()).isFalse();
    assertThat(result.isHasPrevious()).isFalse();
  }

}
