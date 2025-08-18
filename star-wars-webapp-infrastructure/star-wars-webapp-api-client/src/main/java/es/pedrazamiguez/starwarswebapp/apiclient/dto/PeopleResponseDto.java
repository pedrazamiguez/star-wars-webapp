package es.pedrazamiguez.starwarswebapp.apiclient.dto;

import lombok.Data;

import java.util.List;

@Data
public class PeopleResponseDto {
  private int count;
  private String next;
  private String previous;
  private List<PersonDto> results;
}
