package es.pedrazamiguez.starwarswebapp.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class PaginatedStarships {
  private List<Starship> starships;
  private int totalCount;
  private boolean hasNext;
  private boolean hasPrevious;

  public static PaginatedStarships empty() {
    return PaginatedStarships.builder()
        .starships(Collections.emptyList())
        .totalCount(0)
        .hasNext(false)
        .hasPrevious(false)
        .build();
  }
}
