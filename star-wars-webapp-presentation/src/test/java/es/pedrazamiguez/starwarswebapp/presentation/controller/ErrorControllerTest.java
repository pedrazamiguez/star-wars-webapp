package es.pedrazamiguez.starwarswebapp.presentation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ErrorControllerTest extends AbstractControllerTest {

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(new ErrorController())
        .setViewResolvers(this.getThymeleafViewResolver())
        .build();
  }

  @Test
  void givenErrorRequest_whenHandleError_thenReturnErrorView() throws Exception {
    this.mockMvc.perform(get("/error"))
        .andExpect(status().isOk())
        .andExpect(view().name("error"));
  }
}
