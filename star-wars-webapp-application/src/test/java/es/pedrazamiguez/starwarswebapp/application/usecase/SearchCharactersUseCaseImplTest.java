package es.pedrazamiguez.starwarswebapp.application.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.service.search.SearchService;
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
class SearchCharactersUseCaseImplTest {

  @InjectMocks
  private SearchCharactersUseCaseImpl searchCharactersUseCaseImpl;

  @Mock
  private SearchService<Character> characterSearchService;

  @Test
  void givenSearchParameters_whenSearchCharacters_thenReturnPaginatedCharacters() {
    // Given
    final String searchTerm = "Luke";
    final int page = 1;
    final String sortBy = "name";
    final String sortDirection = "asc";

    final Character lukeSkywalker = Instancio.of(Character.class)
        .set(field(Character::getName), "Luke Skywalker")
        .create();

    final Page<Character> expected = Page.<Character>builder()
        .items(List.of(lukeSkywalker))
        .totalCount(1)
        .hasNext(false)
        .hasPrevious(false)
        .build();

    when(this.characterSearchService.search(searchTerm.trim(), page, sortBy, sortDirection)).thenReturn(expected);

    // When
    final Page<Character> actual =
        this.searchCharactersUseCaseImpl.searchCharacters(searchTerm, page, sortBy, sortDirection);

    // Then
    assertThat(actual).isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(expected);

    verify(this.characterSearchService).search(searchTerm.trim(), page, sortBy, sortDirection);
    verifyNoMoreInteractions(this.characterSearchService);
  }
}
