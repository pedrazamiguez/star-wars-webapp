package es.pedrazamiguez.starwarswebapp.presentation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateFormatConfig {

  @Value("${date.format.display}")
  private String displayFormat;

  @Bean("dateFormatDisplay")
  public String dateFormatDisplay() {
    return this.displayFormat;
  }
}
