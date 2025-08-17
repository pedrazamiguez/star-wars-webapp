package es.pedrazamiguez.starwarswebapp.application.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.PaginatedCharacters;
import es.pedrazamiguez.starwarswebapp.domain.service.client.CharacterClientService;
import es.pedrazamiguez.starwarswebapp.domain.service.search.CharacterSearchService;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.CharacterSortingService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class CharacterSearchServiceImpl implements CharacterSearchService {

  private final int pageSize;

  private final CharacterClientService characterClientService;

  private final Map<String, CharacterSortingService> characterSortingServices;

  public CharacterSearchServiceImpl(
      @Value("${swapi.default-page-size}") final int defaultPageSize,
      final CharacterClientService characterClientService,
      final List<CharacterSortingService> characterSortingServices) {

    this.pageSize = defaultPageSize;
    this.characterClientService = characterClientService;
    this.characterSortingServices =
        characterSortingServices.stream()
            .collect(Collectors.toMap(CharacterSortingService::getSortBy, Function.identity()));
  }

  @Override
  public PaginatedCharacters searchCharacters(
      final String searchTerm, final int page, final String sortBy, final String sortDirection) {

    log.debug(
        "Searching characters for term '{}' on page {}, sortBy: {}, sortDirection: {}",
        searchTerm,
        page,
        sortBy,
        sortDirection);

    final List<Character> characters = this.characterClientService.fetchAllCharacters();
    final List<Character> filteredCharacters =
        characters.stream()
            .filter(
                character ->
                    searchTerm.isEmpty()
                        || character.getName().toLowerCase().contains(searchTerm.toLowerCase()))
            .collect(Collectors.toList());

    final CharacterSortingService characterSortingService =
        this.characterSortingServices.get(sortBy);

    if (!ObjectUtils.isEmpty(characterSortingService)) {

      final Comparator<Character> characterComparator =
          characterSortingService.getComparator(sortDirection);

      filteredCharacters.sort(characterComparator);
    }

    final int from = (page - 1) * this.pageSize;
    final int to = Math.min(from + this.pageSize, filteredCharacters.size());

    final List<Character> pageSlice =
        (from < filteredCharacters.size())
            ? filteredCharacters.subList(from, to)
            : Collections.emptyList();

    final boolean hasNext = to < filteredCharacters.size();
    final boolean hasPrevious = page > 1;

    return PaginatedCharacters.builder()
        .characters(pageSlice)
        .totalCount(filteredCharacters.size())
        .hasNext(hasNext)
        .hasPrevious(hasPrevious)
        .build();
  }
}
