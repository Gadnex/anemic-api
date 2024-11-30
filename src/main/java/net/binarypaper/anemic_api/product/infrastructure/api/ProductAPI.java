package net.binarypaper.anemic_api.product.infrastructure.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.product.*;
import net.binarypaper.anemic_api.product.CreateProductRequest;
import net.binarypaper.anemic_api.product.ListProductResponse;
import net.binarypaper.anemic_api.product.ReadProductResponse;
import net.binarypaper.anemic_api.product.UpdateProductRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/products")
@CrossOrigin(
    origins = {"${application.cors.origins}"},
    exposedHeaders = HttpHeaders.LOCATION)
@Tag(name = "Product API", description = "Manage products")
public class ProductAPI {

  private final ProductApplication productApplication;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
  @Operation(
      summary = "Create new product",
      description =
          """
            <b>As a</b> scrum master<br>
            <b>I want to</b> create a new product<br>
            <b>so that</b> my team can work on the product.
            """)
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Product created",
        headers = {
          @Header(
              name = HttpHeaders.LOCATION,
              description = "The URL of the new product",
              required = true)
        }),
    @ApiResponse(responseCode = "400", description = "Invalid product details", content = @Content)
  })
  public ResponseEntity<ReadProductResponse> createProduct(
      @RequestBody @Valid CreateProductRequest createProductRequest) {
    ReadProductResponse productResponse = productApplication.createProduct(createProductRequest);
    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productResponse.productId())
                .toUri())
        .body(productResponse);
  }

  @GetMapping
  @Operation(
      summary = "Get all products",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view a list of all products<br>
            <b>so that</b> I can select the product I am working on.
            """)
  @ApiResponses({@ApiResponse(responseCode = "200", description = "List of products returned")})
  public List<ListProductResponse> getAllProducts() {
    return productApplication.getAllProducts();
  }

  @GetMapping("{product-id}")
  @Operation(
      summary = "Get product details",
      description =
          """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view the details of a specific product<br>
            <b>so that</b> I can work on the product.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Product returned"),
    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
  })
  public ReadProductResponse getProduct(@PathVariable(name = "product-id") UUID productId) {
    return productApplication.getProduct(productId);
  }

  @PutMapping("{product-id}")
  @Operation(
      summary = "Update an existing product",
      description =
          """
            <b>As a</b> scrum master<br>
            <b>I want to</b> update an existing product<br>
            <b>so that</b> my team can work on the product.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Product updated"),
    @ApiResponse(responseCode = "400", description = "Invalid product details", content = @Content),
    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
  })
  public ReadProductResponse updateProduct(
      @PathVariable(name = "product-id") UUID productId,
      @RequestBody @Valid UpdateProductRequest updateProductRequest) {
    return productApplication.updateProduct(productId, updateProductRequest);
  }

  @DeleteMapping("{product-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Delete product",
      description =
          """
            <b>As a</b> scrum master<br>
            <b>I want to</b> delete a product<br>
            <b>so that</b> so that teams can no longer work on the product.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Product deleted"),
    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
  })
  public void deleteProduct(@PathVariable(name = "product-id") UUID productId) {
    productApplication.deleteProduct(productId);
  }
}
