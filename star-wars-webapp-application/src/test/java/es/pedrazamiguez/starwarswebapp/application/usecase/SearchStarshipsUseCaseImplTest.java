package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.service.search.StarshipSearchService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SearchStarshipsUseCaseImplTest {

  @InjectMocks
  private SearchStarshipsUseCaseImpl searchStarshipsUseCaseImpl;

  @Mock
  private StarshipSearchService starshipSearchService;

  @Test
  void givenSearchParameters_whenSearchStarships_thenReturnPaginatedStarships() {
    // Given
    final String searchTerm = "Nubian yacht";
    final int page = 1;
    final String sortBy = "name";
    final String sortDirection = "asc";

    final Starship nubianYacht = Instancio.of(Starship.class)
        .set(field(Starship::getName), "H-type Nubian yacht")
        .create();

    final Page<Starship> expected = Page.<Starship>builder()
        .items(List.of(nubianYacht))
        .totalCount(1)
        .hasNext(false)
        .hasPrevious(false)
        .build();

    when(this.starshipSearchService.searchStarships(searchTerm.trim(), page, sortBy, sortDirection)).thenReturn(
        expected);

    // When
    final Page<Starship> actual =
        this.searchStarshipsUseCaseImpl.searchStarships(searchTerm, page, sortBy, sortDirection);

    // Then
    assertThat(actual).isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(expected);

    verify(this.starshipSearchService).searchStarships(searchTerm.trim(), page, sortBy, sortDirection);
    verifyNoMoreInteractions(this.starshipSearchService);
  }

}