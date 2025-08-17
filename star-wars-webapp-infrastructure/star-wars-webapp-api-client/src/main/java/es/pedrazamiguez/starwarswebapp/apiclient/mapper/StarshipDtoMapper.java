package es.pedrazamiguez.starwarswebapp.apiclient.mapper;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.StarshipDto;
import es.pedrazamiguez.starwarswebapp.apiclient.dto.StarshipsResponseDto;
import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedStarships;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    imports = {LocalDateTime.class, DateTimeFormatter.class})
public interface StarshipDtoMapper {

  String STARSHIP_ID_REGEX = ".*starships/(\\d+)/?.*";
  String STARSHIP_ID_REPLACEMENT = "$1";

  @Mapping(
      target = "starshipId",
      expression =
          "java(Long.parseLong(starshipDto.getUrl().replaceAll(STARSHIP_ID_REGEX, STARSHIP_ID_REPLACEMENT)))")
  @Mapping(target = "type", source = "starshipClass")
  @Mapping(target = "price", source = "costInCredits")
  @Mapping(
      target = "created",
      expression =
          "java( LocalDateTime.parse(starshipDto.getCreated(), DateTimeFormatter.ISO_DATE_TIME) )")
  @Mapping(
      target = "edited",
      expression =
          "java( LocalDateTime.parse(starshipDto.getEdited(), DateTimeFormatter.ISO_DATE_TIME) )")
  Starship toStarship(StarshipDto starshipDto);

  @Mapping(target = "totalCount", source = "count")
  @Mapping(target = "hasNext", expression = "java( null != starshipsResponseDto.getNext() )")
  @Mapping(
      target = "hasPrevious",
      expression = "java( null != starshipsResponseDto.getPrevious() )")
  @Mapping(target = "starships", source = "results")
  PaginatedStarships toPaginatedStarships(StarshipsResponseDto starshipsResponseDto);
}
