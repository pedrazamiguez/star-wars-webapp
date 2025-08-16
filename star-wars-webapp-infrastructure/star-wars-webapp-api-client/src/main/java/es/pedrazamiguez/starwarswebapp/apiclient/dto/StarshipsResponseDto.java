package es.pedrazamiguez.starwarswebapp.apiclient.dto;

import java.util.List;
import lombok.Data;

@Data
public class StarshipsResponseDto {
  private int count;
  private String next;
  private String previous;
  private List<StarshipDto> results;
}
