package es.pedrazamiguez.starwarswebapp.presentation.view;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import java.util.List;
import lombok.Data;

@Data
public class CharacterViewModel {
  private List<Character> characters;
  private String query;
  private Long totalCount;
  private int currentPage;
  private boolean hasNextPage;
  private boolean hasPreviousPage;
  private String sortBy;
  private String sortDirection;
}
