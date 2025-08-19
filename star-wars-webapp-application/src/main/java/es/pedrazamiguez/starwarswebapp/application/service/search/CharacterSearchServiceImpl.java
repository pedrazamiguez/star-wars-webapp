package es.pedrazamiguez.starwarswebapp.application.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.service.client.CharacterClientService;
import es.pedrazamiguez.starwarswebapp.domain.service.sorting.SortingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CharacterSearchServiceImpl extends AbstractSearchService<Character> {

  private final CharacterClientService characterClientService;

  private final Map<String, SortingService<Character>> characterSortingServices;

  public CharacterSearchServiceImpl(@Value("${swapi.default-page-size}") final int pageSize,
                                    final CharacterClientService characterClientService,
                                    final List<SortingService<Character>> characterSortingServices) {
    super(pageSize);

    this.characterClientService = characterClientService;
    this.characterSortingServices = characterSortingServices.stream()
        .collect(Collectors.toMap(SortingService<Character>::getSortBy, Function.identity()));
  }

  @Override
  protected List<Character> fetchAllItems() {
    return this.characterClientService.fetchAllCharacters();
  }

  @Override
  protected boolean qualifiesForSearch(final Character item, final String searchTerm) {
    return item.getName()
        .toLowerCase()
        .contains(searchTerm);
  }

  @Override
  protected Comparator<Character> getComparator(final String sortBy, final String sortDirection) {
    final SortingService<Character> characterSortingService = this.characterSortingServices.get(sortBy);

    if (!ObjectUtils.isEmpty(characterSortingService)) {
      return characterSortingService.getComparator(sortDirection);
    }

    return Comparator.comparing(Character::getCharacterId);
  }

}
