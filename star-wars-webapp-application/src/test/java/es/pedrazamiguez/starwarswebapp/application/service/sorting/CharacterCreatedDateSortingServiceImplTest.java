package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ExtendWith(MockitoExtension.class)
class CharacterCreatedDateSortingServiceImplTest {

  @InjectMocks
  private CharacterCreatedDateSortingServiceImpl characterCreatedDateSortingServiceImpl;

  @Test
  void givenCharacters_whenGetComparatorAsc_thenReturnComparator() {
    // Given
    final String sortDirection = "asc";
    final Character ch1 = Instancio.of(Character.class)
        .set(field(Character::getCreated), LocalDateTime.of(2025, 8, 18, 10, 0))
        .create();
    final Character ch2 = Instancio.of(Character.class)
        .set(field(Character::getCreated), LocalDateTime.of(2023, 8, 18, 11, 0))
        .create();

    // When
    final Comparator<Character> comparator = this.characterCreatedDateSortingServiceImpl.getComparator(sortDirection);
    final int result = comparator.compare(ch1, ch2);

    // Then
    assertThat(comparator).isNotNull();
    assertThat(result).isPositive();
  }

  @Test
  void givenCharacters_whenGetComparatorDesc_thenReturnComparator() {
    // Given
    final String sortDirection = "desc";
    final Character ch1 = Instancio.of(Character.class)
        .set(field(Character::getCreated), LocalDateTime.of(2025, 8, 18, 10, 0))
        .create();
    final Character ch2 = Instancio.of(Character.class)
        .set(field(Character::getCreated), LocalDateTime.of(2023, 8, 18, 11, 0))
        .create();

    // When
    final Comparator<Character> comparator = this.characterCreatedDateSortingServiceImpl.getComparator(sortDirection);
    final int result = comparator.compare(ch1, ch2);

    // Then
    assertThat(comparator).isNotNull();
    assertThat(result).isNegative();
  }

  @Test
  void whenGetSortBy_thenReturnSortBy() {
    assertThat(this.characterCreatedDateSortingServiceImpl.getSortBy()).isEqualTo("created");
  }

}