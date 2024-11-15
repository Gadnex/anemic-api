package net.binarypaper.anemic_api.sprint;

import java.util.UUID;

public record SprintListResponse(
        UUID sprintId,
        String name) {
}
