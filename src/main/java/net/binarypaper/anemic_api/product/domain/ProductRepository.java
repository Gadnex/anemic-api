package net.binarypaper.anemic_api.product.domain;

import java.util.List;
import java.util.UUID;
import net.binarypaper.anemic_api.product.ListProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

  List<ListProductResponse> findByOrderByName();
}
