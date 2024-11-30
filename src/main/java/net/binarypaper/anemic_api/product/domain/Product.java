package net.binarypaper.anemic_api.product.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.binarypaper.anemic_api.product.CreateProductRequest;
import net.binarypaper.anemic_api.product.ReadProductResponse;
import net.binarypaper.anemic_api.product.UpdateProductRequest;

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
