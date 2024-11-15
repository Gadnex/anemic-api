package net.binarypaper.anemic_api.product.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import net.binarypaper.anemic_api.product.ProductCreateRequest;
import net.binarypaper.anemic_api.product.ProductReadResponse;
import net.binarypaper.anemic_api.product.ProductUpdateRequest;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    @Version
    private Integer version;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @Lob
    private String description;

    public Product(ProductCreateRequest productCreateRequest) {
        name = productCreateRequest.name();
        description = productCreateRequest.description();
    }

    public ProductReadResponse toProductReadResponse() {
        return new ProductReadResponse(productId, name, description);
    }

    public void updateProduct(ProductUpdateRequest productUpdateRequest) {
        name = productUpdateRequest.name();
        description = productUpdateRequest.description();
    }
}
