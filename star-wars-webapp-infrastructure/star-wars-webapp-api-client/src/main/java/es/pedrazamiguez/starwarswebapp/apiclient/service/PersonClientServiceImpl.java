package es.pedrazamiguez.starwarswebapp.apiclient.service;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.PeopleResponseDto;
import es.pedrazamiguez.starwarswebapp.apiclient.dto.PersonDto;
import es.pedrazamiguez.starwarswebapp.apiclient.mapper.PersonDtoMapper;
import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.service.PersonClientService;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class PersonClientServiceImpl implements PersonClientService {

  private final RestClient restClient;

  private final PersonDtoMapper personDtoMapper;

  public PersonClientServiceImpl(
      @Value("${swapi.base-url}") final String baseUrl,
      final RestClient.Builder restClientBuilder,
      final PersonDtoMapper personDtoMapper) {

    this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    this.personDtoMapper = personDtoMapper;
  }

  @Override
  public List<Character> getAllCharacters(final int page) {
    log.info("Fetching characters for page {}", page);
    try {
      final String endpointUrl = String.format("/people?page=%d", page);
      final PeopleResponseDto response =
          this.restClient.get().uri(endpointUrl).retrieve().body(PeopleResponseDto.class);

      if (!ObjectUtils.isEmpty(response) && !ObjectUtils.isEmpty(response.getResults())) {
        return response.getResults().stream().map(this.personDtoMapper::toCharacter).toList();
      }

      log.warn("No results found for page {}", page);
      return Collections.emptyList();
    } catch (final Exception e) {
      log.error("Failed to fetch characters for page {}: {}", page, e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<Character> searchCharacters(final String searchTerm, final int page) {
    log.info("Searching characters for term '{}' on page {}", searchTerm, page);
    try {
      final String endpointUrl = String.format("/people?search=%s&page=%d", searchTerm, page);
      final PeopleResponseDto response =
          this.restClient.get().uri(endpointUrl).retrieve().body(PeopleResponseDto.class);

      if (!ObjectUtils.isEmpty(response) && !ObjectUtils.isEmpty(response.getResults())) {
        return response.getResults().stream().map(this.personDtoMapper::toCharacter).toList();
      }

      log.warn("No results found for search term '{}' on page {}", searchTerm, page);
      return Collections.emptyList();
    } catch (final Exception e) {
      log.error(
          "Failed to search characters for term '{}' on page {}: {}",
          searchTerm,
          page,
          e.getMessage(),
          e);
      return Collections.emptyList();
    }
  }

  @Override
  public Character getCharacterById(final Long characterId) {
    log.info("Fetching character with ID {}", characterId);
    try {
      final String endpointUrl = String.format("/people/%d", characterId);
      final PersonDto response =
          this.restClient.get().uri(endpointUrl).retrieve().body(PersonDto.class);

      if (!ObjectUtils.isEmpty(response)) {
        return this.personDtoMapper.toCharacter(response);
      }

      log.warn("No character found with ID {}", characterId);
      return null;
    } catch (final Exception e) {
      log.error("Failed to fetch character with ID {}: {}", characterId, e.getMessage(), e);
      return null;
    }
  }
}
