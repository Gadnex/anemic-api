package net.binarypaper.anemic_api.sprint;

import java.time.LocalDate;
import java.util.UUID;

public record SprintReadResponse(
        UUID sprintId,
        UUID productId,
        String name,
        LocalDate startDate,
        LocalDate endDate) {
}
