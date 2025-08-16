package es.pedrazamiguez.starwarswebapp.apiclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class StarshipDto {
  private String name;
  private String model;

  @JsonProperty("starship_class")
  private String starshipClass;

  private String manufacturer;

  @JsonProperty("cost_in_credits")
  private String costInCredits;

  private String length;
  private String crew;
  private String passengers;

  @JsonProperty("max_atmosphering_speed")
  private String maxAtmospheringSpeed;

  @JsonProperty("hyperdrive_rating")
  private String hyperdriveRating;

  @JsonProperty("MGLT")
  private String mglt;

  @JsonProperty("cargo_capacity")
  private String cargoCapacity;

  private String consumables;
  private List<String> films;
  private List<String> pilots;
  private String url;
  private String created;
  private String edited;
}
