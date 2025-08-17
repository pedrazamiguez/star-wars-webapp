package es.pedrazamiguez.starwarswebapp.config;

import es.pedrazamiguez.starwarswebapp.domain.service.client.CharacterClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "swapi.load-data-on-startup", havingValue = "true")
public class DataPreloader implements ApplicationRunner {

  private static final Logger log = LoggerFactory.getLogger(DataPreloader.class);

  private final CharacterClientService characterClientService;

  public DataPreloader(final CharacterClientService characterClientService) {
    this.characterClientService = characterClientService;
  }

  @Override
  @Async
  public void run(final ApplicationArguments args) throws Exception {
    log.info("Preloading data asynchronously on startup...");
    try {
      this.characterClientService.fetchAllCharacters();
      log.info("Successfully preloaded all characters from SWAPI.");
    } catch (final Exception e) {
      log.error("Could not process data preloading: {}", e.getMessage(), e);
    }
  }
}
