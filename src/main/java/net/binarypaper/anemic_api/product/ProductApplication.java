package net.binarypaper.anemic_api.product;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import net.binarypaper.anemic_api.product.domain.Product;
import net.binarypaper.anemic_api.product.domain.ProductRepository;
import org.hibernate.StaleObjectStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Retryable(retryFor = {StaleObjectStateException.class})
@AllArgsConstructor
public class ProductApplication {

  private final ProductRepository productRepository;

  @Transactional
  public ReadProductResponse createProduct(@Valid CreateProductRequest createProductRequest) {
    Product product = new Product(createProductRequest);
    productRepository.save(product);
    return product.toProductReadResponse();
  }

  public List<ListProductResponse> getAllProducts() {
    return productRepository.findBy(ListProductResponse.class);
  }

  public ReadProductResponse getProduct(@Valid UUID productId) {
    Product product = getProductById(productId);
    return product.toProductReadResponse();
  }

  @Transactional
  public ReadProductResponse updateProduct(
      @Valid UUID productId, @Valid UpdateProductRequest updateProductRequest) {
    Product product = getProductById(productId);
    product.updateProduct(updateProductRequest);
    productRepository.save(product);
    return product.toProductReadResponse();
  }

  @Transactional
  public void deleteProduct(@Valid UUID productId) {
    Product product = getProductById(productId);
    productRepository.delete(product);
  }

  private Product getProductById(@Valid UUID productId) {
    return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
  }
}
