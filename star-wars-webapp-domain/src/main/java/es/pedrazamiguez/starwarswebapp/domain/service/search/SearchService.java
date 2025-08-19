package es.pedrazamiguez.starwarswebapp.domain.service.search;

import es.pedrazamiguez.starwarswebapp.domain.model.Page;

public interface SearchService<T> {

  Page<T> search(String searchTerm, int page, String sortBy, String sortDirection);
}
