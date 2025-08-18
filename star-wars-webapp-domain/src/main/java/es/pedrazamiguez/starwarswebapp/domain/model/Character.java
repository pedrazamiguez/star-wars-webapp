package es.pedrazamiguez.starwarswebapp.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Character {
  private Long characterId;
  private String name;
  private String birthYear;
  private String eyeColor;
  private String gender;
  private String hairColor;
  private String height;
  private String mass;
  private String skinColor;
  private LocalDateTime created;
  private LocalDateTime edited;
}
