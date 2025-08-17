package es.pedrazamiguez.starwarswebapp.domain.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginatedCharacters {
  private List<Character> characters;
  private int totalCount;
  private boolean hasNext;
  private boolean hasPrevious;

  public static PaginatedCharacters empty() {
    return PaginatedCharacters.builder()
        .characters(Collections.emptyList())
        .totalCount(0)
        .hasNext(false)
        .hasPrevious(false)
        .build();
  }
}
