package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ExtendWith(MockitoExtension.class)
class StarshipNameSortingServiceImplTest {

  @InjectMocks
  private StarshipNameSortingServiceImpl starshipNameSortingServiceImpl;

  @Test
  void givenStarships_whenGetComparatorAsc_thenReturnComparator() {
    // Given
    final String sortDirection = "asc";
    final Starship st1 = Instancio.of(Starship.class)
        .set(field(Starship::getName), "A-wing")
        .create();
    final Starship st2 = Instancio.of(Starship.class)
        .set(field(Starship::getName), "Y-wing")
        .create();

    // When
    final Comparator<Starship> comparator = this.starshipNameSortingServiceImpl.getComparator(sortDirection);
    final int result = comparator.compare(st1, st2);

    // Then
    assertThat(comparator).isNotNull();
    assertThat(result).isNegative();
    assertThat(st1.getName()).isLessThan(st2.getName());
  }

  @Test
  void givenStarships_whenGetComparatorDesc_thenReturnComparator() {
    // Given
    final String sortDirection = "desc";
    final Starship st1 = Instancio.of(Starship.class)
        .set(field(Starship::getName), "A-wing")
        .create();
    final Starship st2 = Instancio.of(Starship.class)
        .set(field(Starship::getName), "Y-wing")
        .create();

    // When
    final Comparator<Starship> comparator = this.starshipNameSortingServiceImpl.getComparator(sortDirection);
    final int result = comparator.compare(st1, st2);

    // Then
    assertThat(comparator).isNotNull();
    assertThat(result).isPositive();
    assertThat(st1.getName()).isLessThan(st2.getName());
  }

  @Test
  void whenGetSortBy_thenReturnSortBy() {
    assertThat(this.starshipNameSortingServiceImpl.getSortBy()).isEqualTo("name");
  }
}