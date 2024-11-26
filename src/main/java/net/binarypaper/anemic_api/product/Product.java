package net.binarypaper.anemic_api.product;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonView({Views.List.class, Views.Read.class})
  @Schema(
      description = "The unique identifier of the product",
      example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
  private UUID productId;

  @Version
  @JsonView({Views.Read.class, Views.Update.class})
  @Schema(
      description = "The version of the product used for concurrency purposes when updating",
      example = "0")
  private Integer version;

  @Size(min = 3, message = "Product name must be at least {min} characters long")
  @JsonView({Views.List.class, Views.Read.class, Views.Create.class, Views.Update.class})
  @Schema(description = "The display name of the product", example = "Product 1")
  private String name;

  @Lob
  @JsonView({Views.Read.class, Views.Create.class, Views.Update.class})
  @Schema(description = "The long description of the product", example = "Product 1 description")
  private String description;

  public interface Views {
    public interface List {}

    public interface Read {}

    public interface Create {}

    public interface Update {}
  }
}
