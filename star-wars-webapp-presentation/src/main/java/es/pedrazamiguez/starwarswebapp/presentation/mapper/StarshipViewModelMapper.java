package es.pedrazamiguez.starwarswebapp.presentation.mapper;

import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.presentation.view.StarshipViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StarshipViewModelMapper {

  @Mapping(target = "starships", source = "paginatedStarships.items")
  @Mapping(target = "totalCount", source = "paginatedStarships.totalCount")
  @Mapping(target = "hasNextPage", source = "paginatedStarships.hasNext")
  @Mapping(target = "hasPreviousPage", source = "paginatedStarships.hasPrevious")
  @Mapping(target = "query", source = "query")
  @Mapping(target = "currentPage", source = "page")
  StarshipViewModel toViewModel(Page<Starship> paginatedStarships, String query, int page, String sortBy,
                                String sortDirection);
}
