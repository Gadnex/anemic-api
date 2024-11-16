package net.binarypaper.anemic_api.product.infrastructure.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.product.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(
    path = "products",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = {"${application.cors.origins}"})
@Tag(name = "Product API", description = "Manage products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  @Operation(
      summary = "Create new product",
      description =
          """
            <b>As a</b> scrum master<br>
            <b>I want to</b> create a new product<br>
            <b>so that</b> my team can work on the product.
            """)
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Product created"),
    @ApiResponse(responseCode = "400", description = "Invalid product details", content = @Content)
  })
  ProductReadResponse createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
    return productService.createProduct(productCreateRequest);
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
  List<ProductListResponse> getAllProducts() {
    return productService.getAllProducts();
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
  ProductReadResponse getProduct(@PathVariable(name = "product-id") UUID productId) {
    return productService.getProduct(new ProductId(productId));
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
  ProductReadResponse updateProduct(
      @PathVariable(name = "product-id") UUID productId,
      @RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
    return productService.updateProduct(new ProductId(productId), productUpdateRequest);
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
  void deleteProduct(@PathVariable(name = "product-id") UUID productId) {
    productService.deleteProduct(new ProductId(productId));
  }
}
