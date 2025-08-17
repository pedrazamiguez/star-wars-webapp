package es.pedrazamiguez.starwarswebapp.presentation.view;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import java.util.List;
import lombok.Data;

@Data
public class StarshipViewModel {
  private List<Starship> starships;
  private String query;
  private int totalCount;
  private int currentPage;
  private boolean hasNextPage;
  private boolean hasPreviousPage;
  private String sortBy;
  private String sortDirection;
}
