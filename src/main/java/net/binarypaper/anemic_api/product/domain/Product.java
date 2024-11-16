package net.binarypaper.anemic_api.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID productId;

  @Version private Integer version;

  @NotNull
  @Size(min = 3, max = 100)
  private String name;

  @Lob private String description;

  public Product(CreateProductRequest createProductRequest) {
    name = createProductRequest.name();
    description = createProductRequest.description();
  }

  public ReadProductResponse toProductReadResponse() {
    return new ReadProductResponse(productId, name, description);
  }

  public void updateProduct(UpdateProductRequest updateProductRequest) {
    name = updateProductRequest.name();
    description = updateProductRequest.description();
  }
}
