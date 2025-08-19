package es.pedrazamiguez.starwarswebapp.presentation.controller;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import es.pedrazamiguez.starwarswebapp.domain.model.Page;
import es.pedrazamiguez.starwarswebapp.domain.usecase.SearchCharactersUseCase;
import es.pedrazamiguez.starwarswebapp.presentation.mapper.CharacterViewModelMapper;
import es.pedrazamiguez.starwarswebapp.presentation.view.CharacterViewModel;
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
class CharacterControllerTest extends AbstractControllerTest {

  @InjectMocks
  private CharacterController characterController;

  @Mock
  private SearchCharactersUseCase searchCharactersUseCase;

  @Mock
  private CharacterViewModelMapper characterViewModelMapper;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.characterController)
        .setViewResolvers(this.getThymeleafViewResolver())
        .build();
  }

  @Test
  void givenValidRequest_whenSearchCharacters_thenReturnCharactersView() throws Exception {
    // Given
    final String query = "Luke Skywalker";
    final int page = 1;
    final String sortBy = "name";
    final String sortDirection = "asc";

    final Page<Character> paginatedCharacters = Page.<Character>builder()
        .items(Collections.singletonList(new Character()))
        .totalCount(1)
        .hasNext(false)
        .hasPrevious(false)
        .build();

    final CharacterViewModel viewModel = new CharacterViewModel();

    when(this.searchCharactersUseCase.searchCharacters(query, page, sortBy, sortDirection)).thenReturn(paginatedCharacters);
    when(this.characterViewModelMapper.toViewModel(paginatedCharacters, query, page, sortBy, sortDirection)).thenReturn(viewModel);

    // When & Then
    this.mockMvc.perform(get("/characters").param("query", query)
            .param("page", String.valueOf(page))
            .param("sortBy", sortBy)
            .param("sortDirection", sortDirection))
        .andExpect(status().isOk())
        .andExpect(view().name("characters"))
        .andExpect(model().attribute("viewModel", viewModel));
  }

  @Test
  void givenDefaultParameters_whenSearchCharacters_thenReturnCharactersView() throws Exception {
    // Given
    final Page<Character> paginatedCharacters = Page.empty();
    final CharacterViewModel viewModel = new CharacterViewModel();

    when(this.searchCharactersUseCase.searchCharacters("", 1, "", "")).thenReturn(paginatedCharacters);
    when(this.characterViewModelMapper.toViewModel(paginatedCharacters, "", 1, "", "")).thenReturn(viewModel);

    // When & Then
    this.mockMvc.perform(get("/characters"))
        .andExpect(status().isOk())
        .andExpect(view().name("characters"))
        .andExpect(model().attribute("viewModel", viewModel));
  }

}