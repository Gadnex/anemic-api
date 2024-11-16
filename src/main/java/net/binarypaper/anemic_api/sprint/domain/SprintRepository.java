package net.binarypaper.anemic_api.sprint.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, UUID> {

  <T> List<T> findBy(Class<T> type);
}
