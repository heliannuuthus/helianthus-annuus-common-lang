package io.ghcr.heliannuuthus;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OpenapiConfiguration {

  @Value("${spring.application.name}")
  private String application;

  @Value("${server.port}")
  private String port;

  @Value("${server.address:localhost}")
  private String serverAddress;

  private License license() {
    return new License().name("MIT").url("https://opensource.org/licenses/MIT");
  }

  private Info info() {
    return new Info().title(application).version("v1.0.0").license(license());
  }

  private ExternalDocumentation externalDocumentation() {
    return new ExternalDocumentation()
        .description("heliannuuthus")
        .url("https://heliannuuthus.github.io/");
  }

  @Bean
  public OpenAPI springShopOpenAPI() {
    log.info("openapi: http://{}:{}/redoc.html", serverAddress, port);
    return new OpenAPI().info(info()).externalDocs(externalDocumentation());
  }
}
