package net.binarypaper.anemic_api.sprint;

import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.time.LocalDate;

public record PlanSprintRequest(
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate) {

    public PlanSprintRequest {
        Assert.isTrue(endDate.isAfter(startDate), "endDate must be after startDate");
    }
}
