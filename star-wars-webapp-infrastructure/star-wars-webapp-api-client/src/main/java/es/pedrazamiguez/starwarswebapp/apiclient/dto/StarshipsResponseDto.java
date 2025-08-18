package es.pedrazamiguez.starwarswebapp.apiclient.dto;

import lombok.Data;

import java.util.List;

@Data
public class StarshipsResponseDto {
  private int count;
  private String next;
  private String previous;
  private List<StarshipDto> results;
}
