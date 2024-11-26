package net.binarypaper.anemic_api.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class HttpMethodSerializer extends JsonSerializer<HttpMethod> {
  @Override
  public void serialize(HttpMethod value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(value.name());
  }
}
