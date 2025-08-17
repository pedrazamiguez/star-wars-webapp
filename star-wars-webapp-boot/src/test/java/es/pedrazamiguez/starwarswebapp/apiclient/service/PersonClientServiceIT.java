package es.pedrazamiguez.starwarswebapp.apiclient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import es.pedrazamiguez.starwarswebapp.apiclient.service.client.CharacterClientServiceImpl;
import es.pedrazamiguez.starwarswebapp.domain.model.Character;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CharacterClientServiceIT {

  @Autowired private CharacterClientServiceImpl characterClientServiceImpl;

  @Test
  void givenCharacterId_whenGetCharacterById_thenCharacterReturned() {
    // GIVEN
    final Long characterId = 1L;

    // WHEN
    final Character character = this.characterClientServiceImpl.getCharacterById(characterId);

    // THEN
    assertEquals("Luke Skywalker", character.getName(), "Character name should be Luke Skywalker");
  }
}
