package es.pedrazamiguez.starwarswebapp.presentation.view;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import lombok.Data;

import java.util.List;

@Data
public class CharacterViewModel {
  private List<Character> characters;
  private String query;
  private int totalCount;
  private int currentPage;
  private boolean hasNextPage;
  private boolean hasPreviousPage;
  private String sortBy;
  private String sortDirection;
}
