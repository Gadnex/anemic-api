package net.binarypaper.anemic_api.backlog_item;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogItemRepository extends JpaRepository<BacklogItem, UUID> {}
