package co.uk.leaseloco.leasingnormaliser.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig {

  /**
   * This method creates an instance of the OpenAPI class, which is a model that represents an OpenAPI document.
   * The method sets the title, description, version, and license properties of the Info object, and the description and url properties of the ExternalDocumentation object.
   * @return an instance of the OpenAPI class
   */
  @Bean
  public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("LeaseLoco Project API")
            .description("Exposing LeaseLoco REST methods")
            .version("v1")
            .license(new License()
                .name("Apache 2.0")
                .url("http://springdoc.org")))
        .externalDocs(new ExternalDocumentation()
            .description("LeaseLoco Project API")
            .url("https://www.leaseloco.com/"));
  }
}
