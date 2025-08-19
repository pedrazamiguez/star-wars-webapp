package es.pedrazamiguez.starwarswebapp.presentation.controller;

import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.model.Starship;
import es.pedrazamiguez.starwarswebapp.domain.usecase.SearchStarshipsUseCase;
import es.pedrazamiguez.starwarswebapp.presentation.mapper.StarshipViewModelMapper;
import es.pedrazamiguez.starwarswebapp.presentation.view.StarshipViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class StarshipControllerTest extends AbstractControllerTest {

  @InjectMocks
  private StarshipController starshipController;

  @Mock
  private SearchStarshipsUseCase searchStarshipsUseCase;

  @Mock
  private StarshipViewModelMapper starshipViewModelMapper;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.starshipController)
        .setViewResolvers(this.getThymeleafViewResolver())
        .build();
  }

  @Test
  void givenValidRequest_whenSearchStarships_thenReturnStarshipsView() throws Exception {
    // Given
    final String query = "X-Wing";
    final int page = 1;
    final String sortBy = "name";
    final String sortDirection = "asc";

    final Page<Starship> paginatedStarships = Page.<Starship>builder()
        .items(Collections.singletonList(new Starship()))
        .totalCount(1)
        .hasNext(false)
        .hasPrevious(false)
        .build();

    final StarshipViewModel viewModel = new StarshipViewModel();

    when(this.searchStarshipsUseCase.searchStarships(query, page, sortBy, sortDirection)).thenReturn(paginatedStarships);
    when(this.starshipViewModelMapper.toViewModel(paginatedStarships, query, page, sortBy, sortDirection)).thenReturn(viewModel);

    // When & Then
    this.mockMvc.perform(get("/starships").param("query", query)
            .param("page", String.valueOf(page))
            .param("sortBy", sortBy)
            .param("sortDirection", sortDirection))
        .andExpect(status().isOk())
        .andExpect(view().name("starships"))
        .andExpect(model().attribute("viewModel", viewModel));
  }

  @Test
  void givenDefaultParameters_whenSearchStarships_thenReturnStarshipsView() throws Exception {
    // Given
    final Page<Starship> paginatedStarships = Page.empty();
    final StarshipViewModel viewModel = new StarshipViewModel();

    when(this.searchStarshipsUseCase.searchStarships("", 1, "", "")).thenReturn(paginatedStarships);
    when(this.starshipViewModelMapper.toViewModel(paginatedStarships, "", 1, "", "")).thenReturn(viewModel);

    // When & Then
    this.mockMvc.perform(get("/starships"))
        .andExpect(status().isOk())
        .andExpect(view().name("starships"))
        .andExpect(model().attribute("viewModel", viewModel));
  }

}