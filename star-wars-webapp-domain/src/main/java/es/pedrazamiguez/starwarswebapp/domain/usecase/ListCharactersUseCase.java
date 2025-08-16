package es.pedrazamiguez.starwarswebapp.domain.usecase;

import es.pedrazamiguez.starwarswebapp.domain.model.Character;

import java.util.List;

@FunctionalInterface
public interface ListCharactersUseCase {

  List<Character> listCharacters(int page);
}
