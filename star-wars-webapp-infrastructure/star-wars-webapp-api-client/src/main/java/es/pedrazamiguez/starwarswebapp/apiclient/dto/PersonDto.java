package es.pedrazamiguez.starwarswebapp.apiclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PersonDto {
  private String name;

  @JsonProperty("birth_year")
  private String birthYear;

  @JsonProperty("eye_color")
  private String eyeColor;

  private String gender;

  @JsonProperty("hair_color")
  private String hairColor;

  private String height;
  private String mass;

  @JsonProperty("skin_color")
  private String skinColor;

  private String homeworld;
  private List<String> films;
  private List<String> species;
  private List<String> starships;
  private List<String> vehicles;
  private String url;
  private String created;
  private String edited;
}
