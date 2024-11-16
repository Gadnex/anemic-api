package net.binarypaper.anemic_api.backlog.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreateBacklogItemRequest(
    @NotNull UUID productId,
    @NotNull @Size(min = 3, max = 100) String name,
    @Size(min = 3, max = 255) String summary) {}
