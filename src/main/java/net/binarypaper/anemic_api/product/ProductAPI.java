package net.binarypaper.anemic_api.product;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping(path = "products", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = { "${application.cors.origins}" })
@Tag(name = "Product API", description = "Manage products")
public interface ProductAPI {

    @PostMapping
    @JsonView(Product.Views.Read.class)
    @Operation(summary = "Create new product", description = """
            <b>As a</b> scrum master<br>
            <b>I want to</b> create a new product<br>
            <b>so that</b> my team can work on the product.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid product details", content = @Content)
    })
    Product createProduct(@RequestBody @JsonView(Product.Views.Create.class) @Valid Product product);

    @GetMapping
    @JsonView(Product.Views.List.class)
    @Operation(summary = "Get all products", description = """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view a list of all products<br>
            <b>so that</b> I can select the product I am working on.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products returned")
    })
    List<Product> getAllProducts();

    @GetMapping("{product-id}")
    @JsonView(Product.Views.Read.class)
    @Operation(summary = "Get product details", description = """
            <b>As a</b> scrum team member<br>
            <b>I want to</b> view the details of a specific product<br>
            <b>so that</b> I can work on the product.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product returned"),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    Product getProduct(
            @PathVariable(name = "product-id") UUID productId);

    @PutMapping("{product-id}")
    @JsonView(Product.Views.Read.class)
    @Operation(summary = "Update an existing product", description = """
            <b>As a</b> scrum master<br>
            <b>I want to</b> update an existing product<br>
            <b>so that</b> my team can work on the product.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Invalid product details", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    Product updateProduct(
            @PathVariable(name = "product-id") UUID productId,
            @RequestBody @JsonView(Product.Views.Update.class) @Valid Product product);

    @DeleteMapping("{product-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete product", description = """
            <b>As a</b> scrum master<br>
            <b>I want to</b> delete a product<br>
            <b>so that</b> so that teams can no longer work on the product.
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    void deleteProduct(
            @PathVariable(name = "product-id") UUID productId);

}