package net.binarypaper.anemic_api.sprint.domain;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.util.Assert;

public record PlanSprintRequest(@NotNull LocalDate startDate, @NotNull LocalDate endDate) {

  public PlanSprintRequest {
    Assert.isTrue(endDate.isAfter(startDate), "endDate must be after startDate");
  }
}
