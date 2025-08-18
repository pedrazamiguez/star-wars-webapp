package es.pedrazamiguez.starwarswebapp.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

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
