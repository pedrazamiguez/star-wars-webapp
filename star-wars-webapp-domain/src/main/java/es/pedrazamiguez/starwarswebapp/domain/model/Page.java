package es.pedrazamiguez.starwarswebapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
  private List<T> items;
  private int totalCount;
  private boolean hasNext;
  private boolean hasPrevious;

  public static <T> Page<T> empty() {
    return Page.<T>builder()
        .items(Collections.emptyList())
        .totalCount(0)
        .hasNext(false)
        .hasPrevious(false)
        .build();
  }
}
