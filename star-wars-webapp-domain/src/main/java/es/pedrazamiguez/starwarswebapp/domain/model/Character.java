package es.pedrazamiguez.starwarswebapp.domain.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Character {
  private String name;
  private String birthYear;
  private String eyeColor;
  private String gender;
  private String hairColor;
  private Integer height;
  private String mass;
  private String skinColor;
  private LocalDateTime created;
  private LocalDateTime edited;
}
