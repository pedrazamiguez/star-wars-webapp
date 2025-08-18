package es.pedrazamiguez.starwarswebapp.apiclient.mapper;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.PeopleResponseDto;
import es.pedrazamiguez.starwarswebapp.apiclient.dto.PersonDto;
import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    imports = {LocalDateTime.class, DateTimeFormatter.class})
public interface PersonDtoMapper {

  String PEOPLE_ID_REGEX = ".*people/(\\d+)/?.*";
  String PEOPLE_ID_REPLACEMENT = "$1";

  @Mapping(
      target = "characterId",
      expression =
          "java(Long.parseLong(personDto.getUrl().replaceAll(PEOPLE_ID_REGEX, PEOPLE_ID_REPLACEMENT)))")
  @Mapping(
      target = "created",
      expression =
          "java( LocalDateTime.parse(personDto.getCreated(), DateTimeFormatter.ISO_DATE_TIME) )")
  @Mapping(
      target = "edited",
      expression =
          "java( LocalDateTime.parse(personDto.getEdited(), DateTimeFormatter.ISO_DATE_TIME) )")
  Character toCharacter(PersonDto personDto);

  @Mapping(target = "totalCount", source = "count")
  @Mapping(target = "hasNext", expression = "java( null != peopleResponseDto.getNext() )")
  @Mapping(target = "hasPrevious", expression = "java( null != peopleResponseDto.getPrevious() )")
  @Mapping(target = "characters", source = "results")
  PaginatedCharacters toPaginatedCharacters(PeopleResponseDto peopleResponseDto);
}
