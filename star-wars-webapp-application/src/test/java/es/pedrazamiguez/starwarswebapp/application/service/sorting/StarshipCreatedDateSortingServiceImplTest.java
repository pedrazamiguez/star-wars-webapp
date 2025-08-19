package es.pedrazamiguez.starwarswebapp.application.service.sorting;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
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
class StarshipCreatedDateSortingServiceImplTest {

  @InjectMocks
  private StarshipCreatedDateSortingServiceImpl starshipCreatedDateSortingServiceImpl;

  @Test
  void givenStarships_whenGetComparatorAsc_thenReturnComparator() {
    // Given
    final String sortDirection = "asc";
    final Starship st1 = Instancio.of(Starship.class)
        .set(field(Starship::getCreated), LocalDateTime.now())
        .create();
    final Starship st2 = Instancio.of(Starship.class)
        .set(field(Starship::getCreated), LocalDateTime.now()
            .plusHours(1))
        .create();

    // When
    final Comparator<Starship> comparator = this.starshipCreatedDateSortingServiceImpl.getComparator(sortDirection);
    final int result = comparator.compare(st1, st2);

    // Then
    assertThat(comparator).isNotNull();
    assertThat(result).isNegative();
    assertThat(st1.getCreated()).isBefore(st2.getCreated());
  }

  @Test
  void givenStarships_whenGetComparatorDesc_thenReturnComparator() {
    // Given
    final String sortDirection = "desc";
    final Starship st1 = Instancio.of(Starship.class)
        .set(field(Starship::getCreated), LocalDateTime.now())
        .create();
    final Starship st2 = Instancio.of(Starship.class)
        .set(field(Starship::getCreated), LocalDateTime.now()
            .plusHours(1))
        .create();

    // When
    final Comparator<Starship> comparator = this.starshipCreatedDateSortingServiceImpl.getComparator(sortDirection);
    final int result = comparator.compare(st1, st2);

    // Then
    assertThat(comparator).isNotNull();
    assertThat(result).isPositive();
    assertThat(st1.getCreated()).isBefore(st2.getCreated());
  }

  @Test
  void whenGetSortBy_thenReturnSortBy() {
    assertThat(this.starshipCreatedDateSortingServiceImpl.getSortBy()).isEqualTo("created");
  }

}