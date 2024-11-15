package net.binarypaper.anemic_api.backlog_item.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BacklogItemRepository extends JpaRepository<BacklogItem, UUID> {

    <T> List<T> findBy(Class<T> type);
}
