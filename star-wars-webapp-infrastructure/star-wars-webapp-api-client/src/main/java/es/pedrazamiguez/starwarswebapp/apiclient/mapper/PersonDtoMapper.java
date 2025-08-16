package es.pedrazamiguez.starwarswebapp.apiclient.mapper;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.PersonDto;
import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    imports = {LocalDateTime.class, DateTimeFormatter.class})
public interface PersonDtoMapper {

  @Mapping(
      target = "created",
      expression =
          "java( LocalDateTime.parse(personDto.getCreated(), DateTimeFormatter.ISO_DATE_TIME) )")
  @Mapping(
      target = "edited",
      expression =
          "java( LocalDateTime.parse(personDto.getEdited(), DateTimeFormatter.ISO_DATE_TIME) )")
  Character toCharacter(PersonDto personDto);
}
