package net.binarypaper.anemic_api.product;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProductId(@NotNull UUID productId) {
}
