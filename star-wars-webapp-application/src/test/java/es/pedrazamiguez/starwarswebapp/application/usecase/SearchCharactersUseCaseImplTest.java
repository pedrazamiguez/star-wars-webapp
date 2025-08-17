package es.pedrazamiguez.starwarswebapp.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;
import es.pedrazamiguez.starwarswebapp.domain.service.search.CharacterSearchService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchCharactersUseCaseImplTest {

  @InjectMocks private SearchCharactersUseCaseImpl searchCharactersUseCaseImpl;

  @Mock private CharacterSearchService characterSearchService;

  @Test
  void givenSearchParameters_whenSearchCharacters_thenReturnPaginatedCharacters() {
    // Given
    final String searchTerm = "Luke";
    final int page = 1;
    final String sortBy = "name";
    final String sortDirection = "asc";
    final PaginatedCharacters paginatedCharacters =
        PaginatedCharacters.builder()
            .characters(Instancio.ofList(Character.class).size(1).create())
            .totalCount(1)
            .hasNext(false)
            .hasPrevious(false)
            .build();

    when(this.characterSearchService.searchCharacters(
            searchTerm.trim(), page, sortBy, sortDirection))
        .thenReturn(paginatedCharacters);

    // When
    final PaginatedCharacters result =
        this.searchCharactersUseCaseImpl.searchCharacters(searchTerm, page, sortBy, sortDirection);

    // Then
    assertNotNull(result);
    assertEquals(paginatedCharacters.getCharacters().size(), result.getCharacters().size());
    assertEquals(paginatedCharacters.getTotalCount(), result.getTotalCount());
    assertEquals(paginatedCharacters.isHasNext(), result.isHasNext());
    assertEquals(paginatedCharacters.isHasPrevious(), result.isHasPrevious());
    assertEquals(
        paginatedCharacters.getCharacters().get(0).getName(),
        result.getCharacters().get(0).getName());

    verify(this.characterSearchService, times(1))
        .searchCharacters(searchTerm.trim(), page, sortBy, sortDirection);
    verifyNoMoreInteractions(this.characterSearchService);
  }
}
