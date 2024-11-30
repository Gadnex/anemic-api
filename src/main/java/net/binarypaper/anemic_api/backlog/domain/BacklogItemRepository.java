package net.binarypaper.anemic_api.backlog.domain;

import java.util.List;
import java.util.UUID;
import net.binarypaper.anemic_api.backlog.ListBacklogItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogItemRepository extends JpaRepository<BacklogItem, UUID> {

  List<ListBacklogItemResponse> findByOrderByName();
}
