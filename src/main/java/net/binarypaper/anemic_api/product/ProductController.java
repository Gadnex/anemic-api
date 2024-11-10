package net.binarypaper.anemic_api.product;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProductController implements ProductAPI {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Product updateProduct(UUID productId, Product product) {
        Product productFromBD = getProduct(productId);
        if (!productFromBD.getVersion().equals(product.getVersion())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The product version is out of date. Please get the product details again before updating.");
        }
        product.setProductId(productId);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID productId) {
        Product product = getProduct(productId);
        productRepository.delete(product);
    }
}
