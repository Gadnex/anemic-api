package net.binarypaper.anemic_api.product;

import jakarta.validation.Valid;
import java.util.List;
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
public class ProductService {

  private final ProductRepository productRepository;

  @Transactional
  public ProductReadResponse createProduct(@Valid ProductCreateRequest productCreateRequest) {
    Product product = new Product(productCreateRequest);
    productRepository.save(product);
    return product.toProductReadResponse();
  }

  public List<ProductListResponse> getAllProducts() {
    return productRepository.findBy(ProductListResponse.class);
  }

  public ProductReadResponse getProduct(@Valid ProductId productId) {
    Product product = getProductById(productId);
    return product.toProductReadResponse();
  }

  @Transactional
  public ProductReadResponse updateProduct(
      @Valid ProductId productId, @Valid ProductUpdateRequest productUpdateRequest) {
    Product product = getProductById(productId);
    product.updateProduct(productUpdateRequest);
    productRepository.save(product);
    return product.toProductReadResponse();
  }

  @Transactional
  public void deleteProduct(@Valid ProductId productId) {
    Product product = getProductById(productId);
    productRepository.delete(product);
  }

  private Product getProductById(@Valid ProductId productId) {
    return productRepository
        .findById(productId.productId())
        .orElseThrow(ProductNotFoundException::new);
  }
}
