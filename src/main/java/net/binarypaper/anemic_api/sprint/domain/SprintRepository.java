package net.binarypaper.anemic_api.sprint.domain;

import java.util.List;
import java.util.UUID;
import net.binarypaper.anemic_api.sprint.ListSprintResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, UUID> {

  List<ListSprintResponse> findByOrderByName();
}
