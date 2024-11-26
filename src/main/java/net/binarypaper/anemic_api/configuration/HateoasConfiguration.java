package net.binarypaper.anemic_api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL_FORMS)
public class HateoasConfiguration {

  @Bean
  public ObjectMapper objectMapper(
      Jackson2ObjectMapperBuilder builder, HttpMethodSerializer httpMethodSerializer) {
    ObjectMapper objectMapper = builder.build();
    SimpleModule module = new SimpleModule();
    module.addSerializer(HttpMethod.class, httpMethodSerializer);
    objectMapper.registerModule(module);
    return objectMapper;
  }
}
