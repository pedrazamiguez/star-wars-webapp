package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ExtendWith(MockitoExtension.class)
class CharacterNameSortingServiceImplTest {

  @InjectMocks
  private CharacterNameSortingServiceImpl characterNameSortingServiceImpl;

  @Test
  void givenCharacters_whenGetComparatorAsc_thenReturnComparator() {
    // Given
    final String sortDirection = "asc";
    final Character ch1 = Instancio.of(Character.class)
        .set(field(Character::getName), "A" + Instancio.create(String.class))
        .create();
    final Character ch2 = Instancio.of(Character.class)
        .set(field(Character::getName), "Z" + Instancio.create(String.class))
        .create();

    // When
    final Comparator<Character> comparator = this.characterNameSortingServiceImpl.getComparator(sortDirection);
    final int result = comparator.compare(ch1, ch2);

    // Then
    assertThat(comparator).isNotNull();
    assertThat(result).isNegative();
    assertThat(ch1.getName()).isLessThan(ch2.getName());
  }

  @Test
  void givenCharacters_whenGetComparatorDesc_thenReturnComparator() {
    // Given
    final String sortDirection = "desc";
    final Character ch1 = Instancio.of(Character.class)
        .set(field(Character::getName), "A" + Instancio.create(String.class))
        .create();
    final Character ch2 = Instancio.of(Character.class)
        .set(field(Character::getName), "Z" + Instancio.create(String.class))
        .create();

    // When
    final Comparator<Character> comparator = this.characterNameSortingServiceImpl.getComparator(sortDirection);
    final int result = comparator.compare(ch1, ch2);

    // Then
    assertThat(comparator).isNotNull();
    assertThat(result).isPositive();
    assertThat(ch1.getName()).isLessThan(ch2.getName());
  }

  @Test
  void whenGetSortBy_thenReturnSortBy() {
    assertThat(this.characterNameSortingServiceImpl.getSortBy()).isEqualTo("name");
  }

}