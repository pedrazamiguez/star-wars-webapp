package es.pedrazamiguez.starwarswebapp.apiclient.service.client;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.StarshipDto;
import es.pedrazamiguez.starwarswebapp.apiclient.dto.StarshipsResponseDto;
import es.pedrazamiguez.starwarswebapp.apiclient.mapper.StarshipDtoMapper;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.service.client.StarshipClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StarshipClientServiceImpl implements StarshipClientService {

  private final RestClient restClient;

  private final StarshipDtoMapper starshipDtoMapper;

  public StarshipClientServiceImpl(@Value("${swapi.base-url}") final String baseUrl,
                                   final RestClient.Builder restClientBuilder,
                                   final StarshipDtoMapper starshipDtoMapper) {

    this.restClient = restClientBuilder.baseUrl(baseUrl)
        .build();
    this.starshipDtoMapper = starshipDtoMapper;
  }

  @Override
  @Cacheable("allStarships")
  public List<Starship> fetchAllStarships() {
    log.info("Fetching all starships from SWAPI");
    final List<Starship> allStarships = new ArrayList<>();

    int page = 1;
    Page<Starship> paginatedStarships;

    do {
      paginatedStarships = this.getPaginatedStarships(page);
      if (!ObjectUtils.isEmpty(paginatedStarships) && !ObjectUtils.isEmpty(paginatedStarships.getItems())) {
        allStarships.addAll(paginatedStarships.getItems());
        page++;
      } else {
        break;
      }
    } while (paginatedStarships.isHasNext());

    return allStarships;
  }

  @Override
  public Starship getStarshipById(final Long starshipId) {
    log.info("Fetching starship with ID {}", starshipId);

    try {
      final String endpointUrl = String.format("/starships/%d", starshipId);
      final StarshipDto response = this.restClient.get()
          .uri(endpointUrl)
          .retrieve()
          .body(StarshipDto.class);

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

  private Page<Starship> getPaginatedStarships(final int page) {
    log.info("Fetching starships on page {}", page);
    try {
      final String endpointUrl = String.format("/starships?page=%d", page);
      final StarshipsResponseDto response = this.restClient.get()
          .uri(endpointUrl)
          .retrieve()
          .body(StarshipsResponseDto.class);

      return Optional.ofNullable(response)
          .map(this.starshipDtoMapper::toPaginatedStarships)
          .orElse(Page.empty());
    } catch (final Exception e) {
      log.error("Failed to fetch starships on page {}: {}", page, e.getMessage(), e);
      return Page.empty();
    }
  }
}
