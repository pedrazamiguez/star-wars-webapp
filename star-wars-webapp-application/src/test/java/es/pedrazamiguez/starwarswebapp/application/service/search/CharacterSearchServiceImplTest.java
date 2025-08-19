package es.pedrazamiguez.starwarswebapp.application.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.service.client.CharacterClientService;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.SortingService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterSearchServiceImplTest {

  private CharacterSearchServiceImpl characterSearchServiceImpl;

  @Mock
  private CharacterClientService characterClientService;

  @Mock
  private SortingService<Character> sortingService;

  @BeforeEach
  void setUp() {
    final int pageSize = 10;
    final List<SortingService<Character>> sortingServices = Collections.singletonList(this.sortingService);
    this.characterSearchServiceImpl = new CharacterSearchServiceImpl(pageSize, this.characterClientService, sortingServices);
  }

  @Test
  void givenValidQueryAndSortParameters_whenSearchCharacters_thenReturnPaginatedResults() {
    // Given
    final String query = "Luke";
    final int page = 1;
    final String sortBy = "name";
    final String sortDirection = "asc";
    final List<Character> characters = Instancio.ofList(Character.class)
        .size(8)
        .set(field(Character::getName), "Luke Skywalker")
        .create();

    when(this.characterClientService.fetchAllCharacters()).thenReturn(characters);

    // When
    final Page<Character> result = this.characterSearchServiceImpl.search(query, page, sortBy, sortDirection);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getItems()).hasSize(characters.size());
    assertThat(result.getTotalCount()).isEqualTo(characters.size());
    assertThat(result.isHasNext()).isFalse();
    assertThat(result.isHasPrevious()).isFalse();
  }

  @Test
  void givenValidQueryAndSortParameters_whenSearchCharacters_thenReturnMixedPaginatedResults() {
    // Given
    final String query = "sky";
    final int page = 1;
    final String sortBy = "name";
    final String sortDirection = "asc";
    final List<Character> characters = Arrays.asList(
        Instancio.of(Character.class)
            .set(field(Character::getName), "Luke Skywalker")
            .create(),
        Instancio.of(Character.class)
            .set(field(Character::getName), "Shmi Skywalker")
            .create(),
        Instancio.create(Character.class),
        Instancio.create(Character.class)
    );

    when(this.characterClientService.fetchAllCharacters()).thenReturn(characters);

    // When
    final Page<Character> result = this.characterSearchServiceImpl.search(query, page, sortBy, sortDirection);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getItems()).hasSize(2);
    assertThat(result.getTotalCount()).isEqualTo(2);
    assertThat(result.isHasNext()).isFalse();
    assertThat(result.isHasPrevious()).isFalse();
  }

}