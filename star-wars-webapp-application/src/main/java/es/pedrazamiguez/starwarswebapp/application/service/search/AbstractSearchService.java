package es.pedrazamiguez.starwarswebapp.application.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractSearchService<T> implements SearchService<T> {

  private final int pageSize;

  @Override
  public Page<T> search(final String searchTerm, final int page, final String sortBy, final String sortDirection) {
    log.debug("Searching for term '{}' on page {}, sortBy: {}, sortDirection: {}", searchTerm, page, sortBy,
        sortDirection);

    final List<T> allItems = this.fetchAllItems();
    final List<T> filteredItems = allItems.stream()
        .filter(item -> searchTerm.isEmpty() || this.qualifiesForSearch(item, searchTerm.toLowerCase()))
        .sorted(this.getComparator(sortBy, sortDirection))
        .toList();

    final int from = (page - 1) * this.pageSize;
    final int to = Math.min(from + this.pageSize, filteredItems.size());

    final List<T> pageSlice = (from < filteredItems.size()) ? filteredItems.subList(from, to) : Collections.emptyList();

    final boolean hasNext = to < filteredItems.size();
    final boolean hasPrevious = page > 1;

    return Page.<T>builder()
        .items(pageSlice)
        .totalCount(filteredItems.size())
        .hasNext(hasNext)
        .hasPrevious(hasPrevious)
        .build();
  }

  protected abstract List<T> fetchAllItems();

  protected abstract boolean qualifiesForSearch(T item, String searchTerm);

  protected abstract Comparator<T> getComparator(String sortBy, String sortDirection);
}
