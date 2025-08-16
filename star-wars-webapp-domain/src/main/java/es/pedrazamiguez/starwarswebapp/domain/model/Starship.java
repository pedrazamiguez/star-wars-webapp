package es.pedrazamiguez.starwarswebapp.domain.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Starship {
  private String name;
  private String model;
  private String type;
  private String manufacturer;
  private String price;
  private String length;
  private String crew;
  private String passengers;
  private String maxAtmospheringSpeed;
  private String hyperdriveRating;
  private String mglt;
  private String cargoCapacity;
  private String consumables;
  private LocalDateTime created;
  private LocalDateTime edited;
}
