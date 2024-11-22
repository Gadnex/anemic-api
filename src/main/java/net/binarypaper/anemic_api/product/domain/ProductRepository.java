package net.binarypaper.anemic_api.product.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

  <T> List<T> findBy(Class<T> type);
}