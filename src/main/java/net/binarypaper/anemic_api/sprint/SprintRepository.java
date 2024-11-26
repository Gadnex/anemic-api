package net.binarypaper.anemic_api.sprint;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, UUID> {}
