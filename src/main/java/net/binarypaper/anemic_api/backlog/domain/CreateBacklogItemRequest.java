package net.binarypaper.anemic_api.backlog.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreateBacklogItemRequest(
    @NotNull @JsonProperty(value = "product-id") UUID productId,
    @NotNull @Size(min = 3, max = 100) String name,
    @Size(min = 3, max = 255) String summary) {}
