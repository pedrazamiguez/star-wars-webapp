package es.pedrazamiguez.starwarswebapp.presentation.controller;

import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

public abstract class AbstractControllerTest {

  protected ThymeleafViewResolver getThymeleafViewResolver() {
    final ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
    thymeleafViewResolver.setTemplateEngine(this.getTemplateEngine());
    thymeleafViewResolver.setCharacterEncoding("UTF-8");
    thymeleafViewResolver.setOrder(1);
    return thymeleafViewResolver;
  }

  private ISpringTemplateEngine getTemplateEngine() {
    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(this.getTemplateResolver());
    return templateEngine;
  }

  private ITemplateResolver getTemplateResolver() {
    final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    return templateResolver;
  }
}
