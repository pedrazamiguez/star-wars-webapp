package es.pedrazamiguez.starwarswebapp.domain.service;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import java.util.List;

public interface StarshipClientService {

  List<Starship> getAllStarships(int page);

  Starship getStarshipById(Long starshipId);
}
