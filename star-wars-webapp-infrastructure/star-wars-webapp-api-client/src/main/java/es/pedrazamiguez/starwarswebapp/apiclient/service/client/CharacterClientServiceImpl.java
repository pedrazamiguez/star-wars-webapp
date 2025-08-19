package es.pedrazamiguez.starwarswebapp.apiclient.service.client;

import es.pedrazamiguez.starwarswebapp.apiclient.dto.PeopleResponseDto;
import es.pedrazamiguez.starwarswebapp.apiclient.dto.PersonDto;
import es.pedrazamiguez.starwarswebapp.apiclient.mapper.PersonDtoMapper;
import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.service.client.CharacterClientService;
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
public class CharacterClientServiceImpl implements CharacterClientService {

  private final RestClient restClient;

  private final PersonDtoMapper personDtoMapper;

  public CharacterClientServiceImpl(@Value("${swapi.base-url}") final String baseUrl,
                                    final RestClient.Builder restClientBuilder, final PersonDtoMapper personDtoMapper) {

    this.restClient = restClientBuilder.baseUrl(baseUrl)
        .build();
    this.personDtoMapper = personDtoMapper;
  }

  @Override
  @Cacheable("allCharacters")
  public List<Character> fetchAllCharacters() {
    log.info("Fetching all characters from SWAPI");
    final List<Character> allCharacters = new ArrayList<>();

    int page = 1;
    Page<Character> paginatedCharacters;

    do {
      paginatedCharacters = this.getPaginatedCharacters(page);
      if (!ObjectUtils.isEmpty(paginatedCharacters) && !ObjectUtils.isEmpty(paginatedCharacters.getItems())) {
        allCharacters.addAll(paginatedCharacters.getItems());
        page++;
      } else {
        break;
      }
    } while (paginatedCharacters.isHasNext());

    return allCharacters;
  }

  @Override
  public Character getCharacterById(final Long characterId) {
    log.info("Fetching character with ID {}", characterId);

    try {
      final String endpointUrl = String.format("/people/%d", characterId);
      final PersonDto response = this.restClient.get()
          .uri(endpointUrl)
          .retrieve()
          .body(PersonDto.class);

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

  private Page<Character> getPaginatedCharacters(final int page) {
    log.info("Fetching characters on page {}", page);
    try {
      final String endpointUrl = String.format("/people?page=%d", page);
      final PeopleResponseDto response = this.restClient.get()
          .uri(endpointUrl)
          .retrieve()
          .body(PeopleResponseDto.class);

      return Optional.ofNullable(response)
          .map(this.personDtoMapper::toPaginatedCharacters)
          .orElse(Page.empty());
    } catch (final Exception e) {
      log.error("Failed to fetch characters on page {}: {}", page, e.getMessage(), e);
      return Page.empty();
    }
  }
}
