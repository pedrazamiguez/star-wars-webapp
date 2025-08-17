package es.pedrazamiguez.starwarswebapp.presentation.mapper;

import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;
import es.pedrazamiguez.starwarswebapp.presentation.view.CharacterViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CharacterViewModelMapper {

  @Mapping(target = "characters", source = "paginatedCharacters.characters")
  @Mapping(target = "totalCount", source = "paginatedCharacters.totalCount")
  @Mapping(target = "hasNextPage", source = "paginatedCharacters.hasNext")
  @Mapping(target = "hasPreviousPage", source = "paginatedCharacters.hasPrevious")
  @Mapping(target = "query", source = "query")
  @Mapping(target = "currentPage", source = "page")
  CharacterViewModel toViewModel(PaginatedCharacters paginatedCharacters, String query, int page);
}
