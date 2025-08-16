package es.pedrazamiguez.starwarswebapp.apiclient.service;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.PeopleResponseDto;
import es.pedrazamiguez.starwarswebapp.apiclient.dto.PersonDto;
import es.pedrazamiguez.starwarswebapp.apiclient.mapper.PersonDtoMapper;
import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;
import es.pedrazamiguez.starwarswebapp.domain.service.CharacterClientService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class CharacterClientServiceImpl implements CharacterClientService {

  private final RestClient restClient;

  private final PersonDtoMapper personDtoMapper;

  public CharacterClientServiceImpl(
      @Value("${swapi.base-url}") final String baseUrl,
      final RestClient.Builder restClientBuilder,
      final PersonDtoMapper personDtoMapper) {

    this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    this.personDtoMapper = personDtoMapper;
  }

  @Override
  public PaginatedCharacters getAllCharacters(final int page) {
    log.info("Fetching characters for page {}", page);
    try {
      final String endpointUrl = String.format("/people?page=%d", page);
      final PeopleResponseDto response =
          this.restClient.get().uri(endpointUrl).retrieve().body(PeopleResponseDto.class);

      return Optional.ofNullable(response)
          .map(this.personDtoMapper::toPaginatedCharacters)
          .orElse(PaginatedCharacters.empty());
    } catch (final Exception e) {
      log.error("Failed to fetch characters for page {}: {}", page, e.getMessage(), e);
      return PaginatedCharacters.empty();
    }
  }

  @Override
  public PaginatedCharacters searchCharacters(final String searchTerm, final int page) {
    log.info("Searching characters for term '{}' on page {}", searchTerm, page);
    try {
      final String endpointUrl = String.format("/people?search=%s&page=%d", searchTerm, page);
      final PeopleResponseDto response =
          this.restClient.get().uri(endpointUrl).retrieve().body(PeopleResponseDto.class);

      return Optional.ofNullable(response)
          .map(this.personDtoMapper::toPaginatedCharacters)
          .orElse(PaginatedCharacters.empty());
    } catch (final Exception e) {
      log.error(
          "Failed to search characters for term '{}' on page {}: {}",
          searchTerm,
          page,
          e.getMessage(),
          e);
      return PaginatedCharacters.empty();
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
