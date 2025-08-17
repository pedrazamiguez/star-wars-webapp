package es.pedrazamiguez.starwarswebapp.presentation.mapper;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedStarships;
import es.pedrazamiguez.starwarswebapp.presentation.view.StarshipViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StarshipViewModelMapper {

  @Mapping(target = "starships", source = "paginatedStarships.starships")
  @Mapping(target = "totalCount", source = "paginatedStarships.totalCount")
  @Mapping(target = "hasNextPage", source = "paginatedStarships.hasNext")
  @Mapping(target = "hasPreviousPage", source = "paginatedStarships.hasPrevious")
  @Mapping(target = "query", source = "query")
  @Mapping(target = "currentPage", source = "page")
  StarshipViewModel toViewModel(
      PaginatedStarships paginatedStarships,
      String query,
      int page,
      String sortBy,
      String sortDirection);
}
