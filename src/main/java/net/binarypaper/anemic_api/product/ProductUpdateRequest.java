package net.binarypaper.anemic_api.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductUpdateRequest(
        @NotNull
        @Size(min = 3, max = 100)
        String name,
        String description) {
}
