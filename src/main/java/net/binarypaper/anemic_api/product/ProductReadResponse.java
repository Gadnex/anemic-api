package net.binarypaper.anemic_api.product;

import java.util.UUID;

public record ProductReadResponse(UUID productId, String name, String description) {}
