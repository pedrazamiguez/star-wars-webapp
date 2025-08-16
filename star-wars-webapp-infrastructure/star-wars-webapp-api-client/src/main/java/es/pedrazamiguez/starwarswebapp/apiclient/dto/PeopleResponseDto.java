package es.pedrazamiguez.starwarswebapp.apiclient.dto;

import java.util.List;
import lombok.Data;

@Data
public class PeopleResponseDto {
  private int count;
  private String next;
  private String previous;
  private List<PersonDto> results;
}
