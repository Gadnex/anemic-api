package net.binarypaper.anemic_api.backlog.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogItemRepository extends JpaRepository<BacklogItem, UUID> {

  <T> List<T> findBy(Class<T> type);
}
