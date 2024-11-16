package net.binarypaper.anemic_api.sprint;

import java.util.UUID;
import org.springframework.util.Assert;

public record SprintId(UUID sprintId) {

  public SprintId {
    Assert.notNull(sprintId, "sprintId must not be null");
  }
}
