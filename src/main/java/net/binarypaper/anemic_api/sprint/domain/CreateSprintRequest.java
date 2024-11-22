package net.binarypaper.anemic_api.sprint.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreateSprintRequest(
    @NotNull UUID productId, @NotNull @Size(min = 3, max = 100) String name) {}
