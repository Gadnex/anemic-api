package net.binarypaper.anemic_api.backlog_item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.util.Assert;

import java.util.UUID;

public record BacklogItemCreateRequest(
        @NotNull
        UUID productId,
        @NotNull
        @Size(min = 3, max = 100)
        String name,
        @Size(min = 3, max = 255)
        String summary) {
}
