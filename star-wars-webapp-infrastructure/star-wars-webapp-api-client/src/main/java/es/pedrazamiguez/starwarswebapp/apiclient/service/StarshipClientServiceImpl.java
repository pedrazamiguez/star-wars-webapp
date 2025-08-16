package es.pedrazamiguez.starwarswebapp.apiclient.service;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.StarshipDto;
import es.pedrazamiguez.starwarswebapp.apiclient.dto.StarshipsResponseDto;
import es.pedrazamiguez.starwarswebapp.apiclient.mapper.StarshipDtoMapper;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.service.StarshipClientService;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class StarshipClientServiceImpl implements StarshipClientService {

  private final RestClient restClient;

  private final StarshipDtoMapper starshipDtoMapper;

  public StarshipClientServiceImpl(
      @Value("${swapi.base-url}") final String baseUrl,
      final RestClient.Builder restClientBuilder,
      final StarshipDtoMapper starshipDtoMapper) {

    this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    this.starshipDtoMapper = starshipDtoMapper;
  }

  @Override
  public List<Starship> getAllStarships(final int page) {
    log.info("Fetching starships for page {}", page);
    try {
      final String endpointUrl = String.format("/starships/?page=%d", page);
      final StarshipsResponseDto response =
          this.restClient.get().uri(endpointUrl).retrieve().body(StarshipsResponseDto.class);

      if (!ObjectUtils.isEmpty(response) && !ObjectUtils.isEmpty(response.getResults())) {
        return response.getResults().stream().map(this.starshipDtoMapper::toStarship).toList();
      }

      log.warn("No results found for page {}", page);
      return Collections.emptyList();
    } catch (final Exception e) {
      log.error("Failed to fetch starships for page {}: {}", page, e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  @Override
  public Starship getStarshipById(final Long starshipId) {
    log.info("Fetching starship with ID {}", starshipId);
    try {
      final String endpointUrl = String.format("/starships/%d", starshipId);
      final StarshipDto response =
          this.restClient.get().uri(endpointUrl).retrieve().body(StarshipDto.class);

      if (!ObjectUtils.isEmpty(response)) {
        return this.starshipDtoMapper.toStarship(response);
      }

      log.warn("No starship found with ID {}", starshipId);
      return null;
    } catch (final Exception e) {
      log.error("Failed to fetch starship with ID {}: {}", starshipId, e.getMessage(), e);
      return null;
    }
  }
}
