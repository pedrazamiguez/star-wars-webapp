package es.pedrazamiguez.starwarswebapp.domain.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;

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
