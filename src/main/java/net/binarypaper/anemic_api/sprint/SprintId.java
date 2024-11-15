package net.binarypaper.anemic_api.sprint;

import org.springframework.util.Assert;

import java.util.UUID;

public record SprintId(UUID sprintId) {

    public SprintId {
        Assert.notNull(sprintId, "sprintId must not be null");
    }
}
