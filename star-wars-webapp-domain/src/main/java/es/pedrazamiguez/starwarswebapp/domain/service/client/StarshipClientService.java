package es.pedrazamiguez.starwarswebapp.domain.service.client;

import es.pedrazamiguez.starwarswebapp.domain.model.Starship;

import java.util.List;

public interface StarshipClientService {

  List<Starship> fetchAllStarships();

  Starship getStarshipById(Long starshipId);
}
